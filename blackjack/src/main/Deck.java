package main;

import javafx.scene.image.Image;
import main.Card.Rank;
import main.Card.Suit;

/**
 * This is the deck driver to determine which image to display, as well as the drawing of cards
 * 
 * @author Logan Korec & Nathan Kao
 *
 */
public class Deck {

	private Card[] cards = new Card[52];

	public Deck() {
		refill();
	}

	public void refill() {

		/**
		 * Card legend Number represents card value with exception of ace, being 13, and
		 * face cards Jack is 10, Queen is 11, King is 12 
		 * 1-13 is clubs 
		 * 14-26 is diamonds 
		 * 27-39 is hearts 
		 * 40-52 is spades
		 */

		int i = 1;
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				String fname = "";
				
				if (suit == Suit.Clubs) {
					fname = String.valueOf(i) + ".png";
				} else if (suit == Suit.Diamonds) {
					fname = String.valueOf(i) + ".png";
				} else if (suit == Suit.Hearts) {
					fname = String.valueOf(i) + ".png";
				} else if (suit == Suit.Spades) {
					fname = String.valueOf(i) + ".png";
				}

				cards[i - 1] = new Card(suit, rank, new Image(fname, 90, 126, true, true));
				(i)++;
			}
		}
	}

	public Card drawCard() {
		
		/* Draws a random card using a random number generator MATH and will locate the number of the card
		 * associated with the name of the png.
		 */
		System.out.println();
		Card card = null;
		while (card == null) {
			int index = (int) (Math.random() * cards.length);
			card = cards[index];
			cards[index] = null;

		}
		return card;
	}

}