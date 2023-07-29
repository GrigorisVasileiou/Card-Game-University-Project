import java.util.Scanner;

public class Game {
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		int numPlayers; 
		
		while (true) {
			System.out.print("Enter number of players: ");
			numPlayers = scanner.nextInt();
			
			if (numPlayers <= 0 || numPlayers >= 8) {
				System.out.println("Invalid number of players. Try again!");
			} else {
				break;
			}
		}
		
		CrazyEights crazyEights = new CrazyEights(numPlayers);
		
		// let the fun begin!
		crazyEights.play();
	}
}
