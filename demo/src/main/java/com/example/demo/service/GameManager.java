package com.example.demo.service;

import com.example.demo.exception.GameNotFoundException;
import com.example.demo.exception.InvalidMoveException;
import com.example.demo.model.TicTacToeGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameManager {

    private static final Logger logger = LoggerFactory.getLogger(GameManager.class);

    private final Map<String, TicTacToeGame> games = new ConcurrentHashMap<>();

    /**
     * Erzeugt eine 5-stellige Game-ID (z.B. "53842"), die noch nicht in 'games' existiert.
     * Damit ist sichergestellt, dass die ID nicht doppelt vergeben wird.
     */
    private String generate5DigitGameId() {
        String gameId;
        do {
            // Zufallszahl zwischen 10000 und 99999
            int randomNum = (int) (Math.random() * 90000) + 10000;
            gameId = String.valueOf(randomNum);
        } while (games.containsKey(gameId));
        return gameId;
    }

    public String createGame(String player1Name) {
        // Anstelle von UUID jetzt eine 5-stellige ID generieren
        String gameId = generate5DigitGameId();

        TicTacToeGame game = new TicTacToeGame();
        game.setPlayer1Name(player1Name);
        games.put(gameId, game);

        logger.info("Neues Spiel erstellt mit ID: {}", gameId);
        return gameId;
    }

    public String joinGame(String gameId, String playerName) {
        TicTacToeGame game = games.get(gameId);
        if (game == null) {
            throw new GameNotFoundException("Spiel mit dem Schlüssel " + gameId + " wurde nicht gefunden.");
        }

        // Prüfen, ob der Spielername bereits als Player 1 existiert
        if (playerName.equals(game.getPlayer1Name())) {
            throw new IllegalArgumentException("Du bist bereits Spieler 1 in diesem Spiel. Du kannst nicht als Spieler 2 beitreten!⛔");
        }

        // Spieler 2 zuweisen, wenn Slot frei
        if (game.getPlayer2Name() == null || game.getPlayer2Name().isEmpty()) {
            game.setPlayer2Name(playerName);
            logger.info("Spieler 2 '{}' ist dem Spiel {} beigetreten.", playerName, gameId);
            return "🎉 Erfolg! Du bist dem Spiel (ID: " + gameId + ") als Spieler 2 beigetreten! 🎉";
        } else {
            throw new IllegalArgumentException("Spiel ist bereits voll. Es sind schon 2 Spieler in diesem Spiel!⛔");
        }
    }


    public TicTacToeGame makeMove(String gameId, String playerName, int row, int col) {
        TicTacToeGame game = games.get(gameId);
        if (game == null) {
            throw new GameNotFoundException("Spiel nicht gefunden: " + gameId);
        }
        if (game.isGameEnded()) {
            throw new InvalidMoveException("Das Spiel ist bereits beendet.");
        }
        if (!game.getCurrentPlayerName().equals(playerName)) {
            throw new InvalidMoveException("Nicht dein Zug!❌");
        }

        // Den Zug direkt auf dem Model machen
        game.makeMove(row, col);

        // Gewinn oder Unentschieden prüfen
        if (checkWin(game.getBoard(), game.getCurrentPlayer())) {
            game.setGameEnded(true);
            game.setWinner(playerName);
            logger.info("Spiel {} beendet. Gewinner: {}🍾🍾", gameId, playerName);
        } else if (isBoardFull(game.getBoard())) {
            game.setGameEnded(true);
            game.setWinner("Unentschieden");
            logger.info("Spiel {} endet unentschieden.😒", gameId);
        } else {
            // Nächster Spieler
            int nextPlayer = (game.getCurrentPlayer() == 1) ? 2 : 1;
            game.setCurrentPlayer(nextPlayer);
            logger.info("Nächster Spieler ist '{}'.", game.getCurrentPlayerName());
        }

        return game;
    }

    public TicTacToeGame getGameState(String gameId) {
        TicTacToeGame game = games.get(gameId);
        if (game == null) {
            throw new GameNotFoundException("Spiel nicht gefunden: " + gameId);
        }
        return game;
    }

    public String resetGame(String gameId) {
        TicTacToeGame game = games.get(gameId);
        if (game == null) {
            throw new GameNotFoundException("Spiel nicht gefunden: " + gameId);
        }
        String p1 = game.getPlayer1Name();
        String p2 = game.getPlayer2Name();
        game.resetGame();
        game.setPlayer1Name(p1);
        game.setPlayer2Name(p2);
        return "Spiel wurde zurückgesetzt!";
    }

    public String deleteGame(String gameId) {
        if (games.remove(gameId) != null) {
            logger.info("Spiel {} wurde gelöscht.", gameId);
            return "Spiel erfolgreich gelöscht.";
        } else {
            throw new GameNotFoundException("Spiel nicht gefunden: " + gameId);
        }
    }

    // Hilfsfunktionen für Win/Draw
    private boolean checkWin(int[][] board, int player) {
        // Reihen und Spalten
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                    (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        // Diagonalen
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player)
                || (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    private boolean isBoardFull(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
