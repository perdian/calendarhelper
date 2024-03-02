package de.perdian.apps.calendarhelper.modules.items.impl.airtravel;

import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.support.AbstractJourneyItem;

public class AirtravelJourneyItem extends AbstractJourneyItem<AirtravelFlightItem> {

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

    @Override
    protected String createJourneyEventSummary() {
        return "Trip  ✈️ "
            + this.getChildren().getFirst().departureAirportCodeProperty().getValue()
            + " » "
            + this.getChildren().getLast().arrivalAirportCodeProperty().getValue()
        ;
    }

}
