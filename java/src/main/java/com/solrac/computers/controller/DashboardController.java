package com.solrac.computers.controller;

import com.solrac.computers.model.Ticket;
import com.solrac.computers.model.TicketStatus;
import com.solrac.computers.service.II18nService;
import com.solrac.computers.service.ITicketService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Controller for the dashboard view
 */
public class DashboardController {
    @FXML private Label titleLabel;
    @FXML private Label toFixCountLabel;
    @FXML private Label toDeliverCountLabel;
    @FXML private Label deliveredCountLabel;
    @FXML private Label toFixLabel;
    @FXML private Label toDeliverLabel;
    @FXML private Label deliveredLabel;
    @FXML private Label pendingListLabel;
    @FXML private Label readyListLabel;
    @FXML private VBox pendingListContainer;
    @FXML private VBox readyListContainer;
    
    private ITicketService ticketService;
    private II18nService i18nService;
    
    public void setServices(ITicketService ticketService, II18nService i18nService) {
        this.ticketService = ticketService;
        this.i18nService = i18nService;
        loadData();
        updateTexts();
    }
    
    private void loadData() {
        if (ticketService == null) return;
        
        List<Ticket> pending = ticketService.getPendingTickets();
        List<Ticket> ready = ticketService.getReadyTickets();
        List<Ticket> delivered = ticketService.getDeliveredTickets();
        
        // Update counts
        toFixCountLabel.setText(String.valueOf(pending.size()));
        toDeliverCountLabel.setText(String.valueOf(ready.size()));
        deliveredCountLabel.setText(String.valueOf(delivered.size()));
        
        // Populate lists
        populateTicketList(pendingListContainer, pending);
        populateTicketList(readyListContainer, ready);
    }
    
    private void populateTicketList(VBox container, List<Ticket> tickets) {
        container.getChildren().clear();
        
        if (tickets.isEmpty()) {
            Label emptyLabel = new Label(i18nService != null ? 
                i18nService.getString("dashboard.noPending") : "No hay equipos");
            emptyLabel.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 13px;");
            container.getChildren().add(emptyLabel);
            return;
        }
        
        for (Ticket ticket : tickets) {
            VBox ticketCard = createTicketCard(ticket);
            container.getChildren().add(ticketCard);
        }
    }
    
    private VBox createTicketCard(Ticket ticket) {
        VBox card = new VBox(8);
        card.setStyle("""
            -fx-background-color: rgba(255, 255, 255, 0.05);
            -fx-background-radius: 10;
            -fx-padding: 12;
            -fx-border-color: rgba(255, 255, 255, 0.05);
            -fx-border-radius: 10;
            -fx-border-width: 1;
            """);
        
        // Customer name
        Label nameLabel = new Label(ticket.getCustomer().name());
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        
        // Model
        Label modelLabel = new Label(ticket.getModel());
        modelLabel.setStyle("-fx-text-fill: #9ca3af; -fx-font-size: 12px;");
        
        // Date and status
        Label dateLabel = new Label(ticket.getDateCreated().toString());
        dateLabel.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px;");
        
        card.getChildren().addAll(nameLabel, modelLabel, dateLabel);
        
        return card;
    }
    
    private void updateTexts() {
        if (i18nService == null) return;
        
        titleLabel.setText(i18nService.getString("dashboard.title"));
        toFixLabel.setText(i18nService.getString("dashboard.toFix"));
        toDeliverLabel.setText(i18nService.getString("dashboard.toDeliver"));
        deliveredLabel.setText(i18nService.getString("dashboard.delivered"));
        pendingListLabel.setText(i18nService.getString("dashboard.pendingList"));
        readyListLabel.setText(i18nService.getString("dashboard.readyList"));
    }
}
