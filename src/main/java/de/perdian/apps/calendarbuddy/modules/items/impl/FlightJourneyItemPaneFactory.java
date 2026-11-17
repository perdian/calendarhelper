package de.perdian.apps.calendarbuddy.modules.items.impl;

import de.perdian.apps.calendarhelper.support.fx.components.ComponentFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class FlightJourneyItemPaneFactory extends JourneyItemPaneFactory<FlightJourneyItem, FlightItem> {

    @Override
    protected Pane createChildItemPane(FlightItem childItem, FlightJourneyItem parentItem) {
        ComponentFactory componentFactory = new ComponentFactory();
        TextField textField = componentFactory.createTextField();
        BorderPane childPane = new BorderPane();
        childPane.setCenter(textField);
        childPane.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                textField.requestFocus();
            }
        });
        return childPane;
    }

}
