package de.perdian.apps.calendarbuddy.modules.jobs;

import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
import de.perdian.apps.calendarbuddy.support.components.ExceptionDialogBuilder;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

public class JobExecutorStatusPane extends GridPane {

    public JobExecutorStatusPane(JobExecutor jobExecutor) {

        ComponentFactory componentFactory = new ComponentFactory();

        Label jobsLabel = componentFactory.createLabel("Jobs");

        ProgressBar progressBar = new ProgressBar(0d);
        progressBar.disableProperty().bind(jobExecutor.runningProperty().not());
        progressBar.progressProperty().bind(jobExecutor.currentProgressValueProperty());
        progressBar.setMinHeight(25);
        progressBar.setMaxHeight(Double.MAX_VALUE);
        progressBar.setMinWidth(500);
        GridPane.setVgrow(progressBar, Priority.ALWAYS);

        Button cancelButton = componentFactory.createButton("Cancel");
        cancelButton.setOnAction(_ -> {
            Job currentJob = jobExecutor.getCurrentJob();
            if (currentJob != null) {
                currentJob.setCancelled(true);
            }
        });
        cancelButton.setDisable(true);

        Button lastExceptionButton = componentFactory.createButton("", new FontIcon(MaterialDesignC.CHECKBOX_BLANK));
        lastExceptionButton.setTooltip(new Tooltip("Show last exception"));
        lastExceptionButton.disableProperty().bind(jobExecutor.lastExceptionProperty().isNull());
        lastExceptionButton.setOnAction(_ -> {
            new ExceptionDialogBuilder()
                .withException(jobExecutor.getLastException())
                .createDialog()
                .show();
        });
        jobExecutor.lastExceptionProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                lastExceptionButton.setGraphic(new FontIcon(MaterialDesignC.CHECKBOX_INTERMEDIATE));
            } else {
                lastExceptionButton.setGraphic(new FontIcon(MaterialDesignC.CHECKBOX_MARKED));
            }
        });

        Label messageLabel = componentFactory.createLabel("");
        messageLabel.textProperty().bind(jobExecutor.currentProgressMessageProperty());
        messageLabel.setAlignment(Pos.CENTER_LEFT);
        GridPane.setHgrow(messageLabel, Priority.ALWAYS);

        this.setHgap(10);
        this.add(jobsLabel, 0, 0, 1, 1);
        this.add(progressBar, 1, 0, 1, 1);
        this.add(cancelButton, 2, 0, 1, 1);
        this.add(lastExceptionButton, 3, 0, 1, 1);
        this.add(messageLabel, 4, 0, 1, 1);

        jobExecutor.currentJobProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                newValue.cancelledProperty().addListener((_, _, cancelled) -> cancelButton.setDisable(cancelled));
            } else {
                cancelButton.setDisable(true);
            }
        });

    }

}
