package de.perdian.apps.calendarbuddy.modules.items.impl;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.UUID;

public class TrainJourneyItemPaneFactory extends JourneyItemPaneFactory<TrainJourneyItem, TrainItem> {

    @Override
    protected Pane createChildItemPane(TrainJourneyItem parentItem, TrainItem childItem) {
        BorderPane childPane = new BorderPane();
        childPane.setCenter(new Label("TRAIN: " + UUID.randomUUID().toString()));
        return childPane;
    }

}
