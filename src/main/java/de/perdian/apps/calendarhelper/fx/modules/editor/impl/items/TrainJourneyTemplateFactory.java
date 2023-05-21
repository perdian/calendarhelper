package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import de.perdian.apps.calendarhelper.fx.modules.editor.EditorTemplate;
import de.perdian.apps.calendarhelper.fx.modules.editor.EditorTemplateFactory;
import javafx.scene.control.Button;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

import java.util.List;

public class TrainJourneyTemplateFactory implements EditorTemplateFactory {

    @Override
    public EditorTemplate<TrainJourneyItem> createTemplate() {
        EditorTemplate<TrainJourneyItem> template = new EditorTemplate<>();
        template.setTitle("Train");
        template.setIcon(MaterialDesignT.TRAIN);
        template.setItemSupplier(() -> new TrainJourneyItem());
        template.setPaneSupplier(item -> new TrainJourneyPane(item));
        template.setAdditionalButtonsSupplier(item -> List.of(new Button("TEST")));
        return template;
    }

}
