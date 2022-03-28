import java.awt.Point;
import java.util.ArrayList;

public class Block {
	private static ArrayList<Block> placedBlocks= new ArrayList<Block>();
	private static ArrayList<Block> moveBlocks = new ArrayList<Block>();
	private int color;
	private boolean moveable;
	private Point coords;
	
	public Block(int color, int x, int y) {
		coords.setLocation(x, y);
		this.color = color;
		moveBlocks.add(this);
	}
	
	public void placeBlocks() {
		for(int i = moveBlocks.size() - 1; i >= moveBlocks.size(); i--) {
			placedBlocks.add(moveBlocks.get(i));
			moveBlocks.remove(i);
		}
	}
	
	public Point getXY() {
		return coords;
	}
	
	public int color() {
		return color;
	}
}