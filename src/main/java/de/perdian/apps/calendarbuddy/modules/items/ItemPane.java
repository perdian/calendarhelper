package de.perdian.apps.calendarbuddy.modules.items;

import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemPane<I extends Item> extends GridPane {

    private final Label titleLabel;

    protected ItemPane(I item, ItemListModel itemListModel) {

        ComponentFactory componentFactory = new ComponentFactory();
        this.titleLabel = componentFactory.createLabel("");

    }

    protected List<Button> createActionButtons(I item, ObservableList<Item> allItems) {
        List<Button> actionButtons = new ArrayList<>();
        return actionButtons;
    }

    protected Label getTitleLabel() {
        return this.titleLabel;
    }

}
