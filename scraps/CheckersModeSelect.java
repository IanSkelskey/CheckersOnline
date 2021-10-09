package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CheckersModeSelect extends Application {
	/**
	 * Main method.
	 * 
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) {
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
					//Application.launch(CheckersLocalClient.class);
					System.exit(0);
				} else if (group2.getSelectedToggle() == rad2Player) {
					System.out.println("2-Player clicked");
					//Application.launch(CheckersLocalClient.class);
					System.exit(0);
				} else if (group2.getSelectedToggle() == radNetwork) {
					System.out.println("Network game selected!");
					Application.launch(CheckersNetworkClient.class);
					System.exit(0);
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
		public static void main(String[] args) {
		launch(args);
	}
}
