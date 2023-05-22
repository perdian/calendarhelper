package de.perdian.apps.calendarhelper.support.airtravel;

import java.util.Arrays;
import java.util.Collection;

public enum AirportType {

    AIRPORT(Arrays.asList("airport")),
    STATION(Arrays.asList("station")),
    PORT(Arrays.asList("port")),
    UNKNOWN(Arrays.asList("unknown"));

    private Collection<String> values = null;

    private AirportType(Collection<String> values) {
        this.setValues(values);
    }

    public static AirportType parseValue(String value) {
        for (AirportType airportType : AirportType.values()) {
            if (airportType.getValues().contains(value == null ? null : value.toLowerCase())) {
                return airportType;
            }
        }
        throw new IllegalArgumentException("Invalid airport type value found: " + value);
    }

    private Collection<String> getValues() {
        return this.values;
    }
    private void setValues(Collection<String> values) {
        this.values = values;
    }

}
