package de.perdian.apps.calendarbuddy.modules.items.impl;

public enum GenericSingleItemType {

    CONFERENCE("Konferenz", "🎙️"),
    HOTEL("Hotel", "🏨");

    private String title = null;
    private String icon = null;

    GenericSingleItemType(String title, String icon) {
        this.setTitle(title);
        this.setIcon(icon);
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

    public String getIcon() {
        return this.icon;
    }
    private void setIcon(String icon) {
        this.icon = icon;
    }

}
