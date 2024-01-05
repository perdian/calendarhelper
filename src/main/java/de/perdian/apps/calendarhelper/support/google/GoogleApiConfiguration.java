package de.perdian.apps.calendarhelper.support.google;

import de.perdian.apps.calendarhelper.support.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GoogleApiConfiguration {

    private StorageService storageService = null;

    @Bean
    GoogleApiCredentials googleApiCredentials() {
        GoogleApiCredentials googleApiCredentials = new GoogleApiCredentials();
        googleApiCredentials.clientIdProperty().bindBidirectional(this.getStorageService().getPersistentProperty("GoogleApiCredentials.clientId"));
        googleApiCredentials.clientSecretProperty().bindBidirectional(this.getStorageService().getPersistentProperty("GoogleApiCredentials.clientSecret"));
        return googleApiCredentials;
    }

    StorageService getStorageService() {
        return this.storageService;
    }
    @Autowired
    void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

}
