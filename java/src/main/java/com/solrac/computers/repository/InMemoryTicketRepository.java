package com.solrac.computers.repository;

import com.solrac.computers.model.Customer;
import com.solrac.computers.model.DeviceType;
import com.solrac.computers.model.Ticket;
import com.solrac.computers.model.TicketStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of ITicketRepository
 * Thread-safe using ConcurrentHashMap
 */
public class InMemoryTicketRepository implements ITicketRepository {
    private final ConcurrentHashMap<String, Ticket> tickets = new ConcurrentHashMap<>();
    
    public InMemoryTicketRepository() {
        // Initialize with sample data (from React app)
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        Ticket ticket1 = new Ticket(
            "1",
            new Customer("Juan Perez", "Av. Siempre Viva 123", "0981-555-0101"),
            DeviceType.NOTEBOOK,
            "Dell XPS 15",
            "La pantalla parpadea al mover la bisagra.",
            "La bisagra izquierda parece suelta.",
            TicketStatus.IN_PROGRESS,
            50000,
            150000,
            LocalDate.of(2023, 10, 25),
            null
        );
        
        Ticket ticket2 = new Ticket(
            "2",
            new Customer("Maria Gomez", "Calle Falsa 456", "0971-555-0202"),
            DeviceType.SMARTPHONE,
            "iPhone 13",
            "La batería se agota muy rápido.",
            "Necesita cambio de batería.",
            TicketStatus.READY,
            0,
            80000,
            LocalDate.of(2023, 10, 26),
            null
        );
        
        tickets.put(ticket1.getId(), ticket1);
        tickets.put(ticket2.getId(), ticket2);
    }
    
    @Override
    public void save(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
    }
    
    @Override
    public Optional<Ticket> findById(String id) {
        return Optional.ofNullable(tickets.get(id));
    }
    
    @Override
    public List<Ticket> findAll() {
        return List.copyOf(tickets.values());
    }
    
    @Override
    public void update(Ticket ticket) {
        if (tickets.containsKey(ticket.getId())) {
            tickets.put(ticket.getId(), ticket);
        } else {
            throw new IllegalArgumentException("Ticket not found: " + ticket.getId());
        }
    }
    
    @Override
    public void delete(String id) {
        tickets.remove(id);
    }
    
    @Override
    public List<Ticket> findByStatus(TicketStatus status) {
        return tickets.values().stream()
            .filter(t -> t.getStatus() == status)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Ticket> searchByCustomerName(String name) {
        String lowerName = name.toLowerCase();
        return tickets.values().stream()
            .filter(t -> t.getCustomer().name().toLowerCase().contains(lowerName))
            .collect(Collectors.toList());
    }
}
