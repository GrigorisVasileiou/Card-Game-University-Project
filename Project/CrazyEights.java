package assignment;
//GRIGORIOS - RAFAIL VASILEIOY  AM: 5042

public class CrazyEights {
	private Table table;
	private Player player[];
	private int numPlayers;
	
	public CrazyEights(int numPlayers) {
		this.numPlayers = numPlayers;
		
		player = new Player[numPlayers];
		
		player[0] = new Player("HUMAN");
		
		for (int i = 1; i < numPlayers; ++i) {
			player[i] = new Player("COMPUTER_" + i);
		}
		
		table = new Table();
		
		drawCardsInitial();
	}
	
	public void play() {
		int turn = 0;
		
		// Game loop!
		while (true) {
			int donePlayerIndex = findDonePlayer();
			
			if (donePlayerIndex != -1) {
				announceWinner(donePlayerIndex);
				return ;
			}
			
			System.out.println(player[turn] + " turn:");
			System.out.println("Table top card: " + table.getTopCard());
			player[turn].getHand().printHand();
			System.out.println();
			Card selectedCard = null;
			
			if (turn == 0) { 
				selectedCard = player[turn].selectCard(table);
			} else {
				selectedCard = player[turn].computerSelectCard(table);
			}
			
			if (selectedCard != null) {
				player[turn].throwCard(table, selectedCard);
				System.out.println(player[turn] + " threw card " + selectedCard);
			} else {
				player[turn].draw(table);
				System.out.println(player[turn] + " drew a card");
				// compute whose turn is next
				turn = turn + 1;
				if (turn == numPlayers) {
					turn = 0;
				}
			}
		}
	}
	
	private void announceWinner(int donePlayerIndex) {
		if (donePlayerIndex == 0) {
			System.out.println("Human player won!");
		} else {
			System.out.println("Computer won at index: " + donePlayerIndex + " [" + player[donePlayerIndex] + "]");
		}
	}

	// Return the index in the array of a player who is done, otherwise return -1
	private int findDonePlayer() {
		for (int i = 0; i < numPlayers; ++i) {
			if (player[i].isDone()) {
				return i;
			}
		}
		
		return -1;
	}
	
	// when the game starts all players start with 5 cards
	private void drawCardsInitial() {
		for (int i = 0; i < numPlayers; ++i) {
			player[i].draw(table, 5);
		}
	}
}
