package de.perdian.apps.calendarhelper.modules.items.impl.preconstructed;

import de.perdian.apps.calendarhelper.modules.items.ItemsActionsContributor;
import de.perdian.apps.calendarhelper.modules.items.ItemsActionsRegistry;
import org.kordamp.ikonli.materialdesign2.MaterialDesignW;

public class PreconstructedActionsContributor implements ItemsActionsContributor {

    @Override
    public void contributeActionsTo(ItemsActionsRegistry registry) {
        registry.addAction("Preconstructed", MaterialDesignW.WIZARD_HAT, new PreconstructedAction());
    }

}
