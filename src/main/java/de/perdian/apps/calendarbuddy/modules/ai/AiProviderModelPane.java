package de.perdian.apps.calendarbuddy.modules.ai;

import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class AiProviderModelPane extends GridPane {

    public AiProviderModelPane(AiProviderModel aiProviderModel) {

        ComponentFactory componentFactory = new ComponentFactory();

        Label providerLabel = componentFactory.createTitleLabel("Provider");
        ComboBox<AiProvider> providerComboBox = componentFactory.createComboBox(aiProviderModel.selectedProviderProperty(), aiProviderModel.getAvailableProviders());
        providerComboBox.setMinWidth(100);
        GridPane.setHgrow(providerComboBox, Priority.ALWAYS);

        Label apiKeyLabel = componentFactory.createTitleLabel("API Key");
        TextField apiKeyField = componentFactory.createPasswordField(aiProviderModel.apiKeyProperty());

        Label modelLabel = componentFactory.createTitleLabel("Model");
        ComboBox<String> modelBox = componentFactory.createComboBox(aiProviderModel.selectedModelProperty(), FXCollections.observableArrayList(AiAnalyzerConstants.AVAILABLE_MODELS));
        modelBox.setEditable(true);
        modelBox.setMinWidth(170);
        GridPane.setHgrow(modelBox, Priority.ALWAYS);
        GridPane.setMargin(modelLabel, new Insets(8, 0, 0, 0));

        Label pdfEngineLabel = componentFactory.createTitleLabel("PDF Engine");
        ComboBox<String> pdfEngineBox = componentFactory.createComboBox(aiProviderModel.selectedPdfEngineProperty(), FXCollections.observableArrayList(AiAnalyzerConstants.AVAILABLE_PDF_ENGINES));
        pdfEngineBox.setEditable(true);
        GridPane.setHgrow(pdfEngineBox, Priority.ALWAYS);
        GridPane.setMargin(pdfEngineLabel, new Insets(8, 0, 0, 0));

        this.add(providerLabel, 0, 0, 1, 1);
        this.add(providerComboBox, 0, 1, 1, 1);
        this.add(apiKeyLabel, 1, 0, 1, 1);
        this.add(apiKeyField, 1, 1, 1, 1);
        this.add(modelLabel, 0, 2, 1, 1);
        this.add(modelBox, 0, 3, 1, 1);
        this.add(pdfEngineLabel, 1, 2, 1, 1);
        this.add(pdfEngineBox, 1, 3, 1, 1);

        this.setHgap(10);
        this.setVgap(2);

    }

}
