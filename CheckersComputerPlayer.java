package core;

import java.util.Random;
import java.util.Scanner;
import ui.CheckersTextConsole;
/**
 * @since 29SEP2021
 * @author Neil
 * @version Week 6
 */
public class CheckersComputerPlayer {

	public static final int EMPTY = 0;
	public static final int RED = 1;
	public static final int RED_KING = 2;
	public static final int BLACK = 3;
	public static final int BLACK_KING = 4;
	private static char pieceToMove;
	private CheckersTextConsole newgame;
	private static CheckersLogic board;
	public static Scanner input = new Scanner(System.in);
	
	public CheckersComputerPlayer() {
	}	
	
	public boolean isPlayer() {
		return false;
	}
	/**
	 * @param CheckBoard
	 * @param piece
	 */
	public CheckersComputerPlayer(CheckersLogic CheckBoard, char piece) {
		Scanner input = new Scanner(System.in);
		board = CheckBoard;
		setPieceToMove(piece);
	}
	/**
	 * 
	 * @param CheckBoard
	 */
	public void updateComputer(CheckersLogic CheckBoard) {
		board = CheckBoard;
	}
	/**
	 * 
	 * @return
	 */
	public int nextCompMove() {
		core.CheckersLogic.CheckersMove[] moveSet = getMoveSet(board.getLegalMoves(BLACK));
		return chooseMove(moveSet);
	}
	/**
	 * 
	 * @param checkersMoves
	 * @return
	 */
	private static core.CheckersLogic.CheckersMove[] getMoveSet(core.CheckersLogic.CheckersMove[] checkersMoves) {
		core.CheckersLogic.CheckersMove[] moveSet = board.getLegalMoves(BLACK);
		return moveSet;
	}
	/**
	 * 
	 * @param moveSet
	 * @return
	 */
	private int chooseMove(core.CheckersLogic.CheckersMove[] moveSet) {
		Random Randy = new Random();
		return moveSet.length;
	}

	// For use with CheckersGUI
	public static class CheckersMove {
        public int fromRow;  
		public int fromCol;
        public int toRow;     
		public int toCol;
        CheckersMove(int r1, int c1, int r2, int c2) {
            fromRow = r1;
            fromCol = c1;
            toRow = r2;
            toCol = c2;
        }
        public boolean isJump() {
        	return (fromRow - toRow == 2 || fromRow - toRow == -2);
        }
    }
	
	// For use with CheckersTextConsole
	public static void blackTurn() {
		System.out.println("The computer will now make its move. Computing...");
		core.CheckersLogic.CheckersMove[] r1 = getMoveSet(getMoveSet(null));
		core.CheckersLogic.CheckersMove[] c1 = getMoveSet(getMoveSet(null));
		core.CheckersLogic.CheckersMove[] r2 = getMoveSet(getMoveSet(null));
		core.CheckersLogic.CheckersMove[] c2 = getMoveSet(getMoveSet(null));
		try {
				r1 = getMoveSet(r1);
				c1 = getMoveSet(c1);
				r2 = getMoveSet(r2);
				c2 = getMoveSet(c2);
				try {
					CheckersTextConsole.checkMove(r1,c1,r2,c2, board, true);
				} catch (ArrayIndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return; 
			}
			catch(StringIndexOutOfBoundsException s) {
				CheckersTextConsole.invalidMove(s);
				CheckersTextConsole.setMoveValid(false);
				return;
			}
	}

	public CheckersTextConsole getNewgame() {
		return newgame;
	}

	public void setNewgame(CheckersTextConsole newgame) {
		this.newgame = newgame;
	}

	public static char getPieceToMove() {
		return pieceToMove;
	}

	public static void setPieceToMove(char pieceToMove) {
		CheckersComputerPlayer.pieceToMove = pieceToMove;
	}
}

