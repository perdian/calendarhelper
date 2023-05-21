package de.perdian.apps.calendarhelper.support.google.users;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import de.perdian.apps.calendarhelper.support.google.GoogleApiCredentials;
import de.perdian.apps.calendarhelper.support.google.GoogleApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

class GoogleAuthorizationCodeSupplier implements Supplier<GoogleAuthorizationCode> {

    private static final Logger log = LoggerFactory.getLogger(GoogleAuthorizationCodeSupplier.class);

    private GoogleApiCredentials googleApiCredentials = null;
    private NetHttpTransport googleHttpTransport = null;
    private JsonFactory googleJsonFactory = null;

    GoogleAuthorizationCodeSupplier(GoogleApiCredentials googleApplicationCredentials, NetHttpTransport googleHttpTransport, JsonFactory googleJsonFactory) {
        this.setGoogleApiCredentials(googleApplicationCredentials);
        this.setGoogleHttpTransport(googleHttpTransport);
        this.setGoogleJsonFactory(googleJsonFactory);
    }

    @Override
    public GoogleAuthorizationCode get() {

        log.debug("Performing login to retrieve Google authorization code");
        String googleClientId = Objects.requireNonNull(this.getGoogleApiCredentials().getClientId(), "No Google Cliend ID available");
        String googleClientSecret = Objects.requireNonNull(this.getGoogleApiCredentials().getClientSecret(), "No Google Cliend Secret available");
        List<String> googleScopes = this.getGoogleApiCredentials().getScopes();
        AuthorizationCodeFlow authorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(googleHttpTransport, googleJsonFactory, googleClientId, googleClientSecret, googleScopes)
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

    private GoogleApiCredentials getGoogleApiCredentials() {
        return googleApiCredentials;
    }
    private void setGoogleApiCredentials(GoogleApiCredentials googleApiCredentials) {
        this.googleApiCredentials = googleApiCredentials;
    }

    private NetHttpTransport getGoogleHttpTransport() {
        return googleHttpTransport;
    }
    private void setGoogleHttpTransport(NetHttpTransport googleHttpTransport) {
        this.googleHttpTransport = googleHttpTransport;
    }

    private JsonFactory getGoogleJsonFactory() {
        return googleJsonFactory;
    }
    private void setGoogleJsonFactory(JsonFactory googleJsonFactory) {
        this.googleJsonFactory = googleJsonFactory;
    }

}
