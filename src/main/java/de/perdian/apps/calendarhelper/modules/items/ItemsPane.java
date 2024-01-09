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

    private Map<Item, Region> editorItemToRegionMap = null;
    private ObservableList<Item> editorItems = null;
    private VBox editorItemsContainer = null;
    private ItemDefaults itemDefaults = null;

    public ItemsPane(ObservableList<Item> editorItems, ItemDefaults itemDefaults) {

        Map<Item, Region> editorItemToRegionMap = new HashMap<>();
        this.setEditorItemToRegionMap(editorItemToRegionMap);
        this.setEditorItems(editorItems);
        this.setItemDefaults(itemDefaults);

        HBox leftButtonsPane = new HBox(2);
        Label newItemTitleLabel = new Label("New item");
        newItemTitleLabel.setMaxHeight(Double.MAX_VALUE);
        newItemTitleLabel.setAlignment(Pos.CENTER);
        newItemTitleLabel.setPadding(new Insets(0, 10, 0, 4));
        leftButtonsPane.getChildren().add(newItemTitleLabel);

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
            leftButtonsPane.getChildren().add(actionButton);
        };
        ServiceLoader.load(ItemsActionsContributor.class).stream()
            .map(ServiceLoader.Provider::get)
            .forEach(registryContributor -> registryContributor.contributeActionsTo(actionsRegistry));

        Button removeAllEditorItemsButton = new Button("Remove all items", new FontIcon(MaterialDesignD.DELETE_EMPTY));
        removeAllEditorItemsButton.disableProperty().bind(Bindings.isEmpty(editorItems));
        removeAllEditorItemsButton.setOnAction(event -> this.removeAllEditorItems());
        removeAllEditorItemsButton.setFocusTraversable(false);
        HBox rightButtonsPane = new HBox(2);
        rightButtonsPane.getChildren().addAll(removeAllEditorItemsButton);

        BorderPane buttonPane = new BorderPane();
        buttonPane.setPadding(new Insets(5, 5, 5, 5));
        buttonPane.setLeft(leftButtonsPane);
        buttonPane.setRight(rightButtonsPane);
        GridPane.setHgrow(buttonPane, Priority.ALWAYS);

        VBox editorItemsContainer = new VBox(5);
        editorItemsContainer.setPadding(new Insets(5, 5, 5, 5));
        ScrollPane editorItemsContainerScrollPane = new ScrollPane(editorItemsContainer);
        editorItemsContainerScrollPane.setStyle("-fx-background-color: transparent;");
        editorItemsContainerScrollPane.setFitToWidth(true);
        GridPane.setHgrow(editorItemsContainer, Priority.ALWAYS);
        GridPane.setVgrow(editorItemsContainer, Priority.ALWAYS);
        this.setEditorItemsContainer(editorItemsContainer);

        Separator buttonSeparator = new Separator();

        this.add(buttonPane, 0, 0, 1, 1);
        this.add(buttonSeparator, 0, 1, 1, 1);
        this.add(editorItemsContainerScrollPane, 0, 2, 1, 1);

    }

    private <T extends Item, C extends Item> void addItemsFromTemplates(List<ItemTemplate<T>> itemTemplates) {
        for (ItemTemplate<T> itemTemplate : itemTemplates) {

            T item = itemTemplate.createItem(this.getItemDefaults());
            Pane itemPane = itemTemplate.createItemPane(item);
            itemPane.setPadding(new Insets(10, 0, 5, 0));

            Button removeItemButton = new Button("", new FontIcon(MaterialDesignD.DELETE));
            removeItemButton.setTooltip(new Tooltip("Remove item"));
            removeItemButton.setOnAction(event -> this.removeEditorItem(item));
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

            this.getEditorItemsContainer().getChildren().add(wrapperPane);
            this.getEditorItemToRegionMap().put(item, wrapperPane);
            this.getEditorItems().add(item);

        }
    }

    private <T extends Item, C extends Item> List<Button> createAdditionalButtonsForItem(T item) {
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

    private void removeEditorItem(Item editorItem) {
        Region regionForEditorItem = this.getEditorItemToRegionMap().remove(editorItem);
        if (regionForEditorItem != null) {
            Platform.runLater(() -> this.getEditorItemsContainer().getChildren().remove(regionForEditorItem));
        }
        this.getEditorItems().remove(editorItem);
    }

    private void removeAllEditorItems() {
        this.getEditorItemToRegionMap().clear();
        Platform.runLater(() -> this.getEditorItemsContainer().getChildren().clear());
        this.getEditorItems().clear();
    }

    public Map<Item, Region> getEditorItemToRegionMap() {
        return this.editorItemToRegionMap;
    }
    private void setEditorItemToRegionMap(Map<Item, Region> editorItemToRegionMap) {
        this.editorItemToRegionMap = editorItemToRegionMap;
    }

    public ObservableList<Item> getEditorItems() {
        return this.editorItems;
    }
    private void setEditorItems(ObservableList<Item> editorItems) {
        this.editorItems = editorItems;
    }

    public VBox getEditorItemsContainer() {
        return this.editorItemsContainer;
    }
    private void setEditorItemsContainer(VBox editorItemsContainer) {
        this.editorItemsContainer = editorItemsContainer;
    }

    private ItemDefaults getItemDefaults() {
        return this.itemDefaults;
    }
    private void setItemDefaults(ItemDefaults itemDefaults) {
        this.itemDefaults = itemDefaults;
    }

}
