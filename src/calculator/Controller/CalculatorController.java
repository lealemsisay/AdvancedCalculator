package calculator.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.Separator;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.application.Platform;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.Node;
import javafx.scene.control.DialogPane;

public class CalculatorController {

    @FXML
    private TextField display;

    @FXML
    private VBox mainContainer;

    @FXML
    private HBox menuBarContainer;

    @FXML
    private GridPane buttonGrid;

    @FXML
    private Label modeIndicator;

    @FXML
    private Label calculatorLabel;

    @FXML
    private Button themeToggleButton;

    // MenuBar references
    @FXML private MenuBar settingsMenuBar;
    @FXML private MenuBar historyMenuBar;
    @FXML private MenuBar helpMenuBar;
    
    // Menu references
    @FXML private Menu settingsMenu;
    @FXML private Menu historyMenu;
    @FXML private Menu helpMenu;
    
    // MenuItem references
    @FXML private MenuItem standardModeItem, advancedModeItem;
    @FXML private MenuItem radiansModeItem, degreesModeItem;
    @FXML private MenuItem resetItem;
    @FXML private MenuItem clearHistoryItem, showHistoryItem;
    @FXML private MenuItem aboutItem;
    @FXML private SeparatorMenuItem modeSeparator, resetSeparator;

    // Button references
    @FXML private Button mcButton, mrButton, msButton, mPlusButton, mMinusButton;
    @FXML private Button sinButton, cosButton, tanButton, absButton, cButton;
    @FXML private Button cotButton, secButton, cscButton, asinButton, acosButton;
    @FXML private Button atanButton, logButton, lnButton, x2Button, sqrtButton;
    @FXML private Button derivButton, integButton, limButton, xButton, piButton;
    @FXML private Button button7, button8, button9, divButton, decButton;
    @FXML private Button button4, button5, button6, multButton, openParenButton;
    @FXML private Button button1, button2, button3, minusButton, closeParenButton;
    @FXML private Button button0, signButton, equalButton, plusButton, delButton;
    @FXML private Button factorialButton, eButton, quadButton, linearButton;

    private String currentInput = "";
    private double firstOperand = 0;
    private String operator = "";
    private boolean isNewInput = true;
    private double memoryValue = 0;
    private List<String> calculationHistory = new ArrayList<>();
    private Stage primaryStage;
    private boolean isRadiansMode = true;
    private String currentTheme = "light";
    private String currentMode = "standard";

    @FXML
    public void initialize() {
        System.out.println("Calculator Controller initialized!");
        display.setText("0");
        applyLightTheme();
        themeToggleButton.setText("🌙");
        setStandardMode();
        
        // Apply initial styles using CSS classes
        applyInitialStyles();
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    private void applyInitialStyles() {
        // Set initial style classes
        mainContainer.getStyleClass().add("main-container");
        menuBarContainer.getStyleClass().add("menu-bar-container");
        display.getStyleClass().add("display-field");
        modeIndicator.getStyleClass().add("mode-indicator");
        calculatorLabel.getStyleClass().add("calculator-label");
        themeToggleButton.getStyleClass().add("theme-toggle-button");
        
        // Apply button styles
        applyButtonStyles();
    }

    private void applyButtonStyles() {
        if (buttonGrid != null) {
            buttonGrid.getChildren().forEach(node -> {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    button.getStyleClass().add("calc-button");
                    
                    // Add specific button type classes
                    if (button.getText().matches("[0-9]")) {
                        button.getStyleClass().add("number-button");
                    } else if (button.getText().matches("[+\\-*/=]")) {
                        button.getStyleClass().add("operator-button");
                    } else if (button.getText().matches("MC|MR|MS|M\\+|M\\-")) {
                        button.getStyleClass().add("memory-button");
                    } else if (isScientificFunction(button.getText())) {
                        button.getStyleClass().add("scientific-button");
                    } else if (button.getText().equals("C") || button.getText().equals("DEL")) {
                        button.getStyleClass().add("clear-button");
                    }
                }
            });
        }
    }

    @FXML
    private void handleToggleTheme() {
        if (currentTheme.equals("light")) {
            applyDarkTheme();
            themeToggleButton.setText("☀️");
        } else {
            applyLightTheme();
            themeToggleButton.setText("🌙");
        }
    }

    // Mode Handlers
    @FXML
    private void handleStandardMode() {
        setStandardMode();
    }

    @FXML
    private void handleAdvancedMode() {
        setAdvancedMode();
    }

    private void setStandardMode() {
        currentMode = "standard";
        updateModeIndicator();
        setStandardButtonsVisibility();
        setStandardButtonSizes();
        applyCurrentTheme();
    }

    private void setAdvancedMode() {
        currentMode = "advanced";
        updateModeIndicator();
        setAdvancedButtonsVisibility();
        setAdvancedButtonSizes();
        applyCurrentTheme();
    }

