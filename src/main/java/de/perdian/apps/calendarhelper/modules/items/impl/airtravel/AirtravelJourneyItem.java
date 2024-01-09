package de.perdian.apps.calendarhelper.modules.items.impl.airtravel;

import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.support.AbstractParentItem;

public class AirtravelJourneyItem extends AbstractParentItem<AirtravelFlightItem> {

    AirtravelJourneyItem(ItemDefaults itemDefaults) {
        this.appendChild(itemDefaults);
    }

    @Override
    protected AirtravelFlightItem createChildInstance(ItemDefaults itemDefaults) {
        AirtravelFlightItem childItem = new AirtravelFlightItem(itemDefaults);
        if (!this.getChildren().isEmpty()) {
            AirtravelFlightItem previousItem = this.getChildren().get(this.getChildren().size() - 1);
            childItem.departureAirportCodeProperty().setValue(previousItem.arrivalAirportCodeProperty().getValue());
            childItem.startDateProperty().setValue(previousItem.endDateProperty().getValue());
            childItem.bookingCodeProperty().setValue(previousItem.bookingCodeProperty().getValue());
        }
        return childItem;
    }

}
