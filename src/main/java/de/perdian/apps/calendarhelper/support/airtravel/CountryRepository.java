package de.perdian.apps.calendarhelper.support.airtravel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class CountryRepository {

    private static final Logger log = LoggerFactory.getLogger(CountryRepository.class);
    private static final CountryRepository instance = new CountryRepository();
    private static final char OPENFLIGHTS_FIELD_DELIMITER = '\"';

    private Map<String, String> countryCodesByTitle = null;

    public static CountryRepository getInstance() {
        return CountryRepository.instance;
    }

    private CountryRepository() {
        try {

            URL countriesResourceURL = CountryRepository.class.getResource("/META-INF/resources/airtravel/countries.dat");
            log.debug("Loading countries from resource: {}", countriesResourceURL);
            Map<String, String> countryCodesByTitle = new LinkedHashMap<>();
            try (BufferedReader countriesReader = new BufferedReader(new InputStreamReader(countriesResourceURL.openStream(), "UTF-8"))) {
                for (String countryLine = countriesReader.readLine(); countryLine != null; countryLine = countriesReader.readLine()) {
                    List<String> countryFields = CountryRepository.tokenizeLine(countryLine);
                    String countryName = countryFields.get(0);
                    String countryCode = countryFields.get(2);
                    countryCodesByTitle.put(countryName, countryCode);
                }
            }
            log.debug("Loaded {} countries from resource: {}", countryCodesByTitle.size(), countriesResourceURL);
            this.setCountryCodesByTitle(countryCodesByTitle);

        } catch (IOException e) {
            throw new RuntimeException("Cannot initialilze CountryRepository", e);
        }
    }

    public String getCountryCodeByTitle(String title) {
        return this.getCountryCodesByTitle().get(title);
    }

    private static List<String> tokenizeLine(String line) {
        String[] lineValues = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        List<String> resultValues = new ArrayList<>(lineValues.length);
        for (String lineValue : lineValues) {
            int startIndex = lineValue.startsWith(String.valueOf(OPENFLIGHTS_FIELD_DELIMITER)) ? 1 : 0;
            int endIndex = lineValue.endsWith(String.valueOf(OPENFLIGHTS_FIELD_DELIMITER)) ? lineValue.length() - 1 : lineValue.length();
            resultValues.add(lineValue.substring(startIndex, endIndex));
        }
        return Collections.unmodifiableList(resultValues);
    }

    private Map<String, String> getCountryCodesByTitle() {
        return this.countryCodesByTitle;
    }
    private void setCountryCodesByTitle(Map<String, String> countryCodesByTitle) {
        this.countryCodesByTitle = countryCodesByTitle;
    }

}
