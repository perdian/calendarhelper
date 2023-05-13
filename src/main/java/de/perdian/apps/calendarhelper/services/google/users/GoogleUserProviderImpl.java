package de.perdian.apps.calendarhelper.services.google.users;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import de.perdian.apps.calendarhelper.services.google.application.GoogleApplicationCredentials;
import de.perdian.apps.calendarhelper.services.google.users.support.GoogleAuthorizationCodeSupplier;
import de.perdian.apps.calendarhelper.services.google.users.support.GoogleRefreshTokenFromAuthorizationCodeFunction;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
class GoogleUserProviderImpl implements GoogleUserProvider {

    private static final Logger log = LoggerFactory.getLogger(GoogleUserProviderImpl.class);
    private GoogleApplicationCredentials googleApplicationCredentials = null;
    private GoogleUser currentUser = null;
    private Stage loginStage = null;

    @Override
    public GoogleUser lookupUser() throws GoogleUserException {
        GoogleUser currentUser = this.getCurrentUser();
        if (currentUser == null) {
            currentUser = this.forceLoginUser();
            this.setCurrentUser(currentUser);
        }
        return currentUser;
    }

    @Override
    public GoogleUser forceLoginUser() throws GoogleUserException {
        try {

            NetHttpTransport googleHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory googleJsonFactory = GsonFactory.getDefaultInstance();

            CompletableFuture.supplyAsync(new GoogleAuthorizationCodeSupplier(this.getGoogleApplicationCredentials(), googleHttpTransport, googleJsonFactory))
                    .thenApply(new GoogleRefreshTokenFromAuthorizationCodeFunction())
                    .get();

            System.err.println("USER LOGIN COMPLETED");

            return null;

        } catch (GoogleUserException e) {
            throw e;
        } catch (Exception e) {
            throw new GoogleUserException("Cannot perform login at Google", e);
        }

    }

    private GoogleUser getCurrentUser() {
        return currentUser;
    }
    private void setCurrentUser(GoogleUser currentUser) {
        this.currentUser = currentUser;
    }

    private Stage getLoginStage() {
        return loginStage;
    }
    private void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    GoogleApplicationCredentials getGoogleApplicationCredentials() {
        return googleApplicationCredentials;
    }
    @Autowired
    void setGoogleApplicationCredentials(GoogleApplicationCredentials googleApplicationCredentials) {
        this.googleApplicationCredentials = googleApplicationCredentials;
    }

}
