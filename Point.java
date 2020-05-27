
public class Point {
    private int x;
    private int y;

    public Point(Integer x, Integer y) {
        this.x = x.intValue();
        this.y = y.intValue();
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
        
    public Point(Point p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Point p) {
        return (this.x == p.getX() && this.y == p.getY());
    }

    public String toString() {
        return String.format(x + ", " + y);
    }

}
