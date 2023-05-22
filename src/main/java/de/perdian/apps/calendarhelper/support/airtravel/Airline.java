package de.perdian.apps.calendarhelper.support.airtravel;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Objects;

public class Airline implements Serializable {

    static final long serialVersionUID = 1L;

    private String code = null;
    private String name = null;
    private String alias = null;
    private String callsign = null;
    private String countryCode = null;
    private String logoUrl = null;

    public static Airline withCode(String code) {
        Airline airline = new Airline();
        airline.setCode(code);
        return airline;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof Airline thatAirline) {
            return Objects.equals(this.getCode(), thatAirline.getCode());
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
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("code", this.getCode());
        toStringBuilder.append("name", this.getName());
        return toStringBuilder.toString();
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

    public String getAlias() {
        return this.alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCallsign() {
        return this.callsign;
    }
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getCountryCode() {
        return this.countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLogoUrl() {
        return this.logoUrl;
    }
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

}
