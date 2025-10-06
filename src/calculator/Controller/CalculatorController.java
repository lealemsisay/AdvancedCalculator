package calculator.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class CalculatorController {

    @FXML
    private TextField display;

    @FXML
    private VBox root;

    private String currentInput = "";
    private double firstOperand = 0;
    private String operator = "";

    public void initialize() {
        // Add keyboard listener
        root.setOnKeyPressed(this::handleKeyPress);
        root.requestFocus(); // important so key events work immediately
    }

    private void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code.isDigitKey()) {
            processInput(event.getText());
        } else if (code == KeyCode.ADD || event.getText().equals("+")) {
            processInput("+");
        } else if (code == KeyCode.SUBTRACT || event.getText().equals("-")) {
            processInput("-");
        } else if (code == KeyCode.MULTIPLY || event.getText().equals("*")) {
            processInput("*");
        } else if (code == KeyCode.DIVIDE || event.getText().equals("/")) {
            processInput("/");
        } else if (code == KeyCode.ENTER || code == KeyCode.EQUALS) {
            processInput("=");
        } else if (code == KeyCode.ESCAPE || code == KeyCode.DELETE || code == KeyCode.BACK_SPACE) {
            processInput("C");
        }
    }

    private void processInput(String value) {
        switch (value) {
            case "C" -> {
                currentInput = "";
                operator = "";
                firstOperand = 0;
                display.setText("");
            }
            case "+", "-", "*", "/" -> {
                if (!currentInput.isEmpty()) {
                    firstOperand = Double.parseDouble(currentInput);
                    operator = value;
                    currentInput = "";
                }
            }
            case "=" -> {
                if (!currentInput.isEmpty() && !operator.isEmpty()) {
                    double secondOperand = Double.parseDouble(currentInput);
                    double result = calculate(firstOperand, secondOperand, operator);
                    display.setText(String.valueOf(result));
                    currentInput = String.valueOf(result);
                    operator = "";
                }
            }
            default -> {
                currentInput += value;
                display.setText(currentInput);
            }
        }
    }

    private double calculate(double a, double b, String op) {
        return switch (op) {
            case "+" ->
                a + b;
            case "-" ->
                a - b;
            case "*" ->
                a * b;
            case "/" ->
                b != 0 ? a / b : 0;
            default ->
                0;
        };
    }
}
