package de.perdian.apps.calendarhelper.modules.items.templates;

import com.fasterxml.jackson.databind.JsonNode;
import de.perdian.apps.calendarhelper.modules.items.Item;

public class ItemTemplate {

    private StoredItemType type = null;
    private JsonNode details = null;

    public Item toItem() {
        throw new UnsupportedOperationException();
    }

    StoredItemType getType() {
        return this.type;
    }
    void setType(StoredItemType type) {
        this.type = type;
    }

    JsonNode getDetails() {
        return this.details;
    }
    void setDetails(JsonNode details) {
        this.details = details;
    }

}
