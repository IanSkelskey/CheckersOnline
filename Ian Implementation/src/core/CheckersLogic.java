package core;

// Imports
import java.awt.Point;
import java.util.ArrayList;

/**
 * Class: CheckersLogic Contains the logic that makes up a game of checkers.
 * 
 * @author Ian Skelskey
 * @version 2.0
 * @since 2021-09-08
 */
public class CheckersLogic implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** Primitive Members. */
	private Board board;
	/** Private char[][] board;. */
	private CheckersPlayer x;
	private CheckersPlayer o;
	private CheckersPlayer activePlayer;
	//private char activePlayer;

	/** Object Members. */
	private String input;

	/**
	 * Constructor for the CheckersLogic class. Creates a 2d character array to
	 * store the game board and initializes it. Player 'x' moves first.
	 */
	public CheckersLogic() {
		board = new Board();
		x = new CheckersHumanPlayer('x', board);
		o = new CheckersHumanPlayer('o', board);
		//initBoard();
		setActivePlayer(x);
	}

	/**
	 * Getter method to access the board from other classes.
	 * 
	 * @return board
	 */
	public Board getBoard() {
		return board;
	}

	// Move Execution Methods
	/**
	 * Executes the move as instructed by the player.
	 * 
	 * @param rowStart The row of the beginning square.
	 * @param colStart The column of the beginning square.
	 * @param rowEnd The row of the ending square.
	 * @param colEnd The column of the ending square.
	 */
	public void move(int rowStart, int colStart, int rowEnd, int colEnd) {
		CheckersPlayer inactivePlayer = o;
		if (activePlayer.equals(o)) {
			inactivePlayer = x;
		}
		activePlayer.movePiece(new Point(colStart, rowStart), new Point(colEnd, rowEnd));

		int xDirection = (colEnd - colStart) / 2;
		int yDirection;
		if (activePlayer.equals(x)) {
			yDirection = -1;
		} else {
			yDirection = 1;
		}

		String type = getMoveType(Math.abs(colEnd - colStart));
		if (type == "jump") {
			System.out.println("executed jump");
			inactivePlayer.deletePiece(new Point(colStart + xDirection, rowStart + yDirection));
		} else if (type == "multi-jump") {
			System.out.println("executed multi jump");
			String[] instruct = splitInstructions();
			for (int i = 0; i < instruct.length; i++) {
				inactivePlayer.deletePiece(new Point(colStart + xDirection, rowStart + yDirection));
			}
		}
	}
		// Validation Methods
	/**
	 * Returns the type of move being performed.
	 * 
	 * @param deltaX The horizontal distance.
	 * @return The type of move being performed: simple, jump, multi-jump
	 */
	public String getMoveType(int deltaX) {
		if (deltaX == 2 && splitInstructions().length == 1) {
			return "jump";
		}
		if (splitInstructions().length > 1) {
			return "multi-jump";
		}
		return "simple";
	}
			/**
	 * Iterates over and executes one or many moves then switches control to other
	 * player.
	 */
	public void turn() {
		String[] instruct = splitInstructions();
		for (int i = 0; i < instruct.length; i++) {
			int startRow = asRow(instruct[i].charAt(0));
			int startCol = asCol(instruct[i].charAt(1));
			int endRow = asRow(instruct[i].charAt(3));
			int endCol = asCol(instruct[i].charAt(4));
			move(startRow, startCol, endRow, endCol);
		}
	}

	// Game Over Methods
	/**
	 * Keeps track of game ending conditions. Main game loop occurs when this is
	 * true.
	 * 
	 * @return True if the game is over.
	 */
	public boolean isOver() {
		if (o.getPieces().isEmpty()) {
			System.out.println("O has no pieces left. X wins!");
			return true;
		}
		if (x.getPieces().isEmpty()) {
			System.out.println("X has no pieces left. O wins!");
			return true;
		}

		ArrayList<Point> Xmovable = x.movablePieces(false);
		ArrayList<Point> Omovable = o.movablePieces(false);

		if (Xmovable.isEmpty() && Omovable.isEmpty()) {
			System.out.println("Neither player can move. Its a draw.");
			return true;
		}

		if (Xmovable.isEmpty()) {
			System.out.println("X has no moves left. O wins!");
			return true;
		}
		if (Omovable.isEmpty()) {
			System.out.println("O has no moves left. X wins!");
			return true;
		}

		return false;
	}

	// Player Methods
	/**
	 * Setter to change the active player.
	 * 
	 * @param player The player whose turn it will be.
	 */
	public void setActivePlayer(CheckersPlayer player) {
		activePlayer = player;
	}

	/**
	 * Getter to access active player from outside class.
	 * 
	 * @return The player whose turn it is.
	 */
	public CheckersPlayer getActivePlayer() {
		return activePlayer;
	}

	/** Method to switch the activePlayer. */
	public void switchPlayer() {
		if (activePlayer.equals(x)) {
			activePlayer = o;
		} else {
			activePlayer = x;
		}
	}

	// Input interpretation methods
	/**
	 * Passes input to the game logic class.
	 * 
	 * @param raw Raw input from the user.
	 */
	public void passInput(String raw) {
		input = raw.toLowerCase();
	}

	/** Clears the input before next turn. */
	public void clearInput() {
		input = "";
	}

	/**
	 * Splits input between commas.
	 * 
	 * @return An array of individual instructions in the format "3a-5c,..."
	 */
	public String[] splitInstructions() {
		return input.split(",");
	}

	/**
	 * Converts board numeric coordinates to array rows.
	 * 
	 * @param input The integer input by the user.
	 * @return The corresponding row in the board array.
	 */
	public int asRow(int input) {
		return 8 - (input - 48);
	}

	/**
	 * Converts board alpha coordinates to array columns.
	 * 
	 * @param input The letter [a-h] input by the user.
	 * @return The corresponding column in the board array.
	 */
	public int asCol(char input) {
		switch (input) {
		case 'a':
			return 0;
		case 'b':
			return 1;
		case 'c':
			return 2;
		case 'd':
			return 3;
		case 'e':
			return 4;
		case 'f':
			return 5;
		case 'g':
			return 6;
		case 'h':
			return 7;
		}
		return -1;
	}

	/**
	 * Makes sure that a move is legal.
	 * 
	 * @return True if the move is legal.
	 */
	public boolean validateMove() {
		String[] instruct = splitInstructions();
		for (int i = 0; i < instruct.length; i++) {
			int startRow = asRow(input.charAt(0));
			int startCol = asCol(input.charAt(1));
			Point startPoint = new Point(startCol,startRow);
			int endRow = asRow(input.charAt(3));
			int endCol = asCol(input.charAt(4));
			Point endPoint = new Point(endCol, endRow);

			int deltaX = Math.abs(endCol - startCol);
			int deltaY = Math.abs(endRow - startRow);

			String type = getMoveType(deltaX);

			if (startRow == endRow) {
				System.out.println("Cannot move horizontally. Only diagonally.");
				return false;
			}

			if (type == "simple" && deltaX != 1) {
				System.out.println("Simple moves must be adjacent diagonally.");
				return false;
			}

			if (activePlayer.equals(x)) {
				if (startRow < endRow) {
					System.out.println("Invalid move. Must move forward.");
					return false;
				}
			} else if (startRow > endRow) {
				System.out.println("Invalid move. Must move forward.");
				return false;
			}
			if (board.getPieceAt(startPoint) != activePlayer.getPiece() || board.getPieceAt(endPoint) != '_') {
				System.out.println("Invalid move. Start square must contain your piece. End square must be empty.");
				return false;
			}
			if (type == "jump") {
				int xDirection = (endCol - startCol) / 2;
				int yDirection;
				if (activePlayer.equals(x)) {
					yDirection = -1;
				} else {
					yDirection = 1;
				}

				CheckersPlayer inactivePlayer;
				if (activePlayer.equals(x)) {
					inactivePlayer = o;
				} else {
					inactivePlayer = x;
				}

								if (deltaX != 2 || deltaY != 2) {
					System.out.println("Invalid jump. 1");
					return false;
				} else if (board.getPieceAt(new Point(startCol + xDirection,startRow + yDirection)) != inactivePlayer.getPiece()) {
					System.out.println("Invalid jump. 2");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks to see that input from user is in the correct format. For example:
	 * 3a-4b is a valid instruction that tells the game to move a piece from square
	 * 3a to square 4b.
	 * 
	 * @return True if user input is in correct format
	 */
	public boolean validateInput() {
		if (input.contains(" ")) {
			System.out.println("Input is not valid. Try again.");
			System.out.println("Please do not input spaces.");
		}

		// valid multi-step instructions are separated with a comma
		String[] instructions = input.split(",");

		for (int i = 0; i < instructions.length; i++) {
			// A valid instruction is 5 characters long
			if (instructions[i].length() != 5) {
				System.out.println(instructions[i]);
				System.out.println("Input is not valid. Try again.");
				System.out.println("Instruction is of invalid length.");
				return false;
			}

			// a valid instruction includes a hyphen to separate coordinate pairs
			if (instructions[i].charAt(2) != '-') {
				System.out.println("Input is not valid. Try again.");
				System.out.println("Make sure to separate coordinate pairs with hyphens.");
				return false;
			}

			String[] pairs = instructions[i].split("-");

			for (int j = 0; j < pairs.length; j++) {
				if (!validatePairs(pairs)) {
					System.out.println("Input is not valid. Try again.");
					System.out.println("Use only the letters and numbers that you see along the board's edges.");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks that pairs match the number-letter format specified in the UI.
	 * 
	 * @param pairs Coordinate pairs
	 * @return True if input matches number letter format: "5g"
	 */
	public boolean validatePairs(String[] pairs) {
		for (int i = 0; i < 2; i++) {
			char number = pairs[i].charAt(0);
			char letter = pairs[i].charAt(1);
			if (!isValidNumber(number) || !isValidLetter(letter)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks that an input character is a valid letter between 'a' and 'h.'
	 * 
	 * @param input A character for testing.
	 * @return True if the character represents a letter [a-h]
	 */
	public boolean isValidLetter(char input) {
		return input > 96 && input < 105;
	}

	/**
	 * Checks that an input character is a valid number between 1 and 8.
	 * 
	 * @param input A number for testing.
	 * @return True if number is between 1 and 8
	 */
	public boolean isValidNumber(char input) {
		return input > 48 && input < 57;
	}
		public CheckersPlayer getPlayer(char p) {
		if(p == 'x') {
			return x;
		}else {
			return o;
		}
	}
}
