/**
 * Hangman Game Application
 * This program implements a Hangman game with a JavaFX interface.
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

public class HangmanGame extends Application {

    private ArrayList<String> words;
    private String selectedWord;
    private StringBuilder displayedWord;
    private int attemptsRemaining;
    private Label wordLabel;
    private Label attemptsLabel;
    private TextField guessInput;
    private Button guessButton;
    private Button newGameButton;
    private Label messageLabel;

    @Override
    public void start(Stage primaryStage) {
        try {
            initializeGame();

            // Create UI elements
            wordLabel = new Label("Word: " + displayedWord.toString());
            attemptsLabel = new Label("Attempts Remaining: " + attemptsRemaining);
            guessInput = new TextField();
            guessInput.setPromptText("Enter a letter");
            guessButton = new Button("Guess");
            newGameButton = new Button("New Game");
            messageLabel = new Label();

            // Add event listeners to buttons
            guessButton.setOnAction(e -> processGuess());
            newGameButton.setOnAction(e -> restartGame());

            // Layout
            VBox layout = new VBox(10, wordLabel, attemptsLabel, guessInput, guessButton, newGameButton, messageLabel);
            layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

            // Scene and Stage
            Scene scene = new Scene(layout, 400, 300);
            primaryStage.setTitle("Hangman Game");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred while starting the game.");
        }
    }

    private void initializeGame() {
        try {
            // Initialize words list
            words = new ArrayList<>();
            words.add("tacos");
            words.add("pizza");
            words.add("hello");
            words.add("better");
            words.add("mayday");

            // Select a random word
            Random random = new Random();
            selectedWord = words.get(random.nextInt(words.size()));

            // Create displayed word with underscores
            displayedWord = new StringBuilder("_".repeat(selectedWord.length()));
            attemptsRemaining = 6;
        } catch (Exception e) {
            showAlert("Error", "An error occurred while initializing the game.");
        }
    }

    private void processGuess() {
        try {
            String input = guessInput.getText().toLowerCase();

            // Validate input
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                showAlert("Invalid Input", "Please enter a valid single letter.");
                return;
            }

            char guessedLetter = input.charAt(0);
            guessInput.clear();

            // Check if letter is in the word
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

            // Check for win or loss
            if (displayedWord.toString().equals(selectedWord)) {
                messageLabel.setText("Congratulations! You guessed the word.");
                guessButton.setDisable(true);
            } else if (attemptsRemaining == 0) {
                messageLabel.setText("Game Over! The word was: " + selectedWord);
                guessButton.setDisable(true);
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred while processing your guess.");
        }
    }

    private void restartGame() {
        try {
            initializeGame();
            wordLabel.setText("Word: " + displayedWord.toString());
            attemptsLabel.setText("Attempts Remaining: " + attemptsRemaining);
            guessInput.clear();
            messageLabel.setText("");
            guessButton.setDisable(false);
        } catch (Exception e) {
            showAlert("Error", "An error occurred while restarting the game.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
