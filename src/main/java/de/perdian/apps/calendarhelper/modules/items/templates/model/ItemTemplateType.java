package de.perdian.apps.calendarhelper.modules.items.templates.model;

import de.perdian.apps.calendarhelper.modules.items.ItemParser;
import de.perdian.apps.calendarhelper.modules.items.impl.train.TrainJourneyItemParser;

public enum ItemTemplateType {

    AIRTRAVEL(null),
    TRAIN_JOURNEY(new TrainJourneyItemParser());

    private ItemParser<?> itemParser = null;

    ItemTemplateType(ItemParser<?> itemParser) {
        this.setItemParser(itemParser);
    }

    public ItemParser<?> getItemParser() {
        return this.itemParser;
    }
    private void setItemParser(ItemParser<?> itemParser) {
        this.itemParser = itemParser;
    }

}
