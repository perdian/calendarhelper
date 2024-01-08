package de.perdian.apps.calendarhelper.support.storage;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.nio.file.Path;

public interface StorageService {

    Path getRootPath();

    default StringProperty getPersistentStringProperty(String propertyName) {
        return this.getPersistentStringProperty(propertyName, null);
    }

    StringProperty getPersistentStringProperty(String propertyName, String defaultValue);

    default <T extends Serializable> ObjectProperty<T> getPersistentObjectProperty(String propertyName) {
        return this.getPersistentObjectProperty(propertyName, null);
    }

    <T extends Serializable> ObjectProperty<T> getPersistentObjectProperty(String propertyName, T defaultValue);

}
