package de.perdian.apps.calendarhelper.fx.support.webview;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.nio.file.Path;

@Configuration
class PersistentCookieStoreConfiguration {

    @PostConstruct
    void initializeWebView() {

        Path cookieStorePath = new File("tmp/storage/cookies/").toPath();
        CookieStore cookieStore = new PersistentCookieStore(cookieStorePath);
        CookieManager cookieManager = new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);

    }

}
