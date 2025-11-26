package com.solrac.computers.util;

import com.solrac.computers.model.DeviceType;
import com.solrac.computers.service.LlamaAIService;

/**
 * Diverse AI Questions Test
 * Tests the AI with various types of questions to verify it works properly
 */
public class DiverseAITest {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║        TEST DE PREGUNTAS VARIADAS - SOLRAC COMPUTERS      ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        LlamaAIService aiService = new LlamaAIService();

        
        // Test 1: Diagnóstico típico
        testQuestion(
            "Test 1: Diagnóstico de Notebook",
            () -> aiService.getDiagnosis(
                DeviceType.NOTEBOOK,
                "La batería se descarga muy rápido, solo dura 1 hora"
            )
        );
        
        // Test 2: Problema de hardware
        testQuestion(
            "Test 2: Problema de Celular",
            () -> aiService.getDiagnosis(
                DeviceType.SMARTPHONE,
                "El teléfono se calienta mucho al cargar"
            )
        );
        
        // Test 3: Problema de consola
        testQuestion(
            "Test 3: Problema de Consola",
            () -> aiService.getDiagnosis(
                DeviceType.CONSOLE,
                "PlayStation 5 muestra error CE-108255-1"
            )
        );
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║              TODOS LOS TESTS COMPLETADOS                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
    
    private static void testQuestion(String testName, QuestionSupplier supplier) {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println(testName);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        try {
            System.out.println("⏳ Consultando...\n");
            String answer = supplier.get();
            
            System.out.println("┌─────────────────────────────────────────────────────────┐");
            System.out.println("│ 🤖 RESPUESTA:                                          │");
            System.out.println("└─────────────────────────────────────────────────────────┘");
            
            // Mostrar solo los primeros 200 caracteres para mantener el output manejable
            if (answer.length() > 200) {
                System.out.println(answer.substring(0, 200) + "...");
                System.out.println("\n[Respuesta completa: " + answer.length() + " caracteres]");
            } else {
                System.out.println(answer);
            }
            
            System.out.println("\n✅ Test completado exitosamente");
            
            // Pequeña pausa entre tests para no saturar la API
            Thread.sleep(1000);
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    @FunctionalInterface
    interface QuestionSupplier {
        String get() throws Exception;
    }
}
