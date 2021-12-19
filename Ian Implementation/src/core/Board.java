package core;

import java.awt.Point;

public class Board {
	private static final int COLUMNS = 8;
	private static final int ROWS = 8;
	private char[][] board = new char[ROWS][COLUMNS];

	Board() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = '_';
			}
		}
	}

	public int boardRows() {
		return ROWS;
	}

	public int boardColumns() {
		return COLUMNS;
	}

	/** Is it off the board? */
	public boolean contains(Point p) {
		int x = (int) p.getX();
		int y = (int) p.getY();

		return x >= 0 && x < COLUMNS && y >= 0 && y < ROWS;
	}

	public void setPieceAt(Point p, char player) {
		int row = (int) p.getY();
		int col = (int) p.getX();
		board[row][col] = player;
	}

	public char getPieceAt(Point p) {
		return board[(int) p.getY()][(int) p.getX()];
	}

	/**
	 * Prints the current board state to the console.
	 * 
	 * @param board
	 */
	@Override
	public String toString() {
		String output = "";
		for (int i = 0; i < 8; i++) {
			output += 8 - i;
			for (int j = 0; j < 8; j++) {
				output += "|" + board[i][j];
			}
			output += "|\n";
		}
		output += "  a b c d e f g h\n";
		return output;
	}
}
