import java.awt.Point;
import java.util.ArrayList;

public class Block {
	private int color;
	private Point coords;
	private boolean rotateCenter;

	public Block(int color, int x, int y, boolean rotateCenter) {
		coords.setLocation(x, y);
		this.color = color;
		this.rotateCenter = rotateCenter;
	}


	public Point getXY() {
		return coords;
	}

	public int color() {
		return color;
	}

	public void changeX(int newX) {
		coords.setLocation(newX, coords.getY());
	}

	public void changeY(int newY) {
		coords.setLocation(coords.getX(), newY);
	}
	
	public boolean center() {
		return rotateCenter;
	}

}