package de.perdian.apps.calendarbuddy.modules.ai;

import de.perdian.apps.calendarbuddy.support.storage.StorageService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class AiProviderModel {

    private final ObservableList<AiProvider> availableProviders;
    private final ObjectProperty<AiProvider> selectedProvider;
    private final StringProperty selectedModel;
    private final StringProperty selectedPdfEngine;
    private final StringProperty apiKey;

    @Autowired
    AiProviderModel(List<AiProvider> availableProviders, StorageService storageService) {

        this.availableProviders = FXCollections.observableArrayList(availableProviders);
        this.selectedModel = storageService.getPersistentStringProperty("AiProviderModel.selectedModel", AiAnalyzerConstants.DEFAULT_MODEL);
        this.selectedPdfEngine = storageService.getPersistentStringProperty("AiProviderModel.selectedPdfEngine", AiAnalyzerConstants.DEFAULT_PDF_ENGINE);
        this.selectedProvider = new SimpleObjectProperty<>();
        this.apiKey = storageService.getPersistentStringProperty("AiProviderModel.apiKey");

        StringProperty selectedProviderNameProperty = storageService.getPersistentStringProperty("AiProviderModel.selectedProviderName");
        AiProvider selectedProvider = availableProviders.stream()
            .filter(provider -> Objects.equals(provider.getName(), selectedProviderNameProperty.getValue()))
            .findFirst()
            .orElse(availableProviders.isEmpty() ? null : availableProviders.get(0));
        this.selectedProvider.setValue(selectedProvider);
        this.selectedProvider.addListener((_, _, newValue) -> selectedProviderNameProperty.setValue(newValue == null ? null : newValue.getName()));

    }

    public ObservableList<AiProvider> getAvailableProviders() {
        return this.availableProviders;
    }

    public AiProvider getSelectedProvider() {
        return this.selectedProvider.get();
    }
    public ObjectProperty<AiProvider> selectedProviderProperty() {
        return this.selectedProvider;
    }
    public void setSelectedProvider(AiProvider selectedProvider) {
        this.selectedProvider.set(selectedProvider);
    }

    public String getSelectedModel() {
        return this.selectedModel.get();
    }
    public StringProperty selectedModelProperty() {
        return this.selectedModel;
    }
    public void setSelectedModel(String selectedModel) {
        this.selectedModel.set(selectedModel);
    }

    public String getSelectedPdfEngine() {
        return this.selectedPdfEngine.get();
    }
    public StringProperty selectedPdfEngineProperty() {
        return this.selectedPdfEngine;
    }
    public void setSelectedPdfEngine(String selectedPdfEngine) {
        this.selectedPdfEngine.set(selectedPdfEngine);
    }

    public String getApiKey() {
        return this.apiKey.get();
    }
    public StringProperty apiKeyProperty() {
        return this.apiKey;
    }
    public void setApiKey(String apiKey) {
        this.apiKey.set(apiKey);
    }

}
