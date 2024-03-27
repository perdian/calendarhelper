package de.perdian.apps.calendarhelper.modules.items.impl.train;

import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.support.AbstractJourneyItem;

public class TrainJourneyItem extends AbstractJourneyItem<TrainRideItem> {

    @Override
    protected TrainRideItem createChildInstance(ItemDefaults itemDefaults) {
        TrainRideItem childItem = new TrainRideItem(itemDefaults);
        if (!this.getChildren().isEmpty()) {
            TrainRideItem previousItem = this.getChildren().get(this.getChildren().size() - 1);
            childItem.departureStationProperty().setValue(previousItem.arrivalStationProperty().getValue());
            childItem.startDateProperty().setValue(previousItem.endDateProperty().getValue());
            childItem.bookingCodeProperty().setValue(previousItem.bookingCodeProperty().getValue());
        }
        return childItem;
    }

    @Override
    protected String createJourneyEventSummary() {
        return "Trip 🚊 "
            + this.getChildren().getFirst().departureStationProperty().getValue()
            + " » "
            + this.getChildren().getLast().arrivalStationProperty().getValue()
            ;
    }

}
