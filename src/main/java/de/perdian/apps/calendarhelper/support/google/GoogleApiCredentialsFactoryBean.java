package de.perdian.apps.calendarhelper.support.google;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Component
public class GoogleApiCredentialsFactoryBean extends AbstractFactoryBean<GoogleApiCredentials> {

    private static final Logger log = LoggerFactory.getLogger(GoogleApiCredentialsFactoryBean.class);

    @Override
    public Class<?> getObjectType() {
        return GoogleApiCredentials.class;
    }

    @Override
    protected GoogleApiCredentials createInstance() {
        Properties applicationProperties = this.loadApplicationProperties();
        GoogleApiCredentials apiCredentials = new GoogleApiCredentials();
        apiCredentials.setClientId(this.extractProperty(applicationProperties, "googleAppClientId", "GOOGLE_APP_CLIENT_ID"));
        apiCredentials.setClientSecret(this.extractProperty(applicationProperties, "googleAppClientSecret", "GOOGLE_APP_CLIENT_SECRET"));
        return apiCredentials;
    }

    private String extractProperty(Properties applicationProperties, String applicationPropertyKey, String environmentVariableName) {
        return applicationProperties.getProperty(applicationPropertyKey, System.getenv(environmentVariableName));
    }

    private Properties loadApplicationProperties() {
        Properties applicationProperties = new Properties();
        Path applicationPropertiesFile = Paths.get(System.getProperty("user.home"), ".calendarhelper/application.properties");
        if (Files.exists(applicationPropertiesFile)) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(Files.newInputStream(applicationPropertiesFile))))) {
                applicationProperties.load(bufferedReader);
                log.debug("Loaded {} application properties from file: {}", applicationProperties.size(), applicationPropertiesFile);
            } catch (Exception e) {
                log.error("Cannot load application properties from file: {}", applicationPropertiesFile);
            }
        }
        return applicationProperties;
    }

}
