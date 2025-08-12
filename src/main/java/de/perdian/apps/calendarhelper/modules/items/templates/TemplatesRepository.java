package de.perdian.apps.calendarhelper.modules.items.templates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TemplatesRepository {

    private static final String ITEM_TEMPLATES_FILE_LOCATION = "classpath:META-INF/resources/preconstructed/item-templates.yaml";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(new YAMLFactory());

    public static TemplatesRepositoryContent loadStoredItems() {
        try {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource itemTemplatesResource = resourceLoader.getResource(TemplatesRepository.ITEM_TEMPLATES_FILE_LOCATION);
            try (InputStream itemTemplatesStream = new BufferedInputStream(itemTemplatesResource.getInputStream())) {
                return OBJECT_MAPPER.readValue(itemTemplatesStream, TemplatesRepositoryContent.class);
            }
        } catch (IOException e) {
            throw new RuntimeException("Canot load preconstructed item templates", e);
        }
    }

}
