package de.perdian.apps.calendarhelper.services.google.users.support;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

class GoogleAuthorizationCodeUserDialog implements AutoCloseable {

    private Stage loginStage = null;

    GoogleAuthorizationCodeUserDialog(String loginUrl) {
        Platform.runLater(() -> {

            WebView webView = new WebView();
            webView.getEngine().load(loginUrl);
            BorderPane loginPane = new BorderPane(webView);

            Stage loginStage = new Stage();
            loginStage.initModality(Modality.APPLICATION_MODAL);
            loginStage.setTitle("Login Google User");
            loginStage.setMinWidth(640);
            loginStage.setMinHeight(480);
            loginStage.setScene(new Scene(loginPane, 800, 850));
            loginStage.centerOnScreen();
            loginStage.show();
            this.setLoginStage(loginStage);

        });
    }

    @Override
    public void close() {
        try {
            if (this.getLoginStage() != null) {
                Platform.runLater(() -> this.getLoginStage().close());
            }
        } catch (Exception e) {
            // Ignore here, there is nothing we can do anyway
        }
    }

    private Stage getLoginStage() {
        return loginStage;
    }
    private void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

}
