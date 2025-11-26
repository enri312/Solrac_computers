package com.solrac.computers.util;

import com.solrac.computers.model.DeviceType;
import com.solrac.computers.service.LlamaAIService;
import com.solrac.computers.service.CostEstimationService;

/**
 * Comprehensive test for AI services
 * Tests diagnosis, cost estimation, and different configurations
 */
public class TestAIServices {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║     PRUEBA COMPLETA DE SERVICIOS DE IA - SOLRAC COMPUTERS ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        // Test 1: Basic Diagnosis
        testBasicDiagnosis();
        
        // Test 2: Cost Estimation
        testCostEstimation();
        
        // Test 3: Multiple Device Types
        testMultipleDeviceTypes();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                    PRUEBAS COMPLETADAS                     ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
    
    private static void testBasicDiagnosis() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("TEST 1: Diagnóstico Básico");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        
        try {
            LlamaAIService aiService = new LlamaAIService();
            
            DeviceType device = DeviceType.NOTEBOOK;
            String problem = "La pantalla parpadea y se apaga sola después de 10 minutos de uso";
            
            System.out.println("📱 Dispositivo: " + device.getDisplayName());
            System.out.println("❓ Problema: " + problem);
            System.out.println("\n⏳ Consultando a la IA...\n");
            
            String diagnosis = aiService.getDiagnosis(device, problem);
            
            System.out.println("┌─────────────────────────────────────────────────────────┐");
            System.out.println("│ 🤖 DIAGNÓSTICO DE LA IA:                               │");
            System.out.println("└─────────────────────────────────────────────────────────┘");
            System.out.println(diagnosis);
            System.out.println("\n✅ Test 1 completado exitosamente\n");
            
        } catch (Exception e) {
            System.out.println("❌ Error en Test 1: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testCostEstimation() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("TEST 2: Estimación de Costos");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        
        try {
            CostEstimationService costService = new CostEstimationService();
            
            DeviceType device = DeviceType.SMARTPHONE;
            String diagnosis = "Pantalla LCD dañada, requiere reemplazo completo del módulo display";
            
            System.out.println("📱 Dispositivo: " + device.getDisplayName());
            System.out.println("🔍 Diagnóstico: " + diagnosis);
            System.out.println("\n⏳ Estimando costos...\n");
            
            String costEstimate = costService.estimateCost(device, diagnosis);
            
            System.out.println("┌─────────────────────────────────────────────────────────┐");
            System.out.println("│ 💰 ESTIMACIÓN DE COSTOS:                               │");
            System.out.println("└─────────────────────────────────────────────────────────┘");
            System.out.println(costEstimate);
            System.out.println("\n✅ Test 2 completado exitosamente\n");
            
        } catch (Exception e) {
            System.out.println("❌ Error en Test 2: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testMultipleDeviceTypes() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("TEST 3: Múltiples Tipos de Dispositivos");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        
        LlamaAIService aiService = new LlamaAIService();
        
        // Test case 1: Desktop
        testDevice(aiService, DeviceType.DESKTOP, "No enciende, solo se escucha un pitido continuo");
        
        // Test case 2: Console
        testDevice(aiService, DeviceType.CONSOLE, "Se sobrecalienta y se apaga durante el juego");
        
        System.out.println("✅ Test 3 completado exitosamente\n");
    }
    
    private static void testDevice(LlamaAIService service, DeviceType device, String problem) {
        System.out.println("─────────────────────────────────────────────────────────");
        System.out.println("📱 Dispositivo: " + device.getDisplayName());
        System.out.println("❓ Problema: " + problem);
        System.out.println("⏳ Consultando...");
        
        try {
            String diagnosis = service.getDiagnosis(device, problem);
            System.out.println("🤖 Diagnóstico: " + diagnosis.substring(0, Math.min(100, diagnosis.length())) + "...");
            System.out.println("✓ OK\n");
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage() + "\n");
        }
    }
}
