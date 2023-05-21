package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import javafx.scene.layout.Pane;

class TrainJourneyPane extends AbstractParentPane<TrainJourneyItem, TrainRideItem> {

    TrainJourneyPane(TrainJourneyItem trainJourneyItem) {
        super(trainJourneyItem);
    }

    @Override
    protected Pane createChildPane(TrainRideItem childItem) {
        return new TrainRidePane(childItem);
    }

}
