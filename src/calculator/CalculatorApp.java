package calculator;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalculatorApp extends Application {

    /**
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/calculator/UI/calculator.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 400);

        // Attach external CSS
        scene.getStylesheets().add(getClass().getResource("/calculator/styles/styles.css").toExternalForm());

        primaryStage.setTitle("JavaFX Calculator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
