package de.perdian.apps.calendarhelper.modules.items.impl.generic;

import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.ItemsEditor;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

public class GenericEditor implements ItemsEditor<GenericItem> {

    @Override
    public String getTitle() {
        return "Generic";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesignC.CALENDAR;
    }

    @Override
    public boolean canHandleItem(Item item) {
        return item instanceof GenericItem;
    }

    @Override
    public GenericItem createItem(ItemDefaults defaults) {
        return new GenericItem(defaults);
    }

    @Override
    public Pane createItemEditorPane(GenericItem item) {
        return new GenericPane(item);
    }

}
