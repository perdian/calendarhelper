package de.perdian.apps.calendarbuddy.modules.google.user;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.TokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.UserCredentials;
import de.perdian.apps.calendarbuddy.modules.google.GoogleConstants;
import de.perdian.apps.calendarbuddy.modules.google.GoogleException;
import de.perdian.apps.calendarbuddy.support.storage.StorageService;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
class GoogleUserServiceImpl implements GoogleUserService {

    private static final Logger log = LoggerFactory.getLogger(GoogleUserServiceImpl.class);

    private StorageService storageService = null;
    private NetHttpTransport httpTransport = null;
    private JsonFactory jsonFactory = null;

    GoogleUserServiceImpl() throws Exception {
        this.setHttpTransport(GoogleNetHttpTransport.newTrustedTransport());
        this.setJsonFactory(GsonFactory.getDefaultInstance());
    }

    @Override
    public GoogleUser lookupUser(String apiClientId, String apiClientSecret) throws GoogleException {
        StringProperty refreshTokenProperty = this.getStorageService().getPersistentStringProperty("GoogleUserService.refreshToken");
        String refreshToken = refreshTokenProperty.getValue();
        if (StringUtils.isNotEmpty(refreshToken)) {
            try {
                GoogleUser userFromRefreshToken = this.lookupUserFromRefreshToken(refreshToken, apiClientId, apiClientSecret);
                if (userFromRefreshToken != null) {
                    return userFromRefreshToken;
                }
            } catch (Exception e) {
                log.warn("Cannot lookup user by existing Google refresh token", e);
            }
        }
        return null;
    }

    @Override
    public GoogleUser ensureUser(String apiClientId, String apiClientSecret) throws GoogleException {
        GoogleUser googleUser = this.lookupUser(apiClientId, apiClientSecret);
        if (googleUser != null) {
            return googleUser;
        } else {
            return this.loginNewUser(apiClientId, apiClientSecret);
        }
    }

    @Override
    public GoogleUser loginNewUser(String apiClientId, String apiClientSecret) throws GoogleException {
        try {

            return CompletableFuture.supplyAsync(() -> this.createAuthorizationCode(apiClientId, apiClientSecret))
                .thenApply(authorizationCode -> this.createRefreshTokenFromAuthorizationCode(authorizationCode))
                .thenApply(refreshToken -> this.lookupUserFromRefreshToken(refreshToken, apiClientId, apiClientSecret))
                .get();

        } catch (GoogleException e) {
            throw e;
        } catch (Exception e) {
            throw new GoogleException("Cannot perform login at Google", e);
        }

    }

    @Override
    public void logoutUser(GoogleUser user) {
        if (user != null) {
            this.getStorageService().getPersistentStringProperty("GoogleUserService.refreshToken").setValue(null);
        }
    }

    private GoogleUser lookupUserFromRefreshToken(String refreshToken, String apiClientId, String apiClientSecret) {
        try {

            UserCredentials userCredentials = UserCredentials.newBuilder()
                .setClientId(apiClientId)
                .setClientSecret(apiClientSecret)
                .setAccessToken(new AccessToken("", new Date()))
                .setRefreshToken(refreshToken)
                .build();

            Oauth2 oauth2Service = new Oauth2.Builder(this.getHttpTransport(), this.getJsonFactory(), new HttpCredentialsAdapter(userCredentials))
                .setApplicationName("Calendar Helper")
                .build();

            Userinfo userInfo = oauth2Service.userinfo().get().execute();

            GoogleUser googleUser = new GoogleUser(userCredentials);
            googleUser.setName(userInfo.getName());
            googleUser.setEmailAddress(userInfo.getEmail());
            return googleUser;

        } catch (Exception e) {
            throw new GoogleException("Cannot load Google user", e);
        }
    }

    private GoogleAuthorizationCode createAuthorizationCode(String apiClientId, String apiClientSecret) {

        log.debug("Performing login to retrieve Google authorization code");
        String googleClientId = Objects.requireNonNull(apiClientId, "No Google Client ID available");
        String googleClientSecret = Objects.requireNonNull(apiClientSecret, "No Google Client Secret available");
        List<String> googleScopes = GoogleConstants.SCOPES;

        AuthorizationCodeFlow authorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(this.getHttpTransport(), this.getJsonFactory(), googleClientId, googleClientSecret, googleScopes)
            .setAccessType("offline")
            .setApprovalPrompt("force")
            .build();

        CompletableFuture<String> authorizationCodeFuture = new CompletableFuture<>();
        try (GoogleAuthorizationCodeCallbackServer callbackServer = new GoogleAuthorizationCodeCallbackServer(authorizationCodeFuture)) {
            String callbackUrl = callbackServer.createCallbackUrl();
            String loginUrl = authorizationCodeFlow.newAuthorizationUrl().setRedirectUri(callbackUrl).build();
            try (GoogleAuthorizationCodeUserDialog userDialog = new GoogleAuthorizationCodeUserDialog(loginUrl, authorizationCodeFuture)) {
                String authorizationCode = authorizationCodeFuture.get();
                log.debug("Retrieved Google authorization code: {}", authorizationCode);
                return new GoogleAuthorizationCode(authorizationCodeFlow, callbackUrl, authorizationCode);
            }
        } catch (Exception e) {
            throw new GoogleException("Cannot request authorization code from Google", e);
        }

    }

    private String createRefreshTokenFromAuthorizationCode(GoogleAuthorizationCode authorizationCode) {
        try {

            TokenRequest tokenRequest = authorizationCode.getFlow()
                .newTokenRequest(authorizationCode.getValue())
                .setRedirectUri(authorizationCode.getCallbackUrl());

            TokenResponse tokenResponse = tokenRequest.execute();
            this.getStorageService().getPersistentStringProperty("GoogleUserService.refreshToken").setValue(tokenResponse.getRefreshToken());
            return tokenResponse.getRefreshToken();

        } catch (IOException e) {
            throw new GoogleException("Cannot request refresh token from Google", e);
        }
    }

    StorageService getStorageService() {
        return this.storageService;
    }
    @Autowired
    void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    private NetHttpTransport getHttpTransport() {
        return this.httpTransport;
    }
    private void setHttpTransport(NetHttpTransport httpTransport) {
        this.httpTransport = httpTransport;
    }

    private JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
    private void setJsonFactory(JsonFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
    }

}
