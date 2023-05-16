package de.perdian.apps.calendarhelper.fx;

import de.perdian.apps.calendarhelper.fx.modules.account.CurrentAccountPane;
import de.perdian.apps.calendarhelper.fx.modules.editor.EditorPane;
import de.perdian.apps.calendarhelper.fx.modules.execution.ExecutionPane;
import de.perdian.apps.calendarhelper.services.google.users.GoogleUserService;
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

        ExecutionPane executionPane = new ExecutionPane(calendarContext);
        TitledPane executionTitledPane = new TitledPane("Execute", executionPane);
        executionTitledPane.setExpanded(true);
        executionTitledPane.setCollapsible(false);
        executionTitledPane.setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(executionTitledPane, Priority.ALWAYS);

        EditorPane editorPane = new EditorPane(calendarContext.editorItems());
        editorPane.disableProperty().bind(calendarContext.executionActiveProperty());
        TitledPane editorTitledPane = new TitledPane("Editor", editorPane);
        editorTitledPane.setExpanded(true);
        editorTitledPane.setCollapsible(false);
        editorTitledPane.setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(editorTitledPane, Priority.ALWAYS);
        GridPane.setVgrow(editorTitledPane, Priority.ALWAYS);

        this.add(currentAccountTitledPane, 0, 0, 1, 1);
        this.add(executionTitledPane, 1, 0, 1, 1);
        this.add(editorTitledPane, 0, 1, 2, 1);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(10, 10, 10, 10));

    }

}
