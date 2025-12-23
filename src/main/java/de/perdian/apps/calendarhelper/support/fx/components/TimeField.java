package de.perdian.apps.calendarhelper.support.fx.components;

import de.perdian.apps.calendarhelper.support.datetime.DateTimeHelper;
import javafx.beans.property.Property;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalTime;

public class TimeField extends BorderPane {

    public TimeField(Property<LocalTime> timeProperty) {

        TextFormatter<LocalTime> timeFormatter = new TextFormatter<>(new TimeStringConverter());
        timeFormatter.valueProperty().bindBidirectional(timeProperty);
        TextField timeField = new TextField();
        timeField.setTextFormatter(timeFormatter);
        timeField.setPromptText("HH:mm");
        timeField.setPrefWidth(55);

        this.setCenter(timeField);

    }

    private static class TimeStringConverter extends StringConverter<LocalTime> {

        @Override
        public String toString(LocalTime object) {
            return object == null ? null : object.toString();
        }
        @Override
        public LocalTime fromString(String string) {
            return StringUtils.isEmpty(string) ? null : DateTimeHelper.parseTime(string);
        }

    }

}
