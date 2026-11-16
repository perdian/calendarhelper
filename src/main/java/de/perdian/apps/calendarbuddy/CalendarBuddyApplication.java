package de.perdian.apps.calendarbuddy;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class CalendarBuddyApplication extends Application {

    private static final Logger log = LoggerFactory.getLogger(CalendarBuddyApplication.class);

    @Override
    public void start(Stage primaryStage) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CalendarBuddyConfiguration.class);
        CalendarBuddyApplicationPane applicationPane = new CalendarBuddyApplicationPane(applicationContext);
        Scene applicationScene = new Scene(applicationPane);

        applicationScene.focusOwnerProperty().addListener((observable, oldValue, newValue) -> {
            System.err.println("NEW FOCUS: " + newValue);
        });

        CalendarBuddyKeyRegistry keyRegistry = applicationContext.getBean(CalendarBuddyKeyRegistry.class);
        applicationScene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> keyRegistry.onKeyPressed(keyEvent));

        log.debug("Opening main application window");
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        primaryStage.setOnCloseRequest(_ -> Platform.exit());
        primaryStage.setMinWidth(1400);
        primaryStage.setMinHeight(700);
        primaryStage.setTitle("Calendar Buddy");
        primaryStage.setWidth(Math.min(1400, screenBounds.getWidth() - 250));
        primaryStage.setHeight(Math.min(900, screenBounds.getHeight() - 250));
        primaryStage.setScene(applicationScene);
        primaryStage.show();

        Map<String, CalendarBuddyLifecycle> lifecycleCallbacks = applicationContext.getBeansOfType(CalendarBuddyLifecycle.class);
        for (Map.Entry<?, CalendarBuddyLifecycle> lifecycleEntry : lifecycleCallbacks.entrySet()) {
            lifecycleEntry.getValue().onAfterApplicationOpened();
        }

    }

}
