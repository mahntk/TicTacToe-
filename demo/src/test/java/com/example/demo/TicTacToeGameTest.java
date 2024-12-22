package com.example.demo;

import com.example.demo.model.TicTacToeGame;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeGameTest {

    @Test
    public void testInitialization() {
        TicTacToeGame game = new TicTacToeGame();
        assertNotNull(game.getBoard());
        assertEquals(1, game.getCurrentPlayer());
        assertFalse(game.isGameEnded());
        assertEquals("", game.getWinner());
    }

    @Test
    public void testSetAndGetBoard() {
        TicTacToeGame game = new TicTacToeGame();
        int[][] newBoard = {
                {1, 2, 0},
                {0, 1, 0},
                {2, 0, 1}
        };
        game.setBoard(newBoard);
        assertArrayEquals(newBoard, game.getBoard());
    }

    @Test
    public void testSetAndGetCurrentPlayer() {
        TicTacToeGame game = new TicTacToeGame();
        game.setCurrentPlayer((byte) 2);
        assertEquals(2, game.getCurrentPlayer());
    }

    @Test
    public void testGameEndAndWinner() {
        TicTacToeGame game = new TicTacToeGame();
        game.setGameEnded(true);
        game.setWinner("Player 1");
        assertTrue(game.isGameEnded());
        assertEquals("Player 1", game.getWinner());
    }
}
