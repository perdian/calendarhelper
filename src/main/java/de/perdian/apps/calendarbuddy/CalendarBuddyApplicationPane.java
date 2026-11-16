package de.perdian.apps.calendarbuddy;

import de.perdian.apps.calendarbuddy.modules.defaults.DefaultsModel;
import de.perdian.apps.calendarbuddy.modules.defaults.DefaultsModelPane;
import de.perdian.apps.calendarbuddy.modules.execution.ExecutionModel;
import de.perdian.apps.calendarbuddy.modules.execution.ExecutionModelPane;
import de.perdian.apps.calendarbuddy.modules.google.GooglePane;
import de.perdian.apps.calendarbuddy.modules.google.api.GoogleApiModel;
import de.perdian.apps.calendarbuddy.modules.google.calendar.GoogleCalendarModel;
import de.perdian.apps.calendarbuddy.modules.google.user.GoogleUserModel;
import de.perdian.apps.calendarbuddy.modules.items.ItemListModel;
import de.perdian.apps.calendarbuddy.modules.items.ItemListModelPane;
import de.perdian.apps.calendarbuddy.modules.items.ItemTypeRegistry;
import de.perdian.apps.calendarbuddy.modules.jobs.JobExecutor;
import de.perdian.apps.calendarbuddy.modules.upload.UploadModel;
import de.perdian.apps.calendarbuddy.support.components.ComponentFactory;
import javafx.geometry.Insets;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignL;
import org.springframework.context.ApplicationContext;

class CalendarBuddyApplicationPane extends BorderPane {

    CalendarBuddyApplicationPane(ApplicationContext applicationContext) {

        ComponentFactory componentFactory = new ComponentFactory();
        CalendarBuddyKeyRegistry keyRegistry = applicationContext.getBean(CalendarBuddyKeyRegistry.class);

        GoogleUserModel googleUserModel = applicationContext.getBean(GoogleUserModel.class);
        GoogleCalendarModel googleCalendarModel = applicationContext.getBean(GoogleCalendarModel.class);
        GoogleApiModel googleApiModel = applicationContext.getBean(GoogleApiModel.class);
        GooglePane googlePane = new GooglePane(googleUserModel, googleCalendarModel, googleApiModel);
        GridPane.setHgrow(googlePane, Priority.ALWAYS);

        DefaultsModel defaultsModel = applicationContext.getBean(DefaultsModel.class);
        DefaultsModelPane defaultsModelPane = new DefaultsModelPane(defaultsModel);
        GridPane.setHgrow(defaultsModelPane, Priority.ALWAYS);

        ExecutionModel executionModel = applicationContext.getBean(ExecutionModel.class);
        ExecutionModelPane executionModelPane = new ExecutionModelPane(executionModel);
        GridPane.setHgrow(executionModelPane, Priority.ALWAYS);

        GridPane generalPane = new GridPane(10, 10);
        generalPane.add(googlePane, 0, 0, 1, 1);
        generalPane.add(componentFactory.createVerticalSeparator(), 1, 0, 1, 1);
        generalPane.add(defaultsModelPane, 2, 0, 1, 1);
        generalPane.add(componentFactory.createVerticalSeparator(), 3, 0, 1, 1);
        generalPane.add(executionModelPane, 4, 0, 1, 1);

        TitledPane generalTitledPane = componentFactory.createTitledPane("General", generalPane);
        generalTitledPane.setGraphic(new FontIcon(MaterialDesignC.COG));
        generalTitledPane.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(generalTitledPane, Priority.ALWAYS);

        ItemTypeRegistry itemTypeListModel = applicationContext.getBean(ItemTypeRegistry.class);
        ItemListModel itemListModel = applicationContext.getBean(ItemListModel.class);
        ItemListModelPane itemListModelPane = new ItemListModelPane(itemListModel, itemTypeListModel, keyRegistry);
        TitledPane itemListTitledPane = componentFactory.createTitledPane("Items", itemListModelPane);
        itemListTitledPane.setGraphic(new FontIcon(MaterialDesignL.LIST_BOX));
        itemListTitledPane.setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(itemListTitledPane, Priority.ALWAYS);
        GridPane.setVgrow(itemListTitledPane, Priority.ALWAYS);

        GridPane contentPane = new GridPane(10, 10);
        contentPane.add(generalTitledPane, 0, 0, 1, 1);
        contentPane.add(itemListTitledPane, 0, 1, 1, 1);
        contentPane.setPadding(new Insets(10, 10, 10, 10));
        this.setCenter(contentPane);

        JobExecutor jobExecutor = applicationContext.getBean(JobExecutor.class);
        CalendarBuddyStatusPane statusPane = new CalendarBuddyStatusPane(googleCalendarModel, jobExecutor);
        statusPane.setPadding(new Insets(10, 15, 10, 15));
        statusPane.setStyle("-fx-border-color: -fx-box-border transparent transparent transparent; -fx-border-width: 1 0 0 0;");
        this.setBottom(statusPane);

        UploadModel uploadModel = applicationContext.getBean(UploadModel.class);
        uploadModel.installOn(generalTitledPane);
        uploadModel.installOn(itemListTitledPane);

    }

}
