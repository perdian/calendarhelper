package de.perdian.apps.calendarbuddy.modules.upload;

import de.perdian.apps.calendarbuddy.modules.jobs.JobExecutor;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collection;
import java.util.List;

@Component
public class UploadModel {

    private final BooleanProperty dragOverActive;
    private final BooleanProperty dragHandlingActive;
    private final DoubleProperty progress;
    private final UploadService uploadService;
    private final JobExecutor jobExecutor;

    @Autowired
    UploadModel(UploadService uploadService, JobExecutor jobExecutor) {
        this.dragOverActive = new SimpleBooleanProperty(false);
        this.dragHandlingActive = new SimpleBooleanProperty(false);
        this.progress = new SimpleDoubleProperty(0);
        this.uploadService = uploadService;
        this.jobExecutor = jobExecutor;
    }

    public void installOn(Node targetNode) {
        targetNode.setOnDragEntered(this::handleOnDragEntered);
        targetNode.setOnDragExited(this::handleOnDragExited);
        targetNode.setOnDragOver(this::handleOnDragOver);
        targetNode.setOnDragDropped(this::handleOnDragDropped);
    }

    private void processUploadedFiles(Collection<File> files) {
        this.dragHandlingActive.setValue(true);
        Thread.ofVirtual().start(() -> {
            try {
                Platform.runLater(() -> this.progress.setValue(ProgressBar.INDETERMINATE_PROGRESS));
                try { Thread.sleep(2000); } catch (InterruptedException e) { }
            } finally {
                Platform.runLater(() -> {
                    this.dragHandlingActive.setValue(false);
                    this.progress.setValue(1);
                });
            }
        });
    }

    private void handleOnDragEntered(DragEvent dragEvent) {
        this.dragOverActive.setValue(true);
        dragEvent.consume();
    }

    private void handleOnDragExited(DragEvent dragEvent) {
        this.dragOverActive.setValue(false);
        dragEvent.consume();
    }

    private void handleOnDragOver(DragEvent dragEvent) {
        if (!this.dragHandlingActive.get()) {
            if (dragEvent.getGestureSource() != this && dragEvent.getDragboard().hasFiles()) {
                dragEvent.acceptTransferModes(TransferMode.COPY);
            }
            dragEvent.consume();
        }
    }

    private void handleOnDragDropped(DragEvent dragEvent) {
        List<File> dragFiles = dragEvent.getDragboard().getFiles();
        if (dragFiles != null && !dragFiles.isEmpty()) {
            try {
                this.processUploadedFiles(dragFiles);
            } finally {
                dragEvent.setDropCompleted(true);
            }
            dragEvent.consume();
        }
    }

}
