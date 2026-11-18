package de.perdian.apps.calendarbuddy.modules.ai;

public interface AiProviderClient {

    AiProviderResponse sendRequest(AiProviderRequest request, String apiKey);

}
