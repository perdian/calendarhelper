package de.perdian.apps.calendarhelper.fx;

import de.perdian.apps.calendarhelper.fx.modules.actions.ActionsPane;
import de.perdian.apps.calendarhelper.fx.modules.calendar.ActiveCalendarPane;
import de.perdian.apps.calendarhelper.fx.modules.editor.EditorPane;
import de.perdian.apps.calendarhelper.fx.modules.user.CurrentUserPane;
import de.perdian.apps.calendarhelper.services.google.users.GoogleUserService;
import javafx.geometry.Insets;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.springframework.context.ApplicationContext;

class CalendarHelperMainPane extends GridPane {

    CalendarHelperMainPane(CalendarHelperContext calendarContext, ApplicationContext applicationContext) {

        CurrentUserPane currentUserPane = new CurrentUserPane(calendarContext.googleUserProperty(), applicationContext.getBean(GoogleUserService.class));
        currentUserPane.setPadding(new Insets(10, 10, 10, 10));
        currentUserPane.setPrefWidth(300);
        TitledPane currentUserTitledPane = new TitledPane("Current user", currentUserPane);
        currentUserTitledPane.setExpanded(true);
        currentUserTitledPane.setCollapsible(false);
        currentUserTitledPane.setMaxHeight(Double.MAX_VALUE);

        ActiveCalendarPane activeCalendarPane = new ActiveCalendarPane(calendarContext.googleCalendars(), calendarContext.activeGoogleCalendarProperty());
        activeCalendarPane.setPadding(new Insets(10, 10, 10, 10));
        activeCalendarPane.setPrefWidth(600);
        TitledPane activeCalendarTitledPane = new TitledPane("Active calendar", activeCalendarPane);
        activeCalendarTitledPane.setExpanded(true);
        activeCalendarTitledPane.setCollapsible(false);
        activeCalendarTitledPane.setMaxHeight(Double.MAX_VALUE);

        ActionsPane actionsPane = new ActionsPane();
        actionsPane.setPadding(new Insets(10, 10, 10, 10));
        TitledPane actionsTitledPane = new TitledPane("Actions", actionsPane);
        actionsTitledPane.setExpanded(true);
        actionsTitledPane.setCollapsible(false);
        actionsTitledPane.setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(actionsTitledPane, Priority.ALWAYS);

        EditorPane editorPane = new EditorPane();
        editorPane.setPadding(new Insets(10, 10, 10, 10));
        TitledPane editorTitledPane = new TitledPane("Editor", editorPane);
        editorTitledPane.setExpanded(true);
        editorTitledPane.setCollapsible(false);
        editorTitledPane.setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(editorTitledPane, Priority.ALWAYS);
        GridPane.setVgrow(editorTitledPane, Priority.ALWAYS);

        this.add(currentUserTitledPane, 0, 0, 1, 1);
        this.add(activeCalendarTitledPane, 1, 0, 1, 1);
        this.add(actionsTitledPane, 2, 0, 1, 1);
        this.add(editorTitledPane, 0, 1, 3, 1);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(10, 10, 10, 10));

    }

}
