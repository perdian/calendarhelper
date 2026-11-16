package de.perdian.apps.calendarbuddy.modules.defaults;

import de.perdian.apps.calendarbuddy.support.storage.StorageService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class DefaultsModel {

    private final StringProperty attendees;
    private final ObjectProperty<ZoneId> timezone;

    @Autowired
    DefaultsModel(StorageService storageService) {
        this.attendees = storageService.getPersistentStringProperty("DefaultsModel.attendees");
        this.timezone = storageService.getPersistentObjectProperty("DefaultsModel.timezone", ZoneId.systemDefault());
    }

    public String getAttendees() {
        return this.attendees.get();
    }
    public StringProperty attendeesProperty() {
        return this.attendees;
    }
    public void setAttendees(String attendees) {
        this.attendees.set(attendees);
    }

    public ZoneId getTimezone() {
        return this.timezone.get();
    }
    public ObjectProperty<ZoneId> timezoneProperty() {
        return this.timezone;
    }
    public void setTimezone(ZoneId timezone) {
        this.timezone.set(timezone);
    }

}
