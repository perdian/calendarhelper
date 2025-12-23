package de.perdian.apps.calendarhelper.modules.items;

import de.perdian.apps.calendarhelper.modules.items.templates.ShowItemTemplatesDialogAction;
import de.perdian.apps.calendarhelper.support.fx.CalendarHelperDialogs;
import de.perdian.apps.calendarhelper.support.fx.components.ComponentFactory;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignD;
import org.kordamp.ikonli.materialdesign2.MaterialDesignZ;

import java.util.ArrayList;
import java.util.List;

class ItemsContainerActionsPane extends BorderPane {

    ItemsContainerActionsPane(ObservableList<Item> items, ItemDefaults defaults, ComponentFactory componentFactory) {

        List<Button> actionButtons = new ArrayList<>();
        for (ItemsEditor<?> itemsEditor : ItemsEditorRegistry.resolveAllEditors()) {
            Button actionButton = new Button(itemsEditor.getTitle(), new FontIcon(itemsEditor.getIcon()));
            actionButton.setOnAction(event -> {
                try {
                    Item createdItem = itemsEditor.createItem(defaults);
                    items.add(createdItem);
                } catch (Exception e) {
                    CalendarHelperDialogs.showErrorDialog("Cannot execute action", "Cannot execute action", e);
                }
            });
            actionButtons.add(actionButton);
        }
        Button showTemplatesAction = componentFactory.createButton("From Templates", new FontIcon(MaterialDesignZ.ZIP_DISK));
        showTemplatesAction.setOnAction(new ShowItemTemplatesDialogAction(items, defaults, componentFactory));
        actionButtons.add(showTemplatesAction);

        Label newItemTitleLabel = componentFactory.createLabel("New item");
        newItemTitleLabel.setMaxHeight(Double.MAX_VALUE);
        newItemTitleLabel.setAlignment(Pos.CENTER);
        newItemTitleLabel.setPadding(new Insets(0, 10, 0, 4));
        HBox leftButtonsPane = new HBox(2);
        leftButtonsPane.getChildren().add(newItemTitleLabel);
        leftButtonsPane.getChildren().addAll(actionButtons);

        Button removeAllItemsButton = componentFactory.createButton("Remove all items", new FontIcon(MaterialDesignD.DELETE_EMPTY));
        removeAllItemsButton.disableProperty().bind(Bindings.isEmpty(items));
        removeAllItemsButton.setOnAction(event -> items.clear());
        removeAllItemsButton.setFocusTraversable(false);
        HBox rightButtonsPane = new HBox(2);
        rightButtonsPane.getChildren().addAll(removeAllItemsButton);

        this.setPadding(new Insets(5, 5, 5, 5));
        this.setLeft(leftButtonsPane);
        this.setRight(rightButtonsPane);

    }

}
