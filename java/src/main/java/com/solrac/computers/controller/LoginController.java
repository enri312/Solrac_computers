package com.solrac.computers.controller;

import com.solrac.computers.service.II18nService;
import com.solrac.computers.service.I18nServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Locale;

/**
 * Controller for the login view
 */
public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Button langButton;
    @FXML private Label titleLabel;
    @FXML private Label userLabel;
    @FXML private Label passLabel;
    @FXML private Label errorLabel;
    
    private final II18nService i18nService = new I18nServiceImpl();
    private boolean isSpanish = true;
    
    @FXML
    public void initialize() {
        updateTexts();
        errorLabel.setVisible(false);
        
        // Set default values for testing
        usernameField.setText("admin");
        passwordField.setText("password");
    }
    
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        // Simple authentication (mock)
        if ("admin".equals(username) && "password".equals(password)) {
            try {
                // Load main frame
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_frame.fxml"));
                Parent root = loader.load();
                
                MainFrameController controller = loader.getController();
                controller.setCurrentUser(username);
                controller.setI18nService(i18nService);
                
                Scene scene = new Scene(root, 1280, 800);
                scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
                
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle(i18nService.getString("app.title"));
                
            } catch (Exception e) {
                e.printStackTrace();
                showError("Error loading main window");
            }
        } else {
            showError("Invalid credentials");
        }
    }
    
    @FXML
    private void handleLanguageToggle() {
        isSpanish = !isSpanish;
        i18nService.setLocale(isSpanish ? new Locale("es") : Locale.ENGLISH);
        updateTexts();
    }
    
    private void updateTexts() {
        titleLabel.setText(i18nService.getString("login.title"));
        userLabel.setText(i18nService.getString("login.user"));
        passLabel.setText(i18nService.getString("login.password"));
        loginButton.setText(i18nService.getString("login.button"));
        langButton.setText(isSpanish ? "ES" : "EN");
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
