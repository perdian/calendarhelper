package de.perdian.apps.calendarhelper.modules.items.impl.airtravel;

import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.ItemsEditor;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;

public class AirtravelJourneyEditor implements ItemsEditor<AirtravelJourneyItem> {

    @Override
    public String getTitle() {
        return "Airtravel";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesignA.AIRPLANE;
    }

    @Override
    public boolean canHandleItem(Item item) {
        return item instanceof AirtravelJourneyItem;
    }

    @Override
    public AirtravelJourneyItem createItem(ItemDefaults defaults) {
        AirtravelJourneyItem journeyItem = new AirtravelJourneyItem(defaults);
        journeyItem.appendChildItem(defaults);
        return journeyItem;
    }

    @Override
    public Pane createItemEditorPane(AirtravelJourneyItem item) {
        return new AirtravelJourneyPane(item);
    }

}
