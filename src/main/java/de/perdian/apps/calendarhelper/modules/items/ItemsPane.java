package de.perdian.apps.calendarhelper.modules.items;

import de.perdian.apps.calendarhelper.modules.items.support.AbstractParentItem;
import de.perdian.apps.calendarhelper.support.fx.CalendarHelperDialogs;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignD;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

import java.util.*;
import java.util.function.Consumer;

public class ItemsPane extends GridPane {

    private Map<Item, Region> itemToRegionMap = null;
    private ObservableList<Item> items = null;
    private VBox itemsContainer = null;
    private ItemDefaults itemDefaults = null;

    public ItemsPane(ObservableList<Item> items, ItemDefaults itemDefaults) {

        this.setItemToRegionMap(new HashMap<>());
        this.setItems(items);
        this.setItemDefaults(itemDefaults);

        Label newItemTitleLabel = new Label("New item");
        newItemTitleLabel.setMaxHeight(Double.MAX_VALUE);
        newItemTitleLabel.setAlignment(Pos.CENTER);
        newItemTitleLabel.setPadding(new Insets(0, 10, 0, 4));
        HBox leftButtonsPane = new HBox(2);
        leftButtonsPane.getChildren().add(newItemTitleLabel);
        leftButtonsPane.getChildren().addAll(this.createActionButtons());

        Button removeAllItemsButton = new Button("Remove all items", new FontIcon(MaterialDesignD.DELETE_EMPTY));
        removeAllItemsButton.disableProperty().bind(Bindings.isEmpty(items));
        removeAllItemsButton.setOnAction(event -> this.removeAllItems());
        removeAllItemsButton.setFocusTraversable(false);
        HBox rightButtonsPane = new HBox(2);
        rightButtonsPane.getChildren().addAll(removeAllItemsButton);

        BorderPane buttonPane = new BorderPane();
        buttonPane.setPadding(new Insets(5, 5, 5, 5));
        buttonPane.setLeft(leftButtonsPane);
        buttonPane.setRight(rightButtonsPane);
        GridPane.setHgrow(buttonPane, Priority.ALWAYS);

        VBox itemsContainer = new VBox(5);
        itemsContainer.setPadding(new Insets(5, 5, 5, 5));
        ScrollPane itemsContainerScrollPane = new ScrollPane(itemsContainer);
        itemsContainerScrollPane.setStyle("-fx-background-color: transparent;");
        itemsContainerScrollPane.setFitToWidth(true);
        GridPane.setHgrow(itemsContainer, Priority.ALWAYS);
        GridPane.setVgrow(itemsContainer, Priority.ALWAYS);
        this.setItemsContainer(itemsContainer);

        this.add(buttonPane, 0, 0, 1, 1);
        this.add(new Separator(), 0, 1, 1, 1);
        this.add(itemsContainerScrollPane, 0, 2, 1, 1);

    }

    private List<Button> createActionButtons() {
        List<Button> actionButtons = new ArrayList<>();
        ItemsActionsRegistry actionsRegistry = (title, icon, action) -> {
            Consumer<List<ItemTemplate<Item>>> templatesConsumer = templates -> this.addItemsFromTemplates(templates);
            EventHandler<ActionEvent> actionEventHandler = event -> {
                try {
                    action.contributeTemplates(templatesConsumer);
                } catch (Exception e) {
                    CalendarHelperDialogs.showErrorDialog("Cannot execute action", "Cannot execute action", e);
                }
            };
            Button actionButton = new Button(title, new FontIcon(icon));
            actionButton.setOnAction(actionEventHandler);
            actionButtons.add(actionButton);
        };
        ServiceLoader.load(ItemsActionsContributor.class).stream()
            .map(ServiceLoader.Provider::get)
            .forEach(registryContributor -> registryContributor.contributeActionsTo(actionsRegistry));
        return actionButtons;

    }

    private List<Button> createAdditionalButtonsForItem(Item item) {
        List<Button> buttonList = new ArrayList<>();
        if (item instanceof AbstractParentItem<?> parentItem) {
            Button newChildButton = new Button("", new FontIcon(MaterialDesignP.PLUS));
            newChildButton.setTooltip(new Tooltip("New child"));
            newChildButton.setOnAction(event -> parentItem.appendChild(this.getItemDefaults()));
            newChildButton.setFocusTraversable(false);
            buttonList.add(newChildButton);
        }
        return buttonList;
    }

    private <T extends Item, C extends Item> void addItemsFromTemplates(List<ItemTemplate<T>> itemTemplates) {
        for (ItemTemplate<T> itemTemplate : itemTemplates) {

            T item = itemTemplate.createItem(this.getItemDefaults());
            Pane itemPane = itemTemplate.createItemPane(item);
            itemPane.setPadding(new Insets(10, 0, 5, 0));

            Button removeItemButton = new Button("", new FontIcon(MaterialDesignD.DELETE));
            removeItemButton.setTooltip(new Tooltip("Remove item"));
            removeItemButton.setOnAction(event -> this.removeItem(item));
            removeItemButton.setFocusTraversable(false);

            List<Button> additionalButtons = this.createAdditionalButtonsForItem(item);
            HBox titleButtonsPane = new HBox(2);
            if (additionalButtons != null && !additionalButtons.isEmpty()) {
                Separator separator = new Separator(Orientation.VERTICAL);
                separator.setPadding(new Insets(0, 2.5, 0, 5));
                titleButtonsPane.getChildren().addAll(additionalButtons);
                titleButtonsPane.getChildren().add(separator);
            }
            titleButtonsPane.getChildren().add(removeItemButton);
            Label titleLabel = new Label(itemTemplate.createTitle(), new FontIcon(itemTemplate.createIcon()));
            titleLabel.setAlignment(Pos.CENTER_LEFT);
            titleLabel.setMaxHeight(Double.MAX_VALUE);
            BorderPane titlePane = new BorderPane();
            titlePane.setLeft(titleLabel);
            titlePane.setRight(titleButtonsPane);

            BorderPane wrapperPane = new BorderPane();
            wrapperPane.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            wrapperPane.setPadding(new Insets(5, 10, 5, 10));
            wrapperPane.setTop(titlePane);
            wrapperPane.setCenter(itemPane);

            this.getItemsContainer().getChildren().add(wrapperPane);
            this.getItemToRegionMap().put(item, wrapperPane);
            this.getItems().add(item);

        }
    }

    private void removeItem(Item item) {
        Region regionForItem = this.getItemToRegionMap().remove(item);
        if (regionForItem != null) {
            Platform.runLater(() -> this.getItemsContainer().getChildren().remove(regionForItem));
        }
        this.getItems().remove(item);
    }

    private void removeAllItems() {
        this.getItemToRegionMap().clear();
        Platform.runLater(() -> this.getItemsContainer().getChildren().clear());
        this.getItems().clear();
    }

    private Map<Item, Region> getItemToRegionMap() {
        return this.itemToRegionMap;
    }
    private void setItemToRegionMap(Map<Item, Region> itemToRegionMap) {
        this.itemToRegionMap = itemToRegionMap;
    }

    private ObservableList<Item> getItems() {
        return this.items;
    }
    private void setItems(ObservableList<Item> items) {
        this.items = items;
    }

    private VBox getItemsContainer() {
        return this.itemsContainer;
    }
    private void setItemsContainer(VBox itemsContainer) {
        this.itemsContainer = itemsContainer;
    }

    private ItemDefaults getItemDefaults() {
        return this.itemDefaults;
    }
    private void setItemDefaults(ItemDefaults itemDefaults) {
        this.itemDefaults = itemDefaults;
    }

}
