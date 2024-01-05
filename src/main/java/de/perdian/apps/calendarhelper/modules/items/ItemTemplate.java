package de.perdian.apps.calendarhelper.modules.items;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.Ikon;

import java.util.List;

public interface ItemTemplate<T extends Item> {

    String createTitle();

    Ikon createIcon();

    T createItem();

    Pane createItemPane(T item);

    default List<Button> createAdditionalButtons(T item) {
        return null;
    }

}
