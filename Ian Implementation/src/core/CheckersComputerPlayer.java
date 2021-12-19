package core;

import java.util.Random;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Class: Checkers Computer Player
 * Checkers computer player allows the computer to analyze all possible moves, pick one at random, 
 * translate it for input, and execute it.
 * @author Ian Skelskey
 * @version 1.0
 * @since 2021-09-08
 */
public class CheckersComputerPlayer extends CheckersPlayer {
	private char piece;
	private Board board;

	/** Default Constructor Creates an empty computer player. */
	public CheckersComputerPlayer() {
			}

	/**
	 * Parameterized Constructor
	 * Initializes a computer player and makes the computer aware of the game that it is a part
	 * of as well as the pieces that it will control.
	 * @param pGame A CheckersLogic object that contains the current game state.
	 * @param piece A character representation of the piece to be controlled: 'x' or 'o'
	 */
	public CheckersComputerPlayer(char p, Board pBoard) {
		super(p,pBoard);
	}

	/**
	 * Next Move
	 * Calls get instruction set and choose instruction
	 * @return A move as a string formatted for input. e.g. "3a-4b"
	 */
	public String nextMove() {
		ArrayList<String> instructionSet = getInstructionSet(movablePieces(false));
		return chooseInstruction(instructionSet);
	}

	/** 
	 * @param movablePieces An array of all pieces that have legal moves available.
	 * @return An array of String instructions formatted for input. This array represents all possible moves on that turn.
	 */
	private ArrayList<String> getInstructionSet(ArrayList<Point> movablePieces) {
		ArrayList<String> instructionSet = new ArrayList<>();

		// starting points
		for (int i = 0; i < movablePieces.size(); i++) {
			Point startPoint = movablePieces.get(i);
			ArrayList<PossibleMove> subMoveList = checkMoves(startPoint, false);

			// possible moves
			for (int j = 0; j < subMoveList.size(); j++) {
				Point endPoint = endingPosition(startPoint, subMoveList.get(j), piece);
				String firstSquare = boardPosition(startPoint);
				String endSquare = boardPosition(endPoint);
				String instruction = firstSquare + "-" + endSquare;
				instructionSet.add(instruction);
			}
		}
		return instructionSet;
	}
		/** 
	 * @param instructionSet The set of all possible moves formatted for input.
	 * @return A particular available move selected at random. (A member of the instructionSet parameter Array)
	 */
	private String chooseInstruction(ArrayList<String> instructionSet) {
		Random rand = new Random();
		return instructionSet.get(rand.nextInt(instructionSet.size()));
	}
		public String boardPosition(Point p) {
		int x = (int) p.getX();
		int y = (int) p.getY();

		char startCol = 'a';
		char startRow = '8';

		startCol += x;
		startRow -= y;

		StringBuilder b = new StringBuilder();
		return b.append(startRow).append(startCol).toString();
	}
}
