package de.perdian.apps.calendarhelper.modules.items.impl.generic;

public enum GenericType {

    CONFERENCE("Konferenz"),
    HOTEL("Hotel");

    private String title = null;

    GenericType(String title) {
        this.setTitle(title);
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
}
