package com.solrac.computers.controller;

import com.solrac.computers.model.Ticket;
import com.solrac.computers.model.TicketStatus;
import com.solrac.computers.service.II18nService;
import com.solrac.computers.service.ITicketService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for tickets list/inventory view
 */
public class TicketsListController {
    @FXML private Label titleLabel;
    @FXML private TextField searchField;
    @FXML private TableView<Ticket> ticketsTable;
    @FXML private TableColumn<Ticket, String> customerColumn;
    @FXML private TableColumn<Ticket, String> deviceColumn;
    @FXML private TableColumn<Ticket, String> modelColumn;
    @FXML private TableColumn<Ticket, String> problemColumn;
    @FXML private TableColumn<Ticket, String> statusColumn;
    @FXML private TableColumn<Ticket, LocalDate> dateColumn;
    @FXML private TableColumn<Ticket, Double> totalColumn;
    
    private ITicketService ticketService;
    private II18nService i18nService;
    private ObservableList<Ticket> ticketsList;
    
    public void setServices(ITicketService ticketService, II18nService i18nService) {
        this.ticketService = ticketService;
        this.i18nService = i18nService;
        loadData();
        updateTexts();
    }
    
    @FXML
    public void initialize() {
        setupTable();
        
        // Add search listener
        searchField.textProperty().addListener((obs, oldVal, newVal) -> handleSearch());
    }
    
    private void setupTable() {
        // Configure columns
        customerColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCustomer().name()));
        
        deviceColumn.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getDeviceType().getDisplayName()));
        
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        
        problemColumn.setCellValueFactory(new PropertyValueFactory<>("problemDescription"));
        
        statusColumn.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().getStatus().getDisplayName()));
        
        // Custom cell factory for status with colored badges
        statusColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    Ticket ticket = getTableView().getItems().get(getIndex());
                    String styleClass = switch (ticket.getStatus()) {
                        case PENDING -> "-fx-text-fill: #fbbf24; -fx-font-weight: bold;";
                        case IN_PROGRESS -> "-fx-text-fill: #60a5fa; -fx-font-weight: bold;";
                        case READY -> "-fx-text-fill: #00ff88; -fx-font-weight: bold;";
                        case DELIVERED -> "-fx-text-fill: #6b7280; -fx-font-weight: bold;";
                    };
                    setStyle(styleClass);
                }
            }
        });
        
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        totalColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.0f", item));
                }
            }
        });
        
        // Make table fill available space
        ticketsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    private void loadData() {
        if (ticketService == null) return;
        
        List<Ticket> tickets = ticketService.getAllTickets();
        ticketsList = FXCollections.observableArrayList(tickets);
        ticketsTable.setItems(ticketsList);
    }
    
    @FXML
    private void handleSearch() {
        String searchText = searchField.getText();
        
        if (searchText == null || searchText.isBlank()) {
            loadData();
            return;
        }
        
        List<Ticket> results = ticketService.searchTickets(searchText);
        ticketsList = FXCollections.observableArrayList(results);
        ticketsTable.setItems(ticketsList);
    }
    
    @FXML
    private void handleRefresh() {
        loadData();
    }
    
    private void updateTexts() {
        if (i18nService == null) return;
        
        titleLabel.setText(i18nService.getString("inventory.title"));
        searchField.setPromptText(i18nService.getString("inventory.searchPlaceholder"));
        customerColumn.setText(i18nService.getString("common.customer"));
        deviceColumn.setText(i18nService.getString("common.device"));
        modelColumn.setText(i18nService.getString("ticket.model"));
        problemColumn.setText(i18nService.getString("inventory.problem"));
        statusColumn.setText(i18nService.getString("common.status"));
        dateColumn.setText(i18nService.getString("inventory.date"));
        totalColumn.setText(i18nService.getString("inventory.total"));
    }
}
