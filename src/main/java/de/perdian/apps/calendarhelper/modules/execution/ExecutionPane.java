package de.perdian.apps.calendarhelper.modules.execution;

import de.perdian.apps.calendarhelper.CalendarHelperSelection;
import de.perdian.apps.calendarhelper.modules.execution.handlers.ExecutionActionEventHandler;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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

    private final ExecutionProgress progress = new ExecutionProgress();

    public ExecutionPane(CalendarHelperSelection selection, ApplicationContext applicationContext) {

        Label progressTitleLabel = new Label("Progress");
        ProgressBar progressBar = new ProgressBar();
        progressBar.disableProperty().bind(this.getProgress().busyProperty());
        progressBar.progressProperty().bind(this.getProgress().progressProperty());
        progressBar.setMinHeight(25);
        progressBar.setMaxHeight(Double.MAX_VALUE);
        progressBar.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(progressBar, Priority.ALWAYS);

        BooleanBinding executionDisabled = this.getProgress().busyProperty()
                .or(Bindings.isEmpty(selection.getActiveItems()))
                .or(selection.activeUserProperty().isNull())
                .or(selection.apiCredentialsProperty().isNull());

        Button generateCalendarEntriesButton = new Button("Generate calendar entries", new FontIcon(MaterialDesignC.CREATION));
        generateCalendarEntriesButton.setOnAction(new ExecutionActionEventHandler(selection, this.getProgress(), applicationContext));
        generateCalendarEntriesButton.setMaxHeight(Double.MAX_VALUE);
        generateCalendarEntriesButton.disableProperty().bind(executionDisabled);

        this.add(progressTitleLabel, 0, 0, 1, 1);
        this.add(progressBar, 0, 1, 1, 1);
        this.add(generateCalendarEntriesButton, 1, 0, 1, 2);
        this.setHgap(5);
        this.setVgap(5);
        this.setPadding(new Insets(10, 10, 10, 10));

    }

    public ExecutionProgress getProgress() {
        return this.progress;
    }

}
