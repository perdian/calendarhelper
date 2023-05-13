package de.perdian.apps.calendarhelper.services.google.users;

class GoogleRefreshToken {

    private String value = null;

    GoogleRefreshToken(String value) {
        this.setValue(value);
    }

    public String toString() {
        return this.getValue();
    }

    String getValue() {
        return value;
    }
    private void setValue(String value) {
        this.value = value;
    }

}
