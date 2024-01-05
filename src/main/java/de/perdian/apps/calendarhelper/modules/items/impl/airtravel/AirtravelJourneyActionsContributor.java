package de.perdian.apps.calendarhelper.modules.items.impl.airtravel;

import de.perdian.apps.calendarhelper.modules.items.ItemsActionsContributor;
import de.perdian.apps.calendarhelper.modules.items.ItemsActionsRegistry;

public class AirtravelJourneyActionsContributor implements ItemsActionsContributor {

    @Override
    public void contributeActionsTo(ItemsActionsRegistry registry) {
        registry.addDirectTemplateInvocation(new AirtravelJourneyTemplate());
    }

}
