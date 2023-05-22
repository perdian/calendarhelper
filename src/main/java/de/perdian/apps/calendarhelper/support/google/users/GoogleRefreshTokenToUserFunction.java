package de.perdian.apps.calendarhelper.support.google.users;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.UserCredentials;
import de.perdian.apps.calendarhelper.support.google.GoogleApiCredentials;
import de.perdian.apps.calendarhelper.support.google.GoogleApiException;

import java.util.Date;
import java.util.function.Function;

class GoogleRefreshTokenToUserFunction implements Function<GoogleRefreshToken, GoogleUser> {

    private GoogleApiCredentials googleApplicationCredentials = null;

    GoogleRefreshTokenToUserFunction(GoogleApiCredentials googleApplicationCredentials) {
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

            Oauth2 oauth2Service = new Oauth2.Builder(httpTransport, jsonFactory, new HttpCredentialsAdapter(userCredentials))
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

    private GoogleApiCredentials getGoogleApplicationCredentials() {
        return this.googleApplicationCredentials;
    }
    private void setGoogleApplicationCredentials(GoogleApiCredentials googleApplicationCredentials) {
        this.googleApplicationCredentials = googleApplicationCredentials;
    }

}
