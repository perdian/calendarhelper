package de.perdian.apps.calendarhelper.modules.items.templates;

import java.util.ArrayList;
import java.util.List;

public class ItemTemplateRepositoryContent {

    private List<ItemTemplateCategory> categories = new ArrayList<>();

    public List<ItemTemplateCategory> getCategories() {
        return this.categories;
    }
    public void setCategories(List<ItemTemplateCategory> categories) {
        this.categories = categories;
    }

}
