package com.solrac.computers.model;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Entity representing a repair ticket
 */
public class Ticket {
    private final String id;
    private Customer customer;
    private DeviceType deviceType;
    private String model;
    private String problemDescription;
    private String observations;
    private TicketStatus status;
    private double amountPaid;
    private double totalCost;
    private final LocalDate dateCreated;
    private String aiDiagnosis;
    
    // Constructor for new tickets
    public Ticket(Customer customer, DeviceType deviceType, String model, 
                  String problemDescription, String observations, 
                  double amountPaid, double totalCost) {
        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.deviceType = deviceType;
        this.model = model;
        this.problemDescription = problemDescription;
        this.observations = observations;
        this.status = TicketStatus.PENDING;
        this.amountPaid = amountPaid;
        this.totalCost = totalCost;
        this.dateCreated = LocalDate.now();
    }
    
    // Constructor for loading existing tickets
    public Ticket(String id, Customer customer, DeviceType deviceType, String model,
                  String problemDescription, String observations, TicketStatus status,
                  double amountPaid, double totalCost, LocalDate dateCreated, String aiDiagnosis) {
        this.id = id;
        this.customer = customer;
        this.deviceType = deviceType;
        this.model = model;
        this.problemDescription = problemDescription;
        this.observations = observations;
        this.status = status;
        this.amountPaid = amountPaid;
        this.totalCost = totalCost;
        this.dateCreated = dateCreated;
        this.aiDiagnosis = aiDiagnosis;
    }
    
    // Getters
    public String getId() { return id; }
    public Customer getCustomer() { return customer; }
    public DeviceType getDeviceType() { return deviceType; }
    public String getModel() { return model; }
    public String getProblemDescription() { return problemDescription; }
    public String getObservations() { return observations; }
    public TicketStatus getStatus() { return status; }
    public double getAmountPaid() { return amountPaid; }
    public double getTotalCost() { return totalCost; }
    public LocalDate getDateCreated() { return dateCreated; }
    public String getAiDiagnosis() { return aiDiagnosis; }
    
    // Setters (for mutable fields)
    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setDeviceType(DeviceType deviceType) { this.deviceType = deviceType; }
    public void setModel(String model) { this.model = model; }
    public void setProblemDescription(String problemDescription) { this.problemDescription = problemDescription; }
    public void setObservations(String observations) { this.observations = observations; }
    public void setStatus(TicketStatus status) { this.status = status; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
    public void setAiDiagnosis(String aiDiagnosis) { this.aiDiagnosis = aiDiagnosis; }
    
    public double getRemainingBalance() {
        return totalCost - amountPaid;
    }
}
