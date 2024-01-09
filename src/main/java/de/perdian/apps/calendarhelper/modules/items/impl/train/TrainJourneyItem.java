package de.perdian.apps.calendarhelper.modules.items.impl.train;

import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.support.AbstractParentItem;

public class TrainJourneyItem extends AbstractParentItem<TrainRideItem> {

    public TrainJourneyItem(ItemDefaults itemDefaults) {
        this.appendChild(itemDefaults);
    }

    @Override
    protected TrainRideItem createChildInstance(ItemDefaults itemDefaults) {
        TrainRideItem childItem = new TrainRideItem(itemDefaults);
        if (!this.getChildren().isEmpty()) {
            TrainRideItem previousItem = this.getChildren().get(this.getChildren().size() - 1);
            childItem.departureStationProperty().setValue(previousItem.arrivalStationProperty().getValue());
            childItem.startDateProperty().setValue(previousItem.endDateProperty().getValue());
        }
        return childItem;
    }

}
