package de.perdian.apps.calendarhelper.modules.items.templates;

import de.perdian.apps.calendarhelper.modules.items.Item;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.function.Consumer;

class ItemTemplatePane extends GridPane {

    private final BooleanProperty selected = new SimpleBooleanProperty(false);

    private Item item = null;

    public ItemTemplatePane(Item item, Pane itemEditorPane, Consumer<Item> itemConsumer) {
        this.setItem(item);

        ToggleButton selectButton = new ToggleButton("Select");
        selectButton.selectedProperty().bindBidirectional(this.selectedProperty());
        selectButton.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(selectButton, Priority.ALWAYS);
        Button createItemsButton = new Button("Create Item");
        createItemsButton.setOnAction(_ -> itemConsumer.accept(item));
        createItemsButton.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(createItemsButton, Priority.ALWAYS);

        GridPane actionsPane = new GridPane();
        actionsPane.setVgap(5);
        actionsPane.add(selectButton, 0, 0, 1, 1);
        actionsPane.add(createItemsButton, 0, 1, 1, 1);
        BorderPane actionsWrapperPane = new BorderPane(actionsPane);
        actionsWrapperPane.setPadding(new Insets(10, 10, 10, 10));
        actionsWrapperPane.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        BorderPane itemEditorWrapperPane = new BorderPane(itemEditorPane);
        itemEditorWrapperPane.setPadding(new Insets(10, 10, 10, 10));
        itemEditorWrapperPane.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        GridPane.setHgrow(itemEditorWrapperPane, Priority.ALWAYS);

        this.add(actionsWrapperPane, 0, 0, 1, 1);
        this.add(itemEditorWrapperPane, 1, 0, 1, 1);
        this.setHgap(5);

    }

    BooleanProperty selectedProperty() {
        return this.selected;
    }

    Item getItem() {
        return this.item;
    }
    private void setItem(Item item) {
        this.item = item;
    }

}
