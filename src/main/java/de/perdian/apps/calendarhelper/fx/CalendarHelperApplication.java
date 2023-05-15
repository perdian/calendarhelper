package de.perdian.apps.calendarhelper.fx;

import de.perdian.apps.calendarhelper.CalendarHelperConfiguration;
import de.perdian.apps.calendarhelper.services.google.GoogleApiException;
import de.perdian.apps.calendarhelper.services.google.users.GoogleUser;
import de.perdian.apps.calendarhelper.services.google.users.GoogleUserService;
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
    private CalendarHelperContext calendarHelperContext = null;

    @Override
    public void init() {

        log.info("Initializing Spring ApplicationContext");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CalendarHelperConfiguration.class);
        CalendarHelperContext calendarHelperContext = new CalendarHelperContext(applicationContext);

        this.setApplicationContext(applicationContext);
        this.setCalendarHelperContext(calendarHelperContext);

    }

    @Override
    public void start(Stage primaryStage) {

        CalendarHelperMainPane mainPane = new CalendarHelperMainPane(this.getCalendarHelperContext(), this.getApplicationContext());
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
                GoogleUserService googleUserProvider = this.getApplicationContext().getBean(GoogleUserService.class);
                GoogleUser googleUser = googleUserProvider.lookupUser();
                log.info("Using Google user: {}", googleUser);
                this.getCalendarHelperContext().activeGoogleUserProperty().setValue(googleUser);
            } catch (GoogleApiException e) {
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

    private CalendarHelperContext getCalendarHelperContext() {
        return calendarHelperContext;
    }
    private void setCalendarHelperContext(CalendarHelperContext calendarHelperContext) {
        this.calendarHelperContext = calendarHelperContext;
    }

    private void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    private ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
