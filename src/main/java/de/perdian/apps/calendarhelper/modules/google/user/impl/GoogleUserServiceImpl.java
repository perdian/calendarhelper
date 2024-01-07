package de.perdian.apps.calendarhelper.modules.google.user.impl;

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
import de.perdian.apps.calendarhelper.modules.google.GoogleApiException;
import de.perdian.apps.calendarhelper.modules.google.apicredentials.GoogleApiCredentials;
import de.perdian.apps.calendarhelper.modules.google.user.GoogleUser;
import de.perdian.apps.calendarhelper.modules.google.user.GoogleUserService;
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

    private GoogleRefreshTokenStore googleRefreshTokenStore = null;
    private NetHttpTransport httpTransport = null;
    private JsonFactory jsonFactory = null;

    GoogleUserServiceImpl() throws Exception {
        this.setHttpTransport(GoogleNetHttpTransport.newTrustedTransport());
        this.setJsonFactory(GsonFactory.getDefaultInstance());
    }

    @Override
    public GoogleUser lookupUser(GoogleApiCredentials apiCredentials) throws GoogleApiException {
        GoogleRefreshToken refreshToken = this.getGoogleRefreshTokenStore().loadRefreshToken();
        if (refreshToken != null) {
            try {
                GoogleUser userFromRefreshToken = this.lookupUserFromRefreshToken(refreshToken, apiCredentials);
                if (userFromRefreshToken != null) {
                    return userFromRefreshToken;
                }
            } catch (Exception e) {
                log.warn("Cannot lookup user by existing Google refresh token", e);
            }
        }
        return this.loginNewUser(apiCredentials);
    }

    @Override
    public GoogleUser loginNewUser(GoogleApiCredentials apiCredentials) throws GoogleApiException {
        try {

            NetHttpTransport googleHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory googleJsonFactory = GsonFactory.getDefaultInstance();

            return CompletableFuture.supplyAsync(() -> this.createAuthorizationCode(apiCredentials))
                .thenApply(authorizationCode -> this.createRefreshTokenFromAuthorizationCode(authorizationCode))
                .thenApply(refreshToken -> this.lookupUserFromRefreshToken(refreshToken, apiCredentials))
                .get();

        } catch (GoogleApiException e) {
            throw e;
        } catch (Exception e) {
            throw new GoogleApiException("Cannot perform login at Google", e);
        }

    }

    @Override
    public void logoutUser(GoogleUser user) {
        if (user != null) {
            this.getGoogleRefreshTokenStore().updateRefreshToken(null);
        }
    }

    private GoogleUser lookupUserFromRefreshToken(GoogleRefreshToken refreshToken, GoogleApiCredentials apiCredentials) {
        try {

            UserCredentials userCredentials = UserCredentials.newBuilder()
                .setClientId(apiCredentials.getClientId())
                .setClientSecret(apiCredentials.getClientSecret())
                .setAccessToken(new AccessToken("", new Date()))
                .setRefreshToken(refreshToken.getValue())
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
            throw new GoogleApiException("Cannot load Google user", e);
        }
    }

    private GoogleAuthorizationCode createAuthorizationCode(GoogleApiCredentials apiCredentials) {

        log.debug("Performing login to retrieve Google authorization code");
        String googleClientId = Objects.requireNonNull(apiCredentials.getClientId(), "No Google Client ID available");
        String googleClientSecret = Objects.requireNonNull(apiCredentials.getClientSecret(), "No Google Client Secret available");
        List<String> googleScopes = apiCredentials.getScopes();

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
            throw new GoogleApiException("Cannot request authorization code from Google", e);
        }

    }

    private GoogleRefreshToken createRefreshTokenFromAuthorizationCode(GoogleAuthorizationCode authorizationCode) {
        try {

            TokenRequest tokenRequest = authorizationCode.getFlow()
                .newTokenRequest(authorizationCode.getValue())
                .setRedirectUri(authorizationCode.getCallbackUrl());

            TokenResponse tokenResponse = tokenRequest.execute();

            GoogleRefreshToken token = new GoogleRefreshToken(tokenResponse.getRefreshToken());
            this.getGoogleRefreshTokenStore().updateRefreshToken(token);
            return token;

        } catch (IOException e) {
            throw new GoogleApiException("Cannot request refresh token from Google", e);
        }
    }

    GoogleRefreshTokenStore getGoogleRefreshTokenStore() {
        return this.googleRefreshTokenStore;
    }
    @Autowired
    void setGoogleRefreshTokenStore(GoogleRefreshTokenStore googleRefreshTokenStore) {
        this.googleRefreshTokenStore = googleRefreshTokenStore;
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
