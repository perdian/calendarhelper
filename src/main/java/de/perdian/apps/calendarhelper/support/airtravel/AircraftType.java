package de.perdian.apps.calendarhelper.support.airtravel;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public class AircraftType {

    private String code = null;
    private String name = null;

    public static AircraftType withCode(String code) {
        AircraftType aircraftType = new AircraftType();
        aircraftType.setCode(code);
        return aircraftType;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof AircraftType thatType) {
            return Objects.equals(this.getCode(), thatType.getCode());
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

}
