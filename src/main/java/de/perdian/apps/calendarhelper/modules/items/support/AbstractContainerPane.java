package de.perdian.apps.calendarhelper.modules.items.support;

import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.support.fx.components.ComponentFactory;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractContainerPane<P extends AbstractContainerItem<C>, C extends Item> extends BorderPane {

    private final Map<C, Pane> childToPaneMap = new HashMap<>();

    protected AbstractContainerPane(P parentItem, ComponentFactory parentComponentFactory) {

        ComponentFactory containerComponentFactory = parentComponentFactory.createChild();
        containerComponentFactory.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (KeyCode.PLUS.equals(keyEvent.getCode()) && keyEvent.isMetaDown()) {
                parentItem.getChildren().add(parentItem.createChildItem(parentItem.getDefaults()));
            }
        });

        VBox childrenParentPane = new VBox();
        childrenParentPane.setSpacing(10);
        parentItem.getChildren().forEach(childItem -> this.addChildItem(childItem, parentItem, childrenParentPane, containerComponentFactory));
        this.setCenter(childrenParentPane);

        parentItem.getChildren().addListener((ListChangeListener.Change<? extends C> change) -> {
            while (change.next()) {
                change.getRemoved().forEach(childItem -> this.removeChildItem(childItem, childrenParentPane));
                change.getAddedSubList().forEach(childItem -> this.addChildItem(childItem, parentItem, childrenParentPane, containerComponentFactory));
            }
        });

    }

    private void addChildItem(C childItem, P parentItem, VBox parentPane, ComponentFactory componentFactory) {
        Pane childPane = this.createChildPane(childItem, parentItem, componentFactory);
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

    protected abstract Pane createChildPane(C childItem, P parentItem, ComponentFactory componentFactory);

    private Map<C, Pane> getChildToPaneMap() {
        return this.childToPaneMap;
    }

}
