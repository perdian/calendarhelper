package de.perdian.apps.calendarbuddy.modules.items;

import de.perdian.apps.calendarbuddy.CalendarBuddyKeyRegistry;
import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.Objects;

class ItemTypePane extends BorderPane {

    ItemTypePane(ItemListModel itemListModel, ItemTypeRegistry itemTypeListModel, CalendarBuddyKeyRegistry keyRegistry) {

        ComponentFactory componentFactory = new ComponentFactory();

        HBox hbox = new HBox(5);
        for (int i=0; i < itemTypeListModel.getItemTypes().size(); i++) {
            String itemKeyboardValue = String.valueOf(i+1);
            ItemType<?> itemType = itemTypeListModel.getItemTypes().get(i);
            HBox buttonTitleBox = new HBox(5);
            buttonTitleBox.setAlignment(Pos.CENTER_LEFT);
            buttonTitleBox.getChildren().add(componentFactory.createKeyboardShortcutPane("⌘" + itemKeyboardValue, itemType.getTitle()));
            Button addItemButton = componentFactory.createButton("", buttonTitleBox);
            addItemButton.setFocusTraversable(false);
            addItemButton.setOnAction(_ -> this.onAddItem(itemListModel, itemType));
            hbox.getChildren().add(addItemButton);
            keyRegistry.addKeyEventHandler(keyEvent -> {
                if (Objects.equals(keyEvent.getText(), itemKeyboardValue) && keyEvent.isShortcutDown()) {
                    addItemButton.fire();
                }
            });
        }
        this.setCenter(hbox);

    }

    private void onAddItem(ItemListModel itemListModel, ItemType<?> itemType) {
        Item newItem = itemType.getNewItemSupplier().get();
        itemListModel.getItems().add(newItem);
    }

}
