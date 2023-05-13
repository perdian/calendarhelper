package de.perdian.apps.calendarhelper.fx.support.webview;

import de.perdian.apps.calendarhelper.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.nio.file.Path;

@Configuration
class PersistentCookieStoreConfiguration {

    private StorageService storageService = null;

    @PostConstruct
    void initializeWebView() {
        Path cookiesPath = this.getStorageService().getRootPath().resolve("cookies");
        CookieStore cookieStore = new PersistentCookieStore(cookiesPath);
        CookieManager cookieManager = new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
    }

    StorageService getStorageService() {
        return storageService;
    }
    @Autowired
    void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

}
