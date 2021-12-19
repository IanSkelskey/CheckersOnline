package ui;

import java.util.Scanner;

import core.CheckersComputerPlayer;
import core.CheckersLogic;
import core.CheckersLogic.CheckersMove;

/**
 * @since 29SEP2021
 * @author Neil
 * @version Week 6
 */
public class CheckersTextConsole {
	
	public static final char EMPTY = '_';
	public static final char RED = 'o';
	public static final char RED_KING = 'O';
	public static final char BLACK = 'x';
	public static final char BLACK_KING = 'X';
	
	private static int blackCheckers = 12;
	private static int redCheckers = 12;
	private static boolean gameOver = false;
	private static boolean redTurn = true;
	private static boolean blackTurn = false;
	private static boolean moveValid = true;
	private static char gamemode;
	private static boolean gameInProgress = true;
	
	public static char[][] board;  // board[r][c] is the contents of row r, column c.  

	static Scanner scan = new Scanner(System.in);
	/**
	 * main for debugging purposes. To be commented out or 
	 * overrun by starting main from CheckersGui.java.
	 * @param args Main Method.
	 */
	public static void main(String[] args) {
		mainText();
	}
	/**
	 * 	
	 */
	public static void mainText() {
		System.out.println("You have chosen to play on the Text Console!");
		System.out.println("Let's play a game of checkers. \n");
		printGameModes();
		if ((getGamemode() == 'P' || getGamemode() == 'C')) {
		CheckersTextConsole newgame = new CheckersTextConsole();
			
		} else {
		invalidGameMode();
		return;
		}
		
	}
	/**
	 * 
	 * @return
	 */
	public static boolean isGameInProgress() {
		return gameInProgress;
	}
	/**
	 * 
	 * @param gameInProgress
	 */
	public static void setGameInProgress(boolean gameInProgress) {
		CheckersTextConsole.gameInProgress = gameInProgress;
	}
	/**
	 * 
	 * @return
	 */
	public static char getGamemode() {
		return gamemode;
	}
	/**
	 * 
	 * @param gamemode
	 */
	public static void setGamemode(char gamemode) {
		CheckersTextConsole.gamemode = gamemode;
	}
	/**
	 * 
	 */
	public CheckersTextConsole() {
		    board = new char[8][8];
		    setUpGame();
		    printBoard();
		    runningGame();
	}

	/**
	 * 
	 * @return char for starting game mode from user input
	 */
	public static void printGameModes() {
		String mode = "";
		char P = 'P';
		char C = 'C';
		while (mode == "") {
			System.out.println("Begin Game. Enter 'P' if you want to play against another player; \r\n" 
								+ "enter 'C' to play against a computer.");
			mode = scan.nextLine();
			if (mode.charAt(0) == P || mode.charAt(0) == C) {
				setGamemode(mode.charAt(0));
				return;
			}
			else 
				System.out.println("That was not an option. Try again");
				mode = "";
		}
		return;
	}
	
