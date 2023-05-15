package de.perdian.apps.calendarhelper.fx.modules.editor;

import de.perdian.apps.calendarhelper.fx.model.EditorItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class EditorPane extends BorderPane {

    private Map<EditorItem, Pane> editorItemToPaneMap = null;

    public EditorPane() {

        Map<EditorItem, Pane> editorItemToPaneMap = new HashMap<>();
        this.setEditorItemToPaneMap(editorItemToPaneMap);

    }

    private Map<EditorItem, Pane> getEditorItemToPaneMap() {
        return editorItemToPaneMap;
    }
    private void setEditorItemToPaneMap(Map<EditorItem, Pane> editorItemToPaneMap) {
        this.editorItemToPaneMap = editorItemToPaneMap;
    }

}
