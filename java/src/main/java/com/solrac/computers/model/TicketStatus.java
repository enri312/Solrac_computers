package com.solrac.computers.model;

/**
 * Enum representing the status of a repair ticket
 */
public enum TicketStatus {
    PENDING("Pendiente"),
    IN_PROGRESS("En Reparación"),
    READY("Listo para Entregar"),
    DELIVERED("Entregado");
    
    private final String displayName;
    
    TicketStatus(String displayName) {
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
