package de.perdian.apps.calendarhelper.modules.items.model.types;

public enum Type {

    CONFERENCE("Konferenz"),
    HOTEL("Hotel");

    private String title = null;

    Type(String title) {
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
