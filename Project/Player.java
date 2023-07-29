package assignment;
//GRIGORIOS - RAFAIL VASILEIOY  AM: 5042

import java.util.Scanner;

public class Player {
	private String name;
	private Hand hand = new Hand();
	
	public static void main(String args[]) {
		testHumanPlayer();
		//testComputer();
	}
	
	public static void testHumanPlayer() {
		Player player = new Player("FlashGordon");
		Table table = new Table();
		
		System.out.println("Starting table:");
		System.out.println(table);
		
		// we let the computer draw some times
		int drawCount = 5;
		player.draw(table, drawCount);
		
		System.out.println("Table after player's draw:");
		System.out.println(table);
		player.hand.printHand();
		System.out.println();
		
		while (true) {
			System.out.println("=====================================================");
			System.out.println(table);
			player.hand.printHand();
			System.out.println();
			
			
			Card selectedCard = player.selectCard(table);
			
			if (selectedCard == null) {
				return; 
			}
			
			player.throwCard(table, selectedCard);
		}
	}
	
	public static void testComputer() {
		Player player = new Player("Computer");
		Table table = new Table();
		
		System.out.println("Starting table:");
		System.out.println(table);
		
		// we let the computer draw some times
		int drawCount = 5;
		player.draw(table, drawCount);
		
		System.out.println("Table after player's draw:");
		System.out.println(table);
		player.hand.printHand();
		System.out.println();
		
		// let's play for some times
		int playCount = 3;
		
		for (int i = 0; i < playCount; ++i) {
			System.out.println("========================================================");
			System.out.println(table);
			player.hand.printHand();
			System.out.println();
			
			Card matchedCard = player.computerSelectCard(table);
			
			if (matchedCard != null) {
				System.out.println("Matched card: " + matchedCard);
				player.throwCard(table, matchedCard);
			} else {
				System.out.println("No matched card!");
				break;
			}
		}
	}
	
	public Player(String name) {
		this.name = name;
	}
	
	// Draw 1 card from the table
	public void draw(Table table) {
		hand.addCard(table.drawCard());
	}
	
	// Draw count cards from the table (only if count >= 1)
	public void draw(Table table, int count) {
		for (int i = 0; i < count; ++i) {
			draw(table);
		}
	}
	
	// Throw a card from our hand into the table
	public void throwCard(Table table, Card card) {
		hand.removeCard(card);
		table.throwCard(card);
	}
	
	// Do we have an empty hand?
	public boolean isDone() {
		return hand.isEmpty();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	// What card will the computer select?
	public Card computerSelectCard(Table table) {
		return hand.findMatchingCard(table.getTopCard());
	}
	
	// Ask the user for a card to match!
	public Card selectCard(Table table) {
		Card topCard = table.getTopCard();
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			// ask user for card
			hand.printHand();
			System.out.println("\n");
			System.out.println("Top Card: " + topCard);
			
			System.out.print("Select a card to throw or -1 to pass: " );
			int index = scanner.nextInt();
			Card card = hand.getCard(index);
			
			if (index == -1) {
				return null;
			} else if (card == null) {
				System.out.println("Invalid Index!");
			} else if (card.isEight()) {
				while (true) {
					System.out.println("Select suit [H, D, P, C]: " );
					String suit = scanner.next();
					
					if (suit.equalsIgnoreCase("H")) {
						card.setSuit(Suit.HEARTS);
						return card;
					} else if (suit.equalsIgnoreCase("D")) {
						card.setSuit(Suit.DIAMONDS);
						return card;
					} else if (suit.equalsIgnoreCase("P")) {
						card.setSuit(Suit.PIKES);
						return card;
					} else if (suit.equalsIgnoreCase("C")) {
						card.setSuit(Suit.CLUBS);
						return card;
					} else {
						System.out.println("Invalid input");
					}
				}
			} else if (card.matches(topCard)) {
				return card;
			} else {
				System.out.println("Selected card [" + card + "] doesn't match top card [" + topCard + "]");
			}
		}
	}
}
