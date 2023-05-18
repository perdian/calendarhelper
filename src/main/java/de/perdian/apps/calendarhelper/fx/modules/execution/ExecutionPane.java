package de.perdian.apps.calendarhelper.fx.modules.execution;

import de.perdian.apps.calendarhelper.fx.CalendarHelperContext;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.springframework.context.ApplicationContext;

public class ExecutionPane extends GridPane {

    public ExecutionPane(CalendarHelperContext calendarHelperContext, ApplicationContext applicationContext) {

        Label progressTitleLabel = new Label("Progress");
        ProgressBar progressBar = new ProgressBar();
        progressBar.disableProperty().bind(calendarHelperContext.executionActiveProperty().not());
        progressBar.progressProperty().bind(calendarHelperContext.executionProgressProperty());
        progressBar.setMinHeight(25);
        progressBar.setMaxHeight(Double.MAX_VALUE);
        progressBar.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(progressBar, Priority.ALWAYS);

        Button generateCalendarEntriesButton = new Button("Generate calendar entries", new FontIcon(MaterialDesignC.CREATION));
        generateCalendarEntriesButton.setOnAction(new ExecutionEventHandler(calendarHelperContext, applicationContext));
        generateCalendarEntriesButton.setMaxHeight(Double.MAX_VALUE);
        generateCalendarEntriesButton.disableProperty().bind(Bindings.isEmpty(calendarHelperContext.editorItems()).or(calendarHelperContext.executionActiveProperty()));

        this.add(progressTitleLabel, 0, 0, 1, 1);
        this.add(progressBar, 0, 1, 1, 1);
        this.add(generateCalendarEntriesButton, 1, 0, 1, 2);
        this.setHgap(5);
        this.setVgap(5);
        this.setPadding(new Insets(10, 10, 10, 10));

    }

}
