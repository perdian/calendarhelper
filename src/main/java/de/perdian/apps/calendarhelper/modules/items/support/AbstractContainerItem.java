package de.perdian.apps.calendarhelper.modules.items.support;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public abstract class AbstractContainerItem<C extends Item> extends Item {

    private final ObservableList<C> children = FXCollections.observableArrayList();
    private ItemDefaults defaults = null;

    protected AbstractContainerItem(ItemDefaults defaults) {
        super(defaults);
        this.setDefaults(defaults);
    }

    @Override
    public List<Event> createEvents() {
        return this.getChildren()
            .stream().flatMap(child -> child.createEvents().stream())
            .toList();
    }

    public C appendChildItem(ItemDefaults itemDefaults) {
        C childInstance = this.createChildItem(itemDefaults);
        this.getChildren().add(childInstance);
        return childInstance;
    }

    protected abstract C createChildItem(ItemDefaults itemDefaults);

    public ObservableList<C> getChildren() {
        return this.children;
    }

    public ItemDefaults getDefaults() {
        return this.defaults;
    }
    private void setDefaults(ItemDefaults defaults) {
        this.defaults = defaults;
    }

}
