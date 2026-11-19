package de.perdian.apps.calendarbuddy.modules.upload;

import de.perdian.apps.calendarbuddy.modules.ai.AiAnalyzerService;
import de.perdian.apps.calendarbuddy.modules.ai.AiProvider;
import de.perdian.apps.calendarbuddy.modules.ai.AiProviderModel;
import de.perdian.apps.calendarbuddy.modules.items.Item;
import de.perdian.apps.calendarbuddy.modules.items.ItemListModel;
import de.perdian.apps.calendarbuddy.modules.jobs.JobExecutor;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collection;
import java.util.List;

@Component
public class UploadModel {

    private final BooleanProperty dragOverActive;
    private final BooleanProperty dragHandlingActive;
    private final DoubleProperty progress;
    private final AiAnalyzerService aiAnalyzerService;
    private final AiProviderModel aiProviderModel;
    private final ItemListModel itemListModel;
    private final JobExecutor jobExecutor;

    @Autowired
    UploadModel(AiAnalyzerService aiAnalyzerService, AiProviderModel aiProviderModel, ItemListModel itemListModel, JobExecutor jobExecutor) {
        this.dragOverActive = new SimpleBooleanProperty(false);
        this.dragHandlingActive = new SimpleBooleanProperty(false);
        this.progress = new SimpleDoubleProperty(0);
        this.aiAnalyzerService = aiAnalyzerService;
        this.aiProviderModel = aiProviderModel;
        this.itemListModel = itemListModel;
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
        this.jobExecutor.submitJob(progressController -> {
            try {
                List<FileSystemResource> fileResources = files.stream().map(file -> new FileSystemResource(file)).toList();
                AiProvider aiProvider = this.aiProviderModel.getSelectedProvider();
                String apiKey = this.aiProviderModel.getApiKey();
                String model = this.aiProviderModel.getSelectedModel();
                String pdfEngine = this.aiProviderModel.getSelectedPdfEngine();
                List<Item> aiParsedItems = this.aiAnalyzerService.parseItems(aiProvider, model, pdfEngine, apiKey, fileResources);
                Platform.runLater(() -> this.itemListModel.getItems().addAll(aiParsedItems));
                progressController.updateProgress(1d, "Imported " + aiParsedItems + " items");
            } finally {
                this.dragHandlingActive.setValue(false);
            }
        }, "Parsing " + files.size() + " documents");
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
