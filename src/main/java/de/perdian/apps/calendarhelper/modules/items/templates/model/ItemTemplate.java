package de.perdian.apps.calendarhelper.modules.items.templates.model;

import com.fasterxml.jackson.databind.JsonNode;
import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;

public class ItemTemplate {

    private ItemTemplateType type = null;
    private JsonNode details = null;

    public Item createItem(ItemDefaults defaults) {
        return this.getType().getItemParser().parseItemDetails(this.getDetails(), defaults);
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
