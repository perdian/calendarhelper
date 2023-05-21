package de.perdian.apps.calendarhelper.fx.modules.editor;

import de.perdian.apps.calendarhelper.fx.modules.editor.impl.items.GenericItem;
import de.perdian.apps.calendarhelper.fx.modules.editor.impl.items.GenericTemplateFactory;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

public class EditorPane extends GridPane {

    private Map<EditorItem, Region> editorItemToRegionMap = null;
    private ObservableList<EditorItem> editorItems = null;
    private VBox editorItemsContainer = null;

    public EditorPane(ObservableList<EditorItem> editorItems) {

        Map<EditorItem, Region> editorItemToRegionMap = new HashMap<>();
        this.setEditorItemToRegionMap(editorItemToRegionMap);
        this.setEditorItems(editorItems);

        HBox leftButtonsPane = new HBox(2);
        Label newItemTitleLabel = new Label("New item");
        newItemTitleLabel.setMaxHeight(Double.MAX_VALUE);
        newItemTitleLabel.setAlignment(Pos.CENTER);
        newItemTitleLabel.setPadding(new Insets(0, 5, 0, 4));
        leftButtonsPane.getChildren().add(newItemTitleLabel);

        ServiceLoader.load(EditorTemplateFactory.class).stream()
                .map(provider -> provider.get().createTemplate())
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

        GenericTemplateFactory genericTemplateFactory = new GenericTemplateFactory();
        EditorTemplate<GenericItem> genericTemplate = genericTemplateFactory.createTemplate();
        GenericItem conferenceItem = this.addItemFromTemplate(genericTemplate);
        conferenceItem.startDateProperty().setValue(LocalDate.now());
        conferenceItem.summaryProperty().setValue("Conference " + System.currentTimeMillis());

    }

    private <T extends EditorItem> T addItemFromTemplate(EditorTemplate<T> editorTemplate) {

        T editorItem = editorTemplate.getItemSupplier().get();
        Pane editorItemPane = editorTemplate.getPaneSupplier().apply(editorItem);
        editorItemPane.setPadding(new Insets(10, 0, 5, 0));

        Button removeItemButton = new Button("", new FontIcon(MaterialDesignD.DELETE));
        removeItemButton.setTooltip(new Tooltip("Remove item"));
        removeItemButton.setOnAction(event -> this.removeEditorItem(editorItem));

        HBox titleButtonsPane = new HBox(2);
        List<Button> additionalButtons = editorTemplate.getAdditionalButtonsSupplier() == null ? null : editorTemplate.getAdditionalButtonsSupplier().apply(editorItem);
        if (additionalButtons != null && !additionalButtons.isEmpty()) {
            Separator separator = new Separator(Orientation.VERTICAL);
            separator.setPadding(new Insets(0, 2.5, 0, 5));
            titleButtonsPane.getChildren().addAll(additionalButtons);
            titleButtonsPane.getChildren().add(separator);
        }
        titleButtonsPane.getChildren().add(removeItemButton);
        Label titleLabel = new Label(editorTemplate.getTitle(), new FontIcon(editorTemplate.getIcon()));
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
        this.getEditorItemToRegionMap().put(editorItem, wrapperPane);
        this.getEditorItems().add(editorItem);
        return editorItem;

    }

    private void removeEditorItem(EditorItem editorItem) {
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

    public Map<EditorItem, Region> getEditorItemToRegionMap() {
        return this.editorItemToRegionMap;
    }
    private void setEditorItemToRegionMap(Map<EditorItem, Region> editorItemToRegionMap) {
        this.editorItemToRegionMap = editorItemToRegionMap;
    }

    public ObservableList<EditorItem> getEditorItems() {
        return this.editorItems;
    }
    private void setEditorItems(ObservableList<EditorItem> editorItems) {
        this.editorItems = editorItems;
    }

    public VBox getEditorItemsContainer() {
        return this.editorItemsContainer;
    }
    private void setEditorItemsContainer(VBox editorItemsContainer) {
        this.editorItemsContainer = editorItemsContainer;
    }

}
