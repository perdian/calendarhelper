package de.perdian.apps.calendarhelper;

import de.perdian.apps.calendarhelper.modules.account.fx.CurrentAccountPane;
import de.perdian.apps.calendarhelper.modules.execution.fx.ExecutionPane;
import de.perdian.apps.calendarhelper.modules.itemdefaults.fx.ItemDefaultsPane;
import de.perdian.apps.calendarhelper.modules.items.fx.ItemsPane;
import de.perdian.apps.calendarhelper.support.google.users.GoogleUserService;
import javafx.geometry.Insets;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.springframework.context.ApplicationContext;

class CalendarHelperMainPane extends GridPane {

    CalendarHelperMainPane(CalendarHelperContext calendarContext, ApplicationContext applicationContext) {

        CurrentAccountPane currentAccountPane = new CurrentAccountPane(calendarContext, applicationContext.getBean(GoogleUserService.class));
        currentAccountPane.setPrefWidth(500);
        currentAccountPane.disableProperty().bind(calendarContext.executionActiveProperty());
        TitledPane currentAccountTitledPane = new TitledPane("Current account", currentAccountPane);
        currentAccountTitledPane.setExpanded(true);
        currentAccountTitledPane.setCollapsible(false);
        currentAccountTitledPane.setMaxHeight(Double.MAX_VALUE);

        ItemDefaultsPane itemDefaultsPane = new ItemDefaultsPane(calendarContext.getItemDefaults());
        itemDefaultsPane.setPrefWidth(400);
        itemDefaultsPane.disableProperty().bind(calendarContext.executionActiveProperty());
        TitledPane itemDefaultsTitledPane = new TitledPane("Item defaults", itemDefaultsPane);
        itemDefaultsTitledPane.setExpanded(true);
        itemDefaultsTitledPane.setCollapsible(false);
        itemDefaultsTitledPane.setMaxHeight(Double.MAX_VALUE);

        ExecutionPane executionPane = new ExecutionPane(calendarContext, applicationContext);
        TitledPane executionTitledPane = new TitledPane("Execute", executionPane);
        executionTitledPane.setExpanded(true);
        executionTitledPane.setCollapsible(false);
        executionTitledPane.setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(executionTitledPane, Priority.ALWAYS);

        ItemsPane itemsPane = new ItemsPane(calendarContext.editorItems());
        itemsPane.disableProperty().bind(calendarContext.executionActiveProperty());
        TitledPane itemsTitlePane = new TitledPane("Items", itemsPane);
        itemsTitlePane.setExpanded(true);
        itemsTitlePane.setCollapsible(false);
        itemsTitlePane.setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(itemsTitlePane, Priority.ALWAYS);
        GridPane.setVgrow(itemsTitlePane, Priority.ALWAYS);

        this.add(currentAccountTitledPane, 0, 0, 1, 1);
        this.add(itemDefaultsTitledPane, 1, 0, 1, 1);
        this.add(executionTitledPane, 2, 0, 1, 1);
        this.add(itemsTitlePane, 0, 1, 3, 1);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(10, 10, 10, 10));

    }

}
