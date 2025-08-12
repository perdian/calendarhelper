package de.perdian.apps.calendarhelper.modules.items.templates;

import de.perdian.apps.calendarhelper.modules.items.Item;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ShowItemTemplatesDialogAction implements EventHandler<ActionEvent> {

    private ObservableList<Item> items = null;

    public ShowItemTemplatesDialogAction(ObservableList<Item> items) {
        this.setItems(items);
    }

    @Override
    public void handle(ActionEvent event) {

        BorderPane repositoryPane = new BorderPane();
        Scene repositoryDialogScene = new Scene(repositoryPane);

        Stage repositoryDialogStage = new Stage();
        repositoryDialogStage.initModality(Modality.APPLICATION_MODAL);
        repositoryDialogStage.setScene(repositoryDialogScene);
        repositoryDialogStage.setTitle("Select item(s) from template(s)");
        repositoryDialogStage.setMinWidth(640);
        repositoryDialogStage.setMinHeight(640);
        repositoryDialogStage.setWidth(800);
        repositoryDialogStage.setHeight(600);
        repositoryDialogStage.centerOnScreen();
        repositoryDialogStage.show();

    }

    private ObservableList<Item> getItems() {
        return this.items;
    }
    private void setItems(ObservableList<Item> items) {
        this.items = items;
    }

}
