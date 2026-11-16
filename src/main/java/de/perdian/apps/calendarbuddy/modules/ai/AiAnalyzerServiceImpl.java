package de.perdian.apps.calendarbuddy.modules.ai;

import de.perdian.apps.calendarbuddy.modules.items.Item;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class AiAnalyzerServiceImpl implements AiAnalyzerService {

    @Override
    public List<Item> parseItems(String request, List<Resource> resources) {
        throw new UnsupportedOperationException();
    }

}
