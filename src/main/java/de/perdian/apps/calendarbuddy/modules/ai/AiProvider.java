package de.perdian.apps.calendarbuddy.modules.ai;

public class AiProvider {

    private String name = null;
    private AiProviderClient client = null;

    @Override
    public String toString() {
        return this.getName();
    }

    public String getName() {
        return this.name;
    }
    void setName(String name) {
        this.name = name;
    }

    public AiProviderClient getClient() {
        return this.client;
    }
    void setClient(AiProviderClient client) {
        this.client = client;
    }

}
