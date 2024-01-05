package de.perdian.apps.calendarhelper.modules.items;

import java.util.List;
import java.util.function.Consumer;

@FunctionalInterface
public interface ItemsAction<T extends Item> {

    void contributeTemplates(Consumer<List<ItemTemplate<T>>> templatesConsumer);

}
