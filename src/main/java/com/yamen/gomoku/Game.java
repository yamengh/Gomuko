package com.yamen.gomoku;

import java.util.Random;
import java.util.Scanner;

/**
 * Manages the Gomoku game flow and logic.
 * 
 * @author Yamen Hammoud
 */
public class Game {
    private final GameBoard board;
    private final Random random;
    private final Scanner scanner;
    private static final int BOARD_SIZE = 6;
    private static final char PLAYER_SYMBOL = 'X';
    private static final char COMPUTER_SYMBOL = 'O';

    /**
     * Initializes a new Gomoku game with a 6x6 board.
     */
    public Game() {
        this.board = new GameBoard(BOARD_SIZE, BOARD_SIZE);
        this.random = new Random();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the game loop.
     */
    public void start() {
        System.out.println("Welcome to Gomoku!");
        System.out.println("You are X, computer is O");
        board.printBoard();

        while (true) {
            // Player's turn
            playerMove();
            board.printBoard();
            
            if (checkGameOver(PLAYER_SYMBOL)) {
                System.out.println("Congratulations! You win!");
                break;
            }
            
            if (board.isBoardFull()) {
                System.out.println("It's a draw!");
                break;
            }

            // Computer's turn
            System.out.println("\nComputer's turn...");
            computerMove();
            board.printBoard();
            
            if (checkGameOver(COMPUTER_SYMBOL)) {
                System.out.println("Computer wins! Better luck next time!");
                break;
            }
            
            if (board.isBoardFull()) {
                System.out.println("It's a draw!");
                break;
            }
        }
        
        scanner.close();
    }

    /**
     * Handles the player's move.
     */
    private void playerMove() {
        while (true) {
            try {
                System.out.print("\nEnter row (1-6) and column (1-6) separated by space: ");
                int row = scanner.nextInt() - 1;
                int col = scanner.nextInt() - 1;
                
                if (board.makeMove(row, col, PLAYER_SYMBOL)) {
                    break;
                } else {
                    System.out.println("Invalid move. Try again!");
                }
            } catch (Exception e) {
                System.out.println("Please enter valid numbers!");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    /**
     * Handles the computer's move.
     */
    private void computerMove() {
        while (true) {
            int row = random.nextInt(BOARD_SIZE);
            int col = random.nextInt(BOARD_SIZE);
            
            if (board.makeMove(row, col, COMPUTER_SYMBOL)) {
                System.out.println("Computer placed at: (" + (row + 1) + ", " + (col + 1) + ")");
                break;
            }
        }
    }

    /**
     * Checks if the game is over.
     * 
     * @param playerSymbol the symbol to check for win
     * @return true if the game is over, false otherwise
     */
    private boolean checkGameOver(char playerSymbol) {
        return board.checkWin(playerSymbol);
    }
}
