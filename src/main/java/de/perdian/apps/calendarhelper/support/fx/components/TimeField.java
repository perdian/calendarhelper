package de.perdian.apps.calendarhelper.support.fx.components;

import de.perdian.apps.calendarhelper.support.datetime.DateTimeHelper;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalTime;

public class TimeField extends BorderPane {

    public TimeField(ObjectProperty<LocalTime> timeProperty) {

        TextFormatter<LocalTime> timeFormatter = new TextFormatter<>(new TimeStringConverter());
        timeFormatter.valueProperty().bindBidirectional(timeProperty);
        TextField timeField = new TextField();
        timeField.setTextFormatter(timeFormatter);
        timeField.setPromptText("HH:mm");
        timeField.setPrefWidth(75);

        this.setCenter(timeField);

    }

    private class TimeStringConverter extends StringConverter<LocalTime> {

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
