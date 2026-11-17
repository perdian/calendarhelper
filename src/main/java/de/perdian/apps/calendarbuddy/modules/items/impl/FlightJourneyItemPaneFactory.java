package de.perdian.apps.calendarbuddy.modules.items.impl;

import javafx.scene.layout.Pane;

public class FlightJourneyItemPaneFactory extends JourneyItemPaneFactory<FlightJourneyItem, FlightSingleItem> {

    @Override
    protected Pane createChildItemPane(FlightSingleItem childItem, FlightJourneyItem parentItem) {
        return new FlightSingleItemPane(childItem, parentItem);
    }

}
