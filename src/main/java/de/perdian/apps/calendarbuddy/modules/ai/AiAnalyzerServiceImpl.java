package de.perdian.apps.calendarbuddy.modules.ai;

import de.perdian.apps.calendarbuddy.modules.items.Item;
import de.perdian.apps.calendarbuddy.modules.items.ItemImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class AiAnalyzerServiceImpl implements AiAnalyzerService {

    private ItemImportService itemImportService = null;

    @Override
    public List<Item> parseItems(AiProvider aiProvider, String model, String apiKey, List<? extends Resource> resources) {

        String aiProviderPrompt = this.createDataExtractionUserPrompt(resources);
        AiProviderRequest aiProviderRequest = new AiProviderRequest();
        aiProviderRequest.setPrompt(aiProviderPrompt);
        aiProviderRequest.setModel(model);
        aiProviderRequest.setAttachedFiles(new ArrayList<>(resources));

        AiProviderResponse aiProviderResponse = aiProvider.getClient().sendRequest(aiProviderRequest, apiKey);
        String aiProviderResponseString = aiProviderResponse.getMessage();
        return this.getItemImportService().importItemsFromJsonString(aiProviderResponseString);

    }

    private String createDataExtractionUserPrompt(List<? extends Resource> resources) {
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
