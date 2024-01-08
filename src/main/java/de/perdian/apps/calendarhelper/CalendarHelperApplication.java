package de.perdian.apps.calendarhelper;

import de.perdian.apps.calendarhelper.modules.google.GoogleApiException;
import de.perdian.apps.calendarhelper.modules.google.user.GoogleUser;
import de.perdian.apps.calendarhelper.modules.google.user.GoogleUserService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
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

    @Override
    public void init() {
        log.info("Initializing Spring ApplicationContext");
        this.setApplicationContext(new AnnotationConfigApplicationContext(CalendarHelperConfiguration.class));
    }

    @Override
    public void start(Stage primaryStage) {

        CalendarHelperSelection selection = new CalendarHelperSelection(this.getApplicationContext());
        CalendarHelperMainPane mainPane = new CalendarHelperMainPane(selection, this.getApplicationContext());
        Scene primaryScene = new Scene(mainPane);

        log.debug("Opening main application window");
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        primaryStage.getIcons().add(new Image(this.getClass().getClassLoader().getResourceAsStream("icons/calendar-regular.png")));
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.setMinWidth(1400);
        primaryStage.setMinHeight(700);
        primaryStage.setTitle("Calendar Helper");
        primaryStage.setWidth(Math.min(1400, screenBounds.getWidth() - 250));
        primaryStage.setHeight(Math.min(900, screenBounds.getHeight() - 250));
        primaryStage.setScene(primaryScene);
        primaryStage.show();

        if (selection.apiCredentialsProperty().getValue() != null) {
            Thread.ofVirtual().start(() -> {
                log.debug("Ensure that we have a valid Google user");
                try {
                    GoogleUserService googleUserProvider = this.getApplicationContext().getBean(GoogleUserService.class);
                    GoogleUser googleUser = googleUserProvider.lookupUser(selection.apiCredentialsProperty().getValue());
                    log.info("Using Google user: {}", googleUser);
                    selection.activeUserProperty().setValue(googleUser);
                } catch (GoogleApiException e) {
                    log.info("Could not lookup Google user", e);
                }
            });
        }

    }

    private ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }
    private void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
