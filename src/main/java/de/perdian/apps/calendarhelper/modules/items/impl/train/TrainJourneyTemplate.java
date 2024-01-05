package de.perdian.apps.calendarhelper.modules.items.impl.train;

import de.perdian.apps.calendarhelper.modules.items.support.AbstractParentTemplate;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

public class TrainJourneyTemplate extends AbstractParentTemplate<TrainJourneyItem, TrainRideItem> {

    public TrainJourneyTemplate() {
        super("Train", MaterialDesignT.TRAIN);
    }

    @Override
    public TrainJourneyItem createItem() {
        TrainJourneyItem trainJourneyItem = new TrainJourneyItem();
        trainJourneyItem.getChildren().setAll(this.createChildItem(trainJourneyItem));
        return trainJourneyItem;
    }

    @Override
    public Pane createItemPane(TrainJourneyItem item) {
        return new TrainJourneyPane(item);
    }

    @Override
    protected TrainRideItem createChildItem(TrainJourneyItem parentItem) {
        TrainRideItem childItem = new TrainRideItem();
        if (!parentItem.getChildren().isEmpty()) {
            TrainRideItem previousItem = parentItem.getChildren().get(parentItem.getChildren().size() - 1);
            childItem.departureStationProperty().setValue(previousItem.arrivalStationProperty().getValue());
            childItem.startDateProperty().setValue(previousItem.endDateProperty().getValue());
        }
        return childItem;
    }

}
