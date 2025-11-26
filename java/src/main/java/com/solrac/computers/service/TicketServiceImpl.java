package com.solrac.computers.service;

import com.solrac.computers.model.Customer;
import com.solrac.computers.model.DeviceType;
import com.solrac.computers.model.Ticket;
import com.solrac.computers.model.TicketStatus;
import com.solrac.computers.repository.ITicketRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of ticket service with business logic
 */
public class TicketServiceImpl implements ITicketService {
    private final ITicketRepository repository;
    
    public TicketServiceImpl(ITicketRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Ticket createTicket(Customer customer, DeviceType deviceType, String model,
                              String problemDescription, String observations,
                              double amountPaid, double totalCost) {
        // Business validation
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Model cannot be empty");
        }
        if (totalCost < 0) {
            throw new IllegalArgumentException("Total cost cannot be negative");
        }
        if (amountPaid < 0 || amountPaid > totalCost) {
            throw new IllegalArgumentException("Invalid amount paid");
        }
        
        Ticket ticket = new Ticket(customer, deviceType, model, problemDescription,
                                  observations, amountPaid, totalCost);
        repository.save(ticket);
        return ticket;
    }
    
    @Override
    public List<Ticket> getAllTickets() {
        return repository.findAll();
    }
    
    @Override
    public Ticket getTicketById(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Ticket not found: " + id));
    }
    
    @Override
    public void updateTicketStatus(String id, TicketStatus newStatus) {
        Ticket ticket = getTicketById(id);
        ticket.setStatus(newStatus);
        repository.update(ticket);
    }
    
    @Override
    public void updateTicket(Ticket ticket) {
        repository.update(ticket);
    }
    
    @Override
    public List<Ticket> getPendingTickets() {
        return repository.findAll().stream()
            .filter(t -> t.getStatus() == TicketStatus.PENDING || 
                        t.getStatus() == TicketStatus.IN_PROGRESS)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Ticket> getReadyTickets() {
        return repository.findByStatus(TicketStatus.READY);
    }
    
    @Override
    public List<Ticket> getDeliveredTickets() {
        return repository.findByStatus(TicketStatus.DELIVERED);
    }
    
    @Override
    public List<Ticket> searchTickets(String customerName) {
        return repository.searchByCustomerName(customerName);
    }
}
