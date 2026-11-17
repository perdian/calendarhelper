package de.perdian.apps.calendarbuddy.modules.items.impl;

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
