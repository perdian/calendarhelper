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

@SuppressWarnings("DuplicatedCode")
public class AircraftTypeRepository {

    private static final Logger log = LoggerFactory.getLogger(AircraftTypeRepository.class);
    private static final AircraftTypeRepository instance = new AircraftTypeRepository();
    private static final char OPENFLIGHTS_FIELD_DELIMITER = '\"';

    private Map<String, AircraftType> aircraftTypesByCode = null;

    public static AircraftTypeRepository getInstance() {
        return AircraftTypeRepository.instance;
    }

    private AircraftTypeRepository() {
        try {

            URL aircraftTypesResourceURL = AircraftTypeRepository.class.getResource("/META-INF/resources/airtravel/aircrafttypes.dat");
            log.debug("Loading aircraftTypes from resource: {}", aircraftTypesResourceURL);
            Map<String, AircraftType> aircraftTypesByCode = new LinkedHashMap<>();
            try (BufferedReader aircraftTypesReader = new BufferedReader(new InputStreamReader(aircraftTypesResourceURL.openStream(), StandardCharsets.UTF_8))) {
                for (String aircraftTypeLine = aircraftTypesReader.readLine(); aircraftTypeLine != null; aircraftTypeLine = aircraftTypesReader.readLine()) {
                    try {
                        List<String> lineFields = AircraftTypeRepository.tokenizeLine(aircraftTypeLine);
                        if (lineFields.size() >= 3) {
                            AircraftType aircraftType = new AircraftType();
                            aircraftType.setCode(lineFields.get(0));
                            aircraftType.setName(lineFields.get(2));
                            if (StringUtils.isNotEmpty(lineFields.get(0))) {
                                aircraftTypesByCode.putIfAbsent(lineFields.get(0), aircraftType);
                            }
                            if (StringUtils.isNotEmpty(lineFields.get(1))) {
                                aircraftTypesByCode.putIfAbsent(lineFields.get(1), aircraftType);
                            }
                        }
                    } catch (Exception e) {
                        log.warn("Invalid aircraftType line: {}", aircraftTypeLine, e);
                    }
                }
            }

            this.setAircraftTypesByCode(aircraftTypesByCode);
            log.debug("Loaded {} aircraftTypes from resource: {}", aircraftTypesByCode.size(), aircraftTypesResourceURL);

        } catch (IOException e) {
            throw new RuntimeException("Cannot initialilze AircraftTypeRepository", e);
        }
    }

    public AircraftType loadAircraftTypeByCode(String aircraftTypeCode) {
        return StringUtils.isEmpty(aircraftTypeCode) ? null : this.getAircraftTypesByCode().get(aircraftTypeCode);
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

    private Map<String, AircraftType> getAircraftTypesByCode() {
        return this.aircraftTypesByCode;
    }
    private void setAircraftTypesByCode(Map<String, AircraftType> aircraftTypesByCode) {
        this.aircraftTypesByCode = aircraftTypesByCode;
    }

}
