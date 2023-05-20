package de.perdian.apps.calendarhelper.fx.support.components;

import de.perdian.apps.calendarhelper.support.datetime.DateTimeHelper;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class DateField extends BorderPane {

    public DateField(ObjectProperty<LocalDate> dateProperty) {

        TextFormatter<LocalDate> dateFormatter = new TextFormatter<>(new DateStringConverter());
        dateFormatter.valueProperty().bindBidirectional(dateProperty);
        TextField dateField = new TextField();
        dateField.setTextFormatter(dateFormatter);
        dateField.setPromptText("yyyy-MM-dd");
        dateField.setPrefWidth(100);

        this.setCenter(dateField);

    }

    private class DateStringConverter extends StringConverter<LocalDate> {

        @Override
        public String toString(LocalDate object) {
            return object == null ? null : object.toString();
        }
        @Override
        public LocalDate fromString(String string) {
            return StringUtils.isEmpty(string) ? null : DateTimeHelper.parseDate(string);
        }

    }

}
