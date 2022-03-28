import java.awt.Point;
import java.util.ArrayList;

public class Block {
	private int color;
	private Point coords;

	public Block(int color, int x, int y) {
		coords.setLocation(x, y);
		this.color = color;
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

}