package de.perdian.apps.calendarhelper.fx.modules.editor;

import de.perdian.apps.calendarhelper.fx.modules.editor.impl.items.*;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.materialdesign2.MaterialDesignT;

import java.util.function.Function;
import java.util.function.Supplier;

public enum EditorItemTemplate {

    AIRTRAVEL_JOURNEY("Airtravel", MaterialDesignA.AIRPLANE, AirtravelJourneyItem::new, item -> new AirtravelJourneyItemPane(item)),
    TRAIN_JOURNEY("Train", MaterialDesignT.TRAIN, TrainJourneyItem::new, item -> new TrainJourneyItemPane(item)),
    CONFERENCE("Conference", MaterialDesignP.PRESENTATION_PLAY, ConferenceItem::new, item -> new ConferenceItemPane(item));

    private String title = null;
    private Ikon icon = null;
    private Supplier<EditorItem> editorItemSupplier = null;
    private Function<EditorItem, Pane> editorItemPaneFunction = null;

    private <T extends EditorItem> EditorItemTemplate(String title, Ikon icon, Supplier<T> editorItemSupplier, Function<T, Pane> editorItemPaneFunction) {
        this.setTitle(title);
        this.setIcon(icon);
        this.setEditorItemSupplier((Supplier<EditorItem>) editorItemSupplier);
        this.setEditorItemPaneFunction((Function<EditorItem, Pane>) editorItemPaneFunction);
    }

    public String getTitle() {
        return this.title;
    }
    private void setTitle(String title) {
        this.title = title;
    }

    public Ikon getIcon() {
        return this.icon;
    }
    private void setIcon(Ikon icon) {
        this.icon = icon;
    }

    public Supplier<EditorItem> getEditorItemSupplier() {
        return this.editorItemSupplier;
    }
    public void setEditorItemSupplier(Supplier<EditorItem> editorItemSupplier) {
        this.editorItemSupplier = editorItemSupplier;
    }

    public Function<EditorItem, Pane> getEditorItemPaneFunction() {
        return this.editorItemPaneFunction;
    }
    private void setEditorItemPaneFunction(Function<EditorItem, Pane> editorItemPaneFunction) {
        this.editorItemPaneFunction = editorItemPaneFunction;
    }
}
