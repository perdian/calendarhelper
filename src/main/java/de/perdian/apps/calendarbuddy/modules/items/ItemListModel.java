package de.perdian.apps.calendarbuddy.modules.items;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

@Component
public class ItemListModel {

    private final ObservableList<Item> items;

    ItemListModel() {
        this.items = FXCollections.observableArrayList();
    }

    public ObservableList<Item> getItems() {
        return this.items;
    }

}
