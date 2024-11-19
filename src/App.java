import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {

    private final int SIZE = 5; // Grid size (5x5)
    private final Button[][] buttons = new Button[SIZE][SIZE];
    private boolean isPlayerXTurn = true; // True for X's turn, false for O's turn

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Set up the main grid
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        // Initialize buttons and set up event handlers
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Button button = new Button();
                button.setMinSize(100, 100); // Set the size of each button
                button.setStyle("-fx-font-size: 24; -fx-base: lightgray;");
                button.setOnAction(e -> handleButtonClick(row, col, button));
                buttons[row][col] = button;
                grid.add(button, col, row);
            }
        }

        // Set up the scene
        Scene scene = new Scene(grid, 600, 600);
        primaryStage.setTitle("5x5 Tic-Tac-Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Event handler for button clicks
    private void handleButtonClick(int row, int col, Button button) {
        if (!button.getText().isEmpty()) {
            return; // Ignore the click if the cell is already occupied
        }

        // Set the text of the button based on whose turn it is
        if (isPlayerXTurn) {
            button.setText("X");
        } else {
            button.setText("O");
        }

        // Check for a winner
        if (checkForWin(row, col)) {
            showWinnerAlert(isPlayerXTurn ? "Player X wins!" : "Player O wins!");
            return;
        }

        // Check for a draw
        if (checkForDraw()) {
            showWinnerAlert("It's a draw!");
            return;
        }

        // Switch turns
        isPlayerXTurn = !isPlayerXTurn;
    }

    // Check if a player has won
    private boolean checkForWin(int row, int col) {
        String playerSymbol = buttons[row][col].getText();

        // Check row
        if (checkLine(playerSymbol, row, 0, 0, 1)) return true;

        // Check column
        if (checkLine(playerSymbol, 0, col, 1, 0)) return true;

        // Check diagonal (top-left to bottom-right)
        if (row == col && checkLine(playerSymbol, 0, 0, 1, 1)) return true;

        // Check diagonal (top-right to bottom-left)
        if (row + col == SIZE - 1 && checkLine(playerSymbol, 0, SIZE - 1, 1, -1)) return true;

        return false;
    }

    // Helper method to check if a line has the same player's symbol
    private boolean checkLine(String playerSymbol, int startRow, int startCol, int rowIncrement, int colIncrement) {
        for (int i = 0; i < SIZE; i++) {
            int row = startRow + i * rowIncrement;
            int col = startCol + i * colIncrement;
            if (!buttons[row][col].getText().equals(playerSymbol)) {
                return false;
            }
        }
        return true;
    }

    // Check if the board is full (draw)
    private boolean checkForDraw() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    // Show an alert box for the winner or draw
    private void showWinnerAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        // Reset the game
        resetGame();
    }

    // Reset the game for a new round
    private void resetGame() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col].setText("");
            }
        }
        isPlayerXTurn = true; // Start with Player X
    }
}