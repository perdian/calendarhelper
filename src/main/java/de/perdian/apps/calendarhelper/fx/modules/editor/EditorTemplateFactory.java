package de.perdian.apps.calendarhelper.fx.modules.editor;

@FunctionalInterface
public interface EditorTemplateFactory {

    <T extends EditorItem> EditorTemplate<T> createTemplate();

}
