package de.perdian.apps.calendarhelper.modules.items.model.impl;

import de.perdian.apps.calendarhelper.modules.items.fx.impl.TrainJourneyPane;
import de.perdian.apps.calendarhelper.modules.items.model.support.AbstractParentTemplate;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

public class TrainJourneyTemplate extends AbstractParentTemplate<TrainJourneyItem, TrainRideItem> {

    public TrainJourneyTemplate() {
        super("Train", MaterialDesignT.TRAIN);
    }

    @Override
    public TrainJourneyItem createItem() {
        return new TrainJourneyItem();
    }
    @Override
    public Pane createItemPane(TrainJourneyItem item) {
        return new TrainJourneyPane(item);
    }

    @Override
    protected TrainRideItem createChildItem(TrainJourneyItem parentItem) {
        TrainRideItem childItem = new TrainRideItem();
        return childItem;
    }

}
