package com.solrac.computers.util;

import com.solrac.computers.model.DeviceType;
import com.solrac.computers.service.LlamaAIService;
import java.util.Scanner;

/**
 * Interactive AI Test - Ask any question to the AI
 * This allows you to test the AI with custom questions
 */
public class InteractiveAITest {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          TEST INTERACTIVO DE IA - SOLRAC COMPUTERS        ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        LlamaAIService aiService = new LlamaAIService();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("OPCIONES:");
            System.out.println("1. Diagnóstico de Notebook");
            System.out.println("2. Diagnóstico de Celular");
            System.out.println("3. Diagnóstico de PC de Mesa");
            System.out.println("4. Diagnóstico de Consola");
            System.out.println("5. Pregunta libre a la IA");
            System.out.println("0. Salir");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.print("\nSeleccione una opción: ");
            
            String option = scanner.nextLine().trim();
            
            if (option.equals("0")) {
                System.out.println("\n👋 ¡Hasta luego!");
                break;
            }
            
            DeviceType deviceType = null;
            String problem = null;
            
            switch (option) {
                case "1":
                    deviceType = DeviceType.NOTEBOOK;
                    System.out.print("\n📱 Describe el problema del Notebook: ");
                    problem = scanner.nextLine();
                    askDiagnosis(aiService, deviceType, problem);
                    break;
                    
                case "2":
                    deviceType = DeviceType.SMARTPHONE;
                    System.out.print("\n📱 Describe el problema del Celular: ");
                    problem = scanner.nextLine();
                    askDiagnosis(aiService, deviceType, problem);
                    break;
                    
                case "3":
                    deviceType = DeviceType.DESKTOP;
                    System.out.print("\n🖥️ Describe el problema de la PC: ");
                    problem = scanner.nextLine();
                    askDiagnosis(aiService, deviceType, problem);
                    break;
                    
                case "4":
                    deviceType = DeviceType.CONSOLE;
                    System.out.print("\n🎮 Describe el problema de la Consola: ");
                    problem = scanner.nextLine();
                    askDiagnosis(aiService, deviceType, problem);
                    break;
                    
                case "5":
                    askFreeQuestion(aiService, scanner);
                    break;
                    
                default:
                    System.out.println("❌ Opción inválida. Intente nuevamente.");
            }
        }
        
        scanner.close();
    }
    
    private static void askDiagnosis(LlamaAIService aiService, DeviceType deviceType, String problem) {
        if (problem == null || problem.trim().isEmpty()) {
            System.out.println("❌ Debe describir el problema.");
            return;
        }
        
        System.out.println("\n⏳ Consultando a la IA...\n");
        
        try {
            String diagnosis = aiService.getDiagnosis(deviceType, problem);
            
            System.out.println("┌─────────────────────────────────────────────────────────┐");
            System.out.println("│ 🤖 DIAGNÓSTICO DE LA IA:                               │");
            System.out.println("└─────────────────────────────────────────────────────────┘");
            System.out.println(diagnosis);
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
    
    private static void askFreeQuestion(LlamaAIService aiService, Scanner scanner) {
        System.out.println("\n💭 PREGUNTA LIBRE A LA IA");
        System.out.println("Puedes hacer cualquier pregunta relacionada con reparación de dispositivos.");
        System.out.print("\n❓ Tu pregunta: ");
        
        String question = scanner.nextLine();
        
        if (question == null || question.trim().isEmpty()) {
            System.out.println("❌ Debe escribir una pregunta.");
            return;
        }
        
        System.out.println("\n⏳ Consultando a la IA...\n");
        
        try {
            // Usamos NOTEBOOK como tipo genérico para preguntas libres
            String answer = aiService.getDiagnosis(DeviceType.NOTEBOOK, question);
            
            System.out.println("┌─────────────────────────────────────────────────────────┐");
            System.out.println("│ 🤖 RESPUESTA DE LA IA:                                 │");
            System.out.println("└─────────────────────────────────────────────────────────┘");
            System.out.println(answer);
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
