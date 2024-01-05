package de.perdian.apps.calendarhelper.support.storage;

import javafx.beans.property.StringProperty;

import java.nio.file.Path;

public interface StorageService {

    Path getRootPath();

    default StringProperty getPersistentProperty(String propertyName) {
        return this.getPersistentProperty(propertyName, null);
    }

    StringProperty getPersistentProperty(String propertyName, String defaultValue);

}
