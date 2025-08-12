package de.perdian.apps.calendarhelper.modules.items;

import java.util.List;
import java.util.ServiceLoader;

public class ItemsEditorRegistry {

    public static List<ItemsEditor<?>> resolveAllEditors() {
        return ServiceLoader.load(ItemsEditor.class).stream()
            .<ItemsEditor<?>>map(ServiceLoader.Provider::get)
            .toList();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Item> ItemsEditor<T> resolveEditorForItem(T item) {
        for (ItemsEditor<?> itemsEditor : ServiceLoader.load(ItemsEditor.class)) {
            if (itemsEditor.canHandleItem(item)) {
                return (ItemsEditor<T>) itemsEditor;
            }
        }
        throw new IllegalArgumentException("Cannot find ItemEditorFactory for item: " + item);
    }

}
