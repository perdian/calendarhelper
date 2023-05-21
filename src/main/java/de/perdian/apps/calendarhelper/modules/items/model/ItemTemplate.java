package de.perdian.apps.calendarhelper.modules.items.model;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.Ikon;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ItemTemplate<T extends Item> {

    private String title = null;
    private Ikon icon = null;
    private Supplier<T> itemSupplier = null;
    private Function<T, Pane> paneSupplier = null;
    private Function<T, List<Button>> additionalButtonsSupplier = null;

    public ItemTemplate(String title, Ikon icon) {
        this.setTitle(title);
        this.setIcon(icon);
    }

    public abstract T createItem();

    public abstract Pane createItemPane(T item);

    public List<Button> createAdditionalButtons(T item) {
        return Collections.emptyList();
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

}
