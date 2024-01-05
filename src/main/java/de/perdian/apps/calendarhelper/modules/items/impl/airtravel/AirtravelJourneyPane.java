package de.perdian.apps.calendarhelper.modules.items.impl.airtravel;

import de.perdian.apps.calendarhelper.modules.items.support.AbstractParentPane;
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
