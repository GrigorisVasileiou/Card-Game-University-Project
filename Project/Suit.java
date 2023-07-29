public class Suit {
	private int suit;
	
	// All possible suits that we have
	public static Suit HEARTS = new Suit(0);
	public static Suit DIAMONDS = new Suit(1);
	public static Suit CLUBS = new Suit(2);
	public static Suit PIKES = new Suit(3);
	
	private Suit(int suit) {
		this.suit = suit;
	}
	
	@Override
	public String toString() {
		switch (suit) {
		case 0:
			return "H";
		case 1:
			return "D";
		case 2:
			return "C";
		case 3:
			return "P";
		default:
			// this should not happen!
			return "";
		}
	}
	
	public boolean equals(Suit other) {
		return this.suit == other.suit;
	}
}
