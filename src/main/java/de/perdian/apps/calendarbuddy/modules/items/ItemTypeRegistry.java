package de.perdian.apps.calendarbuddy.modules.items;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemTypeRegistry {

    private final ObservableList<ItemType<?>> itemTypes;

    @Autowired
    ItemTypeRegistry(List<ItemType<?>> itemTypes) {
        this.itemTypes = FXCollections.observableArrayList(itemTypes);
    }

    @SuppressWarnings("unchecked")
    <I extends Item> ItemType<I> resolveItemType(Item item) {
        for (ItemType<?> itemType : this.getItemTypes()) {
            if (itemType.getItemClass().isAssignableFrom(item.getClass())) {
                return (ItemType<I>) itemType;
            }
        }
        throw new IllegalArgumentException("No ItemType found for item of class: " + item.getClass().getName());
    }

    public ObservableList<ItemType<?>> getItemTypes() {
        return this.itemTypes;
    }

}
