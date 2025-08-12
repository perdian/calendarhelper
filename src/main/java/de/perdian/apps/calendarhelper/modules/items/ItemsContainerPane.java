package de.perdian.apps.calendarhelper.modules.items;

import javafx.collections.ObservableList;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ItemsContainerPane extends GridPane {

    public ItemsContainerPane(ObservableList<Item> items, ItemDefaults itemDefaults) {

        ItemsContainerActionsPane actionsPane = new ItemsContainerActionsPane(items, itemDefaults);
        GridPane.setHgrow(actionsPane, Priority.ALWAYS);

        ItemsContainerContentPane contentPane = new ItemsContainerContentPane(items, itemDefaults);
        GridPane.setHgrow(contentPane, Priority.ALWAYS);
        GridPane.setVgrow(contentPane, Priority.ALWAYS);

        this.add(actionsPane, 0, 0, 1, 1);
        this.add(new Separator(), 0, 1, 1, 1);
        this.add(contentPane, 0, 2, 1, 1);

    }

}
