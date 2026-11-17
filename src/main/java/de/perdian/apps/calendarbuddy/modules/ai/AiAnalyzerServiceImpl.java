package de.perdian.apps.calendarbuddy.modules.ai;

import de.perdian.apps.calendarbuddy.modules.items.Item;
import de.perdian.apps.calendarbuddy.modules.items.ItemImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class AiAnalyzerServiceImpl implements AiAnalyzerService {

    private ItemImportService itemImportService = null;

    @Override
    public List<Item> parseItems(String request, List<Resource> resources) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String createDataExtractionUserPrompt() {
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append(AiAnalyzerConstants.DATA_EXTRACTION_USER_PROMPT);
        promptBuilder.append("\n");
        promptBuilder.append(this.getItemImportService().createImportJsonSchema());
        return promptBuilder.toString();
    }

    ItemImportService getItemImportService() {
        return this.itemImportService;
    }
    @Autowired
    void setItemImportService(ItemImportService itemImportService) {
        this.itemImportService = itemImportService;
    }

}
