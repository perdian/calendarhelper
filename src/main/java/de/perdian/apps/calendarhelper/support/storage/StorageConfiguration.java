package de.perdian.apps.calendarhelper.support.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
class StorageConfiguration {

    private static final Logger log = LoggerFactory.getLogger(StorageConfiguration.class);

    @Bean
    StorageService storageService() {
        Path storagePath = Paths.get(System.getProperty("user.home"), ".calendarhelper/storage/");
        log.debug("Creating StorageService using path: {}", storagePath);
        return new StorageServiceImpl(storagePath);
    }

}
