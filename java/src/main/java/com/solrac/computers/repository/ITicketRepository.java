package com.solrac.computers.repository;

import com.solrac.computers.model.Ticket;
import com.solrac.computers.model.TicketStatus;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Ticket persistence operations
 * Following Repository Pattern for data access abstraction
 */
public interface ITicketRepository {
    void save(Ticket ticket);
    Optional<Ticket> findById(String id);
    List<Ticket> findAll();
    void update(Ticket ticket);
    void delete(String id);
    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> searchByCustomerName(String name);
}
