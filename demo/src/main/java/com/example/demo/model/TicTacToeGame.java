package com.example.demo.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicTacToeGame {
    private static final Logger logger = LoggerFactory.getLogger(TicTacToeGame.class);

    private int[][] board = new int[3][3];
    private String player1Name = "";
    private String player2Name = "";
    private boolean gameEnded = false;
    private String winner = "";
    private int currentPlayer = 1;  // 1 = Spieler 1, 2 = Spieler 2

    public int[][] getBoard() {
        return board;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public String getWinner() {
        return winner;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public String getCurrentPlayerName() {
        return (currentPlayer == 1) ? player1Name : player2Name;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    // Ein Zug ausführen (falls man die Logik hier abbilden möchte)
    public void makeMove(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            throw new IllegalArgumentException("Ungültige Koordinaten: (0-2)");
        }
        if (board[row][col] != 0) {
            throw new IllegalArgumentException("Feld bereits belegt.");
        }
        if (gameEnded) {
            throw new IllegalArgumentException("Spiel ist bereits beendet.");
        }

        board[row][col] = currentPlayer;
        logger.info("{} hat einen Zug bei ({}, {}) gemacht.", getCurrentPlayerName(), row, col);
    }

    // Spiel zurücksetzen
    public void resetGame() {
        board = new int[3][3];
        gameEnded = false;
        winner = "";
        currentPlayer = 1;
        logger.info("Spiel zurückgesetzt.");
    }
}
