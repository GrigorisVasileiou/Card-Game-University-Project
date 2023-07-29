import java.util.ArrayList;
import java.util.HashMap;

public class Hand {
	private ArrayList<Card> cards = new ArrayList<>();
	private HashMap<Suit, Integer> numOfCardsPerSuit = new HashMap<>();
	
	public static void main(String args[]) {
		Hand hand = new Hand();
		
		System.out.println("Empty hand:");
		System.out.println(hand);
		
		ArrayList<Card> cards = new ArrayList<>();
		cards.add(new Card(Suit.CLUBS, 1));
		cards.add(new Card(Suit.DIAMONDS, 8));
		cards.add(new Card(Suit.DIAMONDS, 2));
		cards.add(new Card(Suit.HEARTS, 4));
		cards.add(new Card(Suit.HEARTS, 2));
		cards.add(new Card(Suit.PIKES, 0));
		cards.add(new Card(Suit.PIKES, 3));
		cards.add(new Card(Suit.PIKES, 7));
		
		for (Card card : cards) {
			hand.addCard(card);
		}
		
		System.out.println("Hand:");
		System.out.println(hand);
		
		hand.printHand();
		System.out.println();
		
		// Let's check now the matching functionality!
		System.out.println("Matching test for hand with no eight!!!");
		hand = new Hand();
		cards.clear();
		cards.add(new Card(Suit.CLUBS, 1));
		cards.add(new Card(Suit.HEARTS, 4));
		
		for (Card card : cards) {
			hand.addCard(card);
		}
		
		hand.printHand();
		System.out.println();
		
		// match clubs with different number
		tryToFindMatch(hand, new Card(Suit.CLUBS, 9));
		tryToFindMatch(hand, new Card(Suit.DIAMONDS, 1));
		// match clubs with same number
		tryToFindMatch(hand, new Card(Suit.HEARTS, 5));
		tryToFindMatch(hand, new Card(Suit.DIAMONDS, 4));
		// No match because we do not have an eight!
		tryToFindMatch(hand, new Card(Suit.PIKES, 0));
		tryToFindMatch(hand, new Card(Suit.PIKES, 3));
		tryToFindMatch(hand, new Card(Suit.DIAMONDS, 9));
		
		
		System.out.println("Matching test for hand with eights!!!");
		hand = new Hand();
		cards.clear();
		cards.add(new Card(Suit.CLUBS, 1));
		cards.add(new Card(Suit.HEARTS, 4));
		cards.add(new Card(Suit.HEARTS, 3));
		cards.add(new Card(Suit.CLUBS, 8));
		
		for (Card card : cards) {
			hand.addCard(card);
		}
		
		hand.printHand();
		System.out.println();
		System.out.println(hand);
		
		// match clubs with different number
		tryToFindMatch(hand, new Card(Suit.CLUBS, 9));
		tryToFindMatch(hand, new Card(Suit.DIAMONDS, 1));
		// match clubs with same number
		tryToFindMatch(hand, new Card(Suit.HEARTS, 5));
		tryToFindMatch(hand, new Card(Suit.DIAMONDS, 4));
		// Here it must return an eight... the 8 CLUBS but it will change its suit to the largest --> HEARTS
		tryToFindMatch(hand, new Card(Suit.PIKES, 0));
		tryToFindMatch(hand, new Card(Suit.PIKES, 2));
		tryToFindMatch(hand, new Card(Suit.DIAMONDS, 9));
	}
	
	private static void tryToFindMatch(Hand hand, Card cardToMatch) { 
		System.out.println("================================================");
		System.out.println("Trying to find a match for card: " + cardToMatch);
		hand.printHand();
		System.out.println();
		Card matchedCard = hand.findMatchingCard(cardToMatch);
		
		if (matchedCard == null) {
			System.out.println("No match found!");
		} else {
			System.out.println("Match found: " + matchedCard);
		}
		System.out.println("================================================");
	}
	
	public Hand() {
		numOfCardsPerSuit.put(Suit.CLUBS, 0);
		numOfCardsPerSuit.put(Suit.DIAMONDS, 0);
		numOfCardsPerSuit.put(Suit.HEARTS, 0);
		numOfCardsPerSuit.put(Suit.PIKES, 0);
	}
	
	public void addCard(Card card) {
		cards.add(card);
		increaseCountPerSuit(card);
	}
	
	public void removeCard(Card card) {
		cards.remove(card);
		decreaseCountPerSuit(card);
	}
	
	public Card getCard(int index) {
		if (index < 0 || index >= cards.size()) {
			return null;
		}
		return cards.get(index);
	}
	
	public void printHand() {
		for (int i = 0; i < cards.size(); ++i) {
			System.out.print("\t" + i);
		}
		System.out.println();
		for (int i = 0; i < cards.size(); ++i) {
			System.out.print("\t" + getCard(i));
		}
	}
	
	public boolean isEmpty() {
		return cards.isEmpty();
	}
	
	public Card findMatchingCard(Card otherCard) {
		return findMatchingCardBonus(otherCard);
	}
	
