package de.perdian.apps.calendarhelper.modules.items.templates;

import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;
import de.perdian.apps.calendarhelper.modules.items.ItemsEditor;
import de.perdian.apps.calendarhelper.modules.items.ItemsEditorRegistry;
import de.perdian.apps.calendarhelper.modules.items.templates.model.ItemTemplate;
import de.perdian.apps.calendarhelper.modules.items.templates.model.ItemTemplateCategory;
import de.perdian.apps.calendarhelper.modules.items.templates.model.ItemTemplateFileContent;
import de.perdian.apps.calendarhelper.support.fx.components.ComponentFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

class ItemTemplateRepositoryContentPane extends VBox {

    public ItemTemplateRepositoryContentPane(ItemTemplateFileContent repositoryContent, Consumer<List<Item>> targetItemsConsumer, ItemDefaults defaults, ComponentFactory componentFactory) {

        List<ItemTemplatePane> itemTemplatePanes = new ArrayList<>();

        Button createItemsButton = componentFactory.createButton("Create items", new FontIcon(MaterialDesignC.CREATION));
        createItemsButton.setOnAction(_ -> {
            List<Item> itemsToCreate = new ArrayList<>();
            for (ItemTemplatePane itemTemplatePane : itemTemplatePanes) {
                if (itemTemplatePane.selectedProperty().getValue()) {
                    itemsToCreate.add(itemTemplatePane.getItem());
                }
            }
            targetItemsConsumer.accept(itemsToCreate);
        });
        Button cancelButton = componentFactory.createButton("Cancel", new FontIcon(MaterialDesignC.CANCEL));
        cancelButton.setOnAction(_ -> targetItemsConsumer.accept(Collections.emptyList()));
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(0, 15, 15, 15));
        buttonBox.setSpacing(5);
        buttonBox.getChildren().addAll(createItemsButton, cancelButton);

        VBox itemsPane = new VBox();
        itemsPane.setSpacing(5);
        for (ItemTemplateCategory category : repositoryContent.getCategories()) {
            VBox categoryPane = new VBox();
            categoryPane.setSpacing(10);
            for (ItemTemplate itemTemplate : category.getItems()) {
                Item item = itemTemplate.createItem(defaults);
                ItemsEditor<Item> itemEditor = ItemsEditorRegistry.resolveEditorForItem(item);
                Pane itemEditorPane = itemEditor.createItemEditorPane(item, componentFactory);
                ItemTemplatePane itemTemplatePane = new ItemTemplatePane(item, itemEditorPane, i -> targetItemsConsumer.accept(List.of(i)), componentFactory);
                categoryPane.getChildren().add(itemTemplatePane);
                itemTemplatePanes.add(itemTemplatePane);
            }
            TitledPane categoryTitledPane = new TitledPane(category.getName(), categoryPane);
            categoryTitledPane.setExpanded(false);
            itemsPane.getChildren().add(categoryTitledPane);
        }

        ScrollPane itemsScrollPane = new ScrollPane(itemsPane);
        itemsScrollPane.setPadding(new Insets(10, 10, 10, 10));
        itemsScrollPane.setStyle("-fx-background-color: transparent;");
        itemsScrollPane.setFitToWidth(true);
        VBox.setVgrow(itemsScrollPane, Priority.ALWAYS);

        this.setSpacing(10);
        this.getChildren().add(itemsScrollPane);
        this.getChildren().add(new Separator());
        this.getChildren().add(buttonBox);

    }

}
