package de.perdian.apps.calendarbuddy.modules.ai.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.perdian.apps.calendarbuddy.modules.ai.AiProviderClient;
import de.perdian.apps.calendarbuddy.modules.ai.AiProviderException;
import de.perdian.apps.calendarbuddy.modules.ai.AiProviderRequest;
import de.perdian.apps.calendarbuddy.modules.ai.AiProviderResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

public class OpenRouterAiProviderClient implements AiProviderClient {

    public static final String PDF_ENGINE_PDF_TEXT = "pdf-text";
    public static final String PDF_ENGINE_MISTRAL_OCR = "mistral-ocr";
    public static final String PDF_ENGINE_NATIVE = "native";

    private static final URI ENDPOINT = URI.create("https://openrouter.ai/api/v1/chat/completions");
    private static final String MEDIATYPE_JSON = "application/json; charset=utf-8";

    private static final Logger log = LoggerFactory.getLogger(OpenRouterAiProviderClient.class.getName());

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private String pdfEngine = OpenRouterAiProviderClient.PDF_ENGINE_NATIVE;

    public OpenRouterAiProviderClient() {
        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .version(HttpClient.Version.HTTP_2)
            .build();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public AiProviderResponse sendRequest(AiProviderRequest request, String apiKey) {
        try {

            ObjectNode httpRequestPayloadNode = this.createRequestPayloadNode(request);
            String httpRequestPayloadString = this.getObjectMapper().writeValueAsString(httpRequestPayloadNode);

            HttpRequest httpRequest = HttpRequest.newBuilder(OpenRouterAiProviderClient.ENDPOINT)
                .timeout(Duration.ofSeconds(120))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", OpenRouterAiProviderClient.MEDIATYPE_JSON)
                .header("X-Title", "CalendarBuddy")
                .POST(HttpRequest.BodyPublishers.ofString(httpRequestPayloadString))
                .build();
            log.debug("Sending request to OpenRouter [using {}]", request.getModel());

            Instant httpRequestStartInstant = Instant.now();
            HttpResponse<String> httpResponse = this.getHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
            int httpResponseCode = httpResponse.statusCode();
            if (httpResponseCode >= 300) {
                throw new AiProviderException("Invalid HTTP response code from OpenRouter endpoint: " + httpResponseCode);
            }

            String httpResponseBodyString = httpResponse.body();
            JsonNode httpResponseNode = this.getObjectMapper().readTree(httpResponseBodyString);
            if (httpResponseNode.has("error")) {
                throw new AiProviderException("Error returned from OpenRouter: " + httpResponseNode.path("error").path("message").asText(httpResponseBodyString));
            }

            JsonNode firstChoiceNode = httpResponseNode.path("choices").path(0);

            AiProviderResponse aiProviderResponse = new AiProviderResponse();
            aiProviderResponse.setMessage(firstChoiceNode.path("message").path("content").asText());
            aiProviderResponse.setModel(httpResponseNode.path("model").asText(request.getModel()));
            aiProviderResponse.setPromptTokens(httpResponseNode.path("usage").path("prompt_tokens").asInt());
            aiProviderResponse.setCompletionTokens(httpResponseNode.path("usage").path("completion_tokens").asInt());
            aiProviderResponse.setDuration(Duration.between(httpRequestStartInstant, Instant.now()));
            return aiProviderResponse;

        } catch (JsonProcessingException e) {
            throw new AiProviderException("Failed to serialize request payload", e);
        } catch (IOException e) {
            throw new AiProviderException("Failed to contact OpenRouter endpoint", e);
        } catch (InterruptedException e) {
            throw new AiProviderException("Interrupted while sending OpenRouter request", e);
        }
    }

    private ObjectNode createRequestPayloadNode(AiProviderRequest request) {
        ObjectNode payloadNode = this.getObjectMapper().createObjectNode();
        payloadNode.put("model", request.getModel());
        if (StringUtils.isNotEmpty(this.getPdfEngine())) {
            payloadNode.putArray("plugins").addObject().put("id", "file-parser").putObject("pdf").put("engine", this.getPdfEngine());
        }
        ObjectNode messageNode = payloadNode.putArray("messages").addObject();
        messageNode.put("role", "user");
        if (request.getAttachedFiles() == null || request.getAttachedFiles().isEmpty()) {
            messageNode.put("content", request.getPrompt());
        } else {
            ArrayNode contentNode = messageNode.putArray("content");
            contentNode.addObject().put("type", "text").put("text", request.getPrompt());
            for (Resource attachedFile : request.getAttachedFiles()) {
                ObjectNode partNode = contentNode.addObject();
                this.appendAttachmentToNode(partNode, attachedFile);
            }
        }
        return payloadNode;
    }

    private void appendAttachmentToNode(ObjectNode targetNode, Resource attachedFile) {
        try {
            String fileName = attachedFile.getFilename();
            int lastPointIndex = fileName.lastIndexOf(".");
            String fileExtension = lastPointIndex > 0 ? fileName.substring(lastPointIndex + 1).toLowerCase() : "";
            switch (fileExtension) {
                case "png", "jpg", "jpeg", "webp", "gif" -> this.appendAttachmentToNodeForImage(targetNode, attachedFile, fileExtension);
                case "pdf" -> this.appendAttachmentToNodeForPdf(targetNode, attachedFile, fileExtension);
                default -> this.appendAttachmentToNodeForBinary(targetNode, attachedFile, fileExtension);
            }
        } catch (IOException e) {
            throw new AiProviderException("Failed to read attachment from: " + attachedFile, e);
        }
    }

    private void appendAttachmentToNodeForImage(ObjectNode targetNode, Resource attachedFile, String attachedFileExtension) throws IOException {
        String mimeType = "jpg".equalsIgnoreCase(attachedFileExtension) ? "image/jpeg" : ("image/" + attachedFileExtension);
        String imageDataUrl = "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(attachedFile.getContentAsByteArray());
        targetNode.put("type", "image_url");
        targetNode.putObject("image_url").put("url", imageDataUrl);
    }

    private void appendAttachmentToNodeForPdf(ObjectNode targetNode, Resource attachedFile, String attachedFileExtension) throws IOException {
        String pdfDataUrl = "data:application/pdf;base64," + Base64.getEncoder().encodeToString(attachedFile.getContentAsByteArray());
        targetNode.put("type", "file");
        targetNode.putObject("file")
            .put("filename", attachedFile.getFilename())
            .put("file_data", pdfDataUrl);
    }

    private void appendAttachmentToNodeForBinary(ObjectNode targetNode, Resource attachedFile, String attachedFileExtension) throws IOException {
        String binaryDataUrl = "data:application/octet-stream;base64," + Base64.getEncoder().encodeToString(attachedFile.getContentAsByteArray());
        targetNode.put("type", "file");
        targetNode.putObject("file")
            .put("filename", attachedFile.getFilename())
            .put("file_data", binaryDataUrl);
    }

    private HttpClient getHttpClient() {
        return this.httpClient;
    }

    private ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    public String getPdfEngine() {
        return this.pdfEngine;
    }
    public void setPdfEngine(String pdfEngine) {
        this.pdfEngine = pdfEngine;
    }

}
