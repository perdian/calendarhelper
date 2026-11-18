package de.perdian.apps.calendarbuddy.modules.items;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.generator.impl.module.SimpleTypeModule;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.github.victools.jsonschema.module.jackson.JacksonOption;
import de.perdian.apps.calendarbuddy.modules.items.impl.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ItemImportService {

    private static final Logger log = LoggerFactory.getLogger(ItemImportService.class);

    private static final List<Class<?>> ITEM_CLASSES = List.of(
        FlightJourneyItem.class, FlightSingleItem.class,
        TrainJourneyItem.class, TrainSingleItem.class,
        GenericSingleItem.class
    );

    private ObjectMapper objectMapper = null;

    @PostConstruct
    void initializeObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerSubtypes(ITEM_CLASSES);
        objectMapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.setObjectMapper(objectMapper);
    }

    public List<Item> importItemsFromJsonString(String json) {
        String cleanedUpJson = this.cleanupJson(json);
        try {
            ItemImportList itemImportList = this.getObjectMapper().readValue(cleanedUpJson, ItemImportList.class);
            return itemImportList.getItems();
        } catch (Exception e) {
            log.info("Failed to import items from JSON string", e);
            log.info("JSON String: {}", cleanedUpJson);
            throw new RuntimeException("Failed to import items from JSON string", e);
        }
    }

    private String cleanupJson(String inputJson) {
        return inputJson
            .replace('\u201C', '"')
            .replace('\u201D', '"')
            .replace('\u201E', '"')
            .replace('\u00AB', '"')
            .replace('\u00BB', '"');
    }

    public String createImportJsonSchema() {
        try {

            SchemaGeneratorConfigBuilder schemaGeneratorConfigBuilder = new SchemaGeneratorConfigBuilder(
                SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON)
                .without(Option.PUBLIC_NONSTATIC_FIELDS,
                    Option.NONPUBLIC_NONSTATIC_FIELDS_WITH_GETTERS,
                    Option.NONPUBLIC_NONSTATIC_FIELDS_WITHOUT_GETTERS)
                .with(Option.GETTER_METHODS)
                .with(new JacksonModule(
                    JacksonOption.RESPECT_JSONPROPERTY_REQUIRED,
                    JacksonOption.FLATTENED_ENUMS_FROM_JSONPROPERTY))
                .with(new SimpleTypeModule()
                    .withStringType(ZoneId.class)
                    .withStringType(ZoneOffset.class))
                .with(Option.EXTRA_OPEN_API_FORMAT_VALUES)
                .with(Option.DEFINITIONS_FOR_ALL_OBJECTS)
                .with(Option.INLINE_ALL_SCHEMAS)
                .without(Option.SCHEMA_VERSION_INDICATOR);


            schemaGeneratorConfigBuilder.forTypesInGeneral().withSubtypeResolver((declaredType, context) -> {
                if (declaredType.getErasedType() != Item.class) {
                    return null; // not responsible, let other resolvers handle it
                }
                return ITEM_CLASSES.stream()
                    .map(itemClass -> context.getTypeContext().resolve(itemClass))
                    .toList();
            });

            schemaGeneratorConfigBuilder.forMethods().withPropertyNameOverrideResolver(method -> {
                String name = method.getDeclaredName();            // e.g. "getStartDate", "isFullDay"
                String stripped = null;
                if (name.startsWith("get") && name.length() > 3) {
                    stripped = name.substring(3);
                } else if (name.startsWith("is") && name.length() > 2) {
                    stripped = name.substring(2);
                }
                return stripped == null
                    ? null                                          // not a getter, keep default
                    : Character.toLowerCase(stripped.charAt(0)) + stripped.substring(1);
            });

            SchemaGenerator schemaGenerator = new SchemaGenerator(schemaGeneratorConfigBuilder.build());
            SchemaBuilder schemaBuilder = schemaGenerator.buildMultipleSchemaDefinitions();

            Class<?> rootClass = ItemImportList.class;
            ObjectNode rootTypes = this.getObjectMapper().createObjectNode();
            rootTypes.set(rootClass.getSimpleName(), schemaBuilder.createSchemaReference(rootClass));

            ObjectNode combined = this.getObjectMapper().createObjectNode();
            combined.set("rootTypes", rootTypes);
            combined.set("$defs", schemaBuilder.collectDefinitions("$defs"));
            this.stripMethodParentheses(combined);

            return this.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(combined);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot create JSON schema", e);
        }
    }

    private static void stripMethodParentheses(JsonNode node) {
        if (node instanceof ObjectNode objectNode) {
            if (objectNode.get("properties") instanceof ObjectNode propertiesNode) {
                Map<String, JsonNode> renamed = new LinkedHashMap<>();
                propertiesNode.fields().forEachRemaining(entry -> renamed.put(stripParentheses(entry.getKey()), entry.getValue()));
                propertiesNode.removeAll();
                propertiesNode.setAll(renamed);
            }
            if (objectNode.get("required") instanceof ArrayNode requiredNode) {
                List<String> names = new ArrayList<>();
                requiredNode.forEach(name -> names.add(stripParentheses(name.asText())));
                requiredNode.removeAll();
                names.forEach(requiredNode::add);
            }
            objectNode.elements().forEachRemaining(ItemImportService::stripMethodParentheses);
        } else if (node instanceof ArrayNode arrayNode) {
            arrayNode.elements().forEachRemaining(ItemImportService::stripMethodParentheses);
        }
    }

    private static String stripParentheses(String name) {

        return name.endsWith("()") ? name.substring(0, name.length() - 2) : name;
    }
    private ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }
    private void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

}
