package de.perdian.apps.calendarhelper.services.google.users.login;

import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

public class LoginPane extends BorderPane {

    public LoginPane(String loginUrl) {
        Platform.runLater(() -> {
            WebView webView = new WebView();
            webView.getEngine().load(loginUrl);
            this.setCenter(webView);
        });
    }

}
