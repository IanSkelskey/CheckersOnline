package core;

import java.util.ArrayList;
/**
 * @since 29SEP2021
 * @author Neil
 * @version Week 6
 */
public class CheckersLogic {
        public static final int EMPTY = 0;
		public static final int RED = 1;
		public static final int RED_KING = 2;
		public static final int BLACK = 3;
		public static final int BLACK_KING = 4;
        public int[][] board;  // board[r][c] is the contents of row r, column c.  

        public CheckersLogic() {
            board = new int[8][8];
            setUpGame();
        }

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
        /**
         * 
         * @param row
         * @param col
         * @return
         */
        public int pieceAt(int row, int col) {
            return board[row][col];
        }
        /**
         * 
         * @param move
         */
        public void makeMove(CheckersMove move) {
            makeMove(move.fromRow, move.fromCol, move.toRow, move.toCol);
        }
        /**
         * 
         * @param fromRow
         * @param fromCol
         * @param toRow
         * @param toCol
         */
        public void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
            board[toRow][toCol] = board[fromRow][fromCol];
            board[fromRow][fromCol] = EMPTY;
            if (fromRow - toRow == 2 || fromRow - toRow == -2) {
                int jumpRow = (fromRow + toRow) / 2;  
                int jumpCol = (fromCol + toCol) / 2;  
                board[jumpRow][jumpCol] = EMPTY;
            }
            if (toRow == 0 && board[toRow][toCol] == RED)
                board[toRow][toCol] = RED_KING;
            if (toRow == 7 && board[toRow][toCol] == BLACK)
                board[toRow][toCol] = BLACK_KING;
        }
        /**
         * 
         * @param player
         * @return
         */
        public CheckersMove[] getLegalMoves(int player) {

            if (player != RED && player != BLACK)
                return null;

            int playerKing;  
            if (player == RED)
                playerKing = RED_KING;
            else
                playerKing = BLACK_KING;

            ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>(); 

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (board[row][col] == player || board[row][col] == playerKing) {
                        if (canJump(player, row, col, row+1, col+1, row+2, col+2))
                            moves.add(new CheckersMove(row, col, row+2, col+2));
                        if (canJump(player, row, col, row-1, col+1, row-2, col+2))
                            moves.add(new CheckersMove(row, col, row-2, col+2));
                        if (canJump(player, row, col, row+1, col-1, row+2, col-2))
                            moves.add(new CheckersMove(row, col, row+2, col-2));
                        if (canJump(player, row, col, row-1, col-1, row-2, col-2))
                            moves.add(new CheckersMove(row, col, row-2, col-2));
                    }
                }
            }
            
            if (moves.size() == 0) {
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                        if (board[row][col] == player || board[row][col] == playerKing) {
                            if (canMove(player,row,col,row+1,col+1))
                                moves.add(new CheckersMove(row,col,row+1,col+1));
                            if (canMove(player,row,col,row-1,col+1))
                                moves.add(new CheckersMove(row,col,row-1,col+1));
                            if (canMove(player,row,col,row+1,col-1))
                                moves.add(new CheckersMove(row,col,row+1,col-1));
                            if (canMove(player,row,col,row-1,col-1))
                                moves.add(new CheckersMove(row,col,row-1,col-1));
                        }
                    }
                }
            }

            if (moves.size() == 0)
                return null;
            else {
                CheckersMove[] moveArray = new CheckersMove[moves.size()];
                for (int i = 0; i < moves.size(); i++)
                    moveArray[i] = moves.get(i);
                return moveArray;
            }
        }  
        /**
         * 
         * @param player
         * @param r1
         * @param c1
         * @param r2
         * @param c2
         * @param r3
         * @param c3
         * @return
         */
        private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3) {
            if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8)
                return false; 
            if (board[r3][c3] != EMPTY)
                return false; 
            if (player == RED) {
                if (board[r1][c1] == RED && r3 > r1)
                    return false; 
                if (board[r2][c2] != BLACK && board[r2][c2] != BLACK_KING)
                    return false;  
                return true;  
            } else {
                if (board[r1][c1] == BLACK && r3 < r1)
                    return false;  
                if (board[r2][c2] != RED && board[r2][c2] != RED_KING)
                    return false; 
                return true;  
            }
        }  
        /**
         * 
         * @param player
         * @param r1
         * @param c1
         * @param r2
         * @param c2
         * @return
         */
        private boolean canMove(int player, int r1, int c1, int r2, int c2) {
            if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
                return false;  
            if (board[r2][c2] != EMPTY)
               return false; 
            if (player == RED) {
                if (board[r1][c1] == RED && r2 > r1)
                    return false; 
                return true;  
            }
            else {
                if (board[r1][c1] == BLACK && r2 < r1)
                    return false; 
                return true; 
            }
        }
        /**
         * 
         * @param player
         * @param row
         * @param col
         * @return
         */
        public CheckersMove[] getLegalJumpsFrom(int player, int row, int col) {
            if (player != RED && player != BLACK)
                return null;
            int playerKing; 
            if (player == RED)
                playerKing = RED_KING;
            else
                playerKing = BLACK_KING;
            ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>();  
            if (board[row][col] == player || board[row][col] == playerKing) {
                if (canJump(player, row, col, row+1, col+1, row+2, col+2))
                    moves.add(new CheckersMove(row, col, row+2, col+2));
                if (canJump(player, row, col, row-1, col+1, row-2, col+2))
                    moves.add(new CheckersMove(row, col, row-2, col+2));
                if (canJump(player, row, col, row+1, col-1, row+2, col-2))
                    moves.add(new CheckersMove(row, col, row+2, col-2));
                if (canJump(player, row, col, row-1, col-1, row-2, col-2))
                    moves.add(new CheckersMove(row, col, row-2, col-2));
            }
            if (moves.size() == 0)
                return null;
            else {
                CheckersMove[] moveArray = new CheckersMove[moves.size()];
                for (int i = 0; i < moves.size(); i++)
                    moveArray[i] = moves.get(i);
                return moveArray;
            }
        }  
          
    } 