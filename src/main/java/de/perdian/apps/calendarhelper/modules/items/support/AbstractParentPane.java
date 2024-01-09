package de.perdian.apps.calendarhelper.modules.items.support;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractParentPane<P extends AbstractParentItem<C>, C extends AbstractSingleItem> extends BorderPane {

    private final Map<C, Pane> childToPaneMap = new HashMap<>();

    public AbstractParentPane(P parentItem) {

        VBox childrenParentPane = new VBox();
        childrenParentPane.setSpacing(10);
        parentItem.getChildren().forEach(childItem -> this.addChildItem(childItem, parentItem, childrenParentPane));
        this.setCenter(childrenParentPane);

        parentItem.getChildren().addListener((ListChangeListener.Change<? extends C> change) -> {
            while (change.next()) {
                change.getRemoved().forEach(childItem -> this.removeChildItem(childItem, childrenParentPane));
                change.getAddedSubList().forEach(childItem -> this.addChildItem(childItem, parentItem, childrenParentPane));
            }
        });

    }

    private void addChildItem(C childItem, P parentItem, VBox parentPane) {
        Pane childPane = this.createChildPane(childItem, parentItem);
        BorderPane childWrapperPane = new BorderPane(childPane);
        this.getChildToPaneMap().put(childItem, childWrapperPane);
        Platform.runLater(() -> {
            parentPane.getChildren().add(childWrapperPane);
            childPane.requestFocus();
        });
    }

    private void removeChildItem(C childItem, VBox parentPane) {
        Pane removedChildPane = this.getChildToPaneMap().remove(childItem);
        if (removedChildPane != null) {
            Platform.runLater(() -> parentPane.getChildren().remove(removedChildPane));
        }
    }

    protected abstract Pane createChildPane(C childItem, P parentItem);

    private Map<C, Pane> getChildToPaneMap() {
        return this.childToPaneMap;
    }

}
