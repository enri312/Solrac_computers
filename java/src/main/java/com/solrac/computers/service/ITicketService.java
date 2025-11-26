package com.solrac.computers.service;

import com.solrac.computers.model.Customer;
import com.solrac.computers.model.DeviceType;
import com.solrac.computers.model.Ticket;
import com.solrac.computers.model.TicketStatus;
import java.util.List;

/**
 * Service interface for ticket business logic
 */
public interface ITicketService {
    Ticket createTicket(Customer customer, DeviceType deviceType, String model,
                       String problemDescription, String observations,
                       double amountPaid, double totalCost);
    
    List<Ticket> getAllTickets();
    Ticket getTicketById(String id);
    void updateTicketStatus(String id, TicketStatus newStatus);
    void updateTicket(Ticket ticket);
    List<Ticket> getPendingTickets();
    List<Ticket> getReadyTickets();
    List<Ticket> getDeliveredTickets();
    List<Ticket> searchTickets(String customerName);
}
