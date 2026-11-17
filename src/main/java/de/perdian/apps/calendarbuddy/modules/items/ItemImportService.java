package de.perdian.apps.calendarbuddy.modules.items;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.github.victools.jsonschema.module.jackson.JacksonOption;
import de.perdian.apps.calendarbuddy.modules.items.impl.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemImportService {

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
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.registerSubtypes(ITEM_CLASSES);
        this.setObjectMapper(objectMapper);
    }

    public List<Item> importItemsFromJsonString(String json) {
        String cleanedUpJson = this.cleanupJson(json);
        try {
            ItemImportList itemImportList = this.getObjectMapper().readValue(cleanedUpJson, ItemImportList.class);
            return itemImportList.getItems();
        } catch (Exception e) {
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
                .with(new JacksonModule(
                    JacksonOption.RESPECT_JSONPROPERTY_REQUIRED,
                    JacksonOption.FLATTENED_ENUMS_FROM_JSONPROPERTY))
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

            SchemaGenerator schemaGenerator = new SchemaGenerator(schemaGeneratorConfigBuilder.build());
            SchemaBuilder schemaBuilder = schemaGenerator.buildMultipleSchemaDefinitions();

            Class<?> rootClass = ItemImportList.class;
            ObjectNode rootTypes = this.getObjectMapper().createObjectNode();
            rootTypes.set(rootClass.getSimpleName(), schemaBuilder.createSchemaReference(rootClass));

            ObjectNode combined = this.getObjectMapper().createObjectNode();
            combined.set("rootTypes", rootTypes);
            combined.set("$defs", schemaBuilder.collectDefinitions("$defs"));

            return this.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(combined);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Cannot create JSON schema", e);
        }
    }

    private ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }
    private void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

}
