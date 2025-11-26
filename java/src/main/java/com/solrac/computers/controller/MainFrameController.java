package com.solrac.computers.controller;

import com.solrac.computers.repository.InMemoryTicketRepository;
import com.solrac.computers.repository.ITicketRepository;
import com.solrac.computers.service.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Main frame controller handling navigation between views
 */
public class MainFrameController {
    @FXML private Button dashboardButton;
    @FXML private Button newTicketButton;
    @FXML private Button listTicketsButton;
    @FXML private Button logoutButton;
    @FXML private Label userLabel;
    @FXML private StackPane contentArea;
    
    private String currentUser;
    private II18nService i18nService;
    private ITicketService ticketService;
    private IAIService aiService;
    
    // Shared repository instance
    private static final ITicketRepository repository = new InMemoryTicketRepository();
    
    public void setCurrentUser(String username) {
        this.currentUser = username;
        if (userLabel != null) {
            userLabel.setText(username);
        }
    }
    
    public void setI18nService(II18nService i18nService) {
        this.i18nService = i18nService;
        updateTexts();
    }
    
    @FXML
    public void initialize() {
        // Initialize services
        ticketService = new TicketServiceImpl(repository);
        aiService = new LlamaAIService();
        
        // Load dashboard by default
        showDashboard();
    }
    
    @FXML
    public void showDashboard() {
        loadView("/fxml/dashboard.fxml", dashboardButton);
    }
    
    @FXML
    public void showNewTicket() {
        loadView("/fxml/new_ticket.fxml", newTicketButton);
    }
    
    @FXML
    public void showTicketsList() {
        loadView("/fxml/tickets_list.fxml", listTicketsButton);
    }
    
    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root, 1024, 768);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(scene);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadView(String fxmlPath, Button activeButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            
            // Pass services to the loaded controller
            Object controller = loader.getController();
            if (controller instanceof DashboardController) {
                ((DashboardController) controller).setServices(ticketService, i18nService);
            } else if (controller instanceof NewTicketController) {
                ((NewTicketController) controller).setServices(ticketService, aiService, i18nService);
                ((NewTicketController) controller).setMainController(this);
            } else if (controller instanceof TicketsListController) {
                ((TicketsListController) controller).setServices(ticketService, i18nService);
            }
            
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
            
            // Update active button styling
            updateActiveButton(activeButton);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void updateActiveButton(Button activeButton) {
        // Remove active class from all buttons
        dashboardButton.getStyleClass().remove("nav-button-active");
        newTicketButton.getStyleClass().remove("nav-button-active");
        listTicketsButton.getStyleClass().remove("nav-button-active");
        
        // Add active class to current button
        if (!activeButton.getStyleClass().contains("nav-button-active")) {
            activeButton.getStyleClass().add("nav-button-active");
        }
    }
    
    private void updateTexts() {
        if (i18nService != null) {
            dashboardButton.setText(i18nService.getString("menu.main"));
            newTicketButton.setText(i18nService.getString("menu.new"));
            listTicketsButton.setText(i18nService.getString("menu.list"));
            logoutButton.setText(i18nService.getString("menu.logout"));
        }
    }
    
    public void refreshCurrentView() {
        // Refresh the current view (useful after creating a ticket)
        if (dashboardButton.getStyleClass().contains("nav-button-active")) {
            showDashboard();
        } else if (listTicketsButton.getStyleClass().contains("nav-button-active")) {
            showTicketsList();
        }
    }
}
