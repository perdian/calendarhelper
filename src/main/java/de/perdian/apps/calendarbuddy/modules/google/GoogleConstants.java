package de.perdian.apps.calendarbuddy.modules.google;

import java.util.List;

public class GoogleConstants {

    public static final List<String> SCOPES = List.of(
        "https://www.googleapis.com/auth/userinfo.email",
        "https://www.googleapis.com/auth/userinfo.profile",
        "https://www.googleapis.com/auth/calendar"
    );

}
