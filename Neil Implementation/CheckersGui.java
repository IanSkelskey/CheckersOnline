package ui;	

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.MouseEvent;
import core.CheckersLogic;

/**
 * CheckersGui runs the JavaFX application that will control all game mode
 * entries and the project logic. Has the main method for the CheckersGame Project.
 * CheckersGui gives four options for game mode including a resign/quit. If Text Console
 * option is chosen the program will continue to run in the IDE Console and the
 * JavaFX windown will remain open to allow for future games.
 * 
/**
 * @since 29SEP2021
 * @author Neil
 * @version Week 6
 */
public class CheckersGui extends Application {
	
	public CheckersBoard board; 
	private Button newGamePVP;  
	private Button resignButton; 
	private Button textConsoleButton;
	private Button newGamePVC;
	private Label message; 
	
	public static void main(String[] args) {
		launch(args);
	}

    public void start(Stage stage) {
   
    	try {       
	        message = new Label("Click \"New Game PvC\" or \"New Game PvP\" or \n \"New Game Text Console\" to begin.");
	        message.setTextFill( Color.rgb(50,255,255) ); 
	        message.setFont( Font.font(null, FontWeight.BOLD, 18) );
	        newGamePVP = new Button("New Game PvP");
	        newGamePVC = new Button("New Game PvC");
	        resignButton = new Button("Resign");
	        textConsoleButton = new Button(" New Game Text Console");
	        board = new CheckersBoard(); 
	        board.drawBoard();  newGamePVC.setOnAction(e -> board.doNewGameComp() );
	        newGamePVP.setOnAction( e -> board.doNewGame() );
	        textConsoleButton.setOnAction ( e -> board.doTextConsoleVersion() );
	        resignButton.setOnAction( e -> board.doResign() );
	        board.setOnMousePressed( e -> board.mousePressed(e) );
	        board.relocate(20,20);
	        
	        newGamePVC.relocate(370, 50);
	        newGamePVP.relocate(370, 100);
	        textConsoleButton.relocate(370, 150);
	        resignButton.relocate(370, 200);
	        message.relocate(20, 370);
	      
	        resignButton.setManaged(false);
	        resignButton.resize(150,30);
	        newGamePVP.setManaged(false);
	        newGamePVP.resize(150,30);
	        newGamePVC.setManaged(false);
	        newGamePVC.resize(150,30);
	        textConsoleButton.setManaged(false);
	        textConsoleButton.resize(150, 30);

	        Pane root = new Pane();
	        
	        root.setPrefWidth(600);
	        root.setPrefHeight(460);
	        root.getChildren().addAll(board, newGamePVP, newGamePVC, resignButton, textConsoleButton, message);
	        root.setStyle("-fx-background-color: darkgreen; "
	                           + "-fx-border-color: darkblue; -fx-border-width:4");
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.setResizable(false);
	        stage.setTitle("Checkers Game!");
	        stage.show();
	
		}
    	catch(Exception e) {
				e.printStackTrace();
		}
    }

    private class CheckersBoard extends Canvas {

        CheckersLogic board;
        boolean gameInProgress;
        int currentPlayer; 
        int selectedRow, selectedCol;
        CheckersLogic.CheckersMove[] legalMoves;  
        CheckersBoard() {
            super(324,324);  // board is 324x324 pixels
            board = new CheckersLogic();
            doResign();
        }

        public Object doNewGameComp() {
			// TODO Auto-generated method stub
			return null;
		}

        void doNewGame() {
            if (gameInProgress == true) {
                message.setText("Finish the current game first!");
                return;
            }
            board.setUpGame();   
            currentPlayer = CheckersLogic.RED;  
            legalMoves = board.getLegalMoves(CheckersLogic.RED);  
            selectedRow = -1;   
            message.setText("Red:  Make your move.");
            gameInProgress = true;
            newGamePVP.setDisable(true);
            resignButton.setDisable(false);
            drawBoard();
        }

        void doResign() {
            if (gameInProgress == false) { 
            	message = new Label("Click \"New Game PvC\" or \"New Game PvP\" or \n \"New Game Text Console\" to begin.");
            	message.setTextFill( Color.rgb(100,255,100) ); 
                message.setFont( Font.font(null, FontWeight.BOLD, 17) );
            	return;
            }
            if (currentPlayer == CheckersLogic.RED)
                gameOver("RED resigns.  BLACK wins.");
            else
                gameOver("BLACK resigns.  RED wins.");
        }
        
        public void doTextConsoleVersion() {
        	if (gameInProgress == true) { 
        		message.setText("Game mode has already been chosen!");
        		return;
        	} else {
        			gameOver("Closing the GUI!");
        			System.out.println("Closing the GUI! \nStandby for Checkers Game!");
        			Stage stage = (Stage) textConsoleButton.getScene().getWindow();
        			stage.close();
        			CheckersTextConsole.mainText();
        	}
        }

        void gameOver(String str) {
            message.setText(str);
            newGamePVP.setDisable(false);
            resignButton.setDisable(true);
            gameInProgress = false;
        }

