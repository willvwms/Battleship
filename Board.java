import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class Board {

	private ArrayList<Ship> ships;
	private ArrayList<Point> guesses;

	public Board (HashMap<String, Integer> ships) {

		guesses = new ArrayList<Point>();
		this.ships = generateShips(ships);
	
	}

	public ArrayList<Point> getGuesses () {
		
		return new ArrayList<Point>(guesses);
		
	}

	public void addGuess (Point p) {
		
		guesses.add(p);

	}

	public ArrayList<Ship> getShips() {
		return new ArrayList<Ship>(ships);
	}

	public void determineIfHit(Point guess){

		boolean isHit = false;

		for (Ship ship : getShips()) {
			if (ship.containsPoint(guess))
				isHit = true;
		}

		// Display feedback message to user:
		if (isHit)
			System.out.printf("%s: Hit!\n\n", guess.toString());
		else
			System.out.printf("%s: Miss!\n\n", guess.toString());

	}

	public void checkSunk(){

		for (Ship ship : getShips()) {
			
			if ( (ship.hitCount( getGuesses() ) == ship.getLength()) && !ship.isSunk() ) {
				System.out.println(ship.sunkMessage());
				ship.sink(true);
			}

		}

	}


	public ArrayList<Ship> generateShips (HashMap<String, Integer> battleShips) {
          
		ArrayList<Ship> ships = new ArrayList<Ship>();
		Random random = new Random();

        Iterator<Map.Entry<String, Integer>> iterator = battleShips.entrySet().iterator();

        while(iterator.hasNext()){

            Map.Entry<String, Integer> entry = iterator.next();
			Ship ship;
			boolean collision;

			do {
				collision = false;

			    int size = entry.getValue().intValue();
			    String name = entry.getKey();
				boolean orientation = random.nextBoolean();
				Point origin = generateOrigin(orientation, size);
				ship = new Ship(origin, orientation, size, name);

				for (Ship otherShip : ships) {
					
					if (ship.collidesWith(otherShip)) {
						// System.out.println("Collison while building " + name +". Re-generating ship.");
						collision = true;
					}
				}

				if (!collision)
					ships.add(ship);

			} while (collision);

            iterator.remove(); 

    	}	
        
        return new ArrayList<Ship>(ships);

	}

	public Point generateOrigin(boolean vertical, int length) {

		Random random = new Random();

		int x = !vertical ? random.nextInt( 10 - (length - 1)) : random.nextInt(10);
		int y = vertical ? random.nextInt( 10 - (length - 1)) : random.nextInt(10);

		return new Point(x, y);

	}

	public void printShipData () {
		ships.forEach(ship -> System.out.println(ship.toString()));
	}

	public boolean isShip (int x, int y) {
		
		boolean isShip = false;
		Point p = new Point(x, y);

        for (Ship s : ships)
        	if (s.containsPoint(p))
        		isShip = true;

		return isShip;

	}

	public void showHidden () {

		for (int row = 9; row >= 0; row--) {
		  System.out.print(row + " ");

		for (int col = 0; col <=9; col++) {

		    if (isShip(col, row))
		      System.out.print("O ");
		    else
		      System.out.print("~ ");
		  }
		  System.out.println();

		}

		System.out.print("  ");
		for (int i = 0; i <=9; i++) {
	  
	 		 System.out.print(i + " ");
		}
  
  		System.out.println();

	}

	public void drawMap() { 

		for (int row = 9; row >= 0; row--) {
		  System.out.print(row + " ");

		for (int col = 0; col <=9; col++) {

		    if (isHitAtCoordinate(col, row))
		      System.out.print("X ");
		    else if (isMissAtCoordinate(col, row))
		      System.out.print(". ");
		    else
		      System.out.print("~ ");
		  }
		  System.out.println();

		}

		System.out.print("  ");
		for (int i = 0; i <=9; i++) {
	  
	 		 System.out.print(i + " ");
		}
  
  		System.out.println();
  		System.out.println();

	}


	public boolean isHitAtCoordinate(int col, int row) {

			 	
		boolean isHit = false;

		Point p = new Point(col, row);
		
		for (Ship ship : getShips()) {
			
			if (alreadyGuessed(p) && ship.containsPoint(p))
				isHit = true;
		}
			
		return isHit;

	}

	public boolean isMissAtCoordinate(int col, int row) {
	
		boolean isMiss = false;

		Point p = new Point(col, row);
	
		boolean notInShips = true;
		for (Ship ship : getShips()) {
			if (ship.containsPoint(p))
				notInShips = false;
		}

		if (alreadyGuessed(p) && notInShips)
			isMiss = true;

		return isMiss;

	}

	public boolean alreadyGuessed (Point currentPoint) {

		boolean alreadyGuessed = false;

		for (Point guess : guesses) {
			if (guess.equals(currentPoint))
				alreadyGuessed = true;
		}

		return alreadyGuessed;
	}

	public boolean allSunk() {
		boolean allSunk = true;
		for (Ship ship : ships) {
			if (!ship.isSunk())
				allSunk = false;
		}
		return allSunk;
	}
}
