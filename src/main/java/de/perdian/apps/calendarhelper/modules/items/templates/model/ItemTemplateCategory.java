package de.perdian.apps.calendarhelper.modules.items.templates.model;

import java.util.List;

public class ItemTemplateCategory {

    private String name = null;
    private List<ItemTemplate> items = null;

    public String getName() {
        return this.name;
    }
    void setName(String name) {
        this.name = name;
    }

    public List<ItemTemplate> getItems() {
        return this.items;
    }
    void setItems(List<ItemTemplate> items) {
        this.items = items;
    }

}
