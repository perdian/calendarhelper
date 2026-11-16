package de.perdian.apps.calendarbuddy.modules.items.impl;

import de.perdian.apps.calendarbuddy.modules.items.Item;
import de.perdian.apps.calendarbuddy.modules.items.ItemListModel;
import de.perdian.apps.calendarbuddy.modules.items.ItemPaneFactory;
import de.perdian.apps.calendarbuddy.modules.items.ItemType;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

import java.util.HashMap;
import java.util.Map;

public abstract class JourneyItemPaneFactory<I extends JourneyItem<C>, C extends Item> extends ItemPaneFactory<I> {

    @Override
    protected HBox createActionButtonBox(I item, ItemType<I> itemType, ItemListModel itemListModel) {

        Button addChildButton = new Button("", new FontIcon(MaterialDesignP.PLUS));
        addChildButton.setFocusTraversable(false);
        addChildButton.setTooltip(new Tooltip("Add child item"));
        addChildButton.setOnAction(_ -> item.appendChildItem());

        HBox actionButtonBox = super.createActionButtonBox(item, itemType, itemListModel);
        actionButtonBox.getChildren().addFirst(addChildButton);
        return actionButtonBox;

    }

    @Override
    public Pane createItemPane(I item, ItemType<I> itemType, ItemListModel itemListModel) {
        Pane itemPane = super.createItemPane(item, itemType, itemListModel);
        itemPane.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.isMetaDown() && keyEvent.getCode() == KeyCode.BACK_SPACE) {
                itemListModel.getItems().remove(item);
                keyEvent.consume();
            } else if (keyEvent.isMetaDown() && keyEvent.getCode() == KeyCode.PLUS) {
                item.appendChildItem();
            }
        });
        return itemPane;
    }

    @Override
    protected Pane createItemContentPane(I item) {
        Map<C, Pane> childrenItemPanes = new HashMap<>();
        VBox childrenItemsPane = new VBox(10);
        for (C childItem : item.getChildren()) {
            Pane childItemPane = this.createChildItemPane(item, childItem);
            childrenItemPanes.put(childItem, childItemPane);
            childrenItemsPane.getChildren().add(childItemPane);
        }
        item.getChildren().addListener((ListChangeListener<? super C>) change -> {
            while (change.next()) {
                for (C addedChildItem : change.getAddedSubList()) {
                    Pane childItemPane = childrenItemPanes.get(addedChildItem);
                    if (childItemPane == null) {
                        Pane newChildItemPane = this.createChildItemPane(item, addedChildItem);
                        childrenItemPanes.put(addedChildItem, newChildItemPane);
                        childrenItemsPane.getChildren().add(newChildItemPane);
                        Platform.runLater(() -> newChildItemPane.requestFocus());
                    }
                }
                for (C removedChildItem : change.getRemoved()) {
                    Pane childItemPane = childrenItemPanes.remove(removedChildItem);
                    if (childItemPane != null) {
                        int indexOfChildItemPane = childrenItemsPane.getChildren().indexOf(childItemPane);
                        childrenItemsPane.getChildren().remove(childItemPane);
                        if (indexOfChildItemPane < childrenItemsPane.getChildren().size()) {
                            childrenItemsPane.getChildren().get(indexOfChildItemPane).requestFocus();
                        } else if (!childrenItemsPane.getChildren().isEmpty()) {
                            childrenItemsPane.getChildren().getLast().requestFocus();
                        }
                    }
                }
            }
        });
        childrenItemsPane.focusedProperty().addListener((_, _, newValue) -> {
            if (newValue) {
                if (!childrenItemsPane.getChildren().isEmpty()) {
                    childrenItemsPane.getChildren().get(0).requestFocus();
                }
            }
        });
        return childrenItemsPane;
    }

    protected abstract Pane createChildItemPane(I parentItem, C childItem);

}
