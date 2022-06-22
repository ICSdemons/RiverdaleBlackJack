package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This is the graphics driver that handles all elements of the graphical
 * output, including managing the buttons and determining the winner
 * 
 * @author Logan Korec & Nathan Kao
 *
 */
public class BlackjackApp extends Application {

	/*
	 * Variables for graphic application
	 */
	private Deck deck = new Deck();
	private Hand dealer, player;

	private Text message = new Text();

	private static int score;
	private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);
	private HBox dealerCards = new HBox(20);
	private HBox playerCards = new HBox(20);

	/*
	 * Creates the graphical output
	 * 
	 */
	private Parent createContent() throws FileNotFoundException {
		
		
		dealer = new Hand(dealerCards.getChildren());
		player = new Hand(playerCards.getChildren());

		Pane root = new Pane();
		root.setPrefSize(800, 600);

		Region background = new Region();
		background.setPrefSize(800, 600);
		background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

		HBox rootLayout = new HBox(5);
		rootLayout.setPadding(new Insets(5, 5, 5, 5));

		// Left background
		Rectangle leftBG = new Rectangle(550, 550);
		leftBG.setArcWidth(50);
		leftBG.setArcHeight(50);
		leftBG.setFill(Color.GREEN);

		// Right background
		Rectangle rightBG = new Rectangle(220, 550);
		rightBG.setArcWidth(50);
		rightBG.setArcHeight(50);
		rightBG.setFill(Color.WHITE);

		// Left screen
		VBox leftVBox = new VBox(50);
		leftVBox.setAlignment(Pos.TOP_CENTER);

		Text dealerScore = new Text("Dealer: ");
		Text playerWinCount = new Text("Player win count : " + score);
		Text playerScore = new Text("Player: ");

		leftVBox.getChildren().addAll(dealerScore, dealerCards, message, playerCards, playerScore);

		// Right screen
		VBox rightVBox = new VBox(20);
		rightVBox.setAlignment(Pos.CENTER);

		Text moneyAmount = new Text("Welcome to Blackjack! Get 21 to win!");

		Button btnPlay = new Button("NEW GAME");
		Button btnHit = new Button("HIT");
		Button btnStand = new Button("STAND");

		HBox buttonsHBox = new HBox(15, btnHit, btnStand);
		buttonsHBox.setAlignment(Pos.CENTER);

		rightVBox.getChildren().addAll(btnPlay, moneyAmount, buttonsHBox, playerWinCount);

		// Add both stacks to root layout

		rootLayout.getChildren().addAll(new StackPane(leftBG, leftVBox), new StackPane(rightBG, rightVBox));
		root.getChildren().addAll(background, rootLayout);

		// Bind properties

		btnPlay.disableProperty().bind(playable);
		btnHit.disableProperty().bind(playable.not());
		btnStand.disableProperty().bind(playable.not());

		// Display the score values
		playerScore.textProperty().bind(new SimpleStringProperty("Player: ").concat(player.valueProperty().asString()));
		dealerScore.textProperty().bind(new SimpleStringProperty("Dealer: ").concat(dealer.valueProperty().asString()));
		
		
		player.valueProperty().addListener((obs, old, newValue) -> {
			if (newValue.intValue() >= 21) {
				endGame();
			}
		});

		dealer.valueProperty().addListener((obs, old, newValue) -> {
			System.out.println(obs);
			System.out.println(old);
			System.out.println(newValue);
			
			if (newValue.intValue() >= 21) {
				System.out.println("EndGame");
				endGame();
			}
		});

		// Input buttons

		btnPlay.setOnAction(e -> {
			startNewGame();
			playerWinCount.textProperty().bind(new SimpleStringProperty("Player win count : " + score));

		});

		btnHit.setOnAction(e -> {
			player.takeCard(deck.drawCard());
		});

		btnStand.setOnAction(e -> {
			while (dealer.valueProperty().get() < 17) {
				dealer.takeCard(deck.drawCard());
			}
		
			endGame();
		});

		return root;
	}

	// Start game
	private void startNewGame() {
		playable.set(true);
		message.setText("");

		deck.refill();

		dealer.reset();
		player.reset();

		dealer.takeCard(deck.drawCard());
		dealer.takeCard(deck.drawCard());
		player.takeCard(deck.drawCard());
		player.takeCard(deck.drawCard());
	}

	// Winner output
	private String endGame() {
		playable.set(false);

		int dealerValue = dealer.valueProperty().get();
		int playerValue = player.valueProperty().get();
		String winner = "Exceptional case: d: " + dealerValue + " p: " + playerValue;

		// Dealer win condition
		if (playerValue == dealerValue) {
			winner = "PUSH";
		} else if (dealerValue == 21 || playerValue > 21 || dealerValue == playerValue
				|| (dealerValue < 21 && dealerValue > playerValue)) {
			winner = "DEALER";
			// Player win condition
		} else if (playerValue == 21 || dealerValue > 21 || playerValue > dealerValue) {
			winner = "PLAYER";
			score=score+1;

		}

		if (winner == "PLAYER") {
			System.out.println(score);
			message.setText("YOU WIN!");
			

		} else if (winner == "DEALER") {
			message.setText("YOU LOSE!");
		} else {
			message.setText("PUSH");
		}

		return winner;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(createContent()));
		primaryStage.setWidth(800);
		primaryStage.setHeight(600);
		primaryStage.setResizable(false);
		primaryStage.setTitle("BlackJack");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}
}
