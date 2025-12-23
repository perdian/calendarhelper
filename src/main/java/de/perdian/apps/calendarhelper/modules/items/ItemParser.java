package de.perdian.apps.calendarhelper.modules.items;

import com.fasterxml.jackson.databind.JsonNode;

public interface ItemParser<T extends Item> {

    T parseItemDetails(JsonNode itemDetailsNode, ItemDefaults defaults);

}
