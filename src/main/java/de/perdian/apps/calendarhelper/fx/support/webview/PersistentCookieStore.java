package de.perdian.apps.calendarhelper.fx.support.webview;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class PersistentCookieStore implements CookieStore {

    private static final Logger log = LoggerFactory.getLogger(PersistentCookieStore.class);

    private Path storagePath = null;
    private Map<PersistentCookieKey, PersistentCookie> cookiesByKey = null;

    public PersistentCookieStore(Path path) {
        this.setStoragePath(path);
        this.setCookiesByKey(new HashMap<>());
        this.loadFromFileSystem();
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        PersistentCookieKey persistentCookieKey = new PersistentCookieKey(cookie);
        this.getCookiesByKey().put(persistentCookieKey, new PersistentCookie(cookie));
        this.flushToFileSystem();
    }

    @Override
    public List<HttpCookie> get(URI uri) {

        Map<String, HttpCookie> cookiesMatchingDomainByName = new LinkedHashMap<>();

        // Add wildcards first. They can then later be overwritten by exact matches
        this.getCookiesByKey().entrySet().stream()
                .filter(entry -> entry.getKey().getDomain().startsWith(".") && uri.getHost().endsWith(entry.getKey().getDomain()))
                .forEach(entry -> cookiesMatchingDomainByName.put(entry.getValue().getCookie().getName(), entry.getValue().getCookie()));

        // Now overwrite with direct matches
        this.getCookiesByKey().entrySet().stream()
                .filter(entry -> uri.getHost().equals(entry.getKey().getDomain()))
                .forEach(entry -> cookiesMatchingDomainByName.put(entry.getValue().getCookie().getName(), entry.getValue().getCookie()));

        return cookiesMatchingDomainByName.entrySet().stream().map(Map.Entry::getValue).toList();

    }

    @Override
    public List<HttpCookie> getCookies() {
        return this.getCookiesByKey().values().stream().map(PersistentCookie::getCookie).toList();
    }

    @Override
    public List<URI> getURIs() {
        return new ArrayList<>();
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        return this.getCookiesByKey().remove(new PersistentCookieKey(cookie)) != null;
    }

    @Override
    public boolean removeAll() {
        this.getCookiesByKey().clear();
        return false;
    }

    private void loadFromFileSystem() {
        if (Files.exists(this.getStoragePath())) {
            log.trace("Loading persistent cookies from path at: {}", this.getStoragePath());
            try (ObjectInputStream objectStream = new ObjectInputStream(new GZIPInputStream(new BufferedInputStream(Files.newInputStream(this.getStoragePath()))))) {
                List<PersistentCookie> allCookies = (List<PersistentCookie>) objectStream.readObject();
                for (PersistentCookie cookie : allCookies) {
                    this.getCookiesByKey().put(new PersistentCookieKey(cookie.getCookie()), cookie);
                }
            } catch (Exception e) {
                log.debug("Cannot load persistent cookies from path at: {}", this.getStoragePath(), e);
            }
        }
    }

    private void flushToFileSystem() {
        Collection<PersistentCookie> allCookies = this.getCookiesByKey().values();
        log.trace("Storing {} persistent cookies into path at: {}", allCookies.size(), this.getStoragePath());
        try (ObjectOutputStream objectStream = new ObjectOutputStream(new GZIPOutputStream(new BufferedOutputStream(Files.newOutputStream(this.getStoragePath()))))) {
            objectStream.writeObject(new ArrayList<>(allCookies));
            objectStream.flush();
        } catch (Exception e) {
            log.debug("Cannot store persistent cookies into path at: {}", this.getStoragePath(), e);
        }
    }

    private Path getStoragePath() {
        return storagePath;
    }
    private void setStoragePath(Path storagePath) {
        this.storagePath = storagePath;
    }

    private Map<PersistentCookieKey, PersistentCookie> getCookiesByKey() {
        return cookiesByKey;
    }
    private void setCookiesByKey(Map<PersistentCookieKey, PersistentCookie> cookiesByKey) {
        this.cookiesByKey = cookiesByKey;
    }

}
