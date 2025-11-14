package calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalculatorApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Starting Calculator...");
        
        try {
            // SIMPLE FXML loading - no complex validation
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UI/calculator.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 360, 620); // Adjusted width and height
            
            // Try to load CSS (but don't crash if it fails)
            try {
                scene.getStylesheets().add(getClass().getResource("styles/styles.css").toExternalForm());
            } catch (Exception e) {
                System.out.println("Note: Running without CSS styles");
            }
            
            primaryStage.setTitle("Calculator");
            primaryStage.setScene(scene);
            primaryStage.show();
            
            System.out.println("Calculator started successfully!");
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}