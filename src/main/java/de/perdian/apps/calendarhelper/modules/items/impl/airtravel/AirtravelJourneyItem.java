package de.perdian.apps.calendarhelper.modules.items.impl.airtravel;

import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.support.AbstractJourneyItem;

public class AirtravelJourneyItem extends AbstractJourneyItem<AirtravelFlightItem> {

    public AirtravelJourneyItem(ItemDefaults defaults) {
        super(defaults);
    }

    @Override
    protected AirtravelFlightItem createChildItem(ItemDefaults itemDefaults) {
        AirtravelFlightItem childItem = new AirtravelFlightItem(itemDefaults);
        if (!this.getChildren().isEmpty()) {
            AirtravelFlightItem previousItem = this.getChildren().get(this.getChildren().size() - 1);
            childItem.departureAirportCodeProperty().setValue(previousItem.arrivalAirportCodeProperty().getValue());
            childItem.getCalendarValues().startDateProperty().setValue(previousItem.getCalendarValues().endDateProperty().getValue());
            childItem.bookingCodeProperty().setValue(previousItem.bookingCodeProperty().getValue());
        }
        return childItem;
    }

    @Override
    protected String createJourneyEventSummary() {
        return "Trip ✈️ "
            + this.getChildren().getFirst().departureAirportCodeProperty().getValue()
            + " » "
            + this.getChildren().getLast().arrivalAirportCodeProperty().getValue()
        ;
    }

}
