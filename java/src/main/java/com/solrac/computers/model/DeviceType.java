package com.solrac.computers.model;

/**
 * Enum representing device types that can be repaired
 */
public enum DeviceType {
    NOTEBOOK("Notebook"),
    SMARTPHONE("Celular"),
    DESKTOP("PC de Mesa"),
    CONSOLE("Consola");
    
    private final String displayName;
    
    DeviceType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
