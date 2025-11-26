# Solrac_Computer's - Java Desktop Application

Aplicación de escritorio JavaFX para gestión de taller de reparación de computadoras.

## Características

- ✅ Gestión de tickets de reparación
- ✅ Dashboard con estadísticas
- ✅ Diagnóstico IA (mock) para problemas técnicos
- ✅ Internacionalización (Español/Inglés)
- ✅ Diseño moderno con glassmorphism
- ✅ Arquitectura SOLID (Model-Repository-Service-Controller)

## Requisitos

- Java 25 (o superior)
- Gradle 8.x

## Ejecutar la Aplicación

### Windows (PowerShell)

```powershell
.\gradlew run
```

### Linux/Mac

```bash
./gradlew run
```

## Compilar

```bash
./gradlew build
```

## Credenciales de Prueba

- **Usuario:** admin
- **Contraseña:** password

## Estructura del Proyecto

```
src/main/
├── java/com/solrac/computers/
│   ├── SolracApp.java (Main)
│   ├── model/ (Entidades)
│   ├── repository/ (Acceso a datos)
│   ├── service/ (Lógica de negocio)
│   └── controller/ (Controladores JavaFX)
└── resources/
    ├── fxml/ (Vistas)
    ├── css/ (Estilos)
    └── i18n/ (Traducciones)
```

## Funcionalidades

### Login
- Autenticación simple
- Cambio de idioma (ES/EN)

### Dashboard
- Estadísticas de equipos
- Listas de equipos pendientes y listos

### Nuevo Ticket
- Formulario de registro de equipos
- Consulta de diagnóstico IA (mock)
- Validación de campos

### Inventario
- Tabla con todos los tickets
- Búsqueda por cliente
- Filtros por estado

## Tecnologías

- **JavaFX 23** - Framework UI
- **Java 25** - Lenguaje
- **Gradle** - Build tool
- **SOLID Principles** - Arquitectura

## Migrado desde

Aplicación React/TypeScript original convertida a JavaFX Desktop.
