package de.perdian.apps.calendarbuddy.modules.ai;

import de.perdian.apps.calendarbuddy.modules.items.Item;
import org.springframework.core.io.Resource;

import java.util.List;

public interface AiAnalyzerService {

    List<Item> parseItems(String request, List<Resource> resources);

    String createDataExtractionUserPrompt();

}
