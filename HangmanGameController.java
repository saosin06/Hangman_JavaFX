package com.example.final_project;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Random;

public class HangmanGameController {

    @FXML
    private Label wordLabel;

    @FXML
    private Label attemptsLabel;

    @FXML
    private TextField guessInput;

    @FXML
    private Button guessButton;

    @FXML
    private Label messageLabel;

    private ArrayList<String> words;
    private String selectedWord;
    private StringBuilder displayedWord;
    private int attemptsRemaining;

    @FXML
    public void initialize() {
        initializeGame();
    }

    private void initializeGame() {
        // Initialize words list
        words = new ArrayList<>();
        words.add("programming");
        words.add("javafx");
        words.add("hangman");
        words.add("application");
        words.add("interface");

        // Select a random word
        Random random = new Random();
        selectedWord = words.get(random.nextInt(words.size()));

        // Create displayed word with underscores
        displayedWord = new StringBuilder("_".repeat(selectedWord.length()));
        wordLabel.setText("Word: " + displayedWord);
        attemptsRemaining = 6;
        attemptsLabel.setText("Attempts Remaining: " + attemptsRemaining);
        messageLabel.setText("");
    }

    @FXML
    private void processGuess() {
        String input = guessInput.getText().toLowerCase();

        // Validate input
        if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
            messageLabel.setText("Please enter a valid single letter.");
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
    }
}
