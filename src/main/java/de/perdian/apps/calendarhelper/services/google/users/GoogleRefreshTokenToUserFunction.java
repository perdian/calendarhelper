package de.perdian.apps.calendarhelper.services.google.users;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Profile;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.UserCredentials;
import de.perdian.apps.calendarhelper.services.google.GoogleApiException;
import de.perdian.apps.calendarhelper.services.google.application.GoogleApplicationCredentials;

import java.util.Date;
import java.util.function.Function;

class GoogleRefreshTokenToUserFunction implements Function<GoogleRefreshToken, GoogleUser> {

    private GoogleApplicationCredentials googleApplicationCredentials = null;

    GoogleRefreshTokenToUserFunction(GoogleApplicationCredentials googleApplicationCredentials) {
        this.setGoogleApplicationCredentials(googleApplicationCredentials);
    }

    @Override
    public GoogleUser apply(GoogleRefreshToken refreshToken) {
        try {

            UserCredentials userCredentials = UserCredentials.newBuilder()
                    .setClientId(this.getGoogleApplicationCredentials().getClientId())
                    .setClientSecret(this.getGoogleApplicationCredentials().getClientSecret())
                    .setAccessToken(new AccessToken("", new Date()))
                    .setRefreshToken(refreshToken.getValue())
                    .build();

            NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

            Gmail gmailService = new Gmail.Builder(httpTransport, jsonFactory, new HttpCredentialsAdapter(userCredentials))
                    .setApplicationName("Calendar Helper")
                    .build();

            Profile gmailProfile = gmailService.users().getProfile("me").execute();

            GoogleUser googleUser = new GoogleUser(userCredentials);
            googleUser.setEmailAddress(gmailProfile.getEmailAddress());
            return googleUser;

        } catch (Exception e) {
            throw new GoogleApiException("Cannot load Google user", e);
        }
    }

    private GoogleApplicationCredentials getGoogleApplicationCredentials() {
        return googleApplicationCredentials;
    }
    private void setGoogleApplicationCredentials(GoogleApplicationCredentials googleApplicationCredentials) {
        this.googleApplicationCredentials = googleApplicationCredentials;
    }

}
