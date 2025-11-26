package com.solrac.computers.service;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.solrac.computers.config.ConfigIA;
import com.solrac.computers.model.DeviceType;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

/**
 * AI Service using deepseek-r1:8b via Ollama
 * Uses Ollama's /api/generate endpoint for better compatibility
 */
public class LlamaAIService implements IAIService {

    private final HttpClient httpClient;
    private final Gson gson;

    public LlamaAIService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    @Override
    public String getDiagnosis(DeviceType deviceType, String problemDescription) {
        try {
            // Build the prompt in Spanish for better results with local model
            String prompt = String.format(
                "Actúa como técnico profesional en reparación de %s. " +
                "Genera diagnóstico detallado y posibles soluciones para este problema:\n%s",
                deviceType.getDisplayName().toLowerCase(),
                problemDescription
            );

            // Create request using Ollama's generate API
            OllamaGenerateRequest requestBody = new OllamaGenerateRequest(
                ConfigIA.MODELO,
                prompt,
                false  // stream = false for complete response
            );

            String jsonBody = gson.toJson(requestBody);

            // Build HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ConfigIA.OLLAMA_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // Send request and get response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return parseGenerateResponse(response.body());
            } else {
                return "Error al conectar con el servicio de IA (código: " + response.statusCode() + 
                       "). Verifique que Ollama esté ejecutándose.";
            }

        } catch (IOException | InterruptedException e) {
            return "Error de conexión con la IA. Verifique que Ollama esté abierto y escuchando en el puerto 11434.";
        } catch (Exception e) {
            return "Error inesperado al procesar la solicitud: " + e.getMessage();
        }
    }

    /**
     * Parse Ollama generate API response and extract diagnosis text
     */
    private String parseGenerateResponse(String responseBody) {
        try {
            OllamaGenerateResponse apiResponse = gson.fromJson(responseBody, OllamaGenerateResponse.class);
            
            if (apiResponse.response != null && !apiResponse.response.isBlank()) {
                // Clean up the response
                return apiResponse.response.trim();
            } else {
                return "No se pudo obtener un diagnóstico (respuesta vacía de la IA).";
            }
        } catch (Exception e) {
            return "Error al procesar la respuesta de la IA. Por favor intente nuevamente.";
        }
    }

    // ==================== POJO Classes for Ollama Generate API ====================
    
    /**
     * Request body for Ollama's /api/generate endpoint
     */
    static class OllamaGenerateRequest {
        @SerializedName("model")
        String model;
        
        @SerializedName("prompt")
        String prompt;
        
        @SerializedName("stream")
        Boolean stream;

        OllamaGenerateRequest(String model, String prompt, Boolean stream) {
            this.model = model;
            this.prompt = prompt;
            this.stream = stream;
        }
    }

    /**
     * Response from Ollama's /api/generate endpoint
     */
    static class OllamaGenerateResponse {
        @SerializedName("model")
        String model;
        
        @SerializedName("response")
        String response;
        
        @SerializedName("done")
        Boolean done;
        
        @SerializedName("context")
        int[] context;
        
        @SerializedName("total_duration")
        Long totalDuration;
        
        @SerializedName("load_duration")
        Long loadDuration;
        
        @SerializedName("prompt_eval_count")
        Integer promptEvalCount;
        
        @SerializedName("eval_count")
        Integer evalCount;
    }
}
