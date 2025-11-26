package com.solrac.computers.service;

import com.solrac.computers.model.DeviceType;

/**
 * Mock implementation of AI service for testing
 * Provides predefined diagnoses based on device type and problem keywords
 */
public class MockAIService implements IAIService {
    
    @Override
    public String getDiagnosis(DeviceType deviceType, String problemDescription) {
        // Simulate processing delay
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String problem = problemDescription.toLowerCase();
        
        // Generate diagnosis based on device type and keywords
        return switch (deviceType) {
            case NOTEBOOK -> getDiagnosisForNotebook(problem);
            case SMARTPHONE -> getDiagnosisForSmartphone(problem);
            case DESKTOP -> getDiagnosisForDesktop(problem);
            case CONSOLE -> getDiagnosisForConsole(problem);
        };
    }
    
    private String getDiagnosisForNotebook(String problem) {
        if (problem.contains("pantalla") || problem.contains("screen")) {
            return """
                **Diagnóstico Técnico:**
                • Problema: Falla en pantalla/bisagra
                • Causas probables:
                  - Cable flex dañado o suelto
                  - Bisagra desgastada
                  - Conector de pantalla defectuoso
                
                **Partes necesarias:**
                • Cable flex de pantalla
                • Kit de bisagras (si aplica)
                
                **Tiempo estimado:** 2-3 horas
                """;
        } else if (problem.contains("batería") || problem.contains("battery") || problem.contains("carga")) {
            return """
                **Diagnóstico Técnico:**
                • Problema: Sistema de energía
                • Causas probables:
                  - Batería degradada (ciclos de carga)
                  - Cargador defectuoso
                  - Puerto de carga dañado
                
                **Partes necesarias:**
                • Batería compatible
                • Cargador original (verificar)
                
                **Tiempo estimado:** 1-2 horas
                """;
        } else if (problem.contains("teclado") || problem.contains("keyboard")) {
            return """
                **Diagnóstico Técnico:**
                • Problema: Teclado
                • Causas probables:
                  - Derrame de líquido
                  - Teclas desgastadas
                  - Cable flex del teclado
                
                **Partes necesarias:**
                • Teclado de reemplazo
                
                **Tiempo estimado:** 1.5-2 horas
                """;
        } else {
            return """
                **Diagnóstico General - Notebook:**
                • Se requiere inspección física detallada
                • Posibles causas: hardware, software, o componentes internos
                • Recomendación: Diagnóstico completo en taller
                
                **Tiempo estimado:** 2-4 horas
                """;
        }
    }
    
    private String getDiagnosisForSmartphone(String problem) {
        if (problem.contains("batería") || problem.contains("battery")) {
            return """
                **Diagnóstico Técnico:**
                • Problema: Batería
                • Causas probables:
                  - Batería hinchada o degradada
                  - Ciclos de carga excedidos
                  - Sobrecalentamiento
                
                **Partes necesarias:**
                • Batería original compatible
                • Adhesivo de pantalla (si se requiere apertura)
                
                **Tiempo estimado:** 30-45 minutos
                """;
        } else if (problem.contains("pantalla") || problem.contains("screen") || problem.contains("táctil")) {
            return """
                **Diagnóstico Técnico:**
                • Problema: Pantalla/Táctil
                • Causas probables:
                  - Pantalla rota o fisurada
                  - Digitalizador dañado
                  - Conector flex suelto
                
                **Partes necesarias:**
                • Pantalla LCD + Táctil (assembly completo)
                • Adhesivo de pantalla
                
                **Tiempo estimado:** 45-60 minutos
                """;
        } else if (problem.contains("carga") || problem.contains("charging") || problem.contains("puerto")) {
            return """
                **Diagnóstico Técnico:**
                • Problema: Puerto de carga
                • Causas probables:
                  - Puerto USB-C/Lightning dañado
                  - Acumulación de suciedad
                  - Placa de carga defectuosa
                
                **Partes necesarias:**
                • Puerto de carga (flex)
                • Limpieza profesional
                
                **Tiempo estimado:** 30-60 minutos
                """;
        } else {
            return """
                **Diagnóstico General - Smartphone:**
                • Se requiere inspección del dispositivo
                • Posibles causas: componentes internos, software, o daño físico
                • Recomendación: Prueba de diagnóstico completa
                
                **Tiempo estimado:** 1-2 horas
                """;
        }
    }
    
    private String getDiagnosisForDesktop(String problem) {
        if (problem.contains("no enciende") || problem.contains("power") || problem.contains("arranque")) {
            return """
                **Diagnóstico Técnico:**
                • Problema: No enciende
                • Causas probables:
                  - Fuente de poder defectuosa
                  - RAM mal conectada
                  - Placa madre dañada
                
                **Partes necesarias:**
                • Fuente de poder (PSU)
                • Posible reemplazo de RAM
                
                **Tiempo estimado:** 2-3 horas
                """;
        } else if (problem.contains("lento") || problem.contains("slow") || problem.contains("rendimiento")) {
            return """
                **Diagnóstico Técnico:**
                • Problema: Rendimiento lento
                • Causas probables:
                  - Disco duro HDD antiguo
                  - Poca memoria RAM
                  - Sobrecalentamiento (pasta térmica)
                
                **Recomendaciones:**
                • Upgrade a SSD
                • Aumentar RAM
                • Limpieza y pasta térmica
                
                **Tiempo estimado:** 1.5-2 horas
                """;
        } else {
            return """
                **Diagnóstico General - PC de Mesa:**
                • Se requiere diagnóstico de componentes
                • Posibles causas: hardware, software, o configuración
                • Recomendación: Prueba de componentes individuales
                
                **Tiempo estimado:** 2-4 horas
                """;
        }
    }
    
    private String getDiagnosisForConsole(String problem) {
        if (problem.contains("no lee") || problem.contains("disco") || problem.contains("disk")) {
            return """
                **Diagnóstico Técnico:**
                • Problema: Lector de discos
                • Causas probables:
                  - Lente láser sucio o dañado
                  - Motor del lector defectuoso
                  - Firmware desactualizado
                
                **Partes necesarias:**
                • Lector de discos compatible
                • Limpieza de lente láser
                
                **Tiempo estimado:** 2-3 horas
                """;
        } else if (problem.contains("sobrecalentamiento") || problem.contains("apaga") || problem.contains("heat")) {
            return """
                **Diagnóstico Técnico:**
                • Problema: Sobrecalentamiento
                • Causas probables:
                  - Pasta térmica seca
                  - Ventilador obstruido
                  - Acumulación de polvo
                
                **Servicio necesario:**
                • Limpieza profunda
                • Reemplazo de pasta térmica
                • Verificación de ventiladores
                
                **Tiempo estimado:** 1.5-2 horas
                """;
        } else {
            return """
                **Diagnóstico General - Consola:**
                • Se requiere inspección del sistema
                • Posibles causas: hardware, firmware, o componentes internos
                • Recomendación: Diagnóstico completo
                
                **Tiempo estimado:** 2-3 horas
                """;
        }
    }
}
