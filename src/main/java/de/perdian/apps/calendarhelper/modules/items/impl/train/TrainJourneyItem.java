package de.perdian.apps.calendarhelper.modules.items.impl.train;

import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.support.AbstractJourneyItem;
import org.apache.commons.lang3.StringUtils;

public class TrainJourneyItem extends AbstractJourneyItem<TrainRideItem> {

    public TrainJourneyItem(ItemDefaults defaults) {
        super(defaults);
    }

    @Override
    protected TrainRideItem createChildItem(ItemDefaults itemDefaults) {
        TrainRideItem childItem = new TrainRideItem(itemDefaults);
        if (!this.getChildren().isEmpty()) {
            TrainRideItem previousItem = this.getChildren().get(this.getChildren().size() - 1);
            childItem.departureStationProperty().setValue(previousItem.arrivalStationProperty().getValue());
            childItem.getCalendarValues().startDateProperty().setValue(previousItem.getCalendarValues().endDateProperty().getValue());
            childItem.bookingCodeProperty().setValue(previousItem.bookingCodeProperty().getValue());
        }
        childItem.getCalendarValues().endDateProperty().addListener((_, _, newValue) -> {
            int currentChildIndex = this.getChildren().indexOf(childItem);
            if (currentChildIndex > -1 && currentChildIndex < this.getChildren().size() - 1) {
                TrainRideItem nextItem = this.getChildren().get(currentChildIndex + 1);
                if (nextItem.getCalendarValues().startDateProperty().getValue() == null) {
                    nextItem.getCalendarValues().startDateProperty().setValue(newValue);
                }
            }
        });
        childItem.bookingCodeProperty().addListener((_, _, newValue) -> {
            int currentChildIndex = this.getChildren().indexOf(childItem);
            if (currentChildIndex > -1 && currentChildIndex < this.getChildren().size() - 1) {
                TrainRideItem nextItem = this.getChildren().get(currentChildIndex + 1);
                if (StringUtils.isEmpty(nextItem.bookingCodeProperty().getValue())) {
                    nextItem.bookingCodeProperty().setValue(newValue);
                }
            }
        });
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
