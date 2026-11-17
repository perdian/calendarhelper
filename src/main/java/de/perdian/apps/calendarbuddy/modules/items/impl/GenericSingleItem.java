package de.perdian.apps.calendarbuddy.modules.items.impl;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarbuddy.modules.defaults.DefaultsModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;

@JsonTypeName("genericSingleItem")
@JsonClassDescription("""
    A single calendar event that is not a flight or a train ride, for example a hotel \
    stay, a conference, an appointment or a reservation. Use it for anything with a start \
    and an end that does not fit the other item types. Multi-day stays and events use \
    fullDay=true with start and end dates.
    """)
public class GenericSingleItem extends SingleItem {

    private final StringProperty summary;
    private final StringProperty location;
    private final ObjectProperty<GenericSingleItemType> type;

    public GenericSingleItem() {
        this.summary = new SimpleStringProperty();
        this.location = new SimpleStringProperty();
        this.type = new SimpleObjectProperty<>();

    }
    @Override
    protected Event createEvent(DefaultsModel defaultsModel) {
        Event event = super.createEvent(defaultsModel);
        event.setSummary(this.createSummary());
        event.setLocation(this.locationProperty().getValue());
        return event;
    }

    private String createSummary() {
        StringBuilder summaryBuilder = new StringBuilder();
        if (this.typeProperty().getValue() != null) {
            summaryBuilder.append(this.typeProperty().getValue());
            summaryBuilder.append(" ").append(this.typeProperty().getValue().getIcon()).append(" ");
        }
        if (StringUtils.isNotEmpty(this.summaryProperty().getValue())) {
            summaryBuilder.append(this.summaryProperty().getValue());
        }
        return summaryBuilder.toString().strip();
    }

    public String getSummary() {
        return this.summary.get();
    }
    public StringProperty summaryProperty() {
        return this.summary;
    }
    public void setSummary(String summary) {
        this.summary.set(summary);
    }

    public String getLocation() {
        return this.location.get();
    }
    public StringProperty locationProperty() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location.set(location);
    }

    public GenericSingleItemType getType() {
        return type.get();
    }
    public ObjectProperty<GenericSingleItemType> typeProperty() {
        return this.type;
    }
    public void setType(GenericSingleItemType type) {
        this.type.set(type);
    }

}
