package de.perdian.apps.calendarhelper.modules.items.impl.train;

import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.ItemTemplate;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

public class TrainJourneyTemplate implements ItemTemplate<TrainJourneyItem> {

    @Override
    public String createTitle() {
        return "Train";
    }

    @Override
    public Ikon createIcon() {
        return MaterialDesignT.TRAIN;
    }

    @Override
    public TrainJourneyItem createItem(ItemDefaults itemDefaults) {
        return new TrainJourneyItem(itemDefaults);
    }

    @Override
    public Pane createItemPane(TrainJourneyItem item) {
        return new TrainJourneyPane(item);
    }

}
