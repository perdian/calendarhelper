package de.perdian.apps.calendarbuddy.modules.items.impl;

public class FlightJourneyItem extends JourneyItem<FlightItem> {

    public FlightJourneyItem() {
        this.getChildren().add(new FlightItem());
    }

    @Override
    protected FlightItem createChildItem() {
        FlightItem childItem = new FlightItem();
        return childItem;
    }

}
