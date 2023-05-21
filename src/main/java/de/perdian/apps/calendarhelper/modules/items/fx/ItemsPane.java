package de.perdian.apps.calendarhelper.modules.items.fx;

import de.perdian.apps.calendarhelper.modules.items.model.Item;
import de.perdian.apps.calendarhelper.modules.items.model.ItemTemplate;
import de.perdian.apps.calendarhelper.modules.items.model.impl.TrainJourneyItem;
import de.perdian.apps.calendarhelper.modules.items.model.impl.TrainJourneyTemplate;
import de.perdian.apps.calendarhelper.modules.items.model.impl.TrainRideItem;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignD;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

public class ItemsPane extends GridPane {

    private Map<Item, Region> editorItemToRegionMap = null;
    private ObservableList<Item> editorItems = null;
    private VBox editorItemsContainer = null;

    public ItemsPane(ObservableList<Item> editorItems) {

        Map<Item, Region> editorItemToRegionMap = new HashMap<>();
        this.setEditorItemToRegionMap(editorItemToRegionMap);
        this.setEditorItems(editorItems);

        HBox leftButtonsPane = new HBox(2);
        Label newItemTitleLabel = new Label("New item");
        newItemTitleLabel.setMaxHeight(Double.MAX_VALUE);
        newItemTitleLabel.setAlignment(Pos.CENTER);
        newItemTitleLabel.setPadding(new Insets(0, 5, 0, 4));
        leftButtonsPane.getChildren().add(newItemTitleLabel);

        ServiceLoader.load(ItemTemplate.class).stream()
                .map(provider -> provider.get())
                .forEach(template -> {
                    Button newEditorItemButton = new Button(template.getTitle(), new FontIcon(template.getIcon()));
                    newEditorItemButton.setOnAction(event -> this.addItemFromTemplate(template));
                    leftButtonsPane.getChildren().add(newEditorItemButton);
                });

        Button removeAllEditorItemsButton = new Button("Remove all items", new FontIcon(MaterialDesignD.DELETE_EMPTY));
        removeAllEditorItemsButton.setOnAction(event -> this.removeAllEditorItems());
        removeAllEditorItemsButton.disableProperty().bind(Bindings.isEmpty(editorItems));
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

        TrainJourneyTemplate trainJourneyTemplate = new TrainJourneyTemplate();
        TrainRideItem trainRideItem1 = new TrainRideItem();
        trainRideItem1.typeProperty().setValue("ICE");
        trainRideItem1.numberProperty().setValue("1234");
        trainRideItem1.departureStationProperty().setValue("Köln Hbf");
        trainRideItem1.startDateProperty().setValue(LocalDate.now());
        trainRideItem1.startTimeProperty().setValue(LocalTime.of(11, 11));
        trainRideItem1.arrivalStationProperty().setValue("Frankfurt Flughafen");
        trainRideItem1.endDateProperty().setValue(LocalDate.now());
        trainRideItem1.endTimeProperty().setValue(LocalTime.of(12, 12));
        TrainRideItem trainRideItem2 = new TrainRideItem();
        trainRideItem2.typeProperty().setValue("ICE");
        trainRideItem2.numberProperty().setValue("5678");
        trainRideItem2.departureStationProperty().setValue("Frankfurt Flughafen");
        trainRideItem2.startDateProperty().setValue(LocalDate.now());
        trainRideItem2.startTimeProperty().setValue(LocalTime.of(12, 00));
        trainRideItem2.arrivalStationProperty().setValue("Stuttgart Hbf");
        trainRideItem2.endDateProperty().setValue(LocalDate.now());
        trainRideItem2.endTimeProperty().setValue(LocalTime.of(14, 00));
        TrainJourneyItem trainJourneyItem = this.addItemFromTemplate(trainJourneyTemplate);
        trainJourneyItem.getChildren().setAll(trainRideItem1, trainRideItem2);

    }

    private <T extends Item> T addItemFromTemplate(ItemTemplate<T> itemTemplate) {

        T item = itemTemplate.createItem();
        Pane editorItemPane = itemTemplate.createItemPane(item);
        editorItemPane.setPadding(new Insets(10, 0, 5, 0));

        Button removeItemButton = new Button("", new FontIcon(MaterialDesignD.DELETE));
        removeItemButton.setTooltip(new Tooltip("Remove item"));
        removeItemButton.setOnAction(event -> this.removeEditorItem(item));

        HBox titleButtonsPane = new HBox(2);
        List<Button> additionalButtons = itemTemplate.createAdditionalButtons(item);
        if (additionalButtons != null && !additionalButtons.isEmpty()) {
            Separator separator = new Separator(Orientation.VERTICAL);
            separator.setPadding(new Insets(0, 2.5, 0, 5));
            titleButtonsPane.getChildren().addAll(additionalButtons);
            titleButtonsPane.getChildren().add(separator);
        }
        titleButtonsPane.getChildren().add(removeItemButton);
        Label titleLabel = new Label(itemTemplate.getTitle(), new FontIcon(itemTemplate.getIcon()));
        titleLabel.setAlignment(Pos.CENTER_LEFT);
        titleLabel.setMaxHeight(Double.MAX_VALUE);
        BorderPane titlePane = new BorderPane();
        titlePane.setLeft(titleLabel);
        titlePane.setRight(titleButtonsPane);

        BorderPane wrapperPane = new BorderPane();
        wrapperPane.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        wrapperPane.setPadding(new Insets(5, 10, 5, 10));
        wrapperPane.setTop(titlePane);
        wrapperPane.setCenter(editorItemPane);

        this.getEditorItemsContainer().getChildren().add(wrapperPane);
        this.getEditorItemToRegionMap().put(item, wrapperPane);
        this.getEditorItems().add(item);
        return item;

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

}
