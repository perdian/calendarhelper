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
        GridPane.setHgrow(providerComboBox, Priority.ALWAYS);

        Label apiKeyLabel = componentFactory.createTitleLabel("API Key");
        TextField apiKeyField = componentFactory.createPasswordField(aiProviderModel.apiKeyProperty());
        GridPane.setMargin(apiKeyLabel, new Insets(8, 0, 0, 0));

        this.add(providerLabel, 0, 0, 1, 1);
        this.add(providerComboBox, 0, 1, 1, 1);
        this.add(apiKeyLabel, 0, 2, 1, 1);
        this.add(apiKeyField, 0, 3, 1, 1);

        this.setHgap(10);
        this.setVgap(2);

    }

}
