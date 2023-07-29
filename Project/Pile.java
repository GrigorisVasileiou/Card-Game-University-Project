import java.util.ArrayList;
import java.util.Random;

public class Pile {
	// the stack of cards is an array list of Card objects
	// The top of the stack is the beginning of the list!
	private ArrayList<Card> stackOfCards = new ArrayList<>();
	
	public static void main(String args[]) {
		Pile allCardsPile = new Pile();
		
		allCardsPile.fill();
		
		allCardsPile.print();
		System.out.println();
		
		Pile fourCardsPile = new Pile();
		ArrayList<Card> fourCards = new ArrayList<>();
		fourCards.add(new Card(Suit.CLUBS, 1));
		fourCards.add(new Card(Suit.DIAMONDS, 8));
		fourCards.add(new Card(Suit.HEARTS, 4));
		fourCards.add(new Card(Suit.PIKES, 0));
		
		fourCardsPile.fill(fourCards);
		
		fourCardsPile.print();
		System.out.println();
		
		System.out.println("Draining full pile...");
		drainPile(allCardsPile);
		
		System.out.println("Draining four cards pile...");
		drainPile(fourCardsPile);
	}
	
	private static void drainPile(Pile pile) {
		System.out.println("Drain start...");
		while (!pile.isEmpty()) {
			Card nextCard = pile.nextCard();
			System.out.println("\tNext card: " + nextCard);
		}
		System.out.println("Drain ended!!!!");
	}
	
	// Create all the cards and shuffle them!
	public void fill() {
		stackOfCards.clear();
		createFullStackOfCards();
		shuffle();
	}
	
	public void fill(ArrayList<Card> otherStackOfCards) {
		stackOfCards.clear();
		stackOfCards.addAll(otherStackOfCards);
		shuffle();
	}
	
	// Return the next card if any. If the stack is empty, then return null!!!!!
	public Card nextCard() {
		if (!isEmpty()) {
			return stackOfCards.remove(0);
		} else {
			return null;
		}
	}
	
	// Check if the stack of cards is empty
	public boolean isEmpty() {
		return stackOfCards.isEmpty();
	}
	
	// Helper method to shuffle our cards
	private void shuffle() {
		Random random = new Random();
		ArrayList<Card> cardsInRandomOrder = new ArrayList<>();
		
		while (!stackOfCards.isEmpty()) {
			int numberOfRemainingCards = stackOfCards.size();
			int randomIndex = random.nextInt(numberOfRemainingCards);
			Card randomCard = stackOfCards.remove(randomIndex);
			
			cardsInRandomOrder.add(randomCard);
		}
		
		stackOfCards = cardsInRandomOrder;
	}
	
	// Print the cards in the stack in one line!
	public void print() {
		for (Card card : stackOfCards) {
			System.out.print(card + " ");
		}
	}
	
	@Override
	public String toString() {
		String result = new String();
		
		for (Card card : stackOfCards) {
			result = result + card + " ";
		}
		
		return result;
	}
	
	// Create all 40 cards!
	private void createFullStackOfCards() {
		createFullStackOfCards(Suit.CLUBS);
		createFullStackOfCards(Suit.DIAMONDS);
		createFullStackOfCards(Suit.HEARTS);
		createFullStackOfCards(Suit.PIKES);
	}
	
	// Create all 10 cards for the given suit
	private void createFullStackOfCards(Suit suit) {
		for (int number = 0; number <= 9; ++number) {
			Card card = new Card(suit, number);
			stackOfCards.add(card);
		}
	}
}
