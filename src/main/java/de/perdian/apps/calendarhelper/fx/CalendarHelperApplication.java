package de.perdian.apps.calendarhelper.fx;

import de.perdian.apps.calendarhelper.CalendarHelperConfiguration;
import de.perdian.apps.calendarhelper.services.google.users.GoogleUserException;
import de.perdian.apps.calendarhelper.services.google.users.GoogleUserProvider;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CalendarHelperApplication extends Application {

    private static final Logger log = LoggerFactory.getLogger(CalendarHelperApplication.class);

    private ApplicationContext applicationContext = null;
    private CalendarContext calendarContext = null;

    @Override
    public void init() {

        log.info("Initializing Spring ApplicationContext");
        this.setCalendarContext(new CalendarContext());
        this.setApplicationContext(new AnnotationConfigApplicationContext(CalendarHelperConfiguration.class));

    }

    @Override
    public void start(Stage primaryStage) {

        CalendarContext calendarContext = this.getCalendarContext();
        CalendarHelperMainPane mainPane = new CalendarHelperMainPane(calendarContext);
        Scene primaryScene = new Scene(mainPane);

        log.debug("Opening main application window");
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        primaryStage.getIcons().add(new Image(this.getClass().getClassLoader().getResourceAsStream("icons/calendar-regular.png")));
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(700);
        primaryStage.setTitle("Calendar Helper");
        primaryStage.setWidth(Math.min(1400, screenBounds.getWidth() - 250));
        primaryStage.setHeight(Math.min(900, screenBounds.getHeight() - 250));
        primaryStage.setScene(primaryScene);
        primaryStage.show();

        Thread.ofVirtual().start(() -> {
            log.debug("Ensure that we have a valid Google user");
            try {
                GoogleUserProvider googleUserProvider = this.getApplicationContext().getBean(GoogleUserProvider.class);
                calendarContext.googleUserProperty().setValue(googleUserProvider.lookupUser());
            } catch (GoogleUserException e) {
                log.info("Could not lookup Google user", e);
                Platform.runLater(() -> {
                    Alert missingUserAlert = new Alert(Alert.AlertType.ERROR);
                    missingUserAlert.setHeaderText("Cannot launch Calender Helper application");
                    missingUserAlert.setContentText("Google user login failed [" + e + "]");
                    missingUserAlert.showAndWait();
                    Platform.exit();
                });
            }
        });

    }

    private CalendarContext getCalendarContext() {
        return calendarContext;
    }
    private void setCalendarContext(CalendarContext calendarContext) {
        this.calendarContext = calendarContext;
    }

    private void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    private ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
