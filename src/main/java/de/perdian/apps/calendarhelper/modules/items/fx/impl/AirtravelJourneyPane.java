package de.perdian.apps.calendarhelper.modules.items.fx.impl;

import de.perdian.apps.calendarhelper.modules.items.fx.support.AbstractParentPane;
import de.perdian.apps.calendarhelper.modules.items.model.impl.AirtravelFlightItem;
import de.perdian.apps.calendarhelper.modules.items.model.impl.AirtravelJourneyItem;
import javafx.scene.layout.Pane;

public class AirtravelJourneyPane extends AbstractParentPane<AirtravelJourneyItem, AirtravelFlightItem> {

    public AirtravelJourneyPane(AirtravelJourneyItem parentItem) {
        super(parentItem);
    }

    @Override
    protected Pane createChildPane(AirtravelFlightItem childItem, AirtravelJourneyItem parentItem) {
        return new AirtravelFlightPane(childItem, parentItem);
    }

}
