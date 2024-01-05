package de.perdian.apps.calendarhelper.modules.items;

import org.kordamp.ikonli.Ikon;

import java.util.List;

@FunctionalInterface
public interface ItemsActionsRegistry<T extends Item> {

    default void addDirectTemplateInvocation(ItemTemplate<T> template) {
        this.addAction(template.createTitle(), template.createIcon(), consumer -> consumer.accept(List.of(template)));
    }

    void addAction(String title, Ikon icon, ItemsAction action);

}
