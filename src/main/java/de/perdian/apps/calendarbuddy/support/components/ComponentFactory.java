package de.perdian.apps.calendarbuddy.support.components;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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

    public Label createTitleLabel(String text) {
        Label titleLabel = this.createLabel(text);
        titleLabel.setStyle("-fx-font-size: 80%;");
        return titleLabel;
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

    public PasswordField createPasswordField(Property<String> property) {
        PasswordField passwordField = new PasswordField();
        passwordField.textProperty().bindBidirectional(property);
        return this.customizeNode(passwordField);
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
        comboBox.setPrefWidth(0);
        comboBox.setMaxWidth(Double.MAX_VALUE);
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

    public ProgressBar createProgressBar() {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setMaxWidth(Double.MAX_VALUE);
        return this.customizeNode(progressBar);
    }

    public TitledPane createTitledPane(String title, Node content) {
        TitledPane titledPane = new TitledPane(title, content);
        titledPane.setCollapsible(false);
        titledPane.setFocusTraversable(false);
        return this.customizeNode(titledPane);
    }

    public Separator createVerticalSeparator() {
        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        return this.customizeNode(separator);
    }

    public Node createKeyboardShortcutPane(String keyboardShortcut, String description) {
        Label keyboardShortcutLabel = new Label(keyboardShortcut);
        keyboardShortcutLabel.setAlignment(Pos.CENTER_LEFT);
        keyboardShortcutLabel.setPadding(new Insets(2, 4, 2, 4));
        keyboardShortcutLabel.setStyle("-fx-font-size: 90%; -fx-background-color: #FFFFFF; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-radius: 5;");
        Label descriptionLabel = new Label(description);
        descriptionLabel.setAlignment(Pos.CENTER_LEFT);
        descriptionLabel.setPadding(new Insets(2, 4, 2, 4));
        GridPane shortcutPane = new GridPane();
        shortcutPane.add(keyboardShortcutLabel, 0, 0);
        shortcutPane.add(descriptionLabel, 1, 0);
        return this.customizeNode(shortcutPane);
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
