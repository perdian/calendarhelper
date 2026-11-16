package de.perdian.apps.calendarbuddy.modules.items;

import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

public abstract class ItemPaneFactory<I extends Item> {

    public Pane createItemPane(I item, ItemType<I> itemType, ItemListModel itemListModel) {

        ComponentFactory componentFactory = new ComponentFactory();
        Label itemTitleLabel = componentFactory.createLabel(itemType.getTitle(), new FontIcon(itemType.getIcon()));
        GridPane.setHgrow(itemTitleLabel, Priority.ALWAYS);
        HBox actionButtonBox = this.createActionButtonBox(item, itemType, itemListModel);

        Pane itemContentPane = this.createItemContentPane(item);

        GridPane itemPane = new GridPane(5, 5);
        itemPane.setPadding(new Insets(10, 10, 10, 10));
        itemPane.add(itemTitleLabel, 0, 0, 1, 1);
        itemPane.add(actionButtonBox, 1, 0, 1, 1);
        itemPane.add(itemContentPane, 0, 1, 2, 1);
        itemPane.focusedProperty().addListener((_, _, newValue) -> {
            if (newValue) {
                itemContentPane.requestFocus();
            }
        });
        return itemPane;

    }

    protected abstract Pane createItemContentPane(I item);

    protected HBox createActionButtonBox(I item, ItemType<I> itemType, ItemListModel itemListModel) {
        ComponentFactory componentFactory = new ComponentFactory();
        Button removeButton = new Button("", new FontIcon(MaterialDesignT.TRASH_CAN));
        removeButton.setFocusTraversable(false);
        removeButton.setTooltip(new Tooltip("Remove item"));
        removeButton.setOnAction(event -> itemListModel.getItems().remove(item));
        return new HBox(5, removeButton);
    }

}
