package de.perdian.apps.calendarbuddy.modules.items;

import de.perdian.apps.calendarbuddy.CalendarBuddyKeyRegistry;
import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.Map;
import java.util.WeakHashMap;

public class ItemListModelPane extends BorderPane {

    private final Map<Item, Pane> itemPanesByItem = new WeakHashMap<>();

    public ItemListModelPane(ItemListModel itemListModel, ItemTypeRegistry itemTypeRegistry, CalendarBuddyKeyRegistry keyRegistry) {

        ComponentFactory componentFactory = new ComponentFactory();
        Button clearAllItemsButton = componentFactory.createButton("", componentFactory.createKeyboardShortcutPane("⌘N", "Clear all"));
        clearAllItemsButton.setOnAction(_ -> itemListModel.getItems().clear());
        clearAllItemsButton.setFocusTraversable(false);
        HBox actionsPane = new HBox(5);
        actionsPane.getChildren().add(clearAllItemsButton);
        this.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.isMetaDown() && keyEvent.getCode() == KeyCode.N) {
                clearAllItemsButton.fire();
                keyEvent.consume();
            }
        });

        ItemTypePane itemTypePane = new ItemTypePane(itemListModel, itemTypeRegistry, keyRegistry);
        BorderPane topPane = new BorderPane();
        topPane.setCenter(itemTypePane);
        topPane.setRight(actionsPane);
        topPane.setPadding(new Insets(5, 5, 5, 5));
        topPane.setStyle("-fx-border-color: transparent  transparent  -fx-box-border transparent; -fx-border-width: 0 0 1 0;");
        this.setTop(topPane);

        ListView<Item> itemListView = new ListView<>(itemListModel.getItems());
        itemListView.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    this.setText(null);
                    this.setGraphic(null);
                } else {
                    ItemType<Item> itemType = itemTypeRegistry.resolveItemType(item);
                    Pane itemPane = itemType.getItemPaneFactory().createItemWrapperPane(item, itemType, itemListModel);
                    this.setText(null);
                    this.setGraphic(itemPane);
                    Platform.runLater(() -> itemPane.requestFocus());
                }
            }
        });
        itemListView.setStyle("-fx-background-color: transparent; fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-selection-bar: transparent; -fx-selection-bar-non-focused: transparent; -fx-focus-traversable: false;");
        itemListView.setSelectionModel(null);
        this.setCenter(itemListView);

    }

    private Map<Item, Pane> getItemPanesByItem() {
        return this.itemPanesByItem;
    }

}
