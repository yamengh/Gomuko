package com.yamen.gomoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the GameBoard class.
 * 
 * @author Yamen Hammoud
 */
class GameBoardTest {
    private GameBoard board;
    private static final int ROWS = 10;
    private static final int COLS = 10;

    @BeforeEach
    void setUp() {
        board = new GameBoard(ROWS, COLS);
    }

    @Test
    void testInitialBoardIsEmpty() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                assertEquals('.', board.getCell(i, j), "Board should be initialized with all cells empty");
            }
        }
    }

    @Test
    void testMakeValidMove() {
        assertTrue(board.makeMove(0, 0, 1), "Should be able to make a valid move");
        assertEquals('X', board.getCell(0, 0), "Cell should contain player 1's mark");
        
        assertTrue(board.makeMove(1, 1, 2), "Should be able to make another valid move");
        assertEquals('O', board.getCell(1, 1), "Cell should contain player 2's mark");
    }

    @Test
    void testMakeInvalidMove() {
        // Make a valid move first
        assertTrue(board.makeMove(0, 0, 1));
        
        // Try to make the same move again
        assertFalse(board.makeMove(0, 0, 1), "Should not be able to make a move on an occupied cell");
        
        // Try to make a move outside the board
        assertFalse(board.makeMove(-1, 0, 1), "Should not be able to make a move with negative row");
        assertFalse(board.makeMove(0, -1, 1), "Should not be able to make a move with negative column");
        assertFalse(board.makeMove(ROWS, 0, 1), "Should not be able to make a move outside row bounds");
        assertFalse(board.makeMove(0, COLS, 1), "Should not be able to make a move outside column bounds");
    }

    @Test
    void testCheckWinHorizontal() {
        // Player 1 makes a horizontal line
        board.makeMove(0, 0, 1);
        board.makeMove(0, 1, 1);
        board.makeMove(0, 2, 1);
        board.makeMove(0, 3, 1);
        assertEquals(0, board.checkWin(0, 3), "Should not win with only 4 in a row");
        
        board.makeMove(0, 4, 1);
        assertEquals(1, board.checkWin(0, 4), "Player 1 should win with 5 in a horizontal line");
    }

    @Test
    void testCheckWinVertical() {
        // Player 2 makes a vertical line
        board.makeMove(0, 0, 2);
        board.makeMove(1, 0, 2);
        board.makeMove(2, 0, 2);
        board.makeMove(3, 0, 2);
        assertEquals(0, board.checkWin(3, 0), "Should not win with only 4 in a column");
        
        board.makeMove(4, 0, 2);
        assertEquals(2, board.checkWin(4, 0), "Player 2 should win with 5 in a vertical line");
    }

    @Test
    void testCheckWinDiagonal() {
        // Player 1 makes a diagonal line (top-left to bottom-right)
        board.makeMove(0, 0, 1);
        board.makeMove(1, 1, 1);
        board.makeMove(2, 2, 1);
        board.makeMove(3, 3, 1);
        assertEquals(0, board.checkWin(3, 3), "Should not win with only 4 in a diagonal");
        
        board.makeMove(4, 4, 1);
        assertEquals(1, board.checkWin(4, 4), "Player 1 should win with 5 in a diagonal line");
        
        // Player 2 makes a diagonal line (top-right to bottom-left)
        board = new GameBoard(ROWS, COLS); // Reset board
        board.makeMove(0, 4, 2);
        board.makeMove(1, 3, 2);
        board.makeMove(2, 2, 2);
        board.makeMove(3, 1, 2);
        assertEquals(0, board.checkWin(3, 1), "Should not win with only 4 in a diagonal");
        
        board.makeMove(4, 0, 2);
        assertEquals(2, board.checkWin(4, 0), "Player 2 should win with 5 in a diagonal line");
    }

    @Test
    void testIsBoardFull() {
        // Fill the board with alternating players
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                int player = (i + j) % 2 == 0 ? 1 : 2;
                board.makeMove(i, j, player);
            }
        }
        
        assertTrue(board.isBoardFull(), "Board should be full when all cells are occupied");
    }
}
