package de.perdian.apps.calendarhelper.support.fx.components;

import de.perdian.apps.calendarhelper.support.datetime.DateTimeHelper;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
        dateField.setPrefWidth(90);
        dateField.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            LocalDate currentValue = dateFormatter.getValue();
            if (currentValue != null && KeyCode.PAGE_DOWN.equals(keyEvent.getCode())) {
                dateFormatter.setValue(currentValue.plusDays(1));
            } else if (currentValue != null && KeyCode.PAGE_UP.equals(keyEvent.getCode())) {
                dateFormatter.setValue(currentValue.plusDays(-1));
            }
        });

        this.setCenter(dateField);

    }

    private static void adjustDate(TextFormatter<LocalDate> dateFormatter, int direction) {
        System.err.println("ADJUST: " + dateFormatter.getValue() + " >> " + direction);
        LocalDate currentDate = dateFormatter.getValue();
        if (currentDate != null) {
            dateFormatter.setValue(currentDate.plusDays(direction));
        }
    }

    private static class DateStringConverter extends StringConverter<LocalDate> {

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
