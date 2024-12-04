/**
 * Hangman Game Application
 * This program implements a Hangman game with a JavaFX interface.
 * The user guesses letters to reveal a randomly selected word.
 * The game ends when the word is guessed or the player runs out of attempts.
 *
 * Author: Nick Costello
 * Date: 12/2/2024
 */
package com.example.final_project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

/**
 * Main application class for the Hangman game.
 * Extends the JavaFX Application class to create a GUI-based game.
 */
public class HangmanGame extends Application {

    private ArrayList<String> words;
    private String selectedWord;
    private StringBuilder displayedWord;
    private int attemptsRemaining;
    private Label wordLabel;
    private Label attemptsLabel;
    private TextField guessInput;
    private Button guessButton;
    private Label messageLabel;
    private Button newGameButton;

    /**
     * Starts the JavaFX application, initializes the game, and sets up the primary stage with the UI elements.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        initializeGame();

        // Create UI elements
        wordLabel = new Label("Word: " + displayedWord.toString());
        attemptsLabel = new Label("Attempts Remaining: " + attemptsRemaining);
        guessInput = new TextField();
        guessInput.setPromptText("Enter a letter");
        guessButton = new Button("Guess");
        messageLabel = new Label();
        newGameButton = new Button("New Game");

        // Add event listeners
        guessButton.setOnAction(e -> processGuess());
        newGameButton.setOnAction(e -> startNewGame());

        // Layout
        VBox layout = new VBox(10, wordLabel, attemptsLabel, guessInput, guessButton, messageLabel, newGameButton);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        // Scene and Stage
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setTitle("Hangman Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Initializes the game by setting up the list of words, selecting a random word,
     * and preparing the displayed word with underscores.
     */
    private void initializeGame() {
        words = new ArrayList<>();
        words.add("tacos");
        words.add("pizza");
        words.add("scratch");
        words.add("python");
        words.add("hello");

        Random random = new Random();
        selectedWord = words.get(random.nextInt(words.size()));
        displayedWord = new StringBuilder("_".repeat(selectedWord.length()));
        attemptsRemaining = 6;
    }

    /**
     * Processes the player's guess, updates the game state, and checks for win or loss conditions.
     * Displays error messages in popup windows for invalid input or other errors.
     */
    private void processGuess() {
        String input = guessInput.getText().toLowerCase();

        if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
            showAlert("Invalid Input", "Please enter a valid single letter.");
            return;
        }

        char guessedLetter = input.charAt(0);
        guessInput.clear();

        boolean isCorrect = false;
        for (int i = 0; i < selectedWord.length(); i++) {
            if (selectedWord.charAt(i) == guessedLetter && displayedWord.charAt(i) == '_') {
                displayedWord.setCharAt(i, guessedLetter);
                isCorrect = true;
            }
        }

        if (isCorrect) {
            wordLabel.setText("Word: " + displayedWord.toString());
            messageLabel.setText("Good guess!");
        } else {
            attemptsRemaining--;
            attemptsLabel.setText("Attempts Remaining: " + attemptsRemaining);
            messageLabel.setText("Wrong guess!");
        }

        if (displayedWord.toString().equals(selectedWord)) {
            showAlert("Congratulations!", "You guessed the word: " + selectedWord);
            guessButton.setDisable(true);
        } else if (attemptsRemaining == 0) {
            showAlert("Game Over", "The word was: " + selectedWord);
            guessButton.setDisable(true);
        }
    }

    /**
     * Displays an alert popup with the specified title and message.
     *
     * @param title   the title of the alert popup
     * @param message the message to display in the alert popup
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Starts a new game by reinitializing the game state and resetting UI elements.
     */
    private void startNewGame() {
        initializeGame();
        wordLabel.setText("Word: " + displayedWord.toString());
        attemptsLabel.setText("Attempts Remaining: " + attemptsRemaining);
        messageLabel.setText("");
        guessInput.clear();
        guessButton.setDisable(false);
    }

    /**
     * Main entry point for the application.
     * Launches the JavaFX application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
