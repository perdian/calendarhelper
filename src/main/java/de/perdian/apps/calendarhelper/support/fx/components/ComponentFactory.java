package de.perdian.apps.calendarhelper.support.fx.components;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentFactory {

    private ComponentFactory parent = null;
    private Map<EventType<Event>, List<EventHandler<Event>>> eventHandlersByType = new HashMap<>();

    public ComponentFactory createChild() {
        ComponentFactory childFactory = new ComponentFactory();
        childFactory.setParent(this);
        return childFactory;
    }

    public Label createLabel(String text) {
        return this.customizeNode(new Label(text));
    }

    public Label createLabel(String text, Node graphic) {
        return this.customizeNode(new Label(text, graphic));
    }

    public TextField createTextField() {
        return this.customizeNode(new TextField());
    }

    public TextField createTextField(Property<String> property) {
        TextField textField = this.createTextField();
        textField.textProperty().bindBidirectional(property);
        return textField;
    }

    public <T> TextField createTextField(Property<T> property, StringConverter<T> stringConverter) {
        TextFormatter<T> textFormatter = new TextFormatter<>(stringConverter);
        textFormatter.valueProperty().bindBidirectional(property);
        TextField textField = this.createTextField();
        textField.setTextFormatter(textFormatter);
        return textField;
    }

    public DateField createDateField(Property<LocalDate> property) {
        return this.customizeNode(new DateField(property));
    }

    public TimeField createTimeField(Property<LocalTime> property) {
        return this.customizeNode(new TimeField(property));
    }

    public TextField createReadOnlyTextField(ObservableValue<String> observableValue) {
        TextField textField = this.createTextField();
        textField.textProperty().bind(observableValue);
        return this.customizeNode(textField);
    }

    public TextArea createTextArea(Property<String> property) {
        TextArea textArea = new TextArea();
        textArea.textProperty().bindBidirectional(property);
        return this.customizeNode(textArea);
    }

    public Button createButton(String title) {
        return this.createButton(title, null);
    }

    public Button createButton(Node graphic) {
        return this.createButton("", graphic);
    }

    public Button createButton(String title, Node graphic) {
        return this.customizeNode(new Button(title, graphic));
    }

    public <T> ComboBox<T> createComboBox(Property<T> property, ObservableList<T> values) {
        ComboBox<T> comboBox = new ComboBox<>(values);
        comboBox.valueProperty().bindBidirectional(property);
        return this.customizeNode(comboBox);
    }

    public CheckBox createCheckBox(String title, Property<Boolean> selectedProperty) {
        CheckBox checkBox = new CheckBox(title);
        checkBox.selectedProperty().bindBidirectional(selectedProperty);
        return this.customizeNode(checkBox);
    }

    public ToggleButton createToggleButton(String title, Property<Boolean> selectedProperty) {
        return this.createToggleButton(title, selectedProperty, null);
    }

    public ToggleButton createToggleButton(String title, Property<Boolean> selectedProperty, Node graphic) {
        ToggleButton toggleButton = new ToggleButton(title, graphic);
        toggleButton.selectedProperty().bindBidirectional(selectedProperty);
        return this.customizeNode(toggleButton);
    }

    @SuppressWarnings("unchecked")
    public <T extends Event> void addEventHandler(EventType<T> eventType, EventHandler<T> eventHandler) {
        this.getEventHandlersByType()
            .computeIfAbsent((EventType<Event>) eventType, _ -> new ArrayList<>())
            .add((EventHandler<Event>) eventHandler);
    }

    private <T extends Node> T customizeNode(T node) {
        this.applyEventHandlers(node);
        return node;
    }

    private void applyEventHandlers(Node targetNode) {
        if (this.getParent() != null) {
            this.getParent().applyEventHandlers(targetNode);
        }
        for (Map.Entry<EventType<Event>, List<EventHandler<Event>>> eventHandlerByTypeEntry : this.getEventHandlersByType().entrySet()) {
            this.applyEventHandlersForType(targetNode, eventHandlerByTypeEntry.getKey(), eventHandlerByTypeEntry.getValue());
        }
    }

    private <T extends Event> void applyEventHandlersForType(Node targetNode, EventType<T> eventType, List<EventHandler<T>> eventHandlers) {
        for (EventHandler<T> eventHandler : eventHandlers) {
            targetNode.addEventHandler(eventType, eventHandler);
        }
    }

    private ComponentFactory getParent() {
        return this.parent;
    }
    private void setParent(ComponentFactory parent) {
        this.parent = parent;
    }

    private Map<EventType<Event>, List<EventHandler<Event>>> getEventHandlersByType() {
        return this.eventHandlersByType;
    }
    private void setEventHandlersByType(Map<EventType<Event>, List<EventHandler<Event>>> eventHandlersByType) {
        this.eventHandlersByType = eventHandlersByType;
    }

}
