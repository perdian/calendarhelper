package de.perdian.apps.calendarhelper.services.google.users;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import de.perdian.apps.calendarhelper.services.google.GoogleApiCredentials;
import de.perdian.apps.calendarhelper.services.google.GoogleApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
class GoogleUserServiceImpl implements GoogleUserService {

    private static final Logger log = LoggerFactory.getLogger(GoogleUserServiceImpl.class);
    private GoogleApiCredentials googleApplicationCredentials = null;
    private GoogleRefreshTokenStore googleRefreshTokenStore = null;

    @Override
    public GoogleUser lookupUser() throws GoogleApiException {

        GoogleRefreshToken refreshToken = this.getGoogleRefreshTokenStore().loadRefreshToken();
        if (refreshToken != null) {
            try {
                GoogleRefreshTokenToUserFunction googleUserFromRefreshTokenFunction = new GoogleRefreshTokenToUserFunction(this.getGoogleApplicationCredentials());
                return googleUserFromRefreshTokenFunction.apply(refreshToken);
            } catch (Exception e) {
                log.warn("Cannot lookup user by existing Google refresh token", e);
            }
        }

        return this.forceLoginUser();

    }

    @Override
    public GoogleUser forceLoginUser() throws GoogleApiException {
        try {

            NetHttpTransport googleHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory googleJsonFactory = GsonFactory.getDefaultInstance();

            return CompletableFuture.supplyAsync(new GoogleAuthorizationCodeSupplier(this.getGoogleApplicationCredentials(), googleHttpTransport, googleJsonFactory))
                    .thenApply(new GoogleAuthorizationCodeToRefreshTokenFunction(this.getGoogleRefreshTokenStore()))
                    .thenApply(new GoogleRefreshTokenToUserFunction(this.getGoogleApplicationCredentials()))
                    .get();

        } catch (GoogleApiException e) {
            throw e;
        } catch (Exception e) {
            throw new GoogleApiException("Cannot perform login at Google", e);
        }

    }

    @Override
    public void logoutUser() {
        this.getGoogleRefreshTokenStore().updateRefreshToken(null);
    }

    GoogleApiCredentials getGoogleApplicationCredentials() {
        return googleApplicationCredentials;
    }
    @Autowired
    void setGoogleApplicationCredentials(GoogleApiCredentials googleApplicationCredentials) {
        this.googleApplicationCredentials = googleApplicationCredentials;
    }

    GoogleRefreshTokenStore getGoogleRefreshTokenStore() {
        return googleRefreshTokenStore;
    }
    @Autowired
    void setGoogleRefreshTokenStore(GoogleRefreshTokenStore googleRefreshTokenStore) {
        this.googleRefreshTokenStore = googleRefreshTokenStore;
    }

}
