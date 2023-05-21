package de.perdian.apps.calendarhelper.fx.modules.editor;

import javafx.scene.layout.Pane;
import org.kordamp.ikonli.Ikon;

import java.util.function.Function;
import java.util.function.Supplier;

public class EditorTemplate<T extends EditorItem> {

    private String title = null;
    private Ikon icon = null;
    private Supplier<T> itemSupplier = null;
    private Function<T, Pane> paneSupplier = null;

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Ikon getIcon() {
        return this.icon;
    }
    public void setIcon(Ikon icon) {
        this.icon = icon;
    }

    public Supplier<T> getItemSupplier() {
        return this.itemSupplier;
    }
    public void setItemSupplier(Supplier<T> itemSupplier) {
        this.itemSupplier = itemSupplier;
    }

    public Function<T, Pane> getPaneSupplier() {
        return this.paneSupplier;
    }
    public void setPaneSupplier(Function<T, Pane> paneSupplier) {
        this.paneSupplier = paneSupplier;
    }

}
