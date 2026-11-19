package de.perdian.apps.calendarbuddy.modules.ai;

import org.springframework.core.io.Resource;

import java.util.List;

public class AiProviderRequest {

    private String prompt = null;
    private String model = AiAnalyzerConstants.DEFAULT_MODEL;
    private String pdfEngine = AiAnalyzerConstants.DEFAULT_PDF_ENGINE;
    private List<Resource> attachedFiles = null;

    public AiProviderRequest() {
    }

    public AiProviderRequest(String prompt) {
        this.setPrompt(prompt);
    }

    public String getPrompt() {
        return this.prompt;
    }
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getModel() {
        return this.model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public String getPdfEngine() {
        return this.pdfEngine;
    }
    public void setPdfEngine(String pdfEngine) {
        this.pdfEngine = pdfEngine;
    }

    public List<Resource> getAttachedFiles() {
        return this.attachedFiles;
    }
    public void setAttachedFiles(List<Resource> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

}
