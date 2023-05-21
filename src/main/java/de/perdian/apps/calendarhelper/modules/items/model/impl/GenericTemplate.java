package de.perdian.apps.calendarhelper.modules.items.model.impl;

import de.perdian.apps.calendarhelper.modules.items.fx.impl.GenericPane;
import de.perdian.apps.calendarhelper.modules.items.model.ItemTemplate;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

public class GenericTemplate extends ItemTemplate<GenericItem> {

    public GenericTemplate() {
        super("Generic", MaterialDesignC.CALENDAR);
    }

    @Override
    public GenericItem createItem() {
        return new GenericItem();
    }
    @Override
    public Pane createItemPane(GenericItem item) {
        return new GenericPane(item);
    }

}
