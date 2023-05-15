package de.perdian.apps.calendarhelper.services.google.users;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import de.perdian.apps.calendarhelper.services.google.GoogleApiException;
import de.perdian.apps.calendarhelper.services.google.application.GoogleApplicationCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

class GoogleAuthorizationCodeSupplier implements Supplier<GoogleAuthorizationCode> {

    private static final Logger log = LoggerFactory.getLogger(GoogleAuthorizationCodeSupplier.class);

    private GoogleApplicationCredentials googleApplicationCredentials = null;
    private NetHttpTransport googleHttpTransport = null;
    private JsonFactory googleJsonFactory = null;

    GoogleAuthorizationCodeSupplier(GoogleApplicationCredentials googleApplicationCredentials, NetHttpTransport googleHttpTransport, JsonFactory googleJsonFactory) {
        this.setGoogleApplicationCredentials(googleApplicationCredentials);
        this.setGoogleHttpTransport(googleHttpTransport);
        this.setGoogleJsonFactory(googleJsonFactory);
    }

    @Override
    public GoogleAuthorizationCode get() {

        log.debug("Performing login to retrieve Google authorization code");
        String googleClientId = this.getGoogleApplicationCredentials().getClientId();
        String googleClientSecret = this.getGoogleApplicationCredentials().getClientSecret();
        List<String> googleScopes = this.getGoogleApplicationCredentials().getScopes();
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

    private GoogleApplicationCredentials getGoogleApplicationCredentials() {
        return googleApplicationCredentials;
    }
    private void setGoogleApplicationCredentials(GoogleApplicationCredentials googleApplicationCredentials) {
        this.googleApplicationCredentials = googleApplicationCredentials;
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
