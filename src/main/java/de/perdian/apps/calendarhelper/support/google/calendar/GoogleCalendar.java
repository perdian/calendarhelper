package de.perdian.apps.calendarhelper.support.google.calendar;

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
        this.setPrimary(calendarListEntry.getPrimary() == null ? false : calendarListEntry.getPrimary().booleanValue());
        this.setTimezone(ZoneId.of(calendarListEntry.getTimeZone()));
    }

    public String toString() {
        return this.getSummary();
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isPrimary() {
        return primary;
    }
    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public ZoneId getTimezone() {
        return timezone;
    }
    public void setTimezone(ZoneId timezone) {
        this.timezone = timezone;
    }

}
