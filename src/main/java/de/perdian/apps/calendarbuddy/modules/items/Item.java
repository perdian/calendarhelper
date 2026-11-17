package de.perdian.apps.calendarbuddy.modules.items;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarbuddy.modules.defaults.DefaultsModel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "itemType")
public abstract class Item {

    public abstract List<Event> createEvents(DefaultsModel defaultsModel);

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
