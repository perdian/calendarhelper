package de.perdian.apps.calendarhelper.modules.items.impl.airtravel;

import de.perdian.apps.calendarhelper.modules.items.support.AbstractContainerPane;
import javafx.scene.layout.Pane;

class AirtravelJourneyPane extends AbstractContainerPane<AirtravelJourneyItem, AirtravelFlightItem> {

    AirtravelJourneyPane(AirtravelJourneyItem parentItem) {
        super(parentItem);
    }

    @Override
    protected Pane createChildPane(AirtravelFlightItem childItem, AirtravelJourneyItem parentItem) {
        return new AirtravelFlightPane(childItem, parentItem);
    }

}
