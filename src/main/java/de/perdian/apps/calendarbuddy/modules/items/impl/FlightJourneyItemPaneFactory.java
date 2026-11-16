package de.perdian.apps.calendarbuddy.modules.items.impl;

import de.perdian.apps.calendarhelper.support.fx.components.ComponentFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class FlightJourneyItemPaneFactory extends JourneyItemPaneFactory<FlightJourneyItem, FlightItem> {

    @Override
    protected Pane createChildItemPane(FlightJourneyItem parentItem, FlightItem childItem) {
        ComponentFactory componentFactory = new ComponentFactory();
        componentFactory.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.isMetaDown() && keyEvent.getCode() == KeyCode.MINUS) {
                parentItem.removeChild(childItem);
                keyEvent.consume();
            }
        });
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
