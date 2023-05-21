package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import de.perdian.apps.calendarhelper.fx.modules.editor.EditorTemplate;
import de.perdian.apps.calendarhelper.fx.modules.editor.EditorTemplateFactory;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;

public class AirtravelJourneyTemplateFactory implements EditorTemplateFactory {

    @Override
    public EditorTemplate<AirtravelJourneyItem> createTemplate() {
        EditorTemplate<AirtravelJourneyItem> template = new EditorTemplate<>();
        template.setTitle("Airtravel");
        template.setIcon(MaterialDesignA.AIRPLANE);
        template.setItemSupplier(() -> new AirtravelJourneyItem());
        template.setPaneSupplier(item -> new AirtravelJourneyPane(item));
        return template;
    }

}
