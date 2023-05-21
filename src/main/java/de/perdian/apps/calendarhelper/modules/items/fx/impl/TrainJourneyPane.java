package de.perdian.apps.calendarhelper.modules.items.fx.impl;

import de.perdian.apps.calendarhelper.modules.items.fx.support.AbstractParentPane;
import de.perdian.apps.calendarhelper.modules.items.model.impl.TrainJourneyItem;
import de.perdian.apps.calendarhelper.modules.items.model.impl.TrainRideItem;
import javafx.scene.layout.Pane;

public class TrainJourneyPane extends AbstractParentPane<TrainJourneyItem, TrainRideItem> {

    public TrainJourneyPane(TrainJourneyItem trainJourneyItem) {
        super(trainJourneyItem);
    }

    @Override
    protected Pane createChildPane(TrainRideItem childItem) {
        return new TrainRidePane(childItem);
    }

}
