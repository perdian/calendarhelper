package de.perdian.apps.calendarbuddy.modules.items.impl;

import de.perdian.apps.calendarbuddy.modules.items.Item;
import de.perdian.apps.calendarbuddy.modules.items.ItemListModel;
import de.perdian.apps.calendarbuddy.modules.items.ItemPaneFactory;
import de.perdian.apps.calendarbuddy.modules.items.ItemType;
import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

import java.util.HashMap;
import java.util.Map;

public abstract class JourneyItemPaneFactory<I extends JourneyItem<C>, C extends Item> extends ItemPaneFactory<I> {

    @Override
    protected HBox createItemHeaderActions(I item, ItemType<I> itemType, ItemListModel itemListModel) {

        ComponentFactory componentFactory = new ComponentFactory();
        Button addChildButton = componentFactory.createButton("", new FontIcon(MaterialDesignP.PLUS));
        addChildButton.setFocusTraversable(false);
        addChildButton.setTooltip(new Tooltip("Add child item"));
        addChildButton.setOnAction(_ -> item.appendChildItem());

        HBox actionButtonBox = super.createItemHeaderActions(item, itemType, itemListModel);
        actionButtonBox.getChildren().addFirst(addChildButton);
        return actionButtonBox;

    }

    @Override
    public Pane createItemWrapperPane(I item, ItemType<I> itemType, ItemListModel itemListModel) {
        Pane itemPane = super.createItemWrapperPane(item, itemType, itemListModel);
        itemPane.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
        if (keyEvent.isMetaDown() && keyEvent.getCode() == KeyCode.PLUS) {
                item.appendChildItem();
            }
        });
        return itemPane;
    }

    @Override
    protected Pane createItemPane(I item) {
        Map<C, Pane> childrenItemPanes = new HashMap<>();
        VBox childrenItemsPane = new VBox(10);
        for (C childItem : item.getChildren()) {
            Pane childItemPane = this.createChildItemPaneWrapper(childItem, item);
            childrenItemPanes.put(childItem, childItemPane);
            childrenItemsPane.getChildren().add(childItemPane);
        }
        item.getChildren().addListener((ListChangeListener<? super C>) change -> {
            while (change.next()) {
                for (C addedChildItem : change.getAddedSubList()) {
                    Pane childItemPane = childrenItemPanes.get(addedChildItem);
                    if (childItemPane == null) {
                        Pane newChildItemPane = this.createChildItemPaneWrapper(addedChildItem, item);
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

    private Pane createChildItemPaneWrapper(C childItem, I parentItem) {
        Pane childItemPane = this.createChildItemPane(childItem, parentItem);
        GridPane.setHgrow(childItemPane, Priority.ALWAYS);
        Pane childItemHeaderActions = this.createChildItemHeaderActions(childItem, parentItem);
        GridPane itemWrapperPane = new GridPane(10, 0);
        itemWrapperPane.focusedProperty().addListener((_, _, newValue) -> {
            if (newValue) {
                childItemPane.requestFocus();
            }
        });
        itemWrapperPane.add(childItemPane, 0, 0, 1, 1);
        itemWrapperPane.add(childItemHeaderActions, 1, 0, 1, 1);
        itemWrapperPane.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.isMetaDown() && keyEvent.getCode() == KeyCode.MINUS) {
                if (parentItem.removeChild(childItem)) {
                    keyEvent.consume();
                }
            }
        });
        return itemWrapperPane;
    }

    protected abstract Pane createChildItemPane(C childItem, I parentItem);

    protected Pane createChildItemHeaderActions(C childItem, I parentItem) {

        ComponentFactory componentFactory = new ComponentFactory();
        Button removeButton = componentFactory.createButton(new FontIcon(MaterialDesignT.TRASH_CAN));
        removeButton.disableProperty().bind(Bindings.size(parentItem.getChildren()).lessThanOrEqualTo(1));
        removeButton.setOnAction(_ -> parentItem.removeChild(childItem));
        removeButton.setFocusTraversable(false);

        HBox buttonBox = new HBox(2);
        buttonBox.getChildren().add(removeButton);
        return buttonBox;

    }

}
