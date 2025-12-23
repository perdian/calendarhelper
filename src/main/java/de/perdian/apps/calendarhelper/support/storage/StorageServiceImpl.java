package de.perdian.apps.calendarhelper.support.storage;

import javafx.beans.property.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Service
class StorageServiceImpl implements StorageService {

    private static final Logger log = LoggerFactory.getLogger(StorageServiceImpl.class);

    private Path rootPath = null;
    private Map<String, Property<?>> properties = null;

    @PostConstruct
    void initialize() {
        Path rootPath = Paths.get(System.getProperty("user.home"), ".calendarhelper/");
        if (Files.exists(rootPath)) {
            log.debug("Using storage root path: {}", rootPath);
        } else {
            try {
                log.debug("Creating storage root path: {}", rootPath);
                Files.createDirectories(rootPath);
            } catch (Exception e) {
                log.error("Cannot create storage root path at: {}", rootPath, e);
            }
        }
        this.setRootPath(rootPath);
        this.setProperties(new HashMap<>());
    }

    @Override
    public StringProperty getPersistentStringProperty(String propertyName, String defaultValue) {
        return this.getPersistentProperty(propertyName, defaultValue, SimpleStringProperty::new);
    }

    @Override
    public <T extends Serializable> ObjectProperty<T> getPersistentObjectProperty(String propertyName, T defaultValue) {
        return this.getPersistentProperty(propertyName, defaultValue, SimpleObjectProperty::new);
    }

    @Override
    public BooleanProperty getPersistentBooleanProperty(String propertyName, boolean defaultValue) {
        return this.getPersistentProperty(propertyName, defaultValue, SimpleBooleanProperty::new);
    }

    private <T extends Serializable, P extends Property<T>> P getPersistentProperty(String propertyName, T defaultValue, Supplier<P> newPropertySupplier) {
        return (P)this.getProperties().computeIfAbsent(propertyName.toLowerCase(), internalPropertyName -> this.createPersistentProperty(propertyName, defaultValue, newPropertySupplier));
    }

    private <T extends Serializable, P extends Property<T>> P createPersistentProperty(String propertyName, T defaultValue, Supplier<P> newPropertySupplier) {
        Path propertyPath = this.getRootPath().resolve("properties/" + propertyName);
        T propertyValueValue = this.loadPropertyValue(propertyName, propertyPath);
        P property = newPropertySupplier.get();
        property.setValue(Optional.ofNullable(propertyValueValue).orElse(defaultValue));
        property.addListener((_, _, newPropertyValue) -> this.writePersistedPropertyValue(propertyName, propertyPath, newPropertyValue));
        return property;
    }

    private <T extends Serializable> T loadPropertyValue(String propertyName, Path propertyPath) {
        if (Files.exists(propertyPath)) {
            log.trace("Loading property '{}' from path: {}", propertyName, propertyPath);
            try (ObjectInputStream objectStream = new ObjectInputStream(new GZIPInputStream(new BufferedInputStream(Files.newInputStream(propertyPath))))) {
                return (T)objectStream.readObject();
            } catch (Exception e) {
                log.warn("Cannot load persisted property for '{}' from path at: {}", propertyName, propertyPath, e);
            }
        }
        return null;
    }

    private void writePersistedPropertyValue(String propertyName, Path propertyPath, Serializable newPersistencePropertyValue) {
        try {
            if (newPersistencePropertyValue == null) {
                Files.deleteIfExists(propertyPath);
            } else {
                if (propertyPath.getParent() != null && !Files.exists(propertyPath.getParent())) {
                    log.trace("Creating parent directory for property at: {}", propertyPath.getParent());
                    Files.createDirectories(propertyPath.getParent());
                }
                try (ObjectOutputStream objectStream = new ObjectOutputStream(new GZIPOutputStream(new BufferedOutputStream(Files.newOutputStream(propertyPath))))) {
                    objectStream.writeObject(newPersistencePropertyValue);
                    objectStream.flush();
                }
            }
        } catch (Exception e) {
            log.warn("Cannot write persistence property for '{}' into path at: {}", propertyName, propertyPath, e);
        }
    }

    @Override
    public Path getRootPath() {
        return this.rootPath;
    }
    private void setRootPath(Path rootPath) {
        this.rootPath = rootPath;
    }

    private Map<String, Property<?>> getProperties() {
        return this.properties;
    }
    private void setProperties(Map<String, Property<?>> properties) {
        this.properties = properties;
    }

}
