package de.perdian.apps.calendarhelper.support.google.users;

import de.perdian.apps.calendarhelper.support.storage.StorageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
class GoogleRefreshTokenStore {

    private static final Logger log = LoggerFactory.getLogger(GoogleRefreshTokenStore.class);

    private StorageService storageService = null;

    void updateRefreshToken(GoogleRefreshToken token) {
        Path tokenPath = this.resolveTokenPath();
        if (token == null || StringUtils.isEmpty(token.getValue())) {
            if (Files.exists(tokenPath)) {
                try {
                    Files.delete(tokenPath);
                } catch (IOException e) {
                    log.debug("Cannot delete refresh token at: {}", tokenPath);
                }
            }
        } else {
            try {
                if (!Files.exists(tokenPath.getParent())) {
                    log.debug("Creating parent directory for refresh token store at: {}", tokenPath.getParent());
                    Files.createDirectories(tokenPath.getParent());
                }
                log.trace("Storing new Google refresh token into: {}", tokenPath);
                Files.writeString(tokenPath, token.getValue());
            } catch (IOException e) {
                log.warn("Cannot store Google refresh token into: {}", tokenPath, e);
            }
        }
    }

    GoogleRefreshToken loadRefreshToken() {
        Path tokenPath = this.resolveTokenPath();
        if (Files.exists(tokenPath)) {
            try {
                log.trace("Try loading Google refresh token from: {}", tokenPath);
                String tokenValue = Files.readString(tokenPath);
                if (StringUtils.isNotEmpty(tokenValue)) {
                    log.debug("Loaded Google refresh token from: {}", tokenPath);
                    return new GoogleRefreshToken(tokenValue);
                }
            } catch (IOException e) {
                log.warn("Cannot read Google refresh token from: {}", tokenPath);
            }
        }
        return null;
    }

    private Path resolveTokenPath() {
        return this.getStorageService().getRootPath().resolve("googleRefreshToken");
    }

    StorageService getStorageService() {
        return storageService;
    }
    @Autowired
    void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

}
