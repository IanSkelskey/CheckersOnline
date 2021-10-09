package core;

import java.awt.Point;
import java.util.ArrayList;

public abstract class CheckersPlayer {
	static final char BLANK = '_';
	char piece;
	char opponent;
	Board board;
	ArrayList<Point> pieces = new ArrayList<>();
		/** Enum Members. */
	public enum PossibleMove {
		LEFTMOVE, RIGHTMOVE, LEFTJUMP, RIGHTJUMP
	}
		public CheckersPlayer(){
			}
		public CheckersPlayer(char p, Board pBoard){
		piece = p;
		board = pBoard;
		initPieces();
		if(p == 'x') {
			opponent = 'o';
		}else {
			opponent = 'x';
		}
			}
		public void initPieces() {
		int start;
		int end;
				if(piece == 'x') {
			start = 5;
			end = 7;
		}else {
			start = 0;
			end = 2;
		}
				for(int i = start; i <= end; i++) {
			for(int j = 0; j < 8; j++) {
				if((i+j)%2 == 1) {
					board.setPieceAt(new Point(j,i), piece);
					addPiece(new Point(j,i));
				}
			}
		}
	}
			abstract String nextMove();
		public void addPiece(Point p) {
		pieces.add(p);
	}
		public char getPiece() {
		return piece;
	}
	public ArrayList<Point> getPieces() {
		return pieces;
	}
		/**
	 * Checks to see if a particular piece has potential moves left.
	 * 
	 * @param row    Row of piece to examine.
	 * @param col    Column of piece to examine.
	 * @param player Owner of piece.
	 * @return True if the piece has available legal moves.
	 */
	public boolean canPieceMove(int row, int col, char player) {
		return !checkMoves(new Point(col, row), true).isEmpty();
	}
		/**
	 * Returns an ArrayList of movable pieces based on player.
	 * 
	 * @param player
	 * @param print
	 * @return
	 */
	public ArrayList<Point> movablePieces(boolean print) {
		ArrayList<Point> movablePieces = new ArrayList<>();
		for (int i = 0; i < pieces.size(); i++) {
			boolean canPieceMove = !checkMoves(pieces.get(i), print).isEmpty();
			if (canPieceMove) {
				movablePieces.add(pieces.get(i));
			}
		}
		return movablePieces;
	}
		public ArrayList<PossibleMove> checkMoves(Point p, boolean print) {
		ArrayList<PossibleMove> moveList = new ArrayList<>();

		char opponent = 'x';
		int forwardDirection = 1;
		int leftDirection = 1;
		int rightDirection = -1;

		if (piece == 'x') {
			forwardDirection = -1;
			leftDirection = -1;
			rightDirection = 1;
			opponent = 'o';
		}

		char leftMoveCharacter = '.';
		int leftMoveX = (int) p.getX() + 1 * leftDirection;
		int leftMoveY = (int) p.getY() + 1 * forwardDirection;
		Point leftMovePoint = new Point(leftMoveX, leftMoveY);
		if (board.contains(leftMovePoint)) {
			leftMoveCharacter = board.getPieceAt(leftMovePoint);
		}

		char rightMoveCharacter = '.';
		int rightMoveX = (int) p.getX() + 1 * rightDirection;
		int rightMoveY = (int) p.getY() + 1 * forwardDirection;
		Point rightMovePoint = new Point(rightMoveX, rightMoveY);

		if (board.contains(rightMovePoint)) {
			rightMoveCharacter = board.getPieceAt(rightMovePoint);
		}

		char leftJumpCharacter = '.';
		int leftJumpX = (int) p.getX() + 2 * leftDirection;
		int leftJumpY = (int) p.getY() + 2 * forwardDirection;
		Point leftJumpPoint = new Point(leftJumpX, leftJumpY);
		if (board.contains(leftJumpPoint)) {
			leftJumpCharacter = board.getPieceAt(leftJumpPoint);
		}

		char rightJumpCharacter = '.';
		int rightJumpX = (int) p.getX() + 2 * rightDirection;
		int rightJumpY = (int) p.getY() + 2 * forwardDirection;
		Point rightJumpPoint = new Point(rightJumpX, rightJumpY);
		if (board.contains(rightJumpPoint)) {
			rightJumpCharacter = board.getPieceAt(rightJumpPoint);
		}

		if (leftMoveCharacter == BLANK) {
			moveList.add(PossibleMove.LEFTMOVE);
		}

		else if (leftMoveCharacter == opponent && leftJumpCharacter == BLANK) {
			moveList.add(PossibleMove.LEFTJUMP);
		}

		if (rightMoveCharacter == BLANK) {
			moveList.add(PossibleMove.RIGHTMOVE);
		}

		else if (rightMoveCharacter == opponent && rightJumpCharacter == BLANK) {
			moveList.add(PossibleMove.RIGHTJUMP);
		}

		if (!moveList.isEmpty() && print) {
			//System.out.println(boardPosition(p) + " Moves = " + moveList);
		}
		return moveList;
	}

	/**
	 * Updates the arrays of pieces.
	 * 
	 * @param player    The player whose piece is moving.
	 * @param fromPoint Point where it started.
	 * @param toPoint   Point where it finishes.
	 */
	public void movePiece(Point fromPoint, Point toPoint) {
		// Update Board Visually
		board.setPieceAt(toPoint, piece);
		pieces.add(toPoint);
		deletePiece(fromPoint);
	}

	/**
	 * Removes a piece from a player's array of pieces.
	 * 
	 * @param player Indicates the player whose piece is being deleted.
	 * @param point  Indicates which piece is being deleted.
	 */
	public void deletePiece(Point point) {
		board.setPieceAt(point, BLANK);
		pieces.remove(point);
	}
		/** Helper function to get the correct position for a PossibleMove. */
	public Point endingPosition(Point startingPoint, PossibleMove move, char player) {
		int yDirection = 1;
		int leftDirection = 1;
		int rightDirection = -1;

		if (player == 'x') {
			yDirection = -1;
			leftDirection = -1;
			rightDirection = 1;
		}

		int x = startingPoint.x;
		int y = startingPoint.y;

		int finalX;
		int finalY;

		switch (move) {
		case LEFTMOVE:
			finalX = x + 1 * leftDirection;
			finalY = y + 1 * yDirection;
			break;
		case RIGHTMOVE:
			finalX = x + 1 * rightDirection;
			finalY = y + 1 * yDirection;
			break;
		case LEFTJUMP:
			finalX = x + 2 * leftDirection;
			finalY = y + 2 * yDirection;
			break;
		case RIGHTJUMP:
			finalX = x + 2 * rightDirection;
			finalY = y + 2 * yDirection;
			break;
		default:
			finalX = -1;
			finalY = -1;
			break;
		}

		return new Point(finalX, finalY);
	}
	@Override
	public String toString() {
		if(piece == 'x') {
			return "x";
		}else {
			return "o";
		}
	}
}
