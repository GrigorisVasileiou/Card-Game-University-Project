package assignment;
//GRIGORIOS - RAFAIL VASILEIOY  AM: 5042

/**
 * Information about 1 card.
 *
 */
public class Card {
	private Suit suit;
	private int number; // number is from 0 to 9
	
	public Card(Suit suit, int number) {
		this.suit = suit;
		this.number = number;
	}
	
	// Accessor + mutator for Suit
	public Suit getSuit() { return suit; }
	public void setSuit(Suit suit) {
		this.suit = suit;
	}
	
	public int getNumber() { return number; }
	
	// Check if this card's number is 8
	public boolean isEight() {
		return number == 8;
	}
	
	/**
	 * Return true if this card matches the parameter card. They match if they have the same suit OR the same number.
	 */
	public boolean matches(Card otherCard) {
		return suit.equals(otherCard.getSuit()) || number == otherCard.number;
	}
	
	@Override
	public String toString() {
		return "" + number + suit;
	}
}
