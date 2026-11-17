package de.perdian.apps.calendarbuddy.modules.items.impl;

import de.perdian.apps.calendarbuddy.modules.items.ItemPaneFactory;
import javafx.scene.layout.Pane;

public class GenericSingleItemPaneFactory extends ItemPaneFactory<GenericSingleItem> {

    @Override
    protected Pane createItemPane(GenericSingleItem genericSingleItem) {
        return new GenericSingleItemPane(genericSingleItem);
    }

}
