package de.perdian.apps.calendarhelper.support.storage;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Service
class StorageServiceImpl implements StorageService {

    private static final Logger log = LoggerFactory.getLogger(StorageServiceImpl.class);

    private Path rootPath = null;
    private Path persistentPropertiesStoragePath = null;
    private Map<String, StringProperty> persistentProperties = null;

    @PostConstruct
    void initialize() {

        Path storagePath = Paths.get(System.getProperty("user.home"), ".calendarhelper/");
        if (Files.exists(storagePath)) {
            log.debug("Using storage root path: {}", storagePath);
        } else {
            try {
                log.debug("Creating storage root path: {}", storagePath);
                Files.createDirectories(storagePath);
            } catch (Exception e) {
                log.error("Cannot create storage root path at: {}", storagePath, e);
            }
        }
        this.setRootPath(storagePath);

        Map<String, StringProperty> persistentProperties = new HashMap<>();
        Path persistentPropertiesStoragePath = storagePath.resolve("persistent-properties");
        if (Files.exists(persistentPropertiesStoragePath)) {
            log.debug("Loading persistent properties from: {}", persistentPropertiesStoragePath.toUri());
            try (InputStream sourceStream = new GZIPInputStream(new BufferedInputStream(Files.newInputStream(persistentPropertiesStoragePath)))) {
                Properties sourceProperties = new Properties();
                sourceProperties.loadFromXML(sourceStream);
                for (Map.Entry<?, ?> sourcePropertyEntry : sourceProperties.entrySet()) {
                    StringProperty persistentProperty = new SimpleStringProperty((String)sourcePropertyEntry.getValue());
                    persistentProperty.addListener((_, oldValue, newValue) -> {
                        if (!Objects.equals(oldValue, newValue)) {
                            this.writePersistentProperties();
                        }
                    });
                    persistentProperties.put((String)sourcePropertyEntry.getKey(), persistentProperty);
                }
                log.trace("Loaded persistent properties from: {}", persistentPropertiesStoragePath.toUri());
            } catch (Exception e) {
                log.warn("Cannot load persistent properties from: {}", persistentPropertiesStoragePath.toUri(), e);
            }

        }
        this.setPersistentPropertiesStoragePath(persistentPropertiesStoragePath);
        this.setPersistentProperties(persistentProperties);
    }

    private void writePersistentProperties() {
        try {
            log.debug("Writing persistent properties into: {}", this.getPersistentPropertiesStoragePath().toUri());
            Properties storageProperties = new Properties();
            for (Map.Entry<String, StringProperty> persistentPropertyEntry : this.getPersistentProperties().entrySet()) {
                if (persistentPropertyEntry.getValue().getValue() != null) {
                    storageProperties.setProperty(persistentPropertyEntry.getKey(), persistentPropertyEntry.getValue().getValue());
                }
            }
            try (OutputStream storageStream = new GZIPOutputStream(new BufferedOutputStream(Files.newOutputStream(this.getPersistentPropertiesStoragePath())))) {
                storageProperties.storeToXML(storageStream, "");
            }
        } catch (Exception e) {
            log.warn("Cannot write persistent properties into: {}", this.getPersistentPropertiesStoragePath().toUri(), e);
        }
    }

    @Override
    public StringProperty getPersistentProperty(String propertyName, String defaultValue) {
        return this.getPersistentProperties().computeIfAbsent(propertyName, newPropertyName -> this.createPersistentProperty(newPropertyName, defaultValue));
    }

    private StringProperty createPersistentProperty(String propertyName, String defaultValue) {
        StringProperty property = new SimpleStringProperty(defaultValue);
        property.addListener((_, oldValue, newValue) -> {
            if (!Objects.equals(oldValue, newValue)) {
                this.writePersistentProperties();
            }
        });
        return property;
    }

    @Override
    public Path getRootPath() {
        return this.rootPath;
    }
    private void setRootPath(Path rootPath) {
        this.rootPath = rootPath;
    }

    private Path getPersistentPropertiesStoragePath() {
        return this.persistentPropertiesStoragePath;
    }
    private void setPersistentPropertiesStoragePath(Path persistentPropertiesStoragePath) {
        this.persistentPropertiesStoragePath = persistentPropertiesStoragePath;
    }

    private Map<String, StringProperty> getPersistentProperties() {
        return this.persistentProperties;
    }
    private void setPersistentProperties(Map<String, StringProperty> persistentProperties) {
        this.persistentProperties = persistentProperties;
    }

}
