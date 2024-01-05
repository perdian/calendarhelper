package de.perdian.apps.calendarhelper.modules.items.impl.preconstructed;

import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.modules.items.ItemTemplate;
import de.perdian.apps.calendarhelper.modules.items.ItemsAction;

import java.util.List;
import java.util.function.Consumer;

class PreconstructedAction implements ItemsAction<Item> {

    @Override
    public void contributeTemplates(Consumer<List<ItemTemplate<Item>>> templatesConsumer) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

}
