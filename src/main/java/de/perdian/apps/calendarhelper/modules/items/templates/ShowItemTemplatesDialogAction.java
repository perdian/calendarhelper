package de.perdian.apps.calendarhelper.modules.items.templates;

import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

public class ShowItemTemplatesDialogAction implements EventHandler<ActionEvent> {

    private ObservableList<Item> items = null;
    private ItemDefaults defaults = null;

    public ShowItemTemplatesDialogAction(ObservableList<Item> items, ItemDefaults defaults) {
        this.setItems(items);
    }

    @Override
    public void handle(ActionEvent event) {

        Stage repositoryDialogStage = new Stage();
        Consumer<List<Item>> itemsConsumer = items -> {
            if (items != null && !items.isEmpty()) {
                this.getItems().addAll(items);
            }
            repositoryDialogStage.close();
        };

        ItemTemplateRepositoryContent repositoryContent = ItemTemplateRepository.loadContent();
        ItemTemplateRepositoryContentPane repositoryContentPane = new ItemTemplateRepositoryContentPane(repositoryContent, itemsConsumer, this.getDefaults());

        Scene repositoryDialogScene = new Scene(repositoryContentPane);
        repositoryDialogScene.setOnKeyPressed(keyEvent -> {
            if (KeyCode.ESCAPE.equals(keyEvent.getCode())) {
                repositoryDialogStage.close();
            }
        });

        repositoryDialogStage.initModality(Modality.APPLICATION_MODAL);
        repositoryDialogStage.setResizable(false);
        repositoryDialogStage.setScene(repositoryDialogScene);
        repositoryDialogStage.setTitle("Select item(s) from template(s)");
        repositoryDialogStage.setMinWidth(1400);
        repositoryDialogStage.setMinHeight(800);
        repositoryDialogStage.centerOnScreen();
        repositoryDialogStage.show();

    }

    private ObservableList<Item> getItems() {
        return this.items;
    }
    private void setItems(ObservableList<Item> items) {
        this.items = items;
    }

    private ItemDefaults getDefaults() {
        return this.defaults;
    }
    private void setDefaults(ItemDefaults defaults) {
        this.defaults = defaults;
    }

}
