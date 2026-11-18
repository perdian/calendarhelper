package de.perdian.apps.calendarbuddy.modules.ai;

import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
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
        TextField modelField = componentFactory.createTextField(aiProviderModel.selectedModelProperty());
        GridPane.setMargin(modelLabel, new Insets(8, 0, 0, 0));

        this.add(providerLabel, 0, 0, 1, 1);
        this.add(providerComboBox, 0, 1, 1, 1);
        this.add(apiKeyLabel, 1, 0, 1, 1);
        this.add(apiKeyField, 1, 1, 1, 1);
        this.add(modelLabel, 0, 2, 2, 1);
        this.add(modelField, 0, 3, 2, 1);

        this.setHgap(10);
        this.setVgap(2);

    }

}
