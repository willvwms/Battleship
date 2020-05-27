import java.util.Random;
import java.util.ArrayList;

public class Ship 
{
	private Point origin;
	private final int length;
	private final boolean isVertical;
	private final String name;
	private boolean isSunk;

	public Ship (Point origin, boolean isVertical, int length, String name) {
		this.origin = new Point(origin);
		this.length = length;
		this.isVertical = isVertical;
		this.name = name;
	}

	public boolean containsPoint(Point p) {
		
		boolean containsPoint = false;

		for (int i = 0; i < this.length; i++) {
		
			int x = isVertical ? origin.getX() : (origin.getX() + i);
			int y = isVertical ? (origin.getY() + i) : origin.getY();

			if ( x == p.getX() && y == p.getY() )
				containsPoint = true;
		}

		return containsPoint;
	
	} 

	public boolean collidesWith(Ship other) {

		boolean collidesWith = false; 
		Point otherOrigin = other.getOrigin();

		for (int i = 0; i < other.length; i++) {
		
			int x = other.getIsVertical() ? otherOrigin.getX() : (otherOrigin.getX() + i);
			int y = other.getIsVertical() ? (otherOrigin.getY() + i) : otherOrigin.getY();
		
			if (containsPoint( new Point (x,y) )){				
				collidesWith = true;
				// System.out.println("collision: " + new Point (x,y).toString());
			}					
		}
		
		return collidesWith;
	
	}

	public int hitCount(ArrayList<Point> guesses) {

		int hitCount = 0;

		for (Point guess : guesses) {
			if (containsPoint(guess)) {
				hitCount++;
			}
		}

		return hitCount;
		
	} 

	public boolean getIsVertical () {
		return isVertical;
	}

	public int getLength () {
		return length;
	}

	public boolean isSunk() {

		return isSunk;

	}

	public void sink(boolean bool) {
		
		if (this.isSunk == bool) 
			System.out.printf("ERROR: Tried to sink %s but it's already sunk!\n", this.name);
		else
			this.isSunk = bool;

	}


	public String sunkMessage () {

		return String.format("*********************\nYou sank the %s!\n*********************", this.name);

	}

	public Point getOrigin () {

		return new Point (origin);

	}

	public String toString() {
		StringBuilder info = new StringBuilder("---\nShip:\n---\n");
		
		info.append("Name: " + this.name + "\n");
		info.append("Length: " + this.length + "\n");
		
		String orientation = isVertical ? "vertical" : "horizontal";
		info.append("Orient: " + orientation + "\n");
		
		info.append("Coordinates:");

		for (int i = 0; i < this.length; i++) {
		
			int x = isVertical ? origin.getX() : (origin.getX() + i);
			int y = isVertical ? (origin.getY() + i) : origin.getY();

			info.append("\n\t" + x + ", " + y);

		}

		return info.toString();

	}

}
