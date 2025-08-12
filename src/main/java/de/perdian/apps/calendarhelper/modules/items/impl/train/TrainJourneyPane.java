package de.perdian.apps.calendarhelper.modules.items.impl.train;

import de.perdian.apps.calendarhelper.modules.items.support.AbstractContainerPane;
import javafx.scene.layout.Pane;

class TrainJourneyPane extends AbstractContainerPane<TrainJourneyItem, TrainRideItem> {

    TrainJourneyPane(TrainJourneyItem trainJourneyItem) {
        super(trainJourneyItem);
    }

    @Override
    protected Pane createChildPane(TrainRideItem childItem, TrainJourneyItem parentItem) {
        return new TrainRidePane(childItem, parentItem);
    }

}
