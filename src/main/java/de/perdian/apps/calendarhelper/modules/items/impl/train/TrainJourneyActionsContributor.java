package de.perdian.apps.calendarhelper.modules.items.impl.train;

import de.perdian.apps.calendarhelper.modules.items.ItemsActionsContributor;
import de.perdian.apps.calendarhelper.modules.items.ItemsActionsRegistry;

public class TrainJourneyActionsContributor implements ItemsActionsContributor {

    @Override
    public void contributeActionsTo(ItemsActionsRegistry registry) {
        registry.addDirectTemplateInvocation(new TrainJourneyTemplate());
    }

}
