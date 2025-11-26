package com.solrac.computers.util;

import com.solrac.computers.model.DeviceType;
import com.solrac.computers.service.LlamaAIService;

public class TestAI {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO PRUEBA DE CONEXION IA ===");
        try {
            LlamaAIService service = new LlamaAIService();
            System.out.println("Enviando solicitud a LLaMA...");
            String diagnosis = service.getDiagnosis(DeviceType.NOTEBOOK, "La pantalla parpadea y se apaga sola");
            System.out.println("=== RESPUESTA RECIBIDA ===");
            System.out.println(diagnosis);
            System.out.println("==========================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
