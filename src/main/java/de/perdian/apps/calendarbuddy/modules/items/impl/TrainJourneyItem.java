package de.perdian.apps.calendarbuddy.modules.items.impl;

public class TrainJourneyItem extends JourneyItem<TrainItem> {

    @Override
    protected TrainItem createChildItem() {
        TrainItem childItem = new TrainItem();
        return childItem;
    }

}
