package main;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This is the card driver to determine which card is to be displayed, as well
 * as organizing the card values
 * 
 * @author Logan Korec & Nathan Kao
 * 
 *
 */
public class Card extends Parent {
	
	/* Suit
	 * Card Generation
	 */
	enum Suit {
		Clubs, Diamonds, Hearts, Spades
	};
	 // Card Value
	enum Rank {
		Two(2), Three(3), Four(4), Five(5), Six(6), Seven(7), Eight(8), Nine(9), Ten(10), Jack(10), Queen(10), King(10),
		Ace(11);

		int value;

		Rank(int value) {
			this.value = value;
		}
	};

	public Suit suit;
	public Rank rank;
	public int value;
	public Image pic;

	
	// Determines the Suit Rank and Image Name
	public Card(Suit suit, Rank rank, Image pic) {

		this.suit = suit;
		this.rank = rank;
		this.value = rank.value;
		this.pic = pic;

		ImageView iv = new ImageView(pic);

		getChildren().add(iv);
	}

	@Override
	public String toString() {
		return rank.toString() + " of " + suit.toString();

	}
}
