package de.perdian.apps.calendarhelper.modules.items.model.impl;

import de.perdian.apps.calendarhelper.modules.items.fx.impl.AirtravelJourneyPane;
import de.perdian.apps.calendarhelper.modules.items.model.support.AbstractParentTemplate;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;

public class AirtravelJourneyTemplate extends AbstractParentTemplate<AirtravelJourneyItem, AirtravelFlightItem> {

    public AirtravelJourneyTemplate() {
        super("Airtravel", MaterialDesignA.AIRPLANE);
    }

    @Override
    public AirtravelJourneyItem createItem() {
        return new AirtravelJourneyItem();
    }

    @Override
    public Pane createItemPane(AirtravelJourneyItem item) {
        return new AirtravelJourneyPane(item);
    }
    @Override
    protected AirtravelFlightItem createChildItem(AirtravelJourneyItem parentItem) {
        AirtravelFlightItem childItem = new AirtravelFlightItem();
        return childItem;
    }

}
