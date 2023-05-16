package de.perdian.apps.calendarhelper.fx.support.components;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;

public class DateField extends GridPane {

    public DateField(ObjectProperty<LocalDate> dateProperty) {

        TextFormatter<Integer> dayFormatter = new TextFormatter(new IntegerStringConverter());
        TextField dayField = new TextField();
        dayField.setTextFormatter(dayFormatter);
        dayField.setPromptText("DD");
        dayField.setPrefWidth(40);

        TextFormatter<Integer> monthFormatter = new TextFormatter(new IntegerStringConverter());
        TextField monthField = new TextField();
        monthField.setTextFormatter(monthFormatter);
        monthField.setPromptText("MM");
        monthField.setPrefWidth(40);

        TextFormatter<Integer> yearFormatter = new TextFormatter(new IntegerStringConverter());
        TextField yearField = new TextField();
        yearField.setTextFormatter(yearFormatter);
        yearField.setPrefWidth(55);
        yearField.setPromptText("YYYY");

        this.updateFormatters(dayFormatter, monthFormatter, yearFormatter, dateProperty.getValue());
        dayFormatter.valueProperty().addListener((o, oldValue, newValue) -> this.updateDateProperty(dateProperty, newValue, monthFormatter.valueProperty().getValue(), yearFormatter.valueProperty().getValue()));
        monthFormatter.valueProperty().addListener((o, oldValue, newValue) -> this.updateDateProperty(dateProperty, dayFormatter.getValue(), newValue, yearFormatter.valueProperty().getValue()));
        yearFormatter.valueProperty().addListener((o, oldValue, newValue) -> this.updateDateProperty(dateProperty, dayFormatter.getValue(), monthFormatter.valueProperty().getValue(), newValue));
        dateProperty.addListener((o, oldValue, newValue) -> this.updateFormatters(dayFormatter, monthFormatter, yearFormatter, newValue));

        this.add(dayField, 0, 0, 1, 1);
        this.add(monthField, 1, 0, 1, 1);
        this.add(yearField, 2, 0, 1, 1);
        this.setHgap(1);

    }
    private void updateDateProperty(ObjectProperty<LocalDate> dateProperty, Object dayValue, Object monthValue, Object yearValue) {
        if (dayValue == null || monthValue == null || yearValue == null) {
            dateProperty.setValue(null);
        } else {
            dateProperty.setValue(LocalDate.of((int) yearValue, (int) monthValue, (int) dayValue));
        }
    }

    private void updateFormatters(TextFormatter<Integer> dayFormatter, TextFormatter<Integer> monthFormatter, TextFormatter<Integer> yearFormatter, LocalDate date) {
        dayFormatter.setValue(date == null ? null : date.getDayOfMonth());
        monthFormatter.setValue(date == null ? null : date.getMonthValue());
        yearFormatter.setValue(date == null ? null : date.getYear());
    }


}
