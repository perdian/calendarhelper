package de.perdian.apps.calendarhelper.modules.items.impl.airtravel;

import de.perdian.apps.calendarhelper.modules.items.support.AbstractParentItemTemplate;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;

public class AirtravelJourneyTemplate extends AbstractParentItemTemplate<AirtravelJourneyItem, AirtravelFlightItem> {

    @Override
    public String createTitle() {
        return "Airtravel";
    }

    @Override
    public Ikon createIcon() {
        return MaterialDesignA.AIRPLANE;
    }

    @Override
    public AirtravelJourneyItem createItem() {
        AirtravelJourneyItem item = new AirtravelJourneyItem();
        item.getChildren().setAll(this.createChildItem(item));
        return item;
    }

    @Override
    public Pane createItemPane(AirtravelJourneyItem item) {
        return new AirtravelJourneyPane(item);
    }

    @Override
    protected AirtravelFlightItem createChildItem(AirtravelJourneyItem parentItem) {
        AirtravelFlightItem childItem = new AirtravelFlightItem();
        if (!parentItem.getChildren().isEmpty()) {
            AirtravelFlightItem previousItem = parentItem.getChildren().get(parentItem.getChildren().size() - 1);
            childItem.departureAirportCodeProperty().setValue(previousItem.arrivalAirportCodeProperty().getValue());
            childItem.startDateProperty().setValue(previousItem.endDateProperty().getValue());
            childItem.bookingCodeProperty().setValue(previousItem.bookingCodeProperty().getValue());
        }
        return childItem;
    }

}
