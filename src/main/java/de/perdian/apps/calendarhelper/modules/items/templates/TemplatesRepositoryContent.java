package de.perdian.apps.calendarhelper.modules.items.templates;

import java.util.ArrayList;
import java.util.List;

public class TemplatesRepositoryContent {

    private List<TemplatesRepositoryCategory> categories = new ArrayList<>();

    public List<TemplatesRepositoryCategory> getCategories() {
        return this.categories;
    }
    public void setCategories(List<TemplatesRepositoryCategory> categories) {
        this.categories = categories;
    }

}