	public Card findMatchingCardBonus(Card otherCard) {
		Card matchedCard = null;
		
		ArrayList<Suit> candidateSuits = new ArrayList<>();
		
		addSuitIfSuitable(candidateSuits, Suit.CLUBS, otherCard);
		addSuitIfSuitable(candidateSuits, Suit.DIAMONDS, otherCard);
		addSuitIfSuitable(candidateSuits, Suit.HEARTS, otherCard);
		addSuitIfSuitable(candidateSuits, Suit.PIKES, otherCard);
		
		Suit suit = findCandidateSuitWithLargestCount(candidateSuits);
		
		if (suit != null) {
			matchedCard = findCardWithSuitThatMatches(suit, otherCard);
		}
		
		if (matchedCard != null) {
			return matchedCard;
		}
		
		// No matching card found!
		// Do we have an eight?
		matchedCard = findCardWithNumberEight();
		
		if (matchedCard != null) {
			matchedCard.setSuit(getSuitWithLargestCount());
		}
		
		return matchedCard;
	}
	
	private Card findCardWithSuitThatMatches(Suit suit, Card otherCard) {
		for (Card card : cards) {
			if (!card.getSuit().equals(suit)) {
				continue;
			}
			
			if (otherCard.getSuit().equals(suit)) {
				return card;
			} else if (card.getNumber() == otherCard.getNumber()) {
				return card;
			}
		}
		return null;
	}

	private Suit findCandidateSuitWithLargestCount(ArrayList<Suit> candidateSuits) {
		Suit largestCountSuit = null;
		int largestCountSoFar = -1;
		
		for (Suit suit : candidateSuits) {
			int countForCurrentSuit = numOfCardsPerSuit.get(suit);
			
			if (largestCountSuit == null || countForCurrentSuit > largestCountSoFar) {
				largestCountSuit = suit;
				largestCountSoFar = countForCurrentSuit;
			}
		}
		
		return largestCountSuit;
	}

	private void addSuitIfSuitable(ArrayList<Suit> candidateSuits, Suit suit, Card otherCard) {
		if (numOfCardsPerSuit.get(suit) > 0 && (otherCard.getSuit().equals(suit) || cardWithSameNumberAndSuitExists(suit, otherCard.getNumber()))) {
			candidateSuits.add(suit);
		}
	}
	
	private boolean cardWithSameNumberAndSuitExists(Suit suit, int number) {
		for (Card card : cards) {
			if (card.getSuit().equals(suit) && card.getNumber() == number) {
				return true;
			}
		}
		return false;
	}
	
	public Card findMatchingCardNormal(Card otherCard) {
		Card matchedCard = null;
		
		// Try to find the FIRST card the matches
		for (int i = 0; i < cards.size(); ++i) {
			if (cards.get(i).matches(otherCard)) {
				matchedCard = cards.get(i);
				break;
			}
		}
		
		if (matchedCard != null) {
			return matchedCard;
		}
		
		// No matching card found!
		// Do we have an eight?
		matchedCard = findCardWithNumberEight();
		
		if (matchedCard != null) {
			matchedCard.setSuit(getSuitWithLargestCount());
		}
		
		return matchedCard;
	}
	
	private Suit getSuitWithLargestCount() {
		Suit largestCountSuit = null;
		int largestCountSoFar = -1;
		
		for (Suit suit : numOfCardsPerSuit.keySet()) {
			int countForCurrentSuit = numOfCardsPerSuit.get(suit);
			
			if (largestCountSuit == null || countForCurrentSuit > largestCountSoFar) {
				largestCountSuit = suit;
				largestCountSoFar = countForCurrentSuit;
			}
		}
		
		return largestCountSuit;
	}
	
	// If a card with number eight exists in our hand then return it. Otherwise, return null.
	private Card findCardWithNumberEight() {	
		for (int i = 0; i < cards.size(); ++i) {
			if (cards.get(i).isEight()) {
				return cards.get(i);
			}
		}
		
		return null;
	}
	
	private void increaseCountPerSuit(Card card) {
		if (!card.isEight()) {
			Integer previousCount = numOfCardsPerSuit.get(card.getSuit());
			numOfCardsPerSuit.put(card.getSuit(), previousCount + 1);
		}
	}
	
	private void decreaseCountPerSuit(Card card) {
		if (!card.isEight()) {
			Integer previousCount = numOfCardsPerSuit.get(card.getSuit());
			numOfCardsPerSuit.put(card.getSuit(), previousCount - 1);
		}
	}
	
	@Override
	public String toString() {
		String result = new String();
		
		result = result + "Cards: " + cards + "\n";
		result = result + "CLUBS: " + numOfCardsPerSuit.get(Suit.CLUBS) + "\n";
		result = result + "DIAMONDS: " + numOfCardsPerSuit.get(Suit.DIAMONDS) + "\n";
		result = result + "HEARTS: " + numOfCardsPerSuit.get(Suit.HEARTS) + "\n";
		result = result + "PIKES: " + numOfCardsPerSuit.get(Suit.PIKES) + "\n";
		
		return result;
	}
}
