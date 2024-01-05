package de.perdian.apps.calendarhelper.modules.items.impl.train;

import de.perdian.apps.calendarhelper.modules.items.support.AbstractParentPane;
import javafx.scene.layout.Pane;

public class TrainJourneyPane extends AbstractParentPane<TrainJourneyItem, TrainRideItem> {

    public TrainJourneyPane(TrainJourneyItem trainJourneyItem) {
        super(trainJourneyItem);
    }

    @Override
    protected Pane createChildPane(TrainRideItem childItem, TrainJourneyItem parentItem) {
        return new TrainRidePane(childItem, parentItem);
    }

}
