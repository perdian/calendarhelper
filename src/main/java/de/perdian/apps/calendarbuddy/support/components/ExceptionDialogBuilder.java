package de.perdian.apps.calendarbuddy.support.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class ExceptionDialogBuilder {

    private String title = null;
    private Throwable exception = null;

    public ExceptionDialogBuilder() {
    }

    public Dialog<?> createDialog() {
        TextArea textarea = new TextArea(ExceptionUtils.getStackTrace(this.getException()));
        textarea.setEditable(false);
        textarea.setFont(Font.font("Monospace"));
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().setContent(textarea);
        alert.setHeaderText(this.getTitle());
        alert.setTitle("Error");
        alert.setHeaderText(this.getTitle());
        return alert;
    }

    public ExceptionDialogBuilder withTitle(String title) {
        this.setTitle(title);
        return this;
    }

    public ExceptionDialogBuilder withException(Throwable exception) {
        this.setException(exception);
        return this;
    }

    String getTitle() {
        return this.title;
    }
    private void setTitle(String title) {
        this.title = title;
    }

    Throwable getException() {
        return this.exception;
    }
    private void setException(Throwable exception) {
        this.exception = exception;
    }

}
