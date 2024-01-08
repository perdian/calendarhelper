package de.perdian.apps.calendarhelper;

import de.perdian.apps.calendarhelper.modules.execution.ExecutionPane;
import de.perdian.apps.calendarhelper.modules.google.apicredentials.GoogleApiCredentialsPane;
import de.perdian.apps.calendarhelper.modules.google.user.GoogleUserPane;
import de.perdian.apps.calendarhelper.modules.google.user.GoogleUserService;
import de.perdian.apps.calendarhelper.modules.itemdefaults.ItemDefaultsPane;
import de.perdian.apps.calendarhelper.modules.items.ItemsPane;
import javafx.geometry.Insets;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;
import org.kordamp.ikonli.materialdesign2.MaterialDesignG;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.springframework.context.ApplicationContext;

class CalendarHelperMainPane extends GridPane {

    CalendarHelperMainPane(CalendarHelperSelection selection, ApplicationContext applicationContext) {

        GoogleApiCredentialsPane apiCredentialsPane = new GoogleApiCredentialsPane(selection.apiCredentialsProperty());
        apiCredentialsPane.setPadding(new Insets(10, 10, 10, 10));
        TitledPane apiCredentialsTitlePane = new TitledPane("Google API Credentials", apiCredentialsPane);
        apiCredentialsTitlePane.setGraphic(new FontIcon(MaterialDesignG.GOOGLE));
        apiCredentialsTitlePane.setCollapsible(false);

        GoogleUserService userService = applicationContext.getBean(GoogleUserService.class);
        GoogleUserPane userPane = new GoogleUserPane(selection, userService);
        userPane.setPadding(new Insets(10, 10, 10, 10));
        userPane.setPrefWidth(400);
        TitledPane userTitledPane = new TitledPane("Google User", userPane);
        userTitledPane.setGraphic(new FontIcon(MaterialDesignF.FACE));
        userTitledPane.setCollapsible(false);

        ItemDefaultsPane itemDefaultsPane = new ItemDefaultsPane(selection.getItemDefaults());
        itemDefaultsPane.setPadding(new Insets(10, 10, 10, 10));
        itemDefaultsPane.setPrefWidth(400);
        itemDefaultsPane.disableProperty().bind(selection.busyProperty());
        TitledPane itemDefaultsTitledPane = new TitledPane("Item defaults", itemDefaultsPane);
        itemDefaultsTitledPane.setGraphic(new FontIcon(MaterialDesignF.FORMAT_LIST_CHECKS));
        itemDefaultsTitledPane.setCollapsible(false);
        itemDefaultsTitledPane.setMaxHeight(Double.MAX_VALUE);
        GridPane.setFillHeight(itemDefaultsTitledPane, true);

        ExecutionPane executionPane = new ExecutionPane(selection, applicationContext);
        TitledPane executionTitledPane = new TitledPane("Execute", executionPane);
        executionTitledPane.setGraphic(new FontIcon(MaterialDesignP.PLAY_BOX));
        executionTitledPane.setCollapsible(false);
        executionTitledPane.setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(executionTitledPane, Priority.ALWAYS);
        GridPane.setFillHeight(executionTitledPane, true);

        ItemsPane itemsPane = new ItemsPane(selection.getActiveItems());
        TitledPane itemsTitledPane = new TitledPane("Items", itemsPane);
        itemsTitledPane.setGraphic(new FontIcon(MaterialDesignF.FORMAT_LIST_BULLETED));
        itemsTitledPane.setCollapsible(false);
        itemsTitledPane.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(itemsTitledPane, Priority.ALWAYS);

        this.add(apiCredentialsTitlePane, 0, 0, 1, 1);
        this.add(userTitledPane, 0, 1, 1, 1);
        this.add(itemDefaultsTitledPane, 1, 0, 1, 2);
        this.add(executionTitledPane, 2, 0, 1, 2);
        this.add(itemsTitledPane, 0, 2, 3, 1);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(10, 10, 10, 10));

    }

}
