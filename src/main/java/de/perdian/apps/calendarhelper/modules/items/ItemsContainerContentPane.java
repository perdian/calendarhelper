package de.perdian.apps.calendarhelper.modules.items;

import de.perdian.apps.calendarhelper.support.fx.components.ComponentFactory;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

class ItemsContainerContentPane extends BorderPane {

    ItemsContainerContentPane(ObservableList<Item> items, ItemDefaults itemDefaults, ComponentFactory componentFactory) {

        VBox itemsContainer = new VBox(5);
        itemsContainer.setPadding(new Insets(5, 5, 5, 5));
        ScrollPane itemsContainerScrollPane = new ScrollPane(itemsContainer);
        itemsContainerScrollPane.setStyle("-fx-background-color: transparent;");
        itemsContainerScrollPane.setFitToWidth(true);
        GridPane.setHgrow(itemsContainer, Priority.ALWAYS);
        GridPane.setVgrow(itemsContainer, Priority.ALWAYS);
        this.setCenter(itemsContainerScrollPane);

        Map<Item, ItemsContainerItemWrapperPane> wrapperPanesByItems = new HashMap<>();
        items.addListener((ListChangeListener.Change<? extends Item> changeEvent) -> {
            while (changeEvent.next()) {
                for (Item newItem : changeEvent.getAddedSubList()) {
                    ItemsContainerItemWrapperPane wrapperPane = new ItemsContainerItemWrapperPane(newItem, items, itemDefaults, componentFactory);
                    itemsContainer.getChildren().add(wrapperPane);
                    wrapperPanesByItems.put(newItem, wrapperPane);
                }
                for (Item removedItem : changeEvent.getRemoved()) {
                    ItemsContainerItemWrapperPane wrapperPaneForItem = wrapperPanesByItems.remove(removedItem);
                    if (wrapperPaneForItem != null) {
                        itemsContainer.getChildren().remove(wrapperPaneForItem);
                    }
                }
            }
        });

    }

}
