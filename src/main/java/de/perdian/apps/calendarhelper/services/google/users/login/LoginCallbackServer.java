package de.perdian.apps.calendarhelper.services.google.users.login;

import de.perdian.apps.calendarhelper.services.google.users.GoogleUser;

import java.util.concurrent.CompletableFuture;

public class LoginCallbackServer implements AutoCloseable {

    public static LoginCallbackServer createServer(CompletableFuture<GoogleUser> userFuture) {
        return new LoginCallbackServer();
    }

    @Override
    public void close() throws Exception {
    }

    public String createCallbackUrl() {
        return "http://localhost/callback";
    }

}
