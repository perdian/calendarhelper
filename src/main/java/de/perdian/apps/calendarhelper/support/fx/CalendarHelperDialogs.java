package de.perdian.apps.calendarhelper.support.fx;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class CalendarHelperDialogs {

    public static void showErrorDialog(String title, String description, Throwable error) {
        Platform.runLater(() -> {
            Alert missingUserAlert = new Alert(Alert.AlertType.ERROR);
            missingUserAlert.setHeaderText(title);
            missingUserAlert.setContentText(description);
            missingUserAlert.showAndWait();
        });
    }

}
