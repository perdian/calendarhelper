package de.perdian.apps.calendarbuddy.modules.items;

import de.perdian.apps.calendarbuddy.modules.items.impl.TrainJourneyItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

@Component
public class ItemListModel {

    private final ObservableList<Item> items;

    ItemListModel() {
        this.items = FXCollections.observableArrayList();

        TrainJourneyItem trainJourneyItem = new TrainJourneyItem();
        this.items.addAll(trainJourneyItem);
        //FlightJourneyItem flightJourneyItem = new FlightJourneyItem();
        //this.items.addAll(trainJourneyItem);
    }

    public ObservableList<Item> getItems() {
        return this.items;
    }

}
