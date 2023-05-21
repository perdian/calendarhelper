package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import de.perdian.apps.calendarhelper.fx.modules.editor.EditorTemplate;
import de.perdian.apps.calendarhelper.fx.modules.editor.EditorTemplateFactory;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

public class GenericTemplateFactory implements EditorTemplateFactory {

    @Override
    public EditorTemplate<GenericItem> createTemplate() {
        EditorTemplate<GenericItem> template = new EditorTemplate<>();
        template.setTitle("Generic");
        template.setIcon(MaterialDesignC.CALENDAR);
        template.setItemSupplier(() -> new GenericItem());
        template.setPaneSupplier(item -> new GenericPane(item));
        return template;
    }

}
