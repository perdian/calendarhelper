package de.perdian.apps.calendarhelper.support.airtravel;

import java.util.Collection;
import java.util.List;

public enum AirportType {

    AIRPORT(List.of("airport")),
    STATION(List.of("station")),
    PORT(List.of("port")),
    UNKNOWN(List.of("unknown"));

    private Collection<String> values = null;

    AirportType(Collection<String> values) {
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
