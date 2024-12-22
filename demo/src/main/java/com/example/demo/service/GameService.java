//package com.example.demo.service;
//
//import com.example.demo.exception.InvalidMoveException;
//import com.example.demo.model.TicTacToeGame;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//@Service
//public class GameService {
//
//    private static final Logger logger = LoggerFactory.getLogger(GameService.class);
//
//    // Spiellogik wird hier gespeichert
//    private TicTacToeGame game = new TicTacToeGame();
//
//    // Neues Spiel starten mit Spielernamen
//    public TicTacToeGame startNewGame(String player1Name, String player2Name) {
//        game = new TicTacToeGame(); // Neues Spiel initialisieren
//        game.setPlayer1Name(player1Name);
//        game.setPlayer2Name(player2Name);
//        logger.info("Neues Spiel gestartet: {} gegen {}", player1Name, player2Name);
//        return game;
//    }
//
//    // Spielzug durchführen
//    public TicTacToeGame makeMove(int row, int col, String playerName) {
//        if (game.isGameEnded()) {
//            throw new InvalidMoveException("Das Spiel ist bereits beendet.");
//        }
//
//        // Feld prüfen
//        if (game.getBoard()[row][col] != 0) {
//            throw new InvalidMoveException("Dieses Feld ist bereits besetzt.");
//        }
//
//        // Richtiger Spieler dran?
//        if (!game.getCurrentPlayerName().equals(playerName)) {
//            throw new InvalidMoveException("Nicht dein Zug, warte auf deinen Gegner.");
//        }
//
//        // Zug ausführen
//        game.getBoard()[row][col] = game.getCurrentPlayer();
//        logger.info("{} hat einen Zug gemacht auf ({}, {})", playerName, row, col);
//
//        // Gewinn- oder Unentschieden-Prüfung
//        if (checkWin(game.getCurrentPlayer())) {
//            game.setWinner(game.getCurrentPlayerName() + " hat gewonnen!");
//            game.setGameEnded(true);
//            logger.info("Spiel beendet: {}", game.getWinner());
//        } else if (isBoardFull()) {
//            game.setWinner("Unentschieden!");
//            game.setGameEnded(true);
//            logger.info("Unentschieden! Spiel endet.");
//        } else {
//            // Nächster Spieler
//            int next = (game.getCurrentPlayer() == 1) ? 2 : 1;
//            game.setCurrentPlayer(next);
//            logger.info("Nächster Spieler: {}", game.getCurrentPlayerName());
//        }
//
//        return game;
//    }
//
//    // Aktuellen Spielstatus abrufen
//    public TicTacToeGame getGameState() {
//        return game;
//    }
//
//    // Spiel zurücksetzen
//    public TicTacToeGame resetGame() {
//        String player1 = game.getPlayer1Name();
//        String player2 = game.getPlayer2Name();
//        game.resetGame();
//        game.setPlayer1Name(player1);
//        game.setPlayer2Name(player2);
//        logger.info("Spiel wurde zurückgesetzt. (Spieler1: {}, Spieler2: {})", player1, player2);
//        return game;
//    }
//
//    // Prüfen, ob ein Spieler gewonnen hat
//    private boolean checkWin(int player) {
//        int[][] board = game.getBoard();
//
//        // Zeilen und Spalten prüfen
//        for (int i = 0; i < 3; i++) {
//            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
//                    (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
//                return true;
//            }
//        }
//
//        // Diagonalen prüfen
//        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
//                (board[0][2] == player && board[1][1] == player && board[2][0] == player);
//    }
//
//    // Prüfen, ob das Spielfeld voll ist (kein Feld = 0)
//    private boolean isBoardFull() {
//        for (int[] row : game.getBoard()) {
//            for (int cell : row) {
//                if (cell == 0) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//}
