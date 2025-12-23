package de.perdian.apps.calendarhelper.modules.items;

import de.perdian.apps.calendarhelper.support.fx.components.ComponentFactory;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.Ikon;

public interface ItemsEditor<T extends Item> {

    String getTitle();
    Ikon getIcon();

    boolean canHandleItem(Item item);

    T createItem(ItemDefaults defaults);

    Pane createItemEditorPane(T item, ComponentFactory componentFactory);

}
