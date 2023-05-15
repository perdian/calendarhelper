package de.perdian.apps.calendarhelper.services.google.users;

import de.perdian.apps.calendarhelper.services.google.GoogleApiException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

class GoogleAuthorizationCodeCallbackServer implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(GoogleAuthorizationCodeCallbackServer.class);

    private HttpServer httpServer = null;
    private CompletableFuture<String> authorizationCodeFuture = null;

    GoogleAuthorizationCodeCallbackServer(CompletableFuture<String> authorizationCodeFuture) throws GoogleApiException {
        this.setAuthorizationCodeFuture(authorizationCodeFuture);

        HttpServer httpServer = ServerBootstrap.bootstrap()
                .registerHandler("/*", (request, response, context) -> {
                    URI requestUri = URI.create(request.getRequestLine().getUri());
                    List<NameValuePair> requestParameters = URLEncodedUtils.parse(requestUri, StandardCharsets.UTF_8);
                    for (NameValuePair requestParameter : requestParameters) {
                        if ("code".equalsIgnoreCase(requestParameter.getName())) {

                            HttpEntity responseEntity = new StringEntity("Authorization code retrieved", StandardCharsets.UTF_8);
                            response.setStatusCode(200);
                            response.setHeader("Content-Type", "text/html");
                            response.setEntity(responseEntity);

                            this.getAuthorizationCodeFuture().complete(requestParameter.getValue());

                        }
                    }
                })
                .create();

        this.setHttpServer(httpServer);

        try {
            log.trace("Starting HTTP server to await incoming request with Google refresh token");
            httpServer.start();
            log.debug("Started HTTP server to await incoming request with Google refresh token listening on port: {}", httpServer.getLocalPort());
        } catch (Exception e) {
            throw new GoogleApiException("Cannot launch HTTP server to await Google refresh token", e);
        }

    }

    @Override
    public void close() {
        if (this.getHttpServer() != null) {
            log.debug("Shutting down HTTP server");
            try {
                this.getHttpServer().stop();
            } catch (Exception e) {
                log.debug("Cannot stop HTTP server", e);
            }
        }
    }

    public String createCallbackUrl() {
        StringBuilder returnUrl = new StringBuilder();
        returnUrl.append("http://localhost:").append(this.getHttpServer().getLocalPort());
        return returnUrl.toString();
    }

    private HttpServer getHttpServer() {
        return this.httpServer;
    }
    private void setHttpServer(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    private CompletableFuture<String> getAuthorizationCodeFuture() {
        return authorizationCodeFuture;
    }
    private void setAuthorizationCodeFuture(CompletableFuture<String> authorizationCodeFuture) {
        this.authorizationCodeFuture = authorizationCodeFuture;
    }

}
