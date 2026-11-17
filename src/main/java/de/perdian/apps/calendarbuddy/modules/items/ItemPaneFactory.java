package de.perdian.apps.calendarbuddy.modules.items;

import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

public abstract class ItemPaneFactory<I extends Item> {

    public Pane createItemWrapperPane(I item, ItemType<I> itemType, ItemListModel itemListModel) {

        ComponentFactory componentFactory = new ComponentFactory();

        Label itemHeaderIconLabel = componentFactory.createLabel("", new FontIcon(itemType.getIcon()));
        itemHeaderIconLabel.setStyle("-fx-font-size: 150%;");
        Label itemHeaderTitleLabel = componentFactory.createLabel(itemType.getTitle());
        GridPane.setHgrow(itemHeaderTitleLabel, Priority.ALWAYS);
        HBox itemHeaderActions = this.createItemHeaderActions(item, itemType, itemListModel);
        GridPane itemHeaderPane = new GridPane(5, 0);
        itemHeaderPane.setPadding(new Insets(0, 0, 5, 0));
        itemHeaderPane.add(itemHeaderIconLabel, 0, 0, 1, 1);
        itemHeaderPane.add(itemHeaderTitleLabel, 1, 0, 1, 1);
        itemHeaderPane.add(itemHeaderActions, 2, 0, 1, 1);
        itemHeaderPane.setStyle("-fx-border-color: transparent transparent -fx-box-border transparent; -fx-border-width: 0 0 1 0;");
        GridPane.setHgrow(itemHeaderPane, Priority.ALWAYS);

        Pane itemContentPane = this.createItemPane(item);
        itemContentPane.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.isMetaDown() && keyEvent.getCode() == KeyCode.BACK_SPACE) {
                itemListModel.getItems().remove(item);
                keyEvent.consume();
            }
        });

        GridPane itemPane = new GridPane(5, 5);
        itemPane.setPadding(new Insets(5, 0, 5, 0));
        itemPane.add(itemHeaderPane, 0, 0, 1, 1);
        itemPane.add(itemContentPane, 0, 1, 1, 1);
        itemPane.focusedProperty().addListener((_, _, newValue) -> {
            if (newValue) {
                itemContentPane.requestFocus();
            }
        });
        return itemPane;

    }

    protected abstract Pane createItemPane(I item);

    protected HBox createItemHeaderActions(I item, ItemType<I> itemType, ItemListModel itemListModel) {
        ComponentFactory componentFactory = new ComponentFactory();
        Button removeButton = componentFactory.createButton("", new FontIcon(MaterialDesignT.TRASH_CAN));
        removeButton.setFocusTraversable(false);
        removeButton.setTooltip(new Tooltip("Remove item"));
        removeButton.setOnAction(event -> itemListModel.getItems().remove(item));
        return new HBox(5, removeButton);
    }

}
