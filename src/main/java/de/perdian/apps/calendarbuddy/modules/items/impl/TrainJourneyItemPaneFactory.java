package de.perdian.apps.calendarbuddy.modules.items.impl;

import javafx.scene.layout.Pane;

public class TrainJourneyItemPaneFactory extends JourneyItemPaneFactory<TrainJourneyItem, TrainSingleItem> {

    @Override
    protected Pane createChildItemPane(TrainSingleItem childItem, TrainJourneyItem parentItem) {
        return new TrainSingleItemPane(childItem, parentItem);
    }

}
