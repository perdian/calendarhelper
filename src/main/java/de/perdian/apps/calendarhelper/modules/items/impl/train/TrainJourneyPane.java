package de.perdian.apps.calendarhelper.modules.items.impl.train;

import de.perdian.apps.calendarhelper.modules.items.support.AbstractContainerPane;
import de.perdian.apps.calendarhelper.support.fx.components.ComponentFactory;
import javafx.scene.layout.Pane;

class TrainJourneyPane extends AbstractContainerPane<TrainJourneyItem, TrainRideItem> {

    TrainJourneyPane(TrainJourneyItem trainJourneyItem, ComponentFactory componentFactory) {
        super(trainJourneyItem, componentFactory);
    }

    @Override
    protected Pane createChildPane(TrainRideItem childItem, TrainJourneyItem parentItem, ComponentFactory componentFactory) {
        return new TrainRidePane(childItem, parentItem, componentFactory);
    }

}
