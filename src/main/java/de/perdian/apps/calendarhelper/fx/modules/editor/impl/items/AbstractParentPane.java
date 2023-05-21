package de.perdian.apps.calendarhelper.fx.modules.editor.impl.items;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractParentPane<P extends AbstractParentItem<C>, C extends AbstractDateTimeItem> extends BorderPane {

    private final Map<C, Pane> childToPaneMap = new HashMap<>();

    public AbstractParentPane(P parentItem) {

        VBox childrenParentPane = new VBox();
        this.setCenter(childrenParentPane);

        parentItem.getChildren().addListener((ListChangeListener.Change<? extends C> change) -> {
            while (change.next()) {
                for (C removedChild : change.getRemoved()) {
                    Pane removedChildPane = this.getChildToPaneMap().remove(removedChild);
                    if (removedChildPane != null) {
                        Platform.runLater(() -> childrenParentPane.getChildren().remove(removedChildPane));
                    }
                }
                for (C newChild : change.getAddedSubList()) {
                    Pane newChildPane = this.createChildPane(newChild);
                    Platform.runLater(() -> childrenParentPane.getChildren().add(newChildPane));
                }
            }
        });

    }

    protected abstract Pane createChildPane(C childItem);

    private Map<C, Pane> getChildToPaneMap() {
        return this.childToPaneMap;
    }

}
