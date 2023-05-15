package de.perdian.apps.calendarhelper.services.google.users;

import com.google.api.client.auth.oauth2.TokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import de.perdian.apps.calendarhelper.services.google.GoogleApiException;

import java.io.IOException;
import java.util.function.Function;

class GoogleAuthorizationCodeToRefreshTokenFunction implements Function<GoogleAuthorizationCode, GoogleRefreshToken> {

    private GoogleRefreshTokenStore googleRefreshTokenStore = null;

    GoogleAuthorizationCodeToRefreshTokenFunction(GoogleRefreshTokenStore googleRefreshTokenStore) {
        this.setGoogleRefreshTokenStore(googleRefreshTokenStore);
    }

    @Override
    public GoogleRefreshToken apply(GoogleAuthorizationCode authorizationCode) {
        try {
            TokenRequest tokenRequest = authorizationCode.getFlow().newTokenRequest(authorizationCode.getValue()).setRedirectUri(authorizationCode.getCallbackUrl());
            TokenResponse tokenResponse = tokenRequest.execute();
            GoogleRefreshToken token = new GoogleRefreshToken(tokenResponse.getRefreshToken());
            this.getGoogleRefreshTokenStore().updateRefreshToken(token);
            return token;
        } catch (IOException e) {
            throw new GoogleApiException("Cannot request refresh token from Google", e);
        }
    }

    private GoogleRefreshTokenStore getGoogleRefreshTokenStore() {
        return googleRefreshTokenStore;
    }
    private void setGoogleRefreshTokenStore(GoogleRefreshTokenStore googleRefreshTokenStore) {
        this.googleRefreshTokenStore = googleRefreshTokenStore;
    }

}
