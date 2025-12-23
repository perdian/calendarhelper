package de.perdian.apps.calendarhelper.support.fx.components;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalTime;

public class ComponentFactory {

    public ComponentFactory createChild() {
        ComponentFactory childFactory = new ComponentFactory();
        return childFactory;
    }

    public Label createLabel(String text) {
        return new Label(text);
    }

    public Label createLabel(String text, Node graphic) {
        return new Label(text, graphic);
    }

    public TextField createTextField() {
        return new TextField();
    }

    public TextField createTextField(Property<String> property) {
        TextField textField = this.createTextField();
        textField.textProperty().bindBidirectional(property);
        return textField;
    }

    public <T> TextField createTextField(Property<T> property, StringConverter<T> stringConverter) {
        TextFormatter<T> textFormatter = new TextFormatter<>(stringConverter);
        textFormatter.valueProperty().bindBidirectional(property);
        TextField textField = new TextField();
        textField.setTextFormatter(textFormatter);
        return textField;
    }

    public DateField createDateField(Property<LocalDate> property) {
        return new DateField(property);
    }

    public TimeField createTimeField(Property<LocalTime> property) {
        return new TimeField(property);
    }

    public TextField createReadOnlyTextField(ObservableValue<String> observableValue) {
        TextField textField = this.createTextField();
        textField.textProperty().bind(observableValue);
        return textField;
    }

    public TextArea createTextArea(Property<String> property) {
        TextArea textArea = new TextArea();
        textArea.textProperty().bindBidirectional(property);
        return textArea;
    }

    public Button createButton(String title) {
        return this.createButton(title, null);
    }

    public Button createButton(Node graphic) {
        return this.createButton("", graphic);
    }

    public Button createButton(String title, Node graphic) {
        return new Button(title, graphic);
    }

    public <T> ComboBox<T> createComboBox(Property<T> property, ObservableList<T> values) {
        ComboBox<T> comboBox = new ComboBox<>(values);
        comboBox.valueProperty().bindBidirectional(property);
        return comboBox;
    }

    public CheckBox createCheckBox(String title, Property<Boolean> selectedProperty) {
        CheckBox checkBox = new CheckBox(title);
        checkBox.selectedProperty().bindBidirectional(selectedProperty);
        return checkBox;
    }

    public ToggleButton createToggleButton(String title, Property<Boolean> selectedProperty) {
        ToggleButton toggleButton = new ToggleButton(title);
        toggleButton.selectedProperty().bindBidirectional(selectedProperty);
        return toggleButton;
    }

}
