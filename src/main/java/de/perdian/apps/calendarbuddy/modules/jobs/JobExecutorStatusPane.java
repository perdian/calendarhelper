package de.perdian.apps.calendarbuddy.modules.jobs;

import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class JobExecutorStatusPane extends GridPane {

    public JobExecutorStatusPane(JobExecutor jobExecutor) {

        ComponentFactory componentFactory = new ComponentFactory();

        Label jobsLabel = componentFactory.createLabel("Jobs");

        ProgressBar progressBar = new ProgressBar(0d);
        progressBar.disableProperty().bind(jobExecutor.runningProperty().not());
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

        Label messageLabel = componentFactory.createLabel("");
        messageLabel.setAlignment(Pos.CENTER_LEFT);
        GridPane.setHgrow(messageLabel, Priority.ALWAYS);

        this.setHgap(10);
        this.add(jobsLabel, 0, 0, 1, 1);
        this.add(progressBar, 1, 0, 1, 1);
        this.add(cancelButton, 2, 0, 1, 1);
        this.add(messageLabel, 3, 0, 1, 1);

        jobExecutor.currentJobProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                Platform.runLater(() -> {
                    progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
                    cancelButton.setDisable(newValue.isCancelled());
                });
                newValue.progressProperty().addListener((_, _, progressValue) -> progressBar.setProgress(progressValue));
                newValue.progressMessageProperty().addListener((_, _, progressMessage) -> messageLabel.setText(progressMessage));
                newValue.cancelledProperty().addListener((_, _, cancelled) -> cancelButton.setDisable(cancelled));
            } else {
                cancelButton.setDisable(true);
            }
        });

    }

}
