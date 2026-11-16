package de.perdian.apps.calendarbuddy.modules.execution;

import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

public class ExecutionModelPane extends GridPane {

    public ExecutionModelPane(ExecutionModel executionModel) {

        ComponentFactory componentFactory = new ComponentFactory();
        Button executeButton = componentFactory.createButton("Execute", new FontIcon(MaterialDesignP.PLAY));
        executeButton.disableProperty().bind(executionModel.availableProperty().not());
        executeButton.setMaxWidth(Double.MAX_VALUE);
        executeButton.setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(executeButton, Priority.ALWAYS);
        GridPane.setVgrow(executeButton, Priority.ALWAYS);

        this.add(executeButton, 0, 0);

    }

}
