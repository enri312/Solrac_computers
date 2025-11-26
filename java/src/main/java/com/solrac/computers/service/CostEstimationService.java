package com.solrac.computers.service;

import com.google.gson.Gson;
import com.solrac.computers.config.ConfigIA;
import com.solrac.computers.model.DeviceType;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * Service for estimating repair costs using AI
 * Provides cost ranges based on diagnosis and device type
 */
public class CostEstimationService {

    private final HttpClient httpClient;
    private final Gson gson;

    public CostEstimationService() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    /**
     * Estimate repair cost based on device and problem
     * @param deviceType Type of device
     * @param diagnosis The diagnosis from AI
     * @return Cost estimation with breakdown
     */
    public String estimateCost(DeviceType deviceType, String diagnosis) {
        try {
            String prompt = String.format(
                "Eres un experto en estimación de costos de reparación de dispositivos electrónicos. " +
                "Basado en este diagnóstico de un %s: '%s', " +
                "proporciona una estimación de costo en USD considerando:\n" +
                "1. Costo de partes/componentes\n" +
                "2. Horas de mano de obra (tarifa: $30/hora)\n" +
                "3. Nivel de dificultad\n\n" +
                "Formato de respuesta:\n" +
                "- Costo mínimo: $X\n" +
                "- Costo máximo: $Y\n" +
                "- Desglose: [breve explicación]\n" +
                "Máximo 100 palabras.",
                deviceType.getDisplayName(),
                diagnosis
            );

            LlamaAIService.ApiRequest requestBody = new LlamaAIService.ApiRequest(
                ConfigIA.MODELO,
                List.of(
                    new LlamaAIService.Message("system", "Eres un experto en costos de reparación de electrónicos. Proporciona estimaciones realistas basadas en precios de mercado."),
                    new LlamaAIService.Message("user", prompt)
                ),
                0.5,  // Lower temperature for more consistent estimates
                300   // Shorter response for cost estimates
            );

            String jsonBody = gson.toJson(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ConfigIA.GPT4ALL_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return parseResponse(response.body());
            } else {
                return "No se pudo estimar el costo. Servicio de IA no disponible.";
            }

        } catch (Exception e) {
            return "Error al estimar costo: " + e.getMessage();
        }
    }

    /**
     * Estimate cost with additional context about parts availability
     */
    public String estimateCostWithContext(DeviceType deviceType, String diagnosis, boolean partsAvailable) {
        String availabilityNote = partsAvailable 
            ? "Las partes están disponibles localmente." 
            : "Las partes deben importarse (agregar 20% al costo).";
        
        String enhancedDiagnosis = diagnosis + "\n\nNota: " + availabilityNote;
        return estimateCost(deviceType, enhancedDiagnosis);
    }

    private String parseResponse(String responseBody) {
        try {
            LlamaAIService.ApiResponse apiResponse = gson.fromJson(responseBody, LlamaAIService.ApiResponse.class);
            
            if (apiResponse.choices != null && !apiResponse.choices.isEmpty()) {
                String content = apiResponse.choices.get(0).message.content;
                return content.replace("\\n", "\n").replace("\\\"", "\"");
            } else {
                return "No se pudo generar estimación de costo.";
            }
        } catch (Exception e) {
            return "Error al procesar estimación: " + e.getMessage();
        }
    }
}
