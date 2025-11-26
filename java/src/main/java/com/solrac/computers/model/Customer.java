package com.solrac.computers.model;

/**
 * Immutable record representing customer information
 */
public record Customer(
    String name,
    String address,
    String phone
) {
    public Customer {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
    }
}
