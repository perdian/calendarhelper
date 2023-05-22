package de.perdian.apps.calendarhelper.support.airtravel;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class AirlineRepository {

    private static final Logger log = LoggerFactory.getLogger(AirlineRepository.class);
    private static final AirlineRepository instance = new AirlineRepository();
    private static final char OPENFLIGHTS_FIELD_DELIMITER = '\"';

    private Map<String, Airline> airlinesByCode = null;

    public static AirlineRepository getInstance() {
        return AirlineRepository.instance;
    }

    private AirlineRepository() {
        try {

            Map<String, Airline> airlinesByCode = new LinkedHashMap<>();

            AirlineRepository.loadAirlinesFromResource("/META-INF/resources/airtravel/airlines.dat")
                    .forEach(originalAirline -> airlinesByCode.putIfAbsent(originalAirline.getCode(), originalAirline));

            AirlineRepository.loadAirlinesFromResource("/META-INF/resources/airtravel/airlines-manual.dat")
                    .forEach(manualAirline -> airlinesByCode.put(manualAirline.getCode(), manualAirline));

            this.setAirlinesByCode(airlinesByCode);

        } catch (IOException e) {
            throw new RuntimeException("Cannot initialilze AirlineRepository", e);
        }
    }

    public Airline loadAirlineByCode(String airlineCode) {
        return StringUtils.isEmpty(airlineCode) ? null : this.getAirlinesByCode().get(airlineCode);
    }

    private static List<Airline> loadAirlinesFromResource(String resourceLocation) throws IOException {
        List<Airline> airlines = new ArrayList<>();
        URL resourceURL = AirlineRepository.class.getResource(resourceLocation);
        log.debug("Loading airlines from resource: {}", resourceURL);
        try (BufferedReader airlinesReader = new BufferedReader(new InputStreamReader(resourceURL.openStream(), StandardCharsets.UTF_8))) {
            for (String airlineLine = airlinesReader.readLine(); airlineLine != null; airlineLine = airlinesReader.readLine()) {
                if (airlineLine.isEmpty() || airlineLine.startsWith("#")) {
                    continue;
                } else {
                    try {
                        List<String> lineFields = AirlineRepository.tokenizeLine(airlineLine);
                        String alias = lineFields.get(2);
                        Airline airline = new Airline();
                        airline.setName(lineFields.get(1));
                        airline.setAlias("\\N".equalsIgnoreCase(alias) ? null : alias);
                        airline.setCode(lineFields.get(3));
                        airline.setCallsign(lineFields.get(5));
                        airline.setCountryCode(CountryRepository.getInstance().getCountryCodeByTitle(lineFields.get(6)));
                        airlines.add(airline);
                    } catch (Exception e) {
                        log.warn("Invalid airline line: {}", airlineLine, e);
                    }
                }
            }
        }
        log.debug("Loaded {} airlines from resource: {}", airlines.size(), resourceURL);
        return airlines;
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

    private Map<String, Airline> getAirlinesByCode() {
        return this.airlinesByCode;
    }
    private void setAirlinesByCode(Map<String, Airline> airlinesByCode) {
        this.airlinesByCode = airlinesByCode;
    }

}
