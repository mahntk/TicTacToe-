package com.example.demo.controller;

import com.example.demo.dto.JoinGameRequest;
import com.example.demo.dto.MoveRequest;
import com.example.demo.dto.StartGameRequest;
import com.example.demo.exception.GameNotFoundException;
import com.example.demo.exception.InvalidMoveException;
import com.example.demo.model.TicTacToeGame;
import com.example.demo.service.GameManager;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameManager gameManager;

    public GameController(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @PostMapping("/start")
    public ResponseEntity<?> startGame(@RequestBody StartGameRequest request) {
        if (request.getPlayer1Name() == null || request.getPlayer1Name().isEmpty()) {
            return ResponseEntity.badRequest().body("player1Name darf nicht leer sein🤦‍♂️.");
        }
        String gameId = gameManager.createGame(request.getPlayer1Name());
        return ResponseEntity.ok().body(
                new ResponseMessage("🎉Spiel erfolgreich erstellt!🎉", gameId)
        );
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinGame(@RequestBody JoinGameRequest request) {
        if (request.getGameId() == null || request.getGameId().isEmpty()) {
            return ResponseEntity.badRequest().body("gameId darf nicht leer sein.");
        }
        if (request.getPlayerName() == null || request.getPlayerName().isEmpty()) {
            return ResponseEntity.badRequest().body("playerName darf nicht leer sein.");
        }
        try {
            String message = gameManager.joinGame(request.getGameId(), request.getPlayerName());
            return ResponseEntity.ok(message);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/move")
    public ResponseEntity<?> makeMove(@RequestBody MoveRequest request) {
        try {
            TicTacToeGame updatedGame = gameManager.makeMove(
                    request.getGameId(),
                    request.getPlayerName(),
                    request.getRow(),
                    request.getCol()
            );
            return ResponseEntity.ok(updatedGame);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (InvalidMoveException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/state/{gameId}")
    public ResponseEntity<?> getGameState(@PathVariable String gameId) {
        try {
            TicTacToeGame game = gameManager.getGameState(gameId);
            return ResponseEntity.ok(game);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/reset/{gameId}")
    public ResponseEntity<?> resetGame(@PathVariable String gameId) {
        try {
            String msg = gameManager.resetGame(gameId);
            return ResponseEntity.ok(msg);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<?> deleteGame(@PathVariable String gameId) {
        try {
            String msg = gameManager.deleteGame(gameId);
            return ResponseEntity.ok(msg);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Beispiel für eine einfache Antwort-Struktur
    public static class ResponseMessage {
        private String message;
        private String gameId;

        public ResponseMessage(String message, String gameId) {
            this.message = message;
            this.gameId = gameId;
        }

        public String getMessage() {
            return message;
        }

        public String getGameId() {
            return gameId;
        }
    }
}
