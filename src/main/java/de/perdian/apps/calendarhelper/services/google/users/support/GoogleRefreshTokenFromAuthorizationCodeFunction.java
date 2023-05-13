package de.perdian.apps.calendarhelper.services.google.users.support;

import java.util.function.Function;

public class GoogleRefreshTokenFromAuthorizationCodeFunction implements Function<String, String> {

    @Override
    public String apply(String authorizationCode) {

        System.err.println("REFRESH TOKEN FROM AUTHORIZATION CODE: " + authorizationCode);

        return null;
    }

}
