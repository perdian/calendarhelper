package de.perdian.apps.calendarhelper.modules.items;

import javafx.scene.layout.Pane;
import org.kordamp.ikonli.Ikon;

public interface ItemTemplate<T extends Item> {

    String createTitle();

    Ikon createIcon();

    T createItem(ItemDefaults itemDefaults);

    Pane createItemPane(T item);

}