        void doClickSquare(int row, int col) {

            for (int i = 0; i < legalMoves.length; i++)
                if (legalMoves[i].fromRow == row && legalMoves[i].fromCol == col) {
                    selectedRow = row;
                    selectedCol = col;
                    if (currentPlayer == CheckersLogic.RED)
                        message.setText("RED:  Your turn to make your move.");
                    else
                        message.setText("BLACK: Your turn to make your move.");
                    drawBoard();
                    return;
                }

            if (selectedRow < 0) {
                message.setText("Click the piece you want to move.");
                return;
            }

            for (int i = 0; i < legalMoves.length; i++)
                if (legalMoves[i].fromRow == selectedRow && legalMoves[i].fromCol == selectedCol
                	&& legalMoves[i].toRow == row && legalMoves[i].toCol == col) {
                    	doMakeMove(legalMoves[i]);
                    	return;
                }
            message.setText("Click the square you want to move to.");
        } 

        void doMakeMove(CheckersLogic.CheckersMove move) {
            board.makeMove(move);
            if (move.isJump()) {
                legalMoves = board.getLegalJumpsFrom(currentPlayer,move.toRow,move.toCol);
                if (legalMoves != null) {
                    if (currentPlayer == CheckersLogic.RED)
                        message.setText("RED:  You must continue jumping.");
                    else
                        message.setText("BLACK:  You must continue jumping.");
                    selectedRow = move.toRow;  
                    selectedCol = move.toCol;
                    drawBoard();
                    return;
                }
            }
            if (currentPlayer == CheckersLogic.RED) {
                currentPlayer = CheckersLogic.BLACK;
                legalMoves = board.getLegalMoves(currentPlayer);
                if (legalMoves == null)
                    gameOver("BLACK has no moves.  RED wins.");
                else if (legalMoves[0].isJump())
                    message.setText("BLACK:  Make your move.  You must jump.");
                else
                    message.setText("BLACK:  Make your move.");
            }
            else {
                currentPlayer = CheckersLogic.RED;
                legalMoves = board.getLegalMoves(currentPlayer);
                if (legalMoves == null)
                    gameOver("RED has no moves.  BLACK wins.");
                else if (legalMoves[0].isJump())
                    message.setText("RED:  Make your move.  You must jump.");
                else
                    message.setText("RED:  Make your move.");
            }
            selectedRow = -1;
            if (legalMoves != null) {
                boolean sameStartSquare = true;
                for (int i = 1; i < legalMoves.length; i++)
                    if (legalMoves[i].fromRow != legalMoves[0].fromRow
                    || legalMoves[i].fromCol != legalMoves[0].fromCol) {
                        sameStartSquare = false;
                        break;
                    }
                if (sameStartSquare) {
                    selectedRow = legalMoves[0].fromRow;
                    selectedCol = legalMoves[0].fromCol;
                }
            }
            drawBoard();
        }  

        public void drawBoard() {
            
            GraphicsContext g = getGraphicsContext2D();
            g.setFont( Font.font(18) );
            g.setStroke(Color.DARKRED);
            g.setLineWidth(3);
            g.strokeRect(1, 1, 322, 322);

            // Draw the squares of the checkerboard and the checkers. 
            // Play on light gray, Gray are empty spaces without Kings.

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if ( row % 2 == col % 2 )
                        g.setFill(Color.LIGHTGRAY);
                    else
                        g.setFill(Color.GRAY);
                    g.fillRect(2 + col*40, 2 + row*40, 40, 40);
                    switch (board.pieceAt(row,col)) {
                    case CheckersLogic.RED:
                        g.setFill(Color.RED);
                        g.fillOval(8 + col*40, 8 + row*40, 28, 28);
                        break;
                    case CheckersLogic.BLACK:
                        g.setFill(Color.BLACK);
                        g.fillOval(8 + col*40, 8 + row*40, 28, 28);
                        break;
                    case CheckersLogic.RED_KING:
                        g.setFill(Color.RED);
                        g.fillOval(8 + col*40, 8 + row*40, 28, 28);
                        g.setFill(Color.WHITE);
                        g.fillText("K", 15 + col*40, 29 + row*40);
                        break;
                    case CheckersLogic.BLACK_KING:
                        g.setFill(Color.BLACK);
                        g.fillOval(8 + col*40, 8 + row*40, 28, 28);
                        g.setFill(Color.WHITE);
                        g.fillText("K", 15 + col*40, 29 + row*40);
                        break;
                    }
                }
            }
            if (gameInProgress) {
                g.setStroke(Color.AQUA);
                g.setLineWidth(4);
                for (int i = 0; i < legalMoves.length; i++) {
                    g.strokeRect(4 + legalMoves[i].fromCol*40, 4 + legalMoves[i].fromRow*40, 36, 36);
                }
                if (selectedRow >= 0) {
                    g.setStroke(Color.WHITE);
                    g.setLineWidth(4);
                    g.strokeRect(4 + selectedCol*40, 4 + selectedRow*40, 36, 36);
                    g.setStroke(Color.CRIMSON);
                    g.setLineWidth(4);
                    for (int i = 0; i < legalMoves.length; i++) {
                        if (legalMoves[i].fromCol == selectedCol && legalMoves[i].fromRow == selectedRow) {
                            g.strokeRect(4 + legalMoves[i].toCol*40, 4 + legalMoves[i].toRow*40, 36, 36);
                        }
                    }
                }
            }
        } 
        
        public void mousePressed(MouseEvent evt) {
            if (gameInProgress == false) {
            	doResign();
            } else {
                int col = (int)((evt.getX() - 2) / 40);
                int row = (int)((evt.getY() - 2) / 40);
                if (col >= 0 && col < 8 && row >= 0 && row < 8)
                    doClickSquare(row,col);
            }
        }

    }

}