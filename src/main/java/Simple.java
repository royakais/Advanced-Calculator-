import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.LinkedList;

public class ScientificCalculatorFX extends Application {
    private TextField display = new TextField();
    private ListView<String> historyList = new ListView<>();
    private LinkedList<String> history = new LinkedList<>();

    @Override
    public void start(Stage primaryStage) {
        display.setEditable(false);
        display.setFont(Font.font("Consolas", 24));
        display.setPrefHeight(60);
        display.setStyle("-fx-background-color: #202020; -fx-text-fill: white; -fx-alignment: center-right;");

        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);
        buttonGrid.setPadding(new Insets(10));
        buttonGrid.setAlignment(Pos.CENTER);

        String[][] buttons = {
                {"7", "8", "9", "/", "sin"},
                {"4", "5", "6", "*", "cos"},
                {"1", "2", "3", "-", "tan"},
                {"0", ".", "=", "+", "log"},
                {"π", "e", "^", "√", "ln"}
        };

        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                String text = buttons[row][col];
                Button btn = createCalcButton(text);
                buttonGrid.add(btn, col, row);
            }
        }

        // Round Button
        Button roundButton = new Button("R");
        roundButton.setShape(new Circle(25));
        roundButton.setMinSize(50, 50);
        roundButton.setStyle("-fx-background-color: #ffcc00; -fx-text-fill: black; -fx-font-weight: bold;");
        roundButton.setOnAction(e -> roundDisplayValue());

        // Scan Button (PhotoMath style concept)
        Button scanButton = new Button();
        scanButton.setGraphic(new ImageView("camera_icon.png")); // Provide your own icon
        scanButton.setStyle("-fx-background-color: #3a3a3a; -fx-shape: "circle";");
        scanButton.setOnAction(e -> showScanFeaturePopup());
        scanButton.setMinSize(50, 50);
        scanButton.setShape(new Circle(25));

        HBox bottomButtons = new HBox(20, roundButton, scanButton);
        bottomButtons.setAlignment(Pos.CENTER);
        bottomButtons.setPadding(new Insets(10));

        // History List
        historyList.setPrefWidth(150);
        historyList.setStyle("-fx-control-inner-background: #1e1e1e; -fx-text-fill: white;");
        Label historyLabel = new Label("History");
        historyLabel.setTextFill(Color.WHITE);

        Button clearHistoryButton = new Button("Clear");
        clearHistoryButton.setOnAction(e -> clearHistory());
        clearHistoryButton.setStyle("-fx-background-color: #ff5555; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox historyHeader = new HBox(10, historyLabel, clearHistoryButton);
        historyHeader.setAlignment(Pos.CENTER_LEFT);

        VBox historyBox = new VBox(5, historyHeader, historyList);
        historyBox.setPadding(new Insets(10));
        historyBox.setStyle("-fx-background-color: #1e1e1e;");

        VBox leftPanel = new VBox(10, display, buttonGrid, bottomButtons);
        leftPanel.setStyle("-fx-background-color: #121212;");

        HBox root = new HBox(10, leftPanel, historyBox);

        Scene scene = new Scene(root, 550, 550);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ultimate Scientific Calculator");
        primaryStage.show();
    }

    private Button createCalcButton(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font(16));
        btn.setPrefSize(70, 50);
        btn.setStyle("-fx-background-color: #2a2a2a; -fx-text-fill: white;");
        btn.setOnAction(e -> handleInput(text));
        return btn;
    }

    private void handleInput(String input) {
        if ("=".equals(input)) {
            try {
                String expression = display.getText();
                String result = evaluate(expression);
                display.setText(result);
                addToHistory(expression + " = " + result);
            } catch (Exception e) {
                display.setText("Error");
            }
        } else if ("π".equals(input)) {
            display.appendText(String.valueOf(Math.PI));
        } else if ("e".equals(input)) {
            display.appendText(String.valueOf(Math.E));
        } else if ("√".equals(input)) {
            display.appendText("sqrt(");
        } else {
            display.appendText(input);
        }
    }

    private String evaluate(String expression) {
        // Placeholder: Add a real expression parser or integrate with exp4j or mXparser
        return "42";
    }

    private void roundDisplayValue() {
        try {
            double val = Double.parseDouble(display.getText());
            display.setText(String.format("%.4f", val));
        } catch (NumberFormatException e) {
            display.setText("Error");
        }
    }

    private void showScanFeaturePopup() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Scan Feature");
        alert.setHeaderText("Photomath-Style Scan");
        alert.setContentText("This feature lets you scan a math problem using your camera. Coming soon!");
        alert.showAndWait();
    }

    private void addToHistory(String record) {
        if (history.size() == 10) {
            history.removeFirst();
        }
        history.add(record);
        historyList.getItems().setAll(history);
    }

    private void clearHistory() {
        history.clear();
        historyList.getItems().clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

