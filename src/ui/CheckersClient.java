package ui;

import core.CheckersComputerPlayer;
import core.CheckersLogic;

import java.awt.Point;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.TitledPane;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import java.awt.Point;
import javafx.scene.control.TextField;

/**
 * CheckersClient Class adds networking to the GUI checkers game.
 * 
 * @author Ian Skelskey
 * @version 1.0
 * @since 09-22-2021
 */
public class CheckersClient extends Application {
	private DataOutputStream toServer;
	private DataInputStream fromServer;

	private static CheckersLogic game = new CheckersLogic();
	private static boolean isSinglePlayer = true;
	boolean inMenu = true;
	String input = "";
	String state = "menu";
	String gameType = "";
	char playerPiece;

	/**
	 * Main method.
	 * 
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) {
		WorkerThread turnThread = new WorkerThread(primaryStage);
		turnThread.start();
	}

	/**
	 * Launches the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Adds the menu to primary stage and shows it.
	 * 
	 * @param primaryStage
	 */
	public void printMenu(Stage primaryStage) {
		inMenu = true;
		Group group1 = new Group();

		Label welcome = new Label("Welcome to the checkers GUI! Select the number of players.");

		ToggleGroup group2 = new ToggleGroup();
		Label localTitle = new Label("Play a game locally:");
		RadioButton rad1Player = new RadioButton("1-Player");
		RadioButton rad2Player = new RadioButton("2-Player");
		Label networkTitle = new Label("Play a game over the network:");
		RadioButton radNetwork = new RadioButton("Find Match");
		rad1Player.setToggleGroup(group2);
		rad2Player.setToggleGroup(group2);
		radNetwork.setToggleGroup(group2);

		Button btnStart = new Button("Start Game");
		Button btnExit = new Button("Exit");

		VBox root = new VBox();
		root.setAlignment(Pos.BASELINE_CENTER);
		root.setSpacing(10);
		root.getChildren().addAll(welcome, localTitle, rad1Player, rad2Player, networkTitle, radNetwork, btnStart,
				btnExit);

		Scene menu = new Scene(root, 460, 500);
		primaryStage.setScene(menu);
		primaryStage.show();
		btnStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (group2.getSelectedToggle() == rad1Player) {
					System.out.println("1-Player clicked");
					gameType = "local";
					isSinglePlayer = true;
					playerPiece = 'x';
					state = "play";
					printBoard(primaryStage);
				} else if (group2.getSelectedToggle() == rad2Player) {
					System.out.println("2-Player clicked");
					gameType = "local";
					isSinglePlayer = false;
					state = "play";
					printBoard(primaryStage);
				} else if (group2.getSelectedToggle() == radNetwork) {
					System.out.println("Network game selected!");
					gameType = "network";
					state = "play";
					printBoard(primaryStage);
					connectToServer();
				} else {
					System.out.println("Please make a selection");
				}
			}
		});
		btnExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}

	/**
	 * Adds the board to primary stage and shows it.
	 * 
	 * @param primaryStage
	 */
	public void printBoard(Stage primaryStage) {
		// Create Board
		Group group = new Group();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Rectangle check = new Rectangle();
				check.setX(20 + 50 * i);
				check.setY(20 + 50 * j);
				check.setWidth(50);
				check.setHeight(50);
				if (i % 2 == 0) {
					if (j % 2 == 0) {
						check.setFill(Color.BLACK);
					} else {
						check.setFill(Color.WHITE);
					}
				} else if (j % 2 == 0) {
					check.setFill(Color.WHITE);
				} else {
					check.setFill(Color.BLACK);
				}
				group.getChildren().addAll(check);
			}
		}

		// add red pieces to scene
		for (int i = 0; i < game.getPlayer('o').getPieces().size(); i++) {
			Point currentPiece = game.getPlayer('o').getPieces().get(i);
			Ellipse piece = new Ellipse();
			piece.setLayoutX(45 + 50 * currentPiece.getX());
			piece.setLayoutY(45 + 50 * currentPiece.getY());
			piece.setRadiusX(20);
			piece.setRadiusY(20);
			piece.setFill(Color.RED);
			group.getChildren().addAll(piece);
		}

		// add blue pieces to scene
		for (int i = 0; i < game.getPlayer('x').getPieces().size(); i++) {
			Point currentPiece = game.getPlayer('x').getPieces().get(i);
			Ellipse piece = new Ellipse();
			piece.setLayoutX(45 + 50 * currentPiece.getX());
			piece.setLayoutY(45 + 50 * currentPiece.getY());
			piece.setRadiusX(20);
			piece.setRadiusY(20);
			piece.setFill(Color.BLUE);
			group.getChildren().addAll(piece);
		}

		// add buttons to scene to control board
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Button checkHandler = new Button();
				checkHandler.setLayoutX(20 + 50 * i);
				checkHandler.setLayoutY(20 + 50 * j);
				checkHandler.setMinWidth(50);
				checkHandler.setMinHeight(50);
				checkHandler.setOpacity(0);
				checkHandler.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent arg0) {
						System.out.println("You clicked x: " + (checkHandler.getLayoutX() - 20) / 50 + "y: "
								+ (checkHandler.getLayoutY() - 20) / 50);
						// convert to string instruction
						// check if coordinate contains a piece under your control
					}
				});
				group.getChildren().addAll(checkHandler);
			}
		}

		Label feedback = new Label();
		feedback.setLayoutX(30);
		feedback.setLayoutY(430);
		feedback.setText("Player " + game.getActivePlayer() + ". Please enter your move: ");
		group.getChildren().addAll(feedback);

		TextField txtInput = new TextField();
		txtInput.setLayoutX(30);
		txtInput.setLayoutY(450);
		group.getChildren().addAll(txtInput);

		Button submit = new Button("Submit");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if ("network".equals(gameType)) {
					if (myTurn()) {
						input = txtInput.getText();
					}
				} else {
					input = txtInput.getText();
					System.out.println("You clicked submit!");
					txtInput.clear();
				}

				System.out.println("You clicked submit!");
				txtInput.clear();
			}
		});
		submit.setLayoutX(200);
		submit.setLayoutY(450);
		group.getChildren().addAll(submit);

		Scene board = new Scene(group, 460, 500);
		board.setFill(Color.BROWN);
		primaryStage.setScene(board);
		primaryStage.show();
	}

	public void connectToServer() {
		System.out.println("before first try catch");
		try {
			Socket socket = new Socket("localhost", 8000);
			System.out.println("socket initialized");
			toServer = new DataOutputStream(socket.getOutputStream());
			toServer.flush();
			System.out.println("toserver initialized");
			fromServer = new DataInputStream(socket.getInputStream());
			System.out.println("fromserver initialized");

			System.out.println("Server connection is complete.");
		} catch (IOException e) {
			System.out.println("Client/Server Connection Failed");
			System.err.println(e);
		}
		try {
			System.out.println("attempting to read player id...");
			playerPiece = fromServer.readChar();
			System.out.println("Player ID is: " + playerPiece);
		} catch (Exception e) {
			System.err.println(e);
		}
		if (!myTurn()) {
			try {
				input = fromServer.readLine();
				System.out.println("received first move: " + input);
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}

	public boolean myTurn() {
		return game.getActivePlayer().getPiece() == playerPiece;
	}

	/**
	 * Class to do complicated work in different thread from the GUI.
	 * 
	 * @author Ian Skelskey
	 * @version 1.0
	 * @since 09-15-2021
	 */
	class WorkerThread extends Thread {
		Stage primaryStage;

		WorkerThread(Stage stage) {
			primaryStage = stage;
			if ("menu".equals(state)) {
				printMenu(primaryStage);
			} else {
				printBoard(primaryStage);
			}
		}

		@Override
		public void run() {
			Runnable updater = new Runnable() {
				@Override
				public void run() {
					if ("menu".equals(state)) {
						printMenu(primaryStage);
					} else {
						printBoard(primaryStage);
					}
				}
			};
			do {
				game = new CheckersLogic();

				Scanner scan = new Scanner(System.in);
				CheckersComputerPlayer cpu1 = new CheckersComputerPlayer('o', game.getBoard());
				CheckersComputerPlayer cpu2 = new CheckersComputerPlayer('x', game.getBoard());
				int turnCount = 1;
				do { // turn loop
						// Creating Menu Scene
										if ("local".equals(gameType) && isSinglePlayer && game.getActivePlayer().getPiece() == 'o') {
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						input = cpu1.nextMove();
					}
					if ("network".equals(gameType) && !myTurn() && turnCount != 1) {
						try {
							System.out.println("getting move from opponent");
							input = fromServer.readLine();
							System.out.println("network input: " + input);
						} catch (IOException e) {
							System.err.println(e);
						}
					}
					if (!input.isEmpty()) {
						game.passInput(input);
						if (game.validateInput() && game.validateMove()) {
							game.turn();
							Platform.runLater(updater);
							System.out.println("Player " + game.getActivePlayer() + " - your turn.");
														if (gameType == "network" && myTurn()) {
								try {
									System.out.println("attempting to send input to server..." + input);
									toServer.writeBytes(input + "\n");
									toServer.flush();
																		System.out.println("input sent.");
								} catch (Exception e) {
									System.err.println(e);
								}
							}

							game.clearInput();
							input = "";
							System.out.println("turn #: " + turnCount);
							turnCount++;
							game.switchPlayer();
						}
					}
				} while (!game.isOver());
			} while (true);
		}
	}
}
