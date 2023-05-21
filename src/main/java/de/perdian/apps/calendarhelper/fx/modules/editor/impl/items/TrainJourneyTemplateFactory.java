package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import de.perdian.apps.calendarhelper.fx.modules.editor.EditorTemplate;
import de.perdian.apps.calendarhelper.fx.modules.editor.EditorTemplateFactory;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

public class TrainJourneyTemplateFactory implements EditorTemplateFactory {

    @Override
    public EditorTemplate<TrainJourneyItem> createTemplate() {
        EditorTemplate<TrainJourneyItem> template = new EditorTemplate<>();
        template.setTitle("Train");
        template.setIcon(MaterialDesignT.TRAIN);
        template.setItemSupplier(() -> new TrainJourneyItem());
        template.setPaneSupplier(item -> new TrainJourneyPane(item));
        return template;
    }

}
