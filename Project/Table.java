import java.util.ArrayList;

public class Table {
	private Pile pile = new Pile();
	private ArrayList<Card> cardsThrown = new ArrayList<>();
	private Card topCard = null; // the last card that was thrown
	
	public static void main(String args[]) {
		Table table = new Table();
		int numberOfTimesToDraw = 60;
		
		for (int i = 0; i < numberOfTimesToDraw; ++i) {
			System.out.println("=====================================================");
			System.out.println("Table status:");
			System.out.println(table);
			Card drawnCard = table.drawCard();
			System.out.println("Card drawn: " + drawnCard);
			if (i%2 == 1) {
				System.out.println("Card is thrown!");
				table.throwCard(drawnCard);
			}
		}
	}
	
	public Table() {
		// create a full pile of 40 cards!
		pile.fill();
		openFirstCardInTable();
	}
	
	public void throwCard(Card card) {
		throwCardInTable(card);
	}
	
	public Card getTopCard() { return topCard; }
	
	public Card drawCard() {
		handleEmptyPile();
		return pile.nextCard();
	}
	
	private void openFirstCardInTable() {
		throwCardInTable(pile.nextCard());
	}
	
	private void throwCardInTable(Card card) {
		topCard = card;
		cardsThrown.add(card);
	}
	
	private void handleEmptyPile() {
		if (!pile.isEmpty()) { return ; }
		
		pile.fill(cardsThrown);
		
		cardsThrown.clear();
		topCard = null;
		
		openFirstCardInTable();
	}
	
	@Override
	public String toString() {
		String result = new String();
		
		result = result + "Pile: " + pile + "\n";
		result = result + "Cards thrown: " + cardsThrown + "\n";
		result = result + "Top Card: " + topCard + "\n";
		
		return result;
	}
}
