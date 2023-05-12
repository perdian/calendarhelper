package de.perdian.apps.calendarhelper.fx;

import de.perdian.apps.calendarhelper.services.google.users.GoogleUser;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class CalendarContext {

    private final ObjectProperty<GoogleUser> googleUser = new SimpleObjectProperty<>();

    public ObjectProperty<GoogleUser> googleUserProperty() {
        return googleUser;
    }

}
