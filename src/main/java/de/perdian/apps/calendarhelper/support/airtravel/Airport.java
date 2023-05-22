package de.perdian.apps.calendarhelper.support.airtravel;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

public class Airport implements Serializable {

    static final long serialVersionUID = 1L;

    private String code = null;
    private String name = null;
    private String city = null;
    private String countryCode = null;
    private Float latitude = null;
    private Float longitude = null;
    private ZoneOffset timezoneOffset = null;
    private ZoneId timezoneId = null;
    private AirportType type = null;

    public static Airport withCode(String code) {
        Airport airport = new Airport();
        airport.setCode(code);
        return airport;
    }

    public static Airport withCodeAndTimezone(String code, ZoneId timezoneId) {
        Airport airport = new Airport();
        airport.setCode(code);
        airport.setTimezoneId(timezoneId);
        return airport;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Airport thatAirport) {
            return Objects.equals(this.getCode(), thatAirport.getCode());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getCode() == null ? 0 : this.getCode().hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return this.countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Float getLatitude() {
        return this.latitude;
    }
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return this.longitude;
    }
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public ZoneOffset getTimezoneOffset() {
        return this.timezoneOffset;
    }
    public void setTimezoneOffset(ZoneOffset timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public ZoneId getTimezoneId() {
        return this.timezoneId;
    }
    public void setTimezoneId(ZoneId timezoneId) {
        this.timezoneId = timezoneId;
    }

    public AirportType getType() {
        return this.type;
    }
    public void setType(AirportType type) {
        this.type = type;
    }

}
