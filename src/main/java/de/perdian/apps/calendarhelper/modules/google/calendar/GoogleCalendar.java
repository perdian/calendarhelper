package de.perdian.apps.calendarhelper.modules.google.calendar;

import com.google.api.services.calendar.model.CalendarListEntry;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.StringUtils;

import java.time.ZoneId;

public class GoogleCalendar {

    private String id = null;
    private Color color = null;
    private String summary = null;
    private boolean primary = false;
    private ZoneId timezone = null;

    public GoogleCalendar(CalendarListEntry calendarListEntry) {
        this.setId(calendarListEntry.getId());
        this.setColor(StringUtils.isEmpty(calendarListEntry.getBackgroundColor()) ? null : Color.web(calendarListEntry.getBackgroundColor()));
        this.setSummary(calendarListEntry.getSummary());
        this.setPrimary(calendarListEntry.getPrimary() != null && calendarListEntry.getPrimary());
        this.setTimezone(ZoneId.of(calendarListEntry.getTimeZone()));
    }

    public String toString() {
        return this.getSummary();
    }

    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Color getColor() {
        return this.color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public String getSummary() {
        return this.summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isPrimary() {
        return this.primary;
    }
    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public ZoneId getTimezone() {
        return this.timezone;
    }
    public void setTimezone(ZoneId timezone) {
        this.timezone = timezone;
    }

}
