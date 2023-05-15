package de.perdian.apps.calendarhelper.fx.modules.actions;

import de.perdian.apps.calendarhelper.fx.modules.editor.EditorItem;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

public class ActionsPane extends BorderPane {

    public ActionsPane(ObservableList<EditorItem> editorItems) {
        Button generateCalendarEntriesButton = new Button("Generate calendar entries", new FontIcon(MaterialDesignC.CREATION));
        generateCalendarEntriesButton.disableProperty().bind(Bindings.isEmpty(editorItems));
        this.setCenter(generateCalendarEntriesButton);
    }

}
