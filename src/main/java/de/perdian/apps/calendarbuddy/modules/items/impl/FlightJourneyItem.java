package de.perdian.apps.calendarbuddy.modules.items.impl;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("flightJourneyItem")
@JsonClassDescription("""
    A journey by plane that consists of one or more individual flights. Use this for every \
    flight booking: a direct flight is a journey with one flight, a trip with connections is \
    one journey with several flights. Put each individual flight into 'children' as a \
    flightSingleItem, in chronological order. Flights that share one booking and form one \
    continuous trip belong into the same journey. A return trip is a separate journey.
    """)
public class FlightJourneyItem extends JourneyItem<FlightSingleItem> {

    public FlightJourneyItem() {
        this.getChildren().add(new FlightSingleItem());
    }

    @Override
    protected FlightSingleItem createChildItem() {
        FlightSingleItem childItem = new FlightSingleItem();
        if (!this.getChildren().isEmpty()) {
            FlightSingleItem previousItem = this.getChildren().get(this.getChildren().size() - 1);
            childItem.departureAirportCodeProperty().setValue(previousItem.arrivalAirportCodeProperty().getValue());
            childItem.startDateProperty().setValue(previousItem.endDateProperty().getValue());
            childItem.bookingCodeProperty().setValue(previousItem.bookingCodeProperty().getValue());
        }
        return childItem;
    }

}
