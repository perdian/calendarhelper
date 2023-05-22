package de.perdian.apps.calendarhelper.support.airtravel;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

public class AirportRepository {

    private static final Logger log = LoggerFactory.getLogger(AirportRepository.class);
    private static final AirportRepository instance = new AirportRepository();
    private static final char OPENFLIGHTS_FIELD_DELIMITER = '\"';

    private Map<String, Airport> airportsByCode = null;

    public static AirportRepository getInstance() {
        return AirportRepository.instance;
    }

    private AirportRepository() {
        try {

            Map<String, Airport> airportsByCode = new LinkedHashMap<>();
            List<String> airportsResourceValues = Arrays.asList("/META-INF/resources/airtravel/airports-extended.dat", "/META-INF/resources/airtravel/airports-manual.dat");
            for (String airportsResourceValue : airportsResourceValues) {

                URL airportsResourceURL = AirportRepository.class.getResource(airportsResourceValue);
                log.debug("Loading airports from resource: {}", airportsResourceURL);
                try (BufferedReader airportsReader = new BufferedReader(new InputStreamReader(airportsResourceURL.openStream(), "UTF-8"))) {
                    for (String airportLine = airportsReader.readLine(); airportLine != null; airportLine = airportsReader.readLine()) {
                        if (airportLine.isEmpty() || airportLine.startsWith("#")) {
                            continue;
                        } else {
                            try {

                                List<String> lineFields = AirportRepository.tokenizeLine(airportLine);
                                String iataCode = lineFields.get(4);
                                ZoneOffset zoneOffset = null;
                                String zoneOffsetString = lineFields.get(9);
                                if (zoneOffsetString != null && !zoneOffsetString.equals("\\N")) {
                                    float zoneOffsetValue = Float.parseFloat(lineFields.get(9));
                                    int zoneOffsetHours = (int) zoneOffsetValue;
                                    int zoneOffsetMinutes = (int) ((Math.abs(zoneOffsetValue) - Math.abs(zoneOffsetHours)) * 60 * Math.signum(zoneOffsetValue));
                                    zoneOffset = ZoneOffset.ofHoursMinutes(zoneOffsetHours, zoneOffsetMinutes);
                                }

                                String zoneIdValue = lineFields.get(11);
                                ZoneId zoneId = zoneIdValue == null || zoneIdValue.equalsIgnoreCase("\\N") ? null : ZoneId.of(zoneIdValue);

                                Airport airport = new Airport();
                                airport.setName(lineFields.get(1));
                                airport.setCity(lineFields.get(2));
                                airport.setCountryCode(CountryRepository.getInstance().getCountryCodeByTitle(lineFields.get(3)));
                                airport.setCode(iataCode);
                                airport.setLatitude(Float.parseFloat(lineFields.get(6)));
                                airport.setLongitude(Float.parseFloat(lineFields.get(7)));
                                airport.setTimezoneOffset(zoneOffset);
                                airport.setTimezoneId(zoneId);
                                airport.setType(AirportType.parseValue(lineFields.get(12)));
                                airportsByCode.put(iataCode, airport);

                            } catch (Exception e) {
                                log.warn("Invalid airport line: {}", airportLine, e);
                            }
                        }
                    }
                }

            }

            this.setAirportsByCode(airportsByCode);
            log.debug("Loaded {} airports from {} resources", airportsByCode.size(), airportsResourceValues.size());

        } catch (IOException e) {
            throw new RuntimeException("Cannot initialilze AirportRepository", e);
        }
    }

    public Airport loadAirportByCode(String airportCode) {
        return StringUtils.isEmpty(airportCode) ? null : this.getAirportsByCode().get(airportCode);
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

    private Map<String, Airport> getAirportsByCode() {
        return this.airportsByCode;
    }
    private void setAirportsByCode(Map<String, Airport> airportsByCode) {
        this.airportsByCode = airportsByCode;
    }

}
