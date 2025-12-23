package de.perdian.apps.calendarhelper.modules.items.impl.airtravel;

import de.perdian.apps.calendarhelper.modules.items.support.AbstractContainerPane;
import de.perdian.apps.calendarhelper.support.fx.components.ComponentFactory;
import javafx.scene.layout.Pane;

class AirtravelJourneyPane extends AbstractContainerPane<AirtravelJourneyItem, AirtravelFlightItem> {

    AirtravelJourneyPane(AirtravelJourneyItem parentItem, ComponentFactory componentFactory) {
        super(parentItem, componentFactory);
    }

    @Override
    protected Pane createChildPane(AirtravelFlightItem childItem, AirtravelJourneyItem parentItem, ComponentFactory componentFactory) {
        return new AirtravelFlightPane(childItem, parentItem, componentFactory);
    }

}
