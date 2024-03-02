package de.perdian.apps.calendarhelper.modules.items.impl.airtravel;

import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.ItemTemplate;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;

public class AirtravelJourneyTemplate implements ItemTemplate<AirtravelJourneyItem> {

    @Override
    public String createTitle() {
        return "Airtravel";
    }

    @Override
    public Ikon createIcon() {
        return MaterialDesignA.AIRPLANE;
    }

    @Override
    public AirtravelJourneyItem createItem(ItemDefaults itemDefaults) {
        AirtravelJourneyItem newJourneyItem = new AirtravelJourneyItem();
        newJourneyItem.appendChild(itemDefaults);
        return newJourneyItem;
    }

    @Override
    public Pane createItemPane(AirtravelJourneyItem item) {
        return new AirtravelJourneyPane(item);
    }

}