    private void setStandardButtonSizes() {
        if (buttonGrid != null) {
            buttonGrid.getChildren().forEach(node -> {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    button.getStyleClass().remove("advanced-mode-button");
                    button.getStyleClass().add("standard-mode-button");
                }
            });
        }
    }

    private void setAdvancedButtonSizes() {
        if (buttonGrid != null) {
            buttonGrid.getChildren().forEach(node -> {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    button.getStyleClass().remove("standard-mode-button");
                    button.getStyleClass().add("advanced-mode-button");
                }
            });
        }
    }

    private void updateModeIndicator() {
        if (modeIndicator != null) {
            modeIndicator.setText(currentMode.equals("standard") ? "Standard Mode" : "Advanced Mode");
        }
    }

    private void setStandardButtonsVisibility() {
        // Memory buttons - visible in both modes
        if (mcButton != null) mcButton.setVisible(true);
        if (mrButton != null) mrButton.setVisible(true);
        if (msButton != null) msButton.setVisible(true);
        if (mPlusButton != null) mPlusButton.setVisible(true);
        if (mMinusButton != null) mMinusButton.setVisible(true);
        
        // Scientific functions - hidden in standard mode
        if (sinButton != null) sinButton.setVisible(false);
        if (cosButton != null) cosButton.setVisible(false);
        if (tanButton != null) tanButton.setVisible(false);
        if (cotButton != null) cotButton.setVisible(false);
        if (secButton != null) secButton.setVisible(false);
        if (cscButton != null) cscButton.setVisible(false);
        if (asinButton != null) asinButton.setVisible(false);
        if (acosButton != null) acosButton.setVisible(false);
        if (atanButton != null) atanButton.setVisible(false);
        if (logButton != null) logButton.setVisible(false);
        if (lnButton != null) lnButton.setVisible(false);
        if (sqrtButton != null) sqrtButton.setVisible(false);
        if (absButton != null) absButton.setVisible(false);
        if (factorialButton != null) factorialButton.setVisible(false);
        if (eButton != null) eButton.setVisible(false);
        
        // Calculus functions - hidden in standard mode
        if (derivButton != null) derivButton.setVisible(false);
        if (integButton != null) integButton.setVisible(false);
        if (limButton != null) limButton.setVisible(false);
        
        // Advanced features - hidden in standard mode
        if (x2Button != null) x2Button.setVisible(false);
        if (piButton != null) piButton.setVisible(false);
        if (xButton != null) xButton.setVisible(false);
        if (quadButton != null) quadButton.setVisible(false);
        if (linearButton != null) linearButton.setVisible(false);
        
        // Basic operations - always visible
        if (cButton != null) cButton.setVisible(true);
        if (button7 != null) button7.setVisible(true);
        if (button8 != null) button8.setVisible(true);
        if (button9 != null) button9.setVisible(true);
        if (divButton != null) divButton.setVisible(true);
        if (button4 != null) button4.setVisible(true);
        if (button5 != null) button5.setVisible(true);
        if (button6 != null) button6.setVisible(true);
        if (multButton != null) multButton.setVisible(true);
        if (openParenButton != null) openParenButton.setVisible(true);
        if (button1 != null) button1.setVisible(true);
        if (button2 != null) button2.setVisible(true);
        if (button3 != null) button3.setVisible(true);
        if (minusButton != null) minusButton.setVisible(true);
        if (closeParenButton != null) closeParenButton.setVisible(true);
        if (button0 != null) button0.setVisible(true);
        if (signButton != null) signButton.setVisible(true);
        if (equalButton != null) equalButton.setVisible(true);
        if (plusButton != null) plusButton.setVisible(true);
        if (delButton != null) delButton.setVisible(true);
        if (decButton != null) decButton.setVisible(true);
    }

    private void setAdvancedButtonsVisibility() {
        // Make all buttons visible in advanced mode
        if (mcButton != null) mcButton.setVisible(true);
        if (mrButton != null) mrButton.setVisible(true);
        if (msButton != null) msButton.setVisible(true);
        if (mPlusButton != null) mPlusButton.setVisible(true);
        if (mMinusButton != null) mMinusButton.setVisible(true);
        if (sinButton != null) sinButton.setVisible(true);
        if (cosButton != null) cosButton.setVisible(true);
        if (tanButton != null) tanButton.setVisible(true);
        if (cotButton != null) cotButton.setVisible(true);
        if (secButton != null) secButton.setVisible(true);
        if (cscButton != null) cscButton.setVisible(true);
        if (asinButton != null) asinButton.setVisible(true);
        if (acosButton != null) acosButton.setVisible(true);
        if (atanButton != null) atanButton.setVisible(true);
        if (logButton != null) logButton.setVisible(true);
        if (lnButton != null) lnButton.setVisible(true);
        if (sqrtButton != null) sqrtButton.setVisible(true);
        if (absButton != null) absButton.setVisible(true);
        if (factorialButton != null) factorialButton.setVisible(true);
        if (eButton != null) eButton.setVisible(true);
        if (derivButton != null) derivButton.setVisible(true);
        if (integButton != null) integButton.setVisible(true);
        if (limButton != null) limButton.setVisible(true);
        if (x2Button != null) x2Button.setVisible(true);
        if (piButton != null) piButton.setVisible(true);
        if (xButton != null) xButton.setVisible(true);
        if (quadButton != null) quadButton.setVisible(true);
        if (linearButton != null) linearButton.setVisible(true);
        if (cButton != null) cButton.setVisible(true);
        if (button7 != null) button7.setVisible(true);
        if (button8 != null) button8.setVisible(true);
        if (button9 != null) button9.setVisible(true);
        if (divButton != null) divButton.setVisible(true);
        if (button4 != null) button4.setVisible(true);
        if (button5 != null) button5.setVisible(true);
        if (button6 != null) button6.setVisible(true);
        if (multButton != null) multButton.setVisible(true);
        if (openParenButton != null) openParenButton.setVisible(true);
        if (button1 != null) button1.setVisible(true);
        if (button2 != null) button2.setVisible(true);
        if (button3 != null) button3.setVisible(true);
        if (minusButton != null) minusButton.setVisible(true);
        if (closeParenButton != null) closeParenButton.setVisible(true);
        if (button0 != null) button0.setVisible(true);
        if (signButton != null) signButton.setVisible(true);
        if (equalButton != null) equalButton.setVisible(true);
        if (plusButton != null) plusButton.setVisible(true);
        if (delButton != null) delButton.setVisible(true);
        if (decButton != null) decButton.setVisible(true);
    }

    // Theme Methods - UPDATED TO USE CSS CLASSES
    private void applyLightTheme() {
        currentTheme = "light";
        applyCurrentTheme();
    }

    private void applyDarkTheme() {
        currentTheme = "dark";
        applyCurrentTheme();
    }

    private void applyCurrentTheme() {
        // Remove existing theme classes
        mainContainer.getStyleClass().removeAll("dark-theme", "light-theme");
        // Add current theme class
        mainContainer.getStyleClass().add(currentTheme + "-theme");
        
        updateModeIndicator();
        System.out.println(currentTheme.substring(0, 1).toUpperCase() + currentTheme.substring(1) + " theme applied");
    }

    // Calculator Operation Methods
    private boolean isScientificFunction(String buttonText) {
        return buttonText.equals("sin") || buttonText.equals("cos") || buttonText.equals("tan") || 
               buttonText.equals("cot") || buttonText.equals("sec") || buttonText.equals("csc") ||
               buttonText.equals("asin") || buttonText.equals("acos") || buttonText.equals("atan") ||
               buttonText.equals("log") || buttonText.equals("ln") || buttonText.equals("x^2") ||
               buttonText.equals("sqrt") || buttonText.equals("abs") || buttonText.equals("!") ||
               buttonText.equals("deriv") || buttonText.equals("integ") || buttonText.equals("lim") ||
               buttonText.equals("Quad") || buttonText.equals("Linear");
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        String buttonText = ((Button) event.getSource()).getText();
        System.out.println("Button pressed: " + buttonText);
        
        // Handle memory operations first
        if (buttonText.equals("MC") || buttonText.equals("MR") || buttonText.equals("MS") || 
            buttonText.equals("M+") || buttonText.equals("M-")) {
            handleMemoryOperation(buttonText);
            return;
        }
        
        // Handle variable x
        if (buttonText.equals("x")) {
            handleVariableX();
            return;
        }
        
        // Handle scientific functions
        if (isScientificFunction(buttonText)) {
            handleScientificFunction(buttonText);
            return;
        }
        
        // Handle constants
        if (buttonText.equals("pi") || buttonText.equals("e")) {
            handleConstant(buttonText);
            return;
        }
        
        switch (buttonText) {
            case "C":
                currentInput = "";
                operator = "";
                firstOperand = 0;
                display.setText("0");
                isNewInput = true;
                break;
            case "DEL":
                handleDelete();
                break;
            case "+/-":
                handleSignChange();
                break;
            case "=":
                calculateResult();
                break;
            case "+": case "-": case "*": case "/":
                handleOperator(buttonText);
                break;
            case ".":
                handleDecimal();
                break;
            case "(": case ")":
                handleParenthesis(buttonText);
                break;
            default:
                // Numbers
                if (buttonText.matches("[0-9]")) {
                    handleNumber(buttonText);
                }
                break;
        }
    }

    private void handleConstant(String constant) {
        switch (constant) {
            case "pi":
                if (isNewInput) {
                    currentInput = String.valueOf(Math.PI);
                } else {
                    currentInput += Math.PI;
                }
                display.setText(currentInput);
                isNewInput = false;
                addToHistory("π = " + Math.PI);
                break;
            case "e":
                if (isNewInput) {
                    currentInput = String.valueOf(Math.E);
                } else {
                    currentInput += Math.E;
                }
                display.setText(currentInput);
                isNewInput = false;
                addToHistory("e = " + Math.E);
                break;
        }
    }

    private void handleVariableX() {
        if (isNewInput) {
            currentInput = "x";
        } else {
            currentInput += "x";
        }
        display.setText(currentInput);
        isNewInput = false;
    }

    private void handleMemoryOperation(String operation) {
        switch (operation) {
            case "MC":
                memoryValue = 0;
                display.setText("Memory Cleared");
                break;
            case "MR":
                if (memoryValue != 0) {
                    currentInput = String.valueOf(memoryValue);
                    display.setText(currentInput);
                    isNewInput = true;
                } else {
                    display.setText("Memory Empty");
                }
                break;
            case "MS":
                if (!currentInput.isEmpty()) {
                    try {
                        memoryValue = Double.parseDouble(currentInput);
                        display.setText("Stored: " + currentInput);
                        addToHistory("Memory Stored: " + currentInput);
                    } catch (NumberFormatException e) {
                        display.setText("Error");
                    }
                }
                break;
            case "M+":
                if (!currentInput.isEmpty()) {
                    try {
                        double currentValue = Double.parseDouble(currentInput);
                        memoryValue += currentValue;
                        display.setText("Added: " + currentInput);
                        addToHistory("Memory Added: " + currentInput);
                    } catch (NumberFormatException e) {
                        display.setText("Error");
                    }
                }
                break;
            case "M-":
                if (!currentInput.isEmpty()) {
                    try {
                        double currentValue = Double.parseDouble(currentInput);
                        memoryValue -= currentValue;
                        display.setText("Subtracted: " + currentInput);
                        addToHistory("Memory Subtracted: " + currentInput);
                    } catch (NumberFormatException e) {
                        display.setText("Error");
                    }
                }
                break;
        }
        
        if (!operation.equals("MR")) {
            new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            if (currentInput.isEmpty()) {
                                display.setText("0");
                            } else {
                                display.setText(currentInput);
                            }
                        });
                    }
                },
                1000
            );
        }
    }

    private void handleScientificFunction(String function) {
        try {
            if (currentInput.isEmpty() && !function.equals("deriv") && !function.equals("integ") && 
                !function.equals("lim") && !function.equals("Quad") && !function.equals("Linear")) {
                display.setText("Error: Enter number first");
                return;
            }
            
            double value = 0;
            String operationText = "";
            double result = 0;
            
            if (!function.equals("deriv") && !function.equals("integ") && !function.equals("lim") &&
                !function.equals("Quad") && !function.equals("Linear")) {
                try {
                    value = Double.parseDouble(currentInput);
                } catch (NumberFormatException e) {
                    display.setText("Error: Enter number first");
                    return;
                }
            }
            
            switch (function) {
                case "sin":
                    result = Math.sin(isRadiansMode ? value : Math.toRadians(value));
                    operationText = "sin(" + value + ")";
                    break;
                case "cos":
                    result = Math.cos(isRadiansMode ? value : Math.toRadians(value));
                    operationText = "cos(" + value + ")";
                    break;
                case "tan":
                    result = Math.tan(isRadiansMode ? value : Math.toRadians(value));
                    operationText = "tan(" + value + ")";
                    break;
                case "cot":
                    double tanValue = Math.tan(isRadiansMode ? value : Math.toRadians(value));
                    if (Math.abs(tanValue) < 1e-10) throw new ArithmeticException("Cotangent undefined");
                    result = 1.0 / tanValue;
                    operationText = "cot(" + value + ")";
                    break;
                case "sec":
                    double cosValue = Math.cos(isRadiansMode ? value : Math.toRadians(value));
                    if (Math.abs(cosValue) < 1e-10) throw new ArithmeticException("Secant undefined");
                    result = 1.0 / cosValue;
                    operationText = "sec(" + value + ")";
                    break;
                case "csc":
                    double sinValue = Math.sin(isRadiansMode ? value : Math.toRadians(value));
                    if (Math.abs(sinValue) < 1e-10) throw new ArithmeticException("Cosecant undefined");
                    result = 1.0 / sinValue;
                    operationText = "csc(" + value + ")";
                    break;
                case "asin":
                    if (value < -1 || value > 1) throw new ArithmeticException("asin domain: [-1, 1]");
                    result = Math.asin(value);
                    if (!isRadiansMode) {
                        result = Math.toDegrees(result);
                    }
                    operationText = "asin(" + value + ")";
                    break;
                case "acos":
                    if (value < -1 || value > 1) throw new ArithmeticException("acos domain: [-1, 1]");
                    result = Math.acos(value);
                    if (!isRadiansMode) {
                        result = Math.toDegrees(result);
                    }
                    operationText = "acos(" + value + ")";
                    break;
                case "atan":
                    result = Math.atan(value);
                    if (!isRadiansMode) {
                        result = Math.toDegrees(result);
                    }
                    operationText = "atan(" + value + ")";
                    break;
                case "log":
                    result = handleCustomLog(value);
                    operationText = "log(" + value + ")";
                    break;
                case "ln":
                    if (value <= 0) throw new ArithmeticException("Ln of non-positive number");
                    result = Math.log(value);
                    operationText = "ln(" + value + ")";
                    break;
                case "x^2":
                    result = value * value;
                    operationText = "(" + value + ")^2";
                    break;
                case "sqrt":
                    if (value < 0) throw new ArithmeticException("Square root of negative number");
                    result = Math.sqrt(value);
                    operationText = "sqrt(" + value + ")";
                    break;
                case "abs":
                    result = Math.abs(value);
                    operationText = "|" + value + "|";
                    break;
                case "!":
                    result = factorial(value);
                    operationText = value + "!";
                    break;
                case "deriv":
                    result = handleDerivative();
                    operationText = "Derivative calculated";
                    break;
                case "integ":
                    result = handleIntegration();
                    operationText = "Integration calculated";
                    break;
                case "lim":
                    result = handleLimit();
                    operationText = "Limit calculated";
                    break;
                case "Quad":
                    solveQuadraticEquation();
                    return;
                case "Linear":
                    solveLinearEquation();
                    return;
                default:
                    display.setText("Function not implemented");
                    return;
            }
            
            if (Double.isNaN(result) || Double.isInfinite(result)) {
                display.setText("Error");
            } else {
                String formattedResult = formatResult(result);
                currentInput = formattedResult;
                display.setText(currentInput);
                addToHistory(operationText + " = " + formattedResult);
                isNewInput = true;
            }
            
        } catch (ArithmeticException e) {
            display.setText("Math Error: " + e.getMessage());
            currentInput = "";
            isNewInput = true;
        } catch (Exception e) {
            display.setText("Error");
            currentInput = "";
            isNewInput = true;
        }
    }

    // Quadratic equation solver
    private void solveQuadraticEquation() {
        try {
            TextInputDialog aDialog = new TextInputDialog("1");
            aDialog.setTitle("Quadratic Equation Solver");
            aDialog.setHeaderText("Solve: ax² + bx + c = 0");
            aDialog.setContentText("Enter coefficient a:");

            Optional<String> aResult = aDialog.showAndWait();
            if (aResult.isPresent() && !aResult.get().trim().isEmpty()) {
                TextInputDialog bDialog = new TextInputDialog("0");
                bDialog.setTitle("Quadratic Equation Solver");
                bDialog.setHeaderText("Solve: ax² + bx + c = 0");
                bDialog.setContentText("Enter coefficient b:");

                Optional<String> bResult = bDialog.showAndWait();
                if (bResult.isPresent() && !bResult.get().trim().isEmpty()) {
                    TextInputDialog cDialog = new TextInputDialog("0");
                    cDialog.setTitle("Quadratic Equation Solver");
                    cDialog.setHeaderText("Solve: ax² + bx + c = 0");
                    cDialog.setContentText("Enter coefficient c:");

                    Optional<String> cResult = cDialog.showAndWait();
                    if (cResult.isPresent() && !cResult.get().trim().isEmpty()) {
                        try {
                            double a = Double.parseDouble(aResult.get().trim());
                            double b = Double.parseDouble(bResult.get().trim());
                            double c = Double.parseDouble(cResult.get().trim());
                            
                            if (a == 0) {
                                throw new ArithmeticException("Coefficient 'a' cannot be zero for quadratic equation");
                            }
                            
                            String solution = solveQuadratic(a, b, c);
                            display.setText(solution);
                            currentInput = solution;
                            addToHistory("Quadratic: " + a + "x² + " + b + "x + " + c + " = 0 → " + solution);
                            
                        } catch (NumberFormatException e) {
                            showAlert("Input Error", "Please enter valid numbers for coefficients");
                        } catch (ArithmeticException e) {
                            showAlert("Math Error", e.getMessage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to solve quadratic equation: " + e.getMessage());
        }
    }

    private String solveQuadratic(double a, double b, double c) {
        double discriminant = b * b - 4 * a * c;
        String equation = formatCoefficient(a, "x²") + formatCoefficient(b, "x", true) + formatCoefficient(c, "", true) + " = 0";
        
        if (discriminant > 0) {
            double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            return equation + " → x = " + formatResult(root1) + ", " + formatResult(root2);
        } else if (discriminant == 0) {
            double root = -b / (2 * a);
            return equation + " → x = " + formatResult(root) + " (double root)";
        } else {
            double realPart = -b / (2 * a);
            double imaginaryPart = Math.sqrt(-discriminant) / (2 * a);
            return equation + " → x = " + formatResult(realPart) + " ± " + formatResult(imaginaryPart) + "i";
        }
    }

    private String formatCoefficient(double coeff, String variable, boolean showPlus) {
        if (coeff == 0) return "";
        
        String sign = "";
        if (showPlus && coeff > 0) sign = "+";
        if (coeff < 0) sign = "-";
        
        double absCoeff = Math.abs(coeff);
        String coeffStr = (absCoeff == 1 && !variable.isEmpty()) ? "" : formatResult(absCoeff);
        
        return " " + sign + " " + coeffStr + variable;
    }

    private String formatCoefficient(double coeff, String variable) {
        return formatCoefficient(coeff, variable, false);
    }

    // Linear equation solver
    private void solveLinearEquation() {
        try {
            TextInputDialog aDialog = new TextInputDialog("1");
            aDialog.setTitle("Linear Equation Solver");
            aDialog.setHeaderText("Solve: ax + b = 0");
            aDialog.setContentText("Enter coefficient a:");

            Optional<String> aResult = aDialog.showAndWait();
            if (aResult.isPresent() && !aResult.get().trim().isEmpty()) {
                TextInputDialog bDialog = new TextInputDialog("0");
                bDialog.setTitle("Linear Equation Solver");
                bDialog.setHeaderText("Solve: ax + b = 0");
                bDialog.setContentText("Enter coefficient b:");

                Optional<String> bResult = bDialog.showAndWait();
                if (bResult.isPresent() && !bResult.get().trim().isEmpty()) {
                    try {
                        double a = Double.parseDouble(aResult.get().trim());
                        double b = Double.parseDouble(bResult.get().trim());
                        
                        if (a == 0) {
                            if (b == 0) {
                                display.setText("Infinite solutions (0 = 0)");
                                currentInput = "Infinite solutions";
                                addToHistory("Linear: 0x + " + b + " = 0 → Infinite solutions");
                            } else {
                                display.setText("No solution (" + b + " ≠ 0)");
                                currentInput = "No solution";
                                addToHistory("Linear: 0x + " + b + " = 0 → No solution");
                            }
                        } else {
                            double solution = -b / a;
                            String equation = formatCoefficient(a, "x") + formatCoefficient(b, "", true) + " = 0";
                            String result = equation + " → x = " + formatResult(solution);
                            display.setText(result);
                            currentInput = result;
                            addToHistory("Linear: " + equation + " → x = " + formatResult(solution));
                        }
                        
                    } catch (NumberFormatException e) {
                        showAlert("Input Error", "Please enter valid numbers for coefficients");
                    }
                }
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to solve linear equation: " + e.getMessage());
        }
    }

    // Handle logarithm with custom base
    private double handleCustomLog(double value) {
        if (value <= 0) {
            throw new ArithmeticException("Logarithm is only defined for positive numbers");
        }
        
        Alert choiceDialog = new Alert(AlertType.CONFIRMATION);
        choiceDialog.setTitle("Logarithm Base");
        choiceDialog.setHeaderText("Choose logarithm base for: " + value);
        choiceDialog.setContentText("Select the base you want to use:");
        
        ButtonType base10Button = new ButtonType("Base 10");
        ButtonType customBaseButton = new ButtonType("Custom Base");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());
        
        choiceDialog.getButtonTypes().setAll(base10Button, customBaseButton, cancelButton);
        
        Optional<ButtonType> result = choiceDialog.showAndWait();
        
        if (result.isPresent()) {
            if (result.get() == base10Button) {
                return Math.log10(value);
            } else if (result.get() == customBaseButton) {
                TextInputDialog baseDialog = new TextInputDialog("e");
                baseDialog.setTitle("Custom Logarithm Base");
                baseDialog.setHeaderText("Enter the base for the logarithm");
                baseDialog.setContentText("Base:");
                
                Optional<String> baseResult = baseDialog.showAndWait();
                if (baseResult.isPresent() && !baseResult.get().trim().isEmpty()) {
                    try {
                        double base = Double.parseDouble(baseResult.get().trim());
                        
                        if (base <= 0) {
                            throw new ArithmeticException("Logarithm base must be positive");
                        }
                        if (base == 1) {
                            throw new ArithmeticException("Logarithm base cannot be 1");
                        }
                        
                        double logResult = Math.log(value) / Math.log(base);
                        addToHistory("log_" + base + "(" + value + ") = " + formatResult(logResult));
                        return logResult;
                        
                    } catch (NumberFormatException e) {
                        throw new ArithmeticException("Invalid base format. Please enter a valid number.");
                    }
                } else {
                    throw new ArithmeticException("No base entered");
                }
            }
        }
        
        throw new ArithmeticException("Logarithm operation cancelled");
    }

    // Integration function
    private double handleIntegration() {
        try {
            TextInputDialog functionDialog = new TextInputDialog("x^2");
            functionDialog.setTitle("Integration Calculation");
            functionDialog.setHeaderText("Enter function for integration (use x as variable):");
            functionDialog.setContentText("Function:");

            Optional<String> functionResult = functionDialog.showAndWait();
            if (functionResult.isPresent() && !functionResult.get().trim().isEmpty()) {
                String function = functionResult.get().trim();
                
                TextInputDialog lowerDialog = new TextInputDialog("0");
                lowerDialog.setTitle("Integration Bounds");
                lowerDialog.setHeaderText("Enter lower bound:");
                lowerDialog.setContentText("a =");

                Optional<String> lowerResult = lowerDialog.showAndWait();
                if (lowerResult.isPresent() && !lowerResult.get().trim().isEmpty()) {
                    TextInputDialog upperDialog = new TextInputDialog("1");
                    upperDialog.setTitle("Integration Bounds");
                    upperDialog.setHeaderText("Enter upper bound:");
                    upperDialog.setContentText("b =");

                    Optional<String> upperResult = upperDialog.showAndWait();
                    if (upperResult.isPresent() && !upperResult.get().trim().isEmpty()) {
                        try {
                            double a = Double.parseDouble(lowerResult.get().trim());
                            double b = Double.parseDouble(upperResult.get().trim());
                            
                            if (a >= b) {
                                throw new ArithmeticException("Lower bound must be less than upper bound");
                            }
                            
                            double result = numericalIntegration(function, a, b);
                            addToHistory("∫ " + function + " dx from " + a + " to " + b + " = " + result);
                            return result;
                            
                        } catch (NumberFormatException e) {
                            throw new ArithmeticException("Invalid bounds format");
                        }
                    }
                }
            }
            return 0;
        } catch (ArithmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new ArithmeticException("Integration failed");
        }
    }

    private double numericalIntegration(String function, double a, double b) {
        int n = 1000;
        if (n % 2 != 0) n++;
        
        double h = (b - a) / n;
        double sum = evaluateIntegrationFunction(function, a) + evaluateIntegrationFunction(function, b);
        
        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            if (i % 2 == 0) {
                sum += 2 * evaluateIntegrationFunction(function, x);
            } else {
                sum += 4 * evaluateIntegrationFunction(function, x);
            }
        }
        
        return sum * h / 3;
    }

    private double evaluateIntegrationFunction(String function, double x) {
        try {
            String func = function.toLowerCase().replaceAll("\\s+", "");
            
            if (func.equals("x") || func.equals("x^1")) return x;
            if (func.equals("x^2")) return x * x;
            if (func.equals("x^3")) return x * x * x;
            if (func.equals("x^4")) return x * x * x * x;
            
            if (func.matches("\\d+\\*x\\^\\d+")) {
                String[] parts = func.split("\\*x\\^");
                double coefficient = Double.parseDouble(parts[0]);
                int power = Integer.parseInt(parts[1]);
                return coefficient * Math.pow(x, power);
            }
            
            if (func.matches("\\d+\\*x")) {
                String coefficient = func.replace("*x", "");
                return Double.parseDouble(coefficient) * x;
            }
            
            if (func.contains("+")) {
                String[] terms = func.split("\\+");
                double result = 0;
                for (String term : terms) {
                    result += evaluateIntegrationFunction(term.trim(), x);
                }
                return result;
            }
            
            if (func.equals("sin(x)")) return Math.sin(x);
            if (func.equals("cos(x)")) return Math.cos(x);
            if (func.equals("tan(x)")) return Math.tan(x);
            if (func.equals("sin(x)/x")) {
                if (Math.abs(x) < 1e-10) return 1.0;
                return Math.sin(x) / x;
            }
            
            if (func.equals("e^x") || func.equals("exp(x)")) return Math.exp(x);
            if (func.equals("ln(x)")) {
                if (x <= 0) throw new ArithmeticException("Ln of non-positive number");
                return Math.log(x);
            }
            if (func.equals("log(x)")) {
                if (x <= 0) throw new ArithmeticException("Log of non-positive number");
                return Math.log10(x);
            }
            
            if (func.equals("sqrt(x)")) {
                if (x < 0) throw new ArithmeticException("Square root of negative number");
                return Math.sqrt(x);
            }
            
            if (func.matches("\\d+(\\.\\d+)?")) {
                return Double.parseDouble(func);
            }
            
            try {
                String expression = func.replace("x", Double.toString(x));
                if (expression.matches("[0-9+\\-*/.() ]+")) {
                    return evaluateSimpleExpression(expression);
                }
            } catch (Exception e) {
            }
            
            throw new ArithmeticException("Unsupported function: " + function);
            
        } catch (NumberFormatException e) {
            throw new ArithmeticException("Invalid function format: " + function);
        } catch (Exception e) {
            throw new ArithmeticException("Cannot evaluate function: " + function + " at x=" + x);
        }
    }

    private double evaluateSimpleExpression(String expression) {
        expression = expression.replaceAll("\\s+", "");
        
        try {
            if (expression.contains("+")) {
                String[] parts = expression.split("\\+");
                return Double.parseDouble(parts[0]) + Double.parseDouble(parts[1]);
            } else if (expression.contains("-")) {
                String[] parts = expression.split("-");
                return Double.parseDouble(parts[0]) - Double.parseDouble(parts[1]);
            } else if (expression.contains("*")) {
                String[] parts = expression.split("\\*");
                return Double.parseDouble(parts[0]) * Double.parseDouble(parts[1]);
            } else if (expression.contains("/")) {
                String[] parts = expression.split("/");
                double denominator = Double.parseDouble(parts[1]);
                if (denominator == 0) throw new ArithmeticException("Division by zero");
                return Double.parseDouble(parts[0]) / denominator;
            } else {
                return Double.parseDouble(expression);
            }
        } catch (Exception e) {
            throw new ArithmeticException("Cannot evaluate expression: " + expression);
        }
    }

    // Limit function
    private double handleLimit() {
        TextInputDialog functionDialog = new TextInputDialog("sin(x)/x");
        functionDialog.setTitle("Limit Calculation");
        functionDialog.setHeaderText("Enter function for limit (use x as variable):");
        functionDialog.setContentText("Function:");

        Optional<String> functionResult = functionDialog.showAndWait();
        if (functionResult.isPresent()) {
            String function = functionResult.get();
            
            TextInputDialog pointDialog = new TextInputDialog("0");
            pointDialog.setTitle("Limit Point");
            pointDialog.setHeaderText("Calculate limit as x approaches:");
            pointDialog.setContentText("x →");

            Optional<String> pointResult = pointDialog.showAndWait();
            if (pointResult.isPresent()) {
                try {
                    double point = Double.parseDouble(pointResult.get());
                    return calculateLimit(function, point);
                } catch (NumberFormatException e) {
                    throw new ArithmeticException("Invalid x value");
                }
            }
        }
        return 0;
    }

    private double calculateLimit(String function, double point) {
        try {
            double leftLimit = evaluateIntegrationFunction(function, point - 1e-8);
            double rightLimit = evaluateIntegrationFunction(function, point + 1e-8);
            
            if (Math.abs(leftLimit - rightLimit) < 1e-6) {
                return (leftLimit + rightLimit) / 2;
            } else {
                throw new ArithmeticException("Limit does not exist");
            }
        } catch (Exception e) {
            throw new ArithmeticException("Cannot calculate limit");
        }
    }

    // Derivative function
    private double handleDerivative() {
        TextInputDialog functionDialog = new TextInputDialog("x^2");
        functionDialog.setTitle("Derivative Calculation");
        functionDialog.setHeaderText("Enter function for derivative (use x as variable):");
        functionDialog.setContentText("Function:");

        Optional<String> functionResult = functionDialog.showAndWait();
        if (functionResult.isPresent()) {
            String function = functionResult.get();
            
            TextInputDialog pointDialog = new TextInputDialog("1");
            pointDialog.setTitle("Derivative Point");
            pointDialog.setHeaderText("Calculate derivative at point:");
            pointDialog.setContentText("x =");

            Optional<String> pointResult = pointDialog.showAndWait();
            if (pointResult.isPresent()) {
                try {
                    double x = Double.parseDouble(pointResult.get());
                    return numericalDerivative(function, x);
                } catch (NumberFormatException e) {
                    throw new ArithmeticException("Invalid x value");
                }
            }
        }
        return 0;
    }

    private double numericalDerivative(String function, double x) {
        double h = 1e-8;
        return (evaluateIntegrationFunction(function, x + h) - evaluateIntegrationFunction(function, x - h)) / (2 * h);
    }

    private double factorial(double value) {
        if (value < 0 || value != Math.floor(value)) {
            throw new ArithmeticException("Factorial requires non-negative integer");
        }
        if (value > 20) {
            throw new ArithmeticException("Factorial too large");
        }
        long result = 1;
        for (int i = 2; i <= value; i++) {
            result *= i;
        }
        return result;
    }

    private void handleNumber(String number) {
        if (isNewInput) {
            currentInput = number;
            isNewInput = false;
        } else {
            currentInput += number;
        }
        display.setText(currentInput);
    }

    private void handleDecimal() {
        if (isNewInput) {
            currentInput = "0.";
            isNewInput = false;
        } else if (!currentInput.contains(".")) {
            currentInput += ".";
        }
        display.setText(currentInput);
    }

    private void handleDelete() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            if (currentInput.isEmpty()) {
                display.setText("0");
                isNewInput = true;
            } else {
                display.setText(currentInput);
            }
        }
    }

    private void handleSignChange() {
        if (!currentInput.isEmpty() && !currentInput.equals("0")) {
            if (currentInput.startsWith("-")) {
                currentInput = currentInput.substring(1);
            } else {
                currentInput = "-" + currentInput;
            }
            display.setText(currentInput);
        }
    }

    private void handleOperator(String newOperator) {
        if (!currentInput.isEmpty()) {
            firstOperand = Double.parseDouble(currentInput);
            operator = newOperator;
            currentInput = "";
            isNewInput = true;
        }
    }

    private void handleParenthesis(String parenthesis) {
        currentInput += parenthesis;
        display.setText(currentInput);
        isNewInput = false;
    }

    private void calculateResult() {
        if (!currentInput.isEmpty() && !operator.isEmpty()) {
            try {
                double secondOperand = Double.parseDouble(currentInput);
                double result = 0;
                
                switch (operator) {
                    case "+": 
                        result = firstOperand + secondOperand; 
                        break;
                    case "-": 
                        result = firstOperand - secondOperand; 
                        break;
                    case "*": 
                        result = firstOperand * secondOperand; 
                        break;
                    case "/": 
                        if (secondOperand != 0) {
                            result = firstOperand / secondOperand; 
                        } else {
                            display.setText("Error: Division by zero");
                            return;
                        }
                        break;
                }
                
                String formattedResult = formatResult(result);
                currentInput = formattedResult;
                display.setText(currentInput);
                
                String historyEntry = firstOperand + " " + operator + " " + secondOperand + " = " + formattedResult;
                addToHistory(historyEntry);
                
                operator = "";
                isNewInput = true;
                
            } catch (Exception e) {
                display.setText("Error");
                currentInput = "";
                isNewInput = true;
            }
        }
    }

    private String formatResult(double result) {
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            return "Error";
        }
        if (result == (long) result) {
            return String.valueOf((long) result);
        } else {
            return String.format("%.10f", result).replaceAll("0*$", "").replaceAll("\\.$", "");
        }
    }

    private void addToHistory(String calculation) {
        calculationHistory.add(0, calculation);
        if (calculationHistory.size() > 50) {
            calculationHistory.remove(calculationHistory.size() - 1);
        }
    }

    // Menu Handlers
    @FXML
    private void handleRadiansMode() {
        isRadiansMode = true;
    }

    @FXML
    private void handleDegreesMode() {
        isRadiansMode = false;
    }

    @FXML
    private void handleReset() {
        currentInput = "";
        operator = "";
        firstOperand = 0;
        memoryValue = 0;
        display.setText("0");
        isNewInput = true;
    }

    // NEW: Method to style alerts based on current theme
  // Update this method in your CalculatorController
// UPDATE this method in your CalculatorController
// UPDATE this method in your CalculatorController - ENHANCED VERSION
// REPLACE your styleAlert method with this BETTER version
private void styleAlert(Alert alert) {
    if (currentTheme.equals("dark")) {
        DialogPane dialogPane = alert.getDialogPane();
        
        // Clear any existing stylesheets and add ours
        dialogPane.getStylesheets().clear();
        try {
            dialogPane.getStylesheets().add(getClass().getResource("/calculator/styles/styles.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("Could not load CSS: " + e.getMessage());
        }
        
        // Apply dark theme class
        dialogPane.getStyleClass().add("dark-theme");
        
        // DIRECT STYLING - This is the key fix!
        // Force style the content label directly
        Node content = dialogPane.lookup(".content.label");
        if (content instanceof Label) {
            Label contentLabel = (Label) content;
            contentLabel.setStyle("-fx-text-fill: #e0f0f2 !important;");
        }
        
        // Force style the header label
        Node header = dialogPane.lookup(".header-panel .label");
        if (header instanceof Label) {
            Label headerLabel = (Label) header;
            headerLabel.setStyle("-fx-text-fill: #e0f0f2 !important;");
        }
        
        // Force style all buttons
            for (Node node : dialogPane.lookupAll(".button")) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setStyle("-fx-text-fill: #e0f0f2 !important; " +
                               "-fx-background-color: #1a2527 !important; " +
                               "-fx-border-color: #2a3a3d !important;");
            }
        }
        
        // Apply CSS after direct styling
        Platform.runLater(() -> {
            dialogPane.applyCss();
        });
    }
}

@FXML
    private void handleClearHistory() {
        if (calculationHistory.isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No History");
            alert.setHeaderText(null);
            alert.setContentText("There is no calculation history to clear.");
            
            styleAlert(alert); // Apply theme styling
            alert.showAndWait();
            return;
        }
        
        Alert confirmationDialog = new Alert(AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Clear History");
        confirmationDialog.setHeaderText("Clear All Calculation History?");
        confirmationDialog.setContentText("You are about to delete " + calculationHistory.size() + 
                                        " calculations from history.\nThis action cannot be undone.");
        
        styleAlert(confirmationDialog); // Apply theme styling
        
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            calculationHistory.clear();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("History Cleared");
            alert.setHeaderText(null);
            alert.setContentText("All calculation history has been cleared.");
            
            styleAlert(alert); // Apply theme styling
            alert.showAndWait();
        }
    }

    @FXML
    private void handleShowHistory() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Calculation History");
        alert.setHeaderText("Previous Calculations");
        
        if (calculationHistory.isEmpty()) {
            alert.setContentText("No calculations in history.");
        } else {
            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setPrefSize(400, 300);
            
            StringBuilder historyText = new StringBuilder();
            for (int i = 0; i < calculationHistory.size(); i++) {
                historyText.append((i + 1)).append(". ").append(calculationHistory.get(i)).append("\n");
            }
            
            textArea.setText(historyText.toString());
            
            ScrollPane scrollPane = new ScrollPane(textArea);
            scrollPane.setPrefSize(400, 300);
            
            alert.getDialogPane().setContent(scrollPane);
        }
        
        styleAlert(alert); // Apply theme styling
        alert.showAndWait();
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About Calculator");
        alert.setHeaderText("Scientific Calculator v3.0");
        alert.setContentText("A feature-rich scientific calculator with:\n" +
                           "• Basic arithmetic operations\n" +
                           "• Trigonometric functions\n" +
                           "• Inverse trigonometric functions (asin, acos, atan)\n" +
                           "• Calculus functions (derivative, integration, limit)\n" +
                           "• Memory functions\n" +
                           "• History tracking\n" +
                           "• Theme support\n" +
                           "• Degree/Radians mode\n" +
                           "• Standard/Advanced mode switching\n" +
                           "• Logarithm with custom base\n" +
                           "• Quadratic and Linear equation solvers\n\n" +
                           "Created with JavaFX");
        
        styleAlert(alert); // Apply theme styling
        alert.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        styleAlert(alert); // Apply theme styling
        alert.showAndWait();
    }
}