package de.perdian.apps.calendarhelper.modules.items.templates;

import com.fasterxml.jackson.databind.JsonNode;
import de.perdian.apps.calendarhelper.modules.items.Item;
import de.perdian.apps.calendarhelper.modules.items.ItemDefaults;

import java.util.List;

public enum ItemTemplateType {

    AIRTRAVEL {
        @Override List<Item> toItems(ItemDefaults defaults, JsonNode detailsNode) {
            return List.of();
        }
    },
    TRAIN {
        @Override List<Item> toItems(ItemDefaults defaults, JsonNode detailsNode) {
            return List.of();
        }
    };

    abstract List<Item> toItems(ItemDefaults defaults, JsonNode detailsNode);

}
