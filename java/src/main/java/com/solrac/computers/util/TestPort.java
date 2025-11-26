package com.solrac.computers.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Utility class to test connectivity to the Ollama AI service.
 * This helps diagnose connection issues with the DeepSeek model.
 */
public class TestPort {
    private static final String OLLAMA_HOST = "localhost";
    private static final int OLLAMA_PORT = 11434;
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    
    public static void main(String[] args) {
        System.out.println("=== Test de Conectividad Ollama (DeepSeek) ===\n");
        
        // Test 1: Check if port is open
        testPortConnection();
        
        // Test 2: Try HTTP connection to API
        testHttpConnection();
        
        System.out.println("\n=== Fin del Test ===");
    }
    
    private static void testPortConnection() {
        System.out.println("1. Probando conexion al puerto " + OLLAMA_PORT + "...");
        try (Socket socket = new Socket(OLLAMA_HOST, OLLAMA_PORT)) {
            System.out.println("   ✓ EXITO: Puerto " + OLLAMA_PORT + " esta abierto y accesible.");
        } catch (Exception e) {
            System.out.println("   ✗ FALLO: No se pudo conectar al puerto " + OLLAMA_PORT);
            System.out.println("   Error: " + e.getMessage());
            System.out.println("   Asegurate de que Ollama este ejecutandose (comando: ollama serve)");
        }
    }
    
    private static void testHttpConnection() {
        System.out.println("\n2. Probando conexion HTTP a la API de Ollama...");
        try {
            URL url = new URL(OLLAMA_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            
            // Send a simple test request
            String jsonRequest = "{\"model\":\"deepseek-r1:8b\",\"prompt\":\"Test\",\"stream\":false}";
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonRequest.getBytes(StandardCharsets.UTF_8));
            }
            
            int responseCode = conn.getResponseCode();
            System.out.println("   Codigo de respuesta HTTP: " + responseCode);
            
            if (responseCode == 200) {
                System.out.println("   ✓ EXITO: La API de Ollama responde correctamente.");
                
                // Read response
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println("   Modelo DeepSeek esta funcionando correctamente.");
                }
            } else {
                System.out.println("   ⚠ ADVERTENCIA: La API respondio con codigo " + responseCode);
                System.out.println("   Verifica que el modelo 'deepseek-r1:8b' este instalado.");
                System.out.println("   Comando para instalar: ollama pull deepseek-r1:8b");
            }
            
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("   ✗ FALLO: No se pudo conectar a la API de Ollama");
            System.out.println("   Error: " + e.getMessage());
            System.out.println("   URL: " + OLLAMA_URL);
        }
    }
}
