# 🚀 Guía Rápida: Servicios de IA - Solrac Computers

## Comandos Rápidos

```bash
# Verificar puerto GPT4All
gradlew.bat testPort

# Test diagnóstico básico
gradlew.bat testAI

# Test completo de servicios
gradlew.bat testAIServices

# Ejecutar aplicación
gradlew.bat run
```

## Uso en Código

### Diagnóstico de IA

```java
LlamaAIService aiService = new LlamaAIService();
String diagnosis = aiService.getDiagnosis(
    DeviceType.NOTEBOOK,
    "La pantalla parpadea y se apaga"
);
```

### Estimación de Costos

```java
CostEstimationService costService = new CostEstimationService();
String estimate = costService.estimateCost(
    DeviceType.SMARTPHONE,
    "Pantalla LCD dañada, requiere reemplazo"
);
```

## Tipos de Dispositivos

```java
DeviceType.NOTEBOOK    // "Notebook"
DeviceType.SMARTPHONE  // "Celular"
DeviceType.DESKTOP     // "PC de Mesa"
DeviceType.CONSOLE     // "Consola"
```

## Configuración

**Archivo:** `ConfigIA.java`

```java
public static final String GPT4ALL_URL = "http://localhost:4891/v1/chat/completions";
public static final String MODELO = "Llama 3.2 3B Instruct";
```

## Parámetros de IA

| Parámetro | Valor Recomendado | Propósito |
|-----------|-------------------|-----------|
| `temperature` | 0.7 | Diagnósticos balanceados |
| `temperature` | 0.5 | Estimaciones de costo |
| `max_tokens` | 500 | Diagnósticos detallados |
| `max_tokens` | 300 | Estimaciones breves |

## Estructura de Respuesta

```json
{
  "choices": [
    {
      "message": {
        "role": "assistant",
        "content": "Diagnóstico aquí..."
      }
    }
  ],
  "usage": {
    "total_tokens": 150
  }
}
```

## Manejo de Errores

```java
try {
    String diagnosis = aiService.getDiagnosis(device, problem);
    // Usar diagnosis
} catch (Exception e) {
    // La implementación actual retorna mensajes de error
    // en lugar de lanzar excepciones
}
```

## Archivos Importantes

```
service/
├── IAIService.java              # Interface
├── LlamaAIService.java          # Implementación principal
├── CostEstimationService.java   # Estimación de costos
└── MockAIService.java           # Para testing sin IA

util/
├── TestPort.java                # Test de conexión
├── TestAI.java                  # Test básico
└── TestAIServices.java          # Suite completa
```

## Troubleshooting

### Error: "Error de conexión con la IA"
- ✅ Verificar que GPT4All esté ejecutándose
- ✅ Verificar puerto 4891 con `gradlew.bat testPort`

### Error: "Respuesta vacía de la IA"
- ✅ Verificar que el modelo esté cargado en GPT4All
- ✅ Verificar nombre del modelo en `ConfigIA.MODELO`

### Error: "Cannot find symbol"
- ✅ Usar Gradle en lugar de javac directo
- ✅ Ejecutar `gradlew.bat build` primero

## Tips de Uso

1. **Prompts claros**: Describe el problema con detalle
2. **Contexto**: Incluye modelo/marca si es relevante
3. **Temperature baja**: Para respuestas consistentes
4. **Cache**: Considera cachear respuestas comunes
5. **Timeout**: Considera agregar timeout para UX

---

**Última actualización:** 2025-11-25
