package de.perdian.apps.calendarbuddy.modules.items.impl;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("trainJourneyItem")
@JsonClassDescription("""
    A journey by train, bus, tram or other scheduled ground transport that consists of one \
    or more individual rides. Use this for every such booking: a direct ride is a journey \
    with one ride, a trip with changes is one journey with several rides. Put each \
    individual ride into 'children' as a trainSingleItem, in chronological order. Rides \
    that share one booking and form one continuous trip belong into the same journey. A \
    return trip is a separate journey.
    """)
public class TrainJourneyItem extends JourneyItem<TrainSingleItem> {

    public TrainJourneyItem() {
        this.getChildren().add(new TrainSingleItem());
    }

    @Override
    protected TrainSingleItem createChildItem() {
        TrainSingleItem childItem = new TrainSingleItem();
        if (!this.getChildren().isEmpty()) {
            TrainSingleItem previousItem = this.getChildren().get(this.getChildren().size() - 1);
            childItem.setStartDate(previousItem.getEndDate());
            childItem.setStartZoneId(previousItem.getEndZoneId());
            childItem.setDepartureStation(previousItem.getArrivalStation());
            childItem.setBookingCode(previousItem.getBookingCode());
        }
        return childItem;
    }

}
