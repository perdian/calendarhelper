package de.perdian.apps.calendarhelper.modules.items.templates;

import com.fasterxml.jackson.databind.JsonNode;
import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;

import java.util.List;

public class ItemTemplate {

    private ItemTemplateType type = null;
    private JsonNode details = null;

    public List<Item> toItems(ItemDefaults defaults) {
        return this.getType().toItems(defaults, this.getDetails());
    }

    ItemTemplateType getType() {
        return this.type;
    }
    void setType(ItemTemplateType type) {
        this.type = type;
    }

    JsonNode getDetails() {
        return this.details;
    }
    void setDetails(JsonNode details) {
        this.details = details;
    }

}
