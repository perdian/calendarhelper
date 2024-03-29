package de.perdian.apps.calendarhelper.modules.google.user.impl;

import de.perdian.apps.calendarhelper.modules.google.GoogleApiException;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.CompletableFuture;

class GoogleAuthorizationCodeUserDialog implements AutoCloseable {

    private Stage loginStage = null;

    GoogleAuthorizationCodeUserDialog(String loginUrl, CompletableFuture<String> authorizationCodeFuture) {
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
            loginStage.setOnCloseRequest(event -> authorizationCodeFuture.completeExceptionally(new GoogleApiException("Login cancelled at Google")));
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
        return this.loginStage;
    }
    private void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

}
