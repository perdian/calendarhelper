package de.perdian.apps.calendarbuddy.modules.ai;

import de.perdian.apps.calendarbuddy.modules.ai.impl.OpenRouterAiProviderClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AiProviderConfiguration {

    @Bean
    AiProvider openRouterAiProvider() {
        AiProvider aiModel = new AiProvider();
        aiModel.setName("OpenRouter");
        aiModel.setClient(this.openRouterAiProviderClient());
        return aiModel;
    }

    @Bean
    OpenRouterAiProviderClient openRouterAiProviderClient() {
        return new OpenRouterAiProviderClient();
    }

}
