package de.perdian.apps.calendarhelper.modules.items;

import de.perdian.apps.calendarhelper.support.fx.components.ComponentFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ItemsContainerPane extends GridPane {

    public ItemsContainerPane(ObservableList<Item> items, ItemDefaults itemDefaults, ComponentFactory componentFactory) {

        ItemsContainerActionsPane actionsPane = new ItemsContainerActionsPane(items, itemDefaults, componentFactory);
        GridPane.setHgrow(actionsPane, Priority.ALWAYS);

        ItemsContainerContentPane contentPane = new ItemsContainerContentPane(items, itemDefaults, componentFactory);
        GridPane.setHgrow(contentPane, Priority.ALWAYS);
        GridPane.setVgrow(contentPane, Priority.ALWAYS);

        this.add(actionsPane, 0, 0, 1, 1);
        this.add(new Separator(), 0, 1, 1, 1);
        this.add(contentPane, 0, 2, 1, 1);

    }

}
