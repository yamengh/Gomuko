package com.yamen.gomoku;

import java.util.Arrays;

/**
 * Represents the game board for Gomoku.
 * 
 * @author Yamen Hammoud
 */
public class GameBoard {
    private final int rows;
    private final int cols;
    private final char[][] board;
    private static final char EMPTY = '.';
    private static final char PLAYER = 'X';
    private static final char COMPUTER = 'O';

    /**
     * Creates a new game board with the specified dimensions.
     * 
     * @param rows number of rows
     * @param cols number of columns
     */
    public GameBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new char[rows][cols];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            Arrays.fill(board[i], EMPTY);
        }
    }

    /**
     * Places a move on the board.
     * 
     * @param row the row index (0-based)
     * @param col the column index (0-based)
     * @param symbol the player's symbol ('X' or 'O')
     * @return true if the move was valid and made, false otherwise
     */
    public boolean makeMove(int row, int col, char symbol) {
        if (row < 0 || row >= rows || col < 0 || col >= cols || board[row][col] != EMPTY) {
            return false;
        }
        board[row][col] = symbol;
        return true;
    }
    
    /**
     * Prints the current state of the board.
     */
    public void printBoard() {
        System.out.print("  ");
        for (int j = 0; j < cols; j++) {
            System.out.print((j + 1) + " ");
        }
        System.out.println();
        
        for (int i = 0; i < rows; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Checks if the specified player has won the game.
     * 
     * @param playerSymbol the player's symbol ('X' or 'O')
     * @return true if the player has won, false otherwise
     */
    public boolean checkWin(char playerSymbol) {
        // Check all possible 5-in-a-row sequences
        return checkHorizontal(playerSymbol) || 
               checkVertical(playerSymbol) || 
               checkDiagonal(playerSymbol);
    }
    
    private boolean checkHorizontal(char playerSymbol) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <= cols - 5; j++) {
                boolean win = true;
                for (int k = 0; k < 5; k++) {
                    if (board[i][j + k] != playerSymbol) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }
        return false;
    }
    
    private boolean checkVertical(char playerSymbol) {
        for (int i = 0; i <= rows - 5; i++) {
            for (int j = 0; j < cols; j++) {
                boolean win = true;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j] != playerSymbol) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }
        return false;
    }
    
    private boolean checkDiagonal(char playerSymbol) {
        // Check diagonal \
        for (int i = 0; i <= rows - 5; i++) {
            for (int j = 0; j <= cols - 5; j++) {
                boolean win = true;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j + k] != playerSymbol) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }
        
        // Check diagonal /
        for (int i = 4; i < rows; i++) {
            for (int j = 0; j <= cols - 5; j++) {
                boolean win = true;
                for (int k = 0; k < 5; k++) {
                    if (board[i - k][j + k] != playerSymbol) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }
        return false;
    }

    private int checkDirection(int row, int col, int dRow, int dCol, char player) {
        int count = 1; // count the current position
        count += countInDirection(row, col, dRow, dCol, player);
        count += countInDirection(row, col, -dRow, -dCol, player);
        return count;
    }

    private int countInDirection(int row, int col, int dRow, int dCol, char player) {
        int count = 0;
        int r = row + dRow;
        int c = col + dCol;
        while (r >= 0 && r < rows && c >= 0 && c < cols && board[r][c] == player) {
            count++;
            r += dRow;
            c += dCol;
        }
        return count;
    }

    /**
     * Checks if the board is full.
     * 
     * @return true if the board is full, false otherwise
     */
    public boolean isBoardFull() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Gets the current state of the board.
     * 
     * @return 2D array representing the board
     */
    public char[][] getBoard() {
        char[][] copy = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, cols);
        }
        return copy;
    }

    /**
     * Gets the character at the specified position.
     * 
     * @param row the row index
     * @param col the column index
     * @return the character at the specified position
     */
    public char getCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return ' ';
        }
        return board[row][col];
    }

    /**
     * Gets the number of rows in the board.
     * 
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the number of columns in the board.
     * 
     * @return the number of columns
     */
    public int getCols() {
        return cols;
    }
}
