package de.perdian.apps.calendarhelper.fx.support.components;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DateField extends GridPane {

    public DateField(ObjectProperty<LocalDate> dateProperty) {

        TextFormatterMappingRegistry textFormatterMappingRegistry = new TextFormatterMappingRegistry();

        TextField dayField = new TextField();
        dayField.setTextFormatter(textFormatterMappingRegistry.createFormatter(ChronoField.DAY_OF_MONTH));
        dayField.setPromptText("DD");
        dayField.setPrefWidth(40);

        TextField monthField = new TextField();
        monthField.setTextFormatter(textFormatterMappingRegistry.createFormatter(ChronoField.MONTH_OF_YEAR));
        monthField.setPromptText("MM");
        monthField.setPrefWidth(40);

        TextFormatter<Integer> yearFormatter = new TextFormatter(new IntegerStringConverter());
        TextField yearField = new TextField();
        yearField.setTextFormatter(textFormatterMappingRegistry.createFormatter(ChronoField.YEAR));
        yearField.setPromptText("YYYY");
        yearField.setPrefWidth(55);

        textFormatterMappingRegistry.updatePropertyToFormatters(dateProperty.getValue());
        textFormatterMappingRegistry.registerProperty(dateProperty);

        this.add(dayField, 0, 0, 1, 1);
        this.add(monthField, 1, 0, 1, 1);
        this.add(yearField, 2, 0, 1, 1);
        this.setHgap(1);

    }

    private void updateDateProperty(ObjectProperty<LocalDate> dateProperty, Object dayValue, Object monthValue, Object yearValue) {
        if (dayValue == null || monthValue == null || yearValue == null) {
            dateProperty.setValue(null);
        } else {
            LocalDate newDate = LocalDate.of((int) yearValue, (int) monthValue, (int) dayValue);
            if (!Objects.equals(newDate, dateProperty.getValue())) {
                dateProperty.setValue(newDate);
            }
        }
    }

    private void updateFormatters(TextFormatter<Integer> dayFormatter, TextFormatter<Integer> monthFormatter, TextFormatter<Integer> yearFormatter, LocalDate date) {
        System.err.println("---");
        System.err.println("DAY   " + date + " >> " + dayFormatter.getValue() + " >> " + (date == null ? "NULL" : date.getDayOfMonth()));
        System.err.println("MONTH " + date + " >> " + monthFormatter.getValue() + " >> " + (date == null ? "NULL" : date.getMonthValue()));
        System.err.println("YEAR " + date + " >> " + yearFormatter.getValue() + " >> " + (date == null ? "NULL" : date.getYear()));
        dayFormatter.setValue(date == null ? null : date.getDayOfMonth());
        monthFormatter.setValue(date == null ? null : date.getMonthValue());
        yearFormatter.setValue(date == null ? null : date.getYear());
    }

    private class TextFormatterMappingRegistry {

        private List<TextFormatterMapping> formatterMappings = new ArrayList<>();
        private List<ObjectProperty<LocalDate>> dateProperties = new ArrayList<>();

        private TextFormatter<Integer> createFormatter(ChronoField chronoField) {
            TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter());
            formatter.valueProperty().addListener((o, oldValue, newValue) -> this.updateFormattersToProperties());
            TextFormatterMapping formatterMapping = new TextFormatterMapping(formatter, chronoField);
            this.getFormatterMappings().add(formatterMapping);
            return formatter;
        }

        private void updatePropertyToFormatters(LocalDate date) {
            for (TextFormatterMapping formatterMapping : this.getFormatterMappings()) {
                formatterMapping.getFormatter().setValue(date == null ? null : date.get(formatterMapping.getChronoField()));
            }
        }

        private void updateFormattersToProperties() {
            LocalDate newDate = this.computeLocalDateFromFormatters();
            this.getDateProperties().forEach(dateProperty -> dateProperty.setValue(newDate));
        }

        private LocalDate computeLocalDateFromFormatters() {
            LocalDate targetDate = LocalDate.of(2000, 1, 1);
            for (int i = this.getFormatterMappings().size() - 1; i >= 0; i--) {
                TextFormatterMapping formatterMapping = this.getFormatterMappings().get(i);
                Integer formatterValue = formatterMapping.getFormatter().getValue();
                if (formatterValue != null) {
                    targetDate = targetDate.with(formatterMapping.getChronoField(), formatterValue);
                }
            }
            return targetDate;
        }

        private void registerProperty(ObjectProperty<LocalDate> dateProperty) {
            dateProperty.addListener((o, oldValue, newValue) -> this.updatePropertyToFormatters(newValue));
            this.getDateProperties().add(dateProperty);
        }

        private List<TextFormatterMapping> getFormatterMappings() {
            return this.formatterMappings;
        }
        private void setFormatterMappings(List<TextFormatterMapping> formatterMappings) {
            this.formatterMappings = formatterMappings;
        }

        private List<ObjectProperty<LocalDate>> getDateProperties() {
            return this.dateProperties;
        }
        private void setDateProperties(List<ObjectProperty<LocalDate>> dateProperties) {
            this.dateProperties = dateProperties;
        }

    }

    private class TextFormatterMapping {

        private TextFormatter<Integer> formatter = null;
        private ChronoField chronoField = null;

        private TextFormatterMapping(TextFormatter<Integer> formatter, ChronoField chronoField) {
            this.setFormatter(formatter);
            this.setChronoField(chronoField);
        }

        private TextFormatter<Integer> getFormatter() {
            return this.formatter;
        }
        private void setFormatter(TextFormatter<Integer> formatter) {
            this.formatter = formatter;
        }

        private ChronoField getChronoField() {
            return this.chronoField;
        }
        private void setChronoField(ChronoField chronoField) {
            this.chronoField = chronoField;
        }

    }

}
