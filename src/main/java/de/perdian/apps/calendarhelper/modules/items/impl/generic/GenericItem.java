package de.perdian.apps.calendarhelper.modules.items.impl.generic;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.support.AbstractSingleItem;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;

public class GenericItem extends AbstractSingleItem {

    private final StringProperty summary = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<GenericType> type = new SimpleObjectProperty<>();

    public GenericItem(ItemDefaults itemDefaults) {
        super(itemDefaults);
    }

    @Override
    protected Event createEvent() {
        Event event = super.createEvent();
        event.setSummary(this.createSummary());
        event.setLocation(this.locationProperty().getValue());
        event.setDescription(this.descriptionProperty().getValue());
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

    public StringProperty summaryProperty() {
        return this.summary;
    }

    public StringProperty locationProperty() {
        return this.location;
    }

    public StringProperty descriptionProperty() {
        return this.description;
    }

    public ObjectProperty<GenericType> typeProperty() {
        return this.type;
    }

}