	/**
	 * 
	 */
	public static void printBoard() {
		char letter = 97;
		char row = 49;
		
		for (int x = 0; x < 8; x++) {
			System.out.print(row);
			row++;
			for (int y = 0; y < 8; y++) {
				System.out.print (" | " + board[x][y]);
			}
			System.out.println(" |");
		}
		System.out.printf("    ");
		for(int x = 0; x < 8; x++) {
			System.out.print(letter + "   ");
			letter++;
			
		}
		System.out.println();
	}
	/**
	 * 
	 */
	public void setUpGame() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if ( row % 2 == col % 2 ) {
					if (row < 3)
						board[row][col] = BLACK;
					else if (row > 4)
						board[row][col] = RED;
					else
						board[row][col] = EMPTY;
					}
				else {
					board[row][col] = EMPTY;
				}
			}
		}
	}
	private static void runningGame() {
		if (getGamemode() == 'P') {
			while (gameOver == false) {
				while (redTurn == true) {
					redTurnText();
					redTurn();
					if(moveValid == true) {
						printBoard();
						blackTurn = true;
						redTurn = false;
					}
					checkWIN();
				}
				while (blackTurn == true) {
					blackTurnText();
					blackTurn();
					if(moveValid == true) {
						printBoard();
						redTurn = true;
						blackTurn = false;
					}
					checkWIN();
				}
			}
			if (gameOver == true) {
				if (redCheckers == 0) 
					printGameOver(BLACK);
				if (blackCheckers == 0)
					printGameOver(RED);
				else
					checkWIN();
			}
		}
		if (getGamemode() == 'C') {
			while (gameOver == false) {
				while (redTurn == true) {
					redTurnText();
					redTurn();
					if(moveValid == true) {
						printBoard();
						blackTurn = true;
						redTurn = false;
					}
					checkWIN();
				}
				while (blackTurn == true) {
					blackTurnText();
					CheckersComputerPlayer.blackTurn();
					if(moveValid == true) {
						printBoard();
						redTurn = true;
						blackTurn = false;
					}
					checkWIN();
				}
			}
			if (gameOver == true) {
				if (redCheckers == 0) 
					printGameOver(BLACK);
				if (blackCheckers == 0)
					printGameOver(RED);
				else
					checkWIN();
			}
		}
	}
	/**
	 * 
	 * @return returns gameOver boolean to stop runningGame()
	 */
	protected static boolean checkWIN() {
		if (blackCheckers == 0 && redCheckers != 0) {
			gameOver = true;
			return gameOver;
		} else if (blackCheckers !=0 && redCheckers == 0) {
			gameOver = true;
			return gameOver;
		} else
			gameOver = false;
			return gameOver;
	}
	/**
	 * 
	 * @throws StringIndexOutOfBoundsException catch user error
	 */
	protected static void redTurn() throws StringIndexOutOfBoundsException {
		try {String input = scan.nextLine();
			int r1 = input.charAt(0);
			int c1 = input.charAt(1);
			int r2 = input.charAt(3);
			int c2 = input.charAt(4);
			checkMove(r1,c1,r2,c2, board, false);
			return; 
		}
		catch(StringIndexOutOfBoundsException s) {
			invalidMove(s);
			setMoveValid(false);
			return;
		}
	}
	/**
	 * 
	 * @throws StringIndexOutOfBoundsException catch user error
	 */
	protected static void blackTurn() throws StringIndexOutOfBoundsException {
		try {String input = scan.nextLine();
			int r1 = input.charAt(0);
			int c1 = input.charAt(1);
			int r2 = input.charAt(3);
			int c2 = input.charAt(4);
			checkMove(r1,c1,r2,c2, board, true);
			return; 
		}
		catch(StringIndexOutOfBoundsException s) {
			CheckersTextConsole.invalidMove(s);
			setMoveValid(false);
			return;
		}
	}
	
	/**
	 * 
	 * @param r1 coordinate corrected
	 * @param c1 coordinate corrected
	 * @param r2 coordinate corrected
	 * @param c2 coordinate corrected
	 * @param board2 gameBoard[][]
	 * @return boolean inBounds if inBounds continue, if not exception thrown, caught, and handled.
	 * @throws ArrayIndexOutOfBoundsException To prevent player from choosing spaces out of bounds.
	 */
	private static boolean inBounds(int r1, int c1, int r2, int c2, char[][] board2) throws ArrayIndexOutOfBoundsException {
		boolean inBounds = true;
		try {board2[r1][c1] = board2[r1][c1];
			 board2[r2][c2] = board2[r2][c2];}
		
		catch(ArrayIndexOutOfBoundsException exception) {
					outOfBounds(exception);
					return inBounds = false;
		}
		return inBounds;
	}
	
	/**
	 * 
	 * @param e exception Passes in exception
	 * {@link #invalidMove(ArrayIndexOutOfBoundsException)}
	 */
	private static void outOfBounds(ArrayIndexOutOfBoundsException e) {
		CheckersTextConsole.invalidMove(e);
	}
	
	/**
	 * 
	 * @param r1 row input 1
	 * @param c1 coloumn input 1
	 * @param r2 row input 2
	 * @param c2 coloumn input 2
	 * @param board2 2darray of current gameBoard
	 * @param black boolean for player turn
	 * 
	 * {@link #invalidMove(ArrayIndexOutOfBoundsException)}
	 */
	public static void checkMove(int r1, int c1, int r2, int c2, char[][] board2, boolean black) throws ArrayIndexOutOfBoundsException {
		setMoveValid(true);
		int moves = 1;
		r1 = r1 - 49;
		c1 = c1 - 97;
		r2 = r2 - 49;
		c2 = c2 - 97;
		boolean inBounds = inBounds(r1, c1, r2, c2, board2);
		if (inBounds == false) {
			setMoveValid(false);
			return;
		}
		//Checks if final coordinate is equal to starting coordinate
		if (board2[r1][c1] == board2[r2][c2]) {
			invalidMove();
			setMoveValid(false);
			return;
		}
		while (black == true) {
			if (moveValid == true && black == true) {
				//Chech for invalid start coordinate
				if (board2[r1][c1] == 'o' || board2[r1][c1] == '_') {
					invalidMove();
					setMoveValid(false);
					return;
				}
				while (board2[r1][c1] == 'x') {
					try{
						switch (moves) {
							case 5 : invalidMove(); return;
							//Normal Move
							case 4 : if (board2[r2][c2] == '_' 
										&& (board2[r2][c2] == board2[r1 + 1][c1 + 1])
										|| (board2[r2][c2] == board2[r1 + 1][c1 - 1])) {
											setMoveValid(true);
											moveChecker(r1,c1,r2,c2, board2);
											return;
									} else {
										break;
									}
								//Diagonal Down-Left Jump
								case 3 : if (board2[r2][c2] == '_'
												&& (board2[r2 - 1][c2 + 1] == 'o')
												&& (board2[r2 - 2][c2 + 2] == board2[r1][c1])) {
													setMoveValid(true);
													int r3 = r2 - 1;
													int c3 = c2 + 1;
													jumpChecker(r1,c1,r2,c2,r3,c3, board2);
													return;
										} else {
												break;
								}
								//Diagonal Down-Right Jump
								case 2 : if (board2[r2][c2] == '_'
												&& (board2[r2 - 1][c2 - 1] == 'o')
												&& (board2[r2 - 2][c2 - 2] == board2[r1][c1])) {
													setMoveValid(true);
													int r3 = r2 - 1;
													int c3 = c2 - 1;
													jumpChecker(r1,c1,r2,c2,r3,c3, board2);
													return;
										} else {
												break;
								}
								case 1: if (board2[r2][c2] == 'o' || board2[r2][c2] == 'x') {
												invalidMove();
												setMoveValid(false);
												return;
										} else {
												break;
										}
							} moves++;
						}catch(ArrayIndexOutOfBoundsException e) {moves++;}
					}
				}
			}
			while (black == false) {
				if (moveValid == true && black == false) {
					//Chech for invalid start coordinate
					if (board2[r1][c1] == 'x' || board2[r1][c1] == '_') {
						invalidMove();
						setMoveValid(false);
						return;
					}
					while (board2[r1][c1] == 'o') {
					try{
						switch (moves) {
							case 5 : invalidMove(); return;
							//Normal Move
							case 4 : if (board2[r2][c2] == '_' 
										&& (board2[r2][c2] == board2[r1 - 1][c1 - 1]) 
										|| (board2[r2][c2] == board2[r1 - 1][c1 + 1])) {
											setMoveValid(true);
											moveChecker(r1,c1,r2,c2, board2);
											return;
									} else {
											break;
							}
							///Why does it call invalid when it isnt suppose to jump
							//Diagonal Up-Left Jump
							case 3: if (board2[r2][c2] == '_'
									   	&& (board2[r2 + 1][c2 + 1] == 'x')
										&& (board2[r2 + 2][c2 + 2] == board2[r1][c1]))  {
											setMoveValid(true);
											int r3 = r2 + 1;
											int c3 = c2 + 1;
											jumpChecker(r1,c1,r2,c2,r3,c3, board2);
											return;
									} else {
											break;
							}
							//Diagonal Up-Right Jump
							case 2: if (board2[r2][c2] == '_'
										&& (board2[r2 + 1][c2 - 1] == 'x')
										&& (board2[r2 + 2][c2 - 2] == board2[r1][c1])) {
												setMoveValid(true);
												int r3 = r2 + 1;
												int c3 = c2 - 1;
												jumpChecker(r1,c1,r2,c2,r3,c3, board2);
												return;
									} else {
											break;
							}
							case 1: if (board2[r2][c2] == 'x' || board2[r2][c2] == 'o') {
										invalidMove();
										setMoveValid(false);
										return;
									} else {
											break;
									}
							} moves++;
						}catch(ArrayIndexOutOfBoundsException e) {moves++;}
					}
				}
			}
		}
	/**
	 * 
	 * @param b
	 * @return
	 */
	public static boolean setMoveValid(boolean b) {
		boolean setMoveValid = b;
		return setMoveValid;
	}
	/**
	 * Method that will jump a checker and remove it.
	 * @param r1 row input 1
	 * @param c1 coloumn input 1
	 * @param r2 row input 2
	 * @param c2 coloumn input 2
	 * @param r3 row input for piece to be removed
	 * @param c3 coloumn input for piece to be removed
	 * 
	 * @param board2 2darray of current gameBoard
	 */
	private static void jumpChecker(int r1, int c1, int r2, int c2, int r3, int c3, char[][] board2) {
		board2[r2][c2] = board2[r1][c1];
		board2[r1][c1] = '_';
			if (board2[r3][c3] == 'o') {
				redCheckers--;
			}
			if (board2[r3][c3] == 'x') {
				blackCheckers--;
			}
		board2[r3][c3] = '_';
		return;
	}

	/**
	 * Valid move verified. Checker will move from r1c1 to r2c2 and r1c1 will become an empty spot.
	 * @param r1 Initial Row
	 * @param c1 Initial Coloumn
	 * @param r2 Final Row
	 * @param c2 Final Coloumn
	 * @param board2 2darray of current gameBoard
	 */
	private static void moveChecker(int r1, int c1, int r2, int c2, char[][] board2) {
		board2[r2][c2] = board2[r1][c1];
		board2[r1][c1] = '_';
		return;
	}
	
	
	public static void invalidGameMode() {
		System.out.println("Sorry, that is not a valid game mode. Try again. \r\n");
	}
	/**
	 * 
	 * @param e ArrayIndexOutOfBoundsException e
	 */
	public static void invalidMove(ArrayIndexOutOfBoundsException e) {
		System.out.println("Sorry, that move is invalid, not in bounds, \n or coordinates chosen are not within the board. Try again. \r\n");
	}
	/**
	 * 
	 */
	public static void invalidMove() {
		System.out.println("Sorry, that move is invalid. Try again. \r\n");
	}
	/**
	 * 
	 * @param s StringIndexOutOfBoundsException s
	 */
	public static void invalidMove(StringIndexOutOfBoundsException s) {
		System.out.println("Sorry, that entry is invalid. Try again. \r\n");
	}
	
	/**
	 * 
	 */
	public static void blackTurnText() {
		System.out.println("Player X – your turn. \r\n" 
				+ "Choose a cell position of piece to be moved and the new position. e.g., 3a-4b or 3g-4h\r\n"
				+ "");
	}
	/**
	 * 
	 */
	public static void redTurnText() {
		System.out.println("Player O – your turn. \r\n"
				+ "Choose a cell position of piece to be moved and the new position. e.g., 3a-4b or 6f-5e\r\n"
				+ "");
	}
	
	/**
	 * 
	 * @param blackORred For displaying winner of game.
	 */
	public static void printGameOver(char blackORred) {
		if (blackORred == 'o')
			System.out.print("Game Over! Red is the winner!");
		if (blackORred == 'x')
			System.out.print("Game Over! Black is the winner!");
	}
	/**
	 * 
	 * @param r1
	 * @param c1
	 * @param r2
	 * @param c2
	 * @param board2
	 * @param black2
	 */
	public static void checkMove(CheckersMove[] r1, CheckersMove[] c1, CheckersMove[] r2, CheckersMove[] c2,
			CheckersLogic board2, boolean black2) {	
	}

}



