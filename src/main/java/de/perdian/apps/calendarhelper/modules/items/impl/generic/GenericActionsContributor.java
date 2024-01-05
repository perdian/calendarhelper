package de.perdian.apps.calendarhelper.modules.items.impl.generic;

import de.perdian.apps.calendarhelper.modules.items.ItemsActionsContributor;
import de.perdian.apps.calendarhelper.modules.items.ItemsActionsRegistry;

public class GenericActionsContributor implements ItemsActionsContributor {

    @Override
    public void contributeActionsTo(ItemsActionsRegistry registry) {
        registry.addDirectTemplateInvocation(new GenericTemplate());
    }

}
