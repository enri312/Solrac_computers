package com.solrac.computers.controller;

import com.solrac.computers.model.Customer;
import com.solrac.computers.model.DeviceType;
import com.solrac.computers.model.Ticket;
import com.solrac.computers.service.IAIService;
import com.solrac.computers.service.II18nService;
import com.solrac.computers.service.ITicketService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


/**
 * Controller for new ticket registration
 */
public class NewTicketController {
    @FXML private Label titleLabel;
    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<DeviceType> deviceTypeCombo;
    @FXML private TextField modelField;
    @FXML private TextField amountPaidField;
    @FXML private TextField totalCostField;
    @FXML private TextArea problemDescArea;
    @FXML private TextArea observationsArea;
    @FXML private TextArea aiDiagnosisArea;
    @FXML private Button consultAiButton;
    @FXML private Button cancelButton;
    @FXML private Button registerButton;
    @FXML private ProgressIndicator aiLoadingIndicator;
    @FXML private VBox aiDiagnosisContainer;

    private ITicketService ticketService;
    private IAIService aiService;
    private II18nService i18nService;
    private MainFrameController mainController;

    public void setServices(ITicketService ticketService, IAIService aiService, II18nService i18nService) {
        this.ticketService = ticketService;
        this.aiService = aiService;
        this.i18nService = i18nService;
        updateTexts();
    }

    public void setMainController(MainFrameController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        deviceTypeCombo.getItems().addAll(DeviceType.values());
        deviceTypeCombo.setValue(DeviceType.NOTEBOOK);
        if (aiDiagnosisContainer != null) {
            aiDiagnosisContainer.setVisible(false);
            aiDiagnosisContainer.setManaged(false);
        }
        aiLoadingIndicator.setVisible(false);
        amountPaidField.setText("0");
        totalCostField.setText("0");
    }

    @FXML
    private void handleConsultAI() {
        String problem = problemDescArea.getText();
        if (problem == null || problem.isBlank()) {
            showAlert("Error", "Por favor ingrese una descripción del problema");
            return;
        }
        consultAiButton.setDisable(true);
        aiLoadingIndicator.setVisible(true);
        aiDiagnosisArea.setText("Consultando IA...");
        if (aiDiagnosisContainer != null) {
            aiDiagnosisContainer.setVisible(true);
            aiDiagnosisContainer.setManaged(true);
        }
        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {
                return consultarIA(problem);
            }
        };
        task.setOnSucceeded(e -> {
            String diagnosis = task.getValue();
            aiDiagnosisArea.setText(diagnosis);
            consultAiButton.setDisable(false);
            aiLoadingIndicator.setVisible(false);
        });
        task.setOnFailed(e -> {
            Throwable ex = task.getException();
            ex.printStackTrace();
            aiDiagnosisArea.setText("❌ No se pudo conectar con la IA\n" + ex.getMessage());
            consultAiButton.setDisable(false);
            aiLoadingIndicator.setVisible(false);
            if (aiDiagnosisContainer != null) {
                aiDiagnosisContainer.setVisible(false);
                aiDiagnosisContainer.setManaged(false);
            }
        });
        new Thread(task).start();
    }

    @FXML
    private void handleRegister() {
        try {
            if (nameField.getText().isBlank()) {
                showAlert("Error", "El nombre del cliente es obligatorio");
                return;
            }
            if (modelField.getText().isBlank()) {
                showAlert("Error", "El modelo del equipo es obligatorio");
                return;
            }
            Customer customer = new Customer(
                nameField.getText(),
                addressField.getText(),
                phoneField.getText()
            );
            double amountPaid = parseDouble(amountPaidField.getText(), 0);
            double totalCost = parseDouble(totalCostField.getText(), 0);
            Ticket ticket = ticketService.createTicket(
                customer,
                deviceTypeCombo.getValue(),
                modelField.getText(),
                problemDescArea.getText(),
                observationsArea.getText(),
                amountPaid,
                totalCost
            );
            if (aiDiagnosisArea.isVisible() && !aiDiagnosisArea.getText().isBlank()) {
                ticket.setAiDiagnosis(aiDiagnosisArea.getText());
                ticketService.updateTicket(ticket);
            }
            showAlert("Éxito", "Ticket registrado correctamente");
            clearForm();
            if (mainController != null) {
                mainController.showTicketsList();
            }
        } catch (Exception e) {
            showAlert("Error", "Error al registrar ticket: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        clearForm();
        if (mainController != null) {
            mainController.showDashboard();
        }
    }

    private void clearForm() {
        nameField.clear();
        addressField.clear();
        phoneField.clear();
        modelField.clear();
        amountPaidField.setText("0");
        totalCostField.setText("0");
        problemDescArea.clear();
        observationsArea.clear();
        aiDiagnosisArea.clear();
        if (aiDiagnosisContainer != null) {
            aiDiagnosisContainer.setVisible(false);
            aiDiagnosisContainer.setManaged(false);
        }
        deviceTypeCombo.setValue(DeviceType.NOTEBOOK);
    }

    private double parseDouble(String text, double defaultValue) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateTexts() {
        if (i18nService == null) return;
        titleLabel.setText(i18nService.getString("ticket.registerTitle"));
        consultAiButton.setText(i18nService.getString("ticket.consultAI"));
        cancelButton.setText(i18nService.getString("ticket.cancel"));
        registerButton.setText(i18nService.getString("ticket.register"));
    }

    /**
     * Consulta el diagnóstico de IA usando el servicio configurado
     * @param problema descripción del problema ingresada por el usuario
     * @return respuesta de la IA en texto plano
     */
    private String consultarIA(String problema) {
        if (problema == null || problema.isBlank()) {
            return "Describa el problema primero.";
        }
        
        try {
            DeviceType deviceType = deviceTypeCombo.getValue();
            if (deviceType == null) {
                deviceType = DeviceType.NOTEBOOK;
            }
            
            // Usar el servicio de IA mejorado
            return aiService.getDiagnosis(deviceType, problema);
            
        } catch (Exception e) {
            return "❌ Error al consultar la IA: " + e.getMessage() + 
                   "\n\nVerifique que Ollama esté ejecutándose en el puerto 11434.";
        }
    }

    // End of class
}
