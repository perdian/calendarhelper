package de.perdian.apps.calendarhelper.support.fx;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class CalendarHelperDialogs {

    public static void showErrorDialog(String title, String description, Throwable error) {
        Platform.runLater(() -> {

            TextArea exceptionArea = new TextArea(ExceptionUtils.getStackTrace(error));
            exceptionArea.setFont(Font.font("Monospaced", 13));
            exceptionArea.setEditable(false);
            BorderPane exceptionPane = new BorderPane(exceptionArea);
            exceptionPane.setPadding(new Insets(10, 10, 10, 10));

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(title);
            alert.setContentText(description);
            if (error != null) {
                alert.getDialogPane().setPrefWidth(800);
                alert.getDialogPane().setPrefHeight(600);
                alert.getDialogPane().setExpandableContent(exceptionPane);
                alert.getDialogPane().setExpanded(true);
            }
            alert.showAndWait();

        });
    }

}
