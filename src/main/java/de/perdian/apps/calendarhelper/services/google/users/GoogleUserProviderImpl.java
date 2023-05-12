package de.perdian.apps.calendarhelper.services.google.users;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import de.perdian.apps.calendarhelper.services.google.application.GoogleApplicationCredentials;
import de.perdian.apps.calendarhelper.services.google.users.login.LoginCallbackServer;
import de.perdian.apps.calendarhelper.services.google.users.login.LoginPane;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
class GoogleUserProviderImpl implements GoogleUserProvider {

    private static final List<String> GOOGLE_SCOPES = List.of("email", "https://mail.google.com/");

    private GoogleApplicationCredentials googleApplicationCredentials = null;
    private GoogleUser currentUser = null;

    @Override
    public GoogleUser lookupValidatedUser() throws GoogleUserException {
        GoogleUser currentUser = this.getCurrentUser();
        if (currentUser == null) {
            currentUser = this.loginUser();
            this.setCurrentUser(currentUser);
        }
        return currentUser;
    }

    private GoogleUser loginUser() throws GoogleUserException {
        try {

            NetHttpTransport googleHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory googleJsonFactory = GsonFactory.getDefaultInstance();

            String googleClientId = this.getGoogleApplicationCredentials().getClientId();
            String googleClientSecret = this.getGoogleApplicationCredentials().getClientSecret();
            AuthorizationCodeFlow authorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(googleHttpTransport, googleJsonFactory, googleClientId, googleClientSecret, GOOGLE_SCOPES)
                    .setAccessType("offline")
                    .setApprovalPrompt("force")
                    .build();

            CompletableFuture<GoogleUser> userFuture = new CompletableFuture<>();
            try (LoginCallbackServer callbackServer = LoginCallbackServer.createServer(userFuture)) {

                String loginUrl = authorizationCodeFlow.newAuthorizationUrl().setRedirectUri(callbackServer.createCallbackUrl()).build();
                LoginPane loginPane = new LoginPane(loginUrl);

                Platform.runLater(() -> {
                    Scene loginScene = new Scene(loginPane, 800, 850);
                    Stage loginStage = new Stage();
                    loginStage.initModality(Modality.APPLICATION_MODAL);
                    loginStage.setTitle("Login Google User");
                    loginStage.setMinWidth(640);
                    loginStage.setMinHeight(480);
                    loginStage.setScene(loginScene);
                    loginStage.centerOnScreen();
                    loginStage.show();
                });

                // Now we wait until the callback server has retrieved the callback
                // and the user can be successfully built
                return userFuture.get();

            }
        } catch (Exception e) {
            throw new GoogleUserException("Cannot perform login at Google", e);
        }

    }

    @Override
    public GoogleUser lookupUser() {
        return this.getCurrentUser();
    }

    private GoogleUser getCurrentUser() {
        return currentUser;
    }
    private void setCurrentUser(GoogleUser currentUser) {
        this.currentUser = currentUser;
    }

    GoogleApplicationCredentials getGoogleApplicationCredentials() {
        return googleApplicationCredentials;
    }
    @Autowired
    void setGoogleApplicationCredentials(GoogleApplicationCredentials googleApplicationCredentials) {
        this.googleApplicationCredentials = googleApplicationCredentials;
    }

}
