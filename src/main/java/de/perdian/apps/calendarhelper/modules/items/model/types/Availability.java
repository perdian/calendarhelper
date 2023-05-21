package de.perdian.apps.calendarhelper.modules.items.model.types;

public enum Availability {

    AVAILABLE("Available", "transparent"),
    BLOCKED("Blocked", "opaque");

    private String title = null;
    private String apiValue = null;

    private Availability(String title, String apiValue) {
        this.setTitle(title);
        this.setApiValue(apiValue);
    }

    @Override
    public String toString() {
        return this.getTitle();
    }

    public String getTitle() {
        return this.title;
    }
    private void setTitle(String title) {
        this.title = title;
    }

    public String getApiValue() {
        return this.apiValue;
    }
    private void setApiValue(String apiValue) {
        this.apiValue = apiValue;
    }

}
