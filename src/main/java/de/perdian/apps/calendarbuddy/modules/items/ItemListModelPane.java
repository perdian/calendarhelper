package de.perdian.apps.calendarbuddy.modules.items;

import de.perdian.apps.calendarbuddy.CalendarBuddyKeyRegistry;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Map;
import java.util.WeakHashMap;

public class ItemListModelPane extends BorderPane {

    private final Map<Item, Pane> itemPanesByItem = new WeakHashMap<>();

    public ItemListModelPane(ItemListModel itemListModel, ItemTypeRegistry itemTypeRegistry, CalendarBuddyKeyRegistry keyRegistry) {

        ItemTypePane itemTypePane = new ItemTypePane(itemListModel, itemTypeRegistry, keyRegistry);
        itemTypePane.setPadding(new Insets(5, 5, 5, 5));
        itemTypePane.setStyle("-fx-border-color: transparent  transparent  -fx-box-border transparent; -fx-border-width: 0 0 1 0;");
        this.setTop(itemTypePane);

        ListView<Item> itemListView = new ListView<>(itemListModel.getItems());
        itemListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    this.setText(null);
                    this.setGraphic(null);
                } else {
                    ItemType<Item> itemType = itemTypeRegistry.resolveItemType(item);
                    Pane itemPane = itemType.getItemPaneFactory().createItemPane(item, itemType, itemListModel);
                    this.setText(null);
                    this.setGraphic(itemPane);
                    Platform.runLater(() -> itemPane.requestFocus());
                }
            }
        });
        itemListView.setStyle("-fx-background-color: transparent; fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-selection-bar: transparent; -fx-selection-bar-non-focused: transparent; -fx-focus-traversable: false;");
        itemListView.setSelectionModel(null);
        this.setCenter(itemListView);

//        VBox itemListBox = new VBox(5);
//        itemListModel.getItems().addListener((ListChangeListener.Change<? extends Item> change) -> this.onItemListChanged(change, itemListBox, itemListModel, itemTypeRegistry));
//        ScrollPane itemListScoller = new ScrollPane(itemListBox);
//        itemListScoller.setStyle("-fx-background-color: transparent;");
//        itemListScoller.setFitToWidth(true);
//        itemListBox.getChildren().addListener((ListChangeListener.Change<? extends Node> change) -> {
//            while (change.next()) {
//                if (!change.getAddedSubList().isEmpty()) {
//                    itemListScoller.setVvalue(itemListScoller.getVmax());
//                }
//            }
//        });
//        this.setCenter(itemListScoller);

    }

    private synchronized <I extends Item> void onItemListChanged(ListChangeListener.Change<I> change, VBox itemListBox, ItemListModel itemListModel, ItemTypeRegistry itemTypeRegistry) {
        while (change.next()) {
            for (Item removedItem : change.getRemoved()) {
                Pane existingItemPane = this.getItemPanesByItem().remove(removedItem);
                if (existingItemPane != null) {
                    itemListBox.getChildren().remove(existingItemPane);
                }
            }
            for (Item addedItem : change.getAddedSubList()) {
                Pane itemPane = this.getItemPanesByItem().get(addedItem);
                if (itemPane == null) {
                    ItemType<Item> itemType = itemTypeRegistry.resolveItemType(addedItem);
                    itemPane = itemType.getItemPaneFactory().createItemPane(addedItem, itemType, itemListModel);
                    this.getItemPanesByItem().put(addedItem, itemPane);
                    itemListBox.getChildren().add(itemPane);
                }
            }
        }
    }

    private Map<Item, Pane> getItemPanesByItem() {
        return this.itemPanesByItem;
    }

}
