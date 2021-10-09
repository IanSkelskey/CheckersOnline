/**
 * Class: CheckersTextConsole
 * Contains the user interface for a console, text-based, implementation of
 * the classic table-top game, checkers.
 * 
 * @author Ian Skelskey
 * @version 2.0
 * @since 2021-09-08
 */
/* UI Package to contain interface logic. */
package ui;

//Imports
import core.*;
import java.util.Scanner;
import javafx.application.Application;

public class CheckersTextConsole {
	private static CheckersLogic game = new CheckersLogic();
	private static boolean debug;
	private static boolean isSinglePlayer = true;
	private static String input;

	/**
	 * Contains UI and creates a CheckersLogic object to contain the game.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (chooseInterface() == "GUI") {
			Application.launch(CheckersClient.class);
		} else {
			consoleGameLoop();
		}
	}

	public static void consoleGameLoop() {
		do { // Main Game Loop
			Scanner scan = new Scanner(System.in);
			// Start a new game
			game = new CheckersLogic();
			// Print Board and Axes
			System.out.print(game.getBoard());
			// One or Two Player Mode?
			chooseGameMode();

			// Welcome Message
			System.out.println("Begin Game. Player " + game.getActivePlayer() + " - your turn.");
			CheckersComputerPlayer cpu1 = null;
			CheckersComputerPlayer cpu2 = null;

			do { // Turn Loop
				if (isSinglePlayer) {
					cpu1 = new CheckersComputerPlayer('o', game.getBoard());
				}
				if (debug) {
					cpu1 = new CheckersComputerPlayer('o', game.getBoard());
					cpu2 = new CheckersComputerPlayer('x', game.getBoard());
					if (game.getActivePlayer().getPiece() == 'o') {
						input = cpu1.nextMove();
					} else {
						input = cpu2.nextMove();
					}
				} else if (!isSinglePlayer || game.getActivePlayer().getPiece() == 'x') {// multi-player game
					input = scan.next();
				} else { // single player game
					input = cpu1.nextMove();
				}

				// Pass input to game logic class
				game.passInput(input);
				// If format is valid, proceed.
				if (game.validateInput() && game.validateMove()) {
					game.turn();
					System.out.print(game.getBoard());
					System.out.println("Player " + game.getActivePlayer() + " - your turn.");
					game.clearInput();
				}
			} while (!game.isOver());

			System.out.println("Game is over.");
			System.out.println("New game starting...");
			scan.close();
		} while (true);
	}

	public static String chooseInterface() {
		String UI = "";
		boolean select = false;
		do {
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter ‘G’ for JavaFX GUI or ’T’ for text UI:");
			char selection = scan.next().charAt(0);
			switch (selection) {
			case 'G':
				select = true;
				UI = "GUI";
				break;
			case 'T':
				select = true;
				UI = "Text UI";
				break;
			default:
				System.out.println("Invalid Entry!\n");
				break;
			}
		} while (!select);
		return UI;
	}

	public static void chooseGameMode() {
		Scanner scan = new Scanner(System.in);
		String input;
		boolean badChoice = true;
		System.out.println(
				"Begin Game. Enter ‘P’ if you want to play against another player; enter ‘C’ to play against computer.");
		do {
			input = scan.next();
			if ("p".equals(input.toLowerCase())) {
				isSinglePlayer = false;
				badChoice = false;
			} else if ("c".equals(input.toLowerCase())) {
				isSinglePlayer = true;
				badChoice = false;
			} else if (input.toLowerCase().contentEquals("debug")) {
				debug = true;
				badChoice = false;
			} else {
				System.out.println("Please enter a 'p' or a 'c' only.");
			}
		} while (badChoice);
		// scan.close();
	}

	/**
	 * Prints the current board state to the console.
	 * 
	 * @param board
	 */
	public static void printBoard(char[][] board) {
		for (int i = 0; i < 8; i++) {
			System.out.print(8 - i);
			for (int j = 0; j < 8; j++) {
				System.out.print("|" + board[i][j]);
			}
			System.out.print("|\n");
		}
		System.out.print("  a b c d e f g h\n");
	}
}