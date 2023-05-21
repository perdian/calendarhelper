package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import com.google.api.services.calendar.model.Event;
import de.perdian.apps.calendarhelper.fx.modules.editor.EditorItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class AbstractParentItem<T extends EditorItem> extends AbstractItem {

    private final ObservableList<T> children = FXCollections.observableArrayList();

    @Override
    public List<Event> createEvents() {
        throw new UnsupportedOperationException();
    }

    public ObservableList<T> getChildren() {
        return this.children;
    }

}
