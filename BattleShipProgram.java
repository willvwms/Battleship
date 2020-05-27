import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;


public class BattleShipProgram {

	public static void main (String[] args) throws IOException {

		do {

			playGame();

		} while (continuePlaying());

	}

	public static void playGame () throws IOException {

		HashMap<String, Integer> ships = new HashMap<String, Integer>();
	    ships.put("Carrier", 5);
	    ships.put("Battleship", 4);
	    ships.put("Cruiser", 3);
	    ships.put("Submarine", 3);
	    ships.put("Destroyer", 2);

		Board board = new Board(ships); 
		// board.printShipData(); // <= Debug: Uncomment to print ship data (table form)
		board.drawMap();
		do {

			// board.showHidden();  // <= Debug: Uncomment to show ship locations on map
			Point guess = getUserInput(board.getGuesses());
			board.addGuess(guess);
			board.drawMap();
			board.determineIfHit(guess);
			board.checkSunk();

		} while (!board.allSunk());

		System.out.println("You sank all the computer's ships! YOU WIN!");
	}


	public static Point getUserInput(ArrayList<Point> guesses) throws IOException {

		Scanner input = new Scanner(System.in);


		boolean validInput;
		Point point;

		do {

			validInput = true;

			ArrayList<Integer> values = new ArrayList<Integer>();
			
			System.out.print("Enter coordinates (x, y): ");
	    	char[] charArray = input.nextLine().toCharArray();
	    	System.out.println();

			for (char ch : charArray) {
				if (Character.isDigit(ch)) 
					values.add(Integer.valueOf(String.valueOf(ch)));
	    	}	    		


	    	if (values.size() != 2) {
	    		System.out.println("Error: enter two numbers between 0 and 9!");
	    		System.out.println();
	    		validInput = false;
	    		point = null;
	    	}
	    	else {
		    	Integer x = values.get(0), y = values.get(1);
		    	point = new Point(x,y);
	
		    	for (Point p : guesses) {
		    	
		    		if (p.equals(point)) {
		    			validInput = false;
		    			System.out.printf("Oops, you already guessed %d, %d!\n\n", x, y);
		    		}

	    		}		    	
	    	}



		} while (!validInput);

		return point;
		
	}


	public static boolean continuePlaying() throws IOException {

		Scanner input = new Scanner(System.in);
		char userResponse;
		boolean continuePlaying;

		do {

			System.out.print("\nDo you want to play again? (y/n):");

			userResponse = input.next().charAt(0);

			if(userResponse != 'y' && userResponse != 'n') 
				{
					System.out.println("Hm I didn't get you -- let's try again:");
				}

		}  while (userResponse != 'y' && userResponse != 'n');
		
		if (userResponse == 'y') {
			continuePlaying = true;
			System.out.println("\nAll right, let's go again!");
		} else {
			continuePlaying = false;
			System.out.println("\nOk, see you later!");
			System.out.println();
		}

		return continuePlaying; 

	}

}
