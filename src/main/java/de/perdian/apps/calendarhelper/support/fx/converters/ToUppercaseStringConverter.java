package de.perdian.apps.calendarhelper.support.fx.converters;

import javafx.util.StringConverter;

public class ToUppercaseStringConverter extends StringConverter<String> {

    @Override
    public String toString(String object) {
        return object;
    }

    @Override
    public String fromString(String string) {
        return string == null ? null : string.toUpperCase();
    }

}
