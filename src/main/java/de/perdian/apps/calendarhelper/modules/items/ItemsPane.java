package de.perdian.apps.calendarhelper.modules.items;

import de.perdian.apps.calendarhelper.modules.items.impl.airtravel.AirtravelFlightItem;
import de.perdian.apps.calendarhelper.modules.items.impl.airtravel.AirtravelJourneyItem;
import de.perdian.apps.calendarhelper.modules.items.impl.airtravel.AirtravelJourneyTemplate;
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

    @SuppressWarnings("unchecked")
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
                .map(ServiceLoader.Provider::get)
                .forEach(template -> {
                    Button newEditorItemButton = new Button(template.getTitle(), new FontIcon(template.getIcon()));
                    newEditorItemButton.setOnAction(event -> this.addItemFromTemplate(template));
                    newEditorItemButton.setFocusTraversable(false);
                    leftButtonsPane.getChildren().add(newEditorItemButton);
                });

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

        AirtravelFlightItem airtravelFlightItem1 = new AirtravelFlightItem();
        airtravelFlightItem1.airlineCodeProperty().setValue("LH");
        airtravelFlightItem1.flightNumberProperty().setValue("1234");
        airtravelFlightItem1.airplaneTypeProperty().setValue("Airbus A380");
        airtravelFlightItem1.seatsProperty().setValue("15A");
        airtravelFlightItem1.departureAirportCodeProperty().setValue("CGN");
        airtravelFlightItem1.startDateProperty().setValue(LocalDate.now());
        airtravelFlightItem1.startTimeProperty().setValue(LocalTime.of(10, 0));
        airtravelFlightItem1.arrivalAirportCodeProperty().setValue("JFK");
        airtravelFlightItem1.endDateProperty().setValue(LocalDate.now());
        airtravelFlightItem1.endTimeProperty().setValue(LocalTime.of(14, 0));
        AirtravelFlightItem airtravelFlightItem2 = new AirtravelFlightItem();
        airtravelFlightItem2.airlineCodeProperty().setValue("UA");
        airtravelFlightItem2.flightNumberProperty().setValue("42");
        airtravelFlightItem2.departureAirportCodeProperty().setValue("JFK");
        airtravelFlightItem2.startDateProperty().setValue(LocalDate.now());
        airtravelFlightItem2.startTimeProperty().setValue(LocalTime.of(16, 0));
        airtravelFlightItem2.arrivalAirportCodeProperty().setValue("LAX");
        airtravelFlightItem2.endDateProperty().setValue(LocalDate.now());
        airtravelFlightItem2.endTimeProperty().setValue(LocalTime.of(23, 0));
        AirtravelJourneyTemplate airtravelJourneyTemplate = new AirtravelJourneyTemplate();
        AirtravelJourneyItem airtravelJourneyItem = this.addItemFromTemplate(airtravelJourneyTemplate);
        airtravelJourneyItem.getChildren().setAll(airtravelFlightItem1, airtravelFlightItem2);

    }

    private <T extends Item> T addItemFromTemplate(ItemTemplate<T> itemTemplate) {

        T item = itemTemplate.createItem();
        Pane editorItemPane = itemTemplate.createItemPane(item);
        editorItemPane.setPadding(new Insets(10, 0, 5, 0));

        Button removeItemButton = new Button("", new FontIcon(MaterialDesignD.DELETE));
        removeItemButton.setTooltip(new Tooltip("Remove item"));
        removeItemButton.setOnAction(event -> this.removeEditorItem(item));
        removeItemButton.setFocusTraversable(false);

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
