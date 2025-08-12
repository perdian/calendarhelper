package de.perdian.apps.calendarhelper.modules.items;

import de.perdian.apps.calendarhelper.modules.items.support.AbstractContainerItem;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignD;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

import java.util.ArrayList;
import java.util.List;

class ItemsContainerItemWrapperPane extends BorderPane {

    public <T extends Item> ItemsContainerItemWrapperPane(T item, ObservableList<Item> allItems, ItemDefaults itemDefaults) {

        ItemsEditor<T> itemsEditor = ItemsEditorRegistry.resolveEditorForItem(item);

        Button removeItemButton = new Button("", new FontIcon(MaterialDesignD.DELETE));
        removeItemButton.setTooltip(new Tooltip("Remove item"));
        removeItemButton.setOnAction(event -> allItems.remove(item));
        removeItemButton.setFocusTraversable(false);

        List<Button> additionalButtonList = new ArrayList<>();
        if (item instanceof AbstractContainerItem<?> parentItem) {
            Button newChildButton = new Button("", new FontIcon(MaterialDesignP.PLUS));
            newChildButton.setTooltip(new Tooltip("New child"));
            newChildButton.setOnAction(event -> parentItem.appendChildItem(itemDefaults));
            newChildButton.setFocusTraversable(false);
            additionalButtonList.add(newChildButton);
        }
        HBox titleButtonsPane = new HBox(2);
        if (additionalButtonList != null && !additionalButtonList.isEmpty()) {
            Separator separator = new Separator(Orientation.VERTICAL);
            separator.setPadding(new Insets(0, 2.5, 0, 5));
            titleButtonsPane.getChildren().addAll(additionalButtonList);
            titleButtonsPane.getChildren().add(separator);
        }
        titleButtonsPane.getChildren().add(removeItemButton);
        Label titleLabel = new Label(itemsEditor.getTitle(), new FontIcon(itemsEditor.getIcon()));
        titleLabel.setAlignment(Pos.CENTER_LEFT);
        titleLabel.setMaxHeight(Double.MAX_VALUE);
        BorderPane titlePane = new BorderPane();
        titlePane.setPadding(new Insets(0, 0, 5, 0));
        titlePane.setLeft(titleLabel);
        titlePane.setRight(titleButtonsPane);

        Pane itemEditorPane = itemsEditor.createItemEditorPane(item);

        this.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setPadding(new Insets(5, 10, 5, 10));
        this.setTop(titlePane);
        this.setCenter(itemEditorPane);

    }

}
