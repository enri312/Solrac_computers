package com.solrac.computers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class for Solrac_Computer's
 * JavaFX desktop application for computer repair shop management
 */
public class SolracApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load login view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            
            // Create scene
            Scene scene = new Scene(root, 1024, 768);
            
            // Load CSS stylesheet
            String css = getClass().getResource("/css/styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            // Configure stage
            primaryStage.setTitle("Solrac_Computer's - Gestión de Reparaciones");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(1024);
            primaryStage.setMinHeight(768);
            
            // Show stage
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading application: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
