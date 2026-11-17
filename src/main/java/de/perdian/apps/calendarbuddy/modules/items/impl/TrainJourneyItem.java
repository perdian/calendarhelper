package de.perdian.apps.calendarbuddy.modules.items.impl;

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
