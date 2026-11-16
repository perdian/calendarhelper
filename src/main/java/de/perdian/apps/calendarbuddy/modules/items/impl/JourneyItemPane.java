package de.perdian.apps.calendarbuddy.modules.items.impl;

import de.perdian.apps.calendarbuddy.modules.items.ItemListModel;
import de.perdian.apps.calendarbuddy.modules.items.ItemPane;

abstract class JourneyItemPane<I extends JourneyItem> extends ItemPane<I> {

    public JourneyItemPane(I item, ItemListModel itemListModel) {
        super(item, itemListModel);
    }

}
