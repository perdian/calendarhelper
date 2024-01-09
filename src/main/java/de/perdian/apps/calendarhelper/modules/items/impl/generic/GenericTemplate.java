package de.perdian.apps.calendarhelper.modules.items.impl.generic;

import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.ItemTemplate;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

public class GenericTemplate implements ItemTemplate<GenericItem> {

    @Override
    public String createTitle() {
        return "Generic";
    }

    @Override
    public Ikon createIcon() {
        return MaterialDesignC.CALENDAR;
    }

    @Override
    public GenericItem createItem(ItemDefaults itemDefaults) {
        return new GenericItem(itemDefaults);
    }

    @Override
    public Pane createItemPane(GenericItem item) {
        return new GenericPane(item);
    }

}
