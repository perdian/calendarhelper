package de.perdian.apps.calendarhelper.modules.google.user.impl;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;

class GoogleAuthorizationCode {

    private AuthorizationCodeFlow flow = null;
    private String callbackUrl = null;
    private String value = null;

    GoogleAuthorizationCode(AuthorizationCodeFlow flow, String callbackUrl, String value) {
        this.setFlow(flow);
        this.setCallbackUrl(callbackUrl);
        this.setValue(value);
    }

    AuthorizationCodeFlow getFlow() {
        return this.flow;
    }
    private void setFlow(AuthorizationCodeFlow flow) {
        this.flow = flow;
    }

    String getCallbackUrl() {
        return this.callbackUrl;
    }
    private void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getValue() {
        return this.value;
    }
    private void setValue(String value) {
        this.value = value;
    }

}
