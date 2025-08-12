package de.perdian.apps.calendarhelper.modules.items.support.types;

public enum CalendarAvailability {

    AVAILABLE("Available", "transparent"),
    BLOCKED("Blocked", "opaque");

    private String title = null;
    private String googleApiValue = null;

    CalendarAvailability(String title, String googleApiValue) {
        this.setTitle(title);
        this.setGoogleApiValue(googleApiValue);
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

    public String getGoogleApiValue() {
        return this.googleApiValue;
    }
    private void setGoogleApiValue(String googleApiValue) {
        this.googleApiValue = googleApiValue;
    }

}
