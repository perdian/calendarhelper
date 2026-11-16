package de.perdian.apps.calendarbuddy.modules.items;

import de.perdian.apps.calendarbuddy.modules.items.impl.FlightJourneyItem;
import de.perdian.apps.calendarbuddy.modules.items.impl.FlightJourneyItemPaneFactory;
import de.perdian.apps.calendarbuddy.modules.items.impl.TrainJourneyItem;
import de.perdian.apps.calendarbuddy.modules.items.impl.TrainJourneyItemPaneFactory;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ItemConfiguration {

    @Bean
    ItemType<FlightJourneyItem> flightJourneyItemType() {
        return new ItemType<>(FlightJourneyItem.class)
            .withTitle("Flight")
            .withItemPaneFactory(new FlightJourneyItemPaneFactory())
            .withIcon(MaterialDesignA.AIRPLANE);
    }

    @Bean
    ItemType<TrainJourneyItem> trainJourneyItemType() {
        return new ItemType<>(TrainJourneyItem.class)
            .withTitle("Train")
            .withItemPaneFactory(new TrainJourneyItemPaneFactory())
            .withIcon(MaterialDesignT.TRAIN);
    }

}
