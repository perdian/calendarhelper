package de.perdian.apps.calendarhelper.modules.items.impl.train;

import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.ItemsEditor;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

class TrainJourneyItemsEditor implements ItemsEditor<TrainJourneyItem> {

    @Override
    public String getTitle() {
        return "Train";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesignT.TRAIN;
    }

    @Override
    public boolean canHandleItem(Item item) {
        return item instanceof TrainJourneyItem;
    }

    @Override
    public TrainJourneyItem createItem(ItemDefaults defaults) {
        TrainJourneyItem newJourneyItem = new TrainJourneyItem(defaults);
        newJourneyItem.appendChildItem(defaults);
        return newJourneyItem;
    }

    @Override
    public Pane createItemEditorPane(TrainJourneyItem item) {
        return new TrainJourneyPane(item);
    }

}
