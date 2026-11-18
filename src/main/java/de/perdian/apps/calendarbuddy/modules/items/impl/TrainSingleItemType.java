package de.perdian.apps.calendarbuddy.modules.items.impl;

public enum TrainSingleItemType {

    ICE("🚆"),
    ECE("🚆"),
    IC("🚊"),
    EC("🚊"),
    RE("🚊"),
    RB("🚊"),
    STR("🚃"),
    U("🚇"),
    BUS("🚍");

    private String icon = null;

    TrainSingleItemType(String icon) {
        this.setIcon(icon);
    }

    public String getIcon() {
        return this.icon;
    }
    private void setIcon(String icon) {
        this.icon = icon;
    }
}
