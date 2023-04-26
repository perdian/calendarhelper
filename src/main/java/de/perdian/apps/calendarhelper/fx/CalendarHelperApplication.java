package de.perdian.apps.calendarhelper.fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CalendarHelperApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        primaryStage.getIcons().add(new Image(this.getClass().getClassLoader().getResourceAsStream("icons/calendar-regular.png")));
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(700);
        primaryStage.setTitle("CalendarHelper");
        primaryStage.setWidth(Math.min(1400, screenBounds.getWidth() - 250));
        primaryStage.setHeight(Math.min(900, screenBounds.getHeight() - 250));
        primaryStage.show();
    }

}
