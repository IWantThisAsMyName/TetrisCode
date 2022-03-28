import java.awt.Point;
import java.util.ArrayList;

public class Block {
	private static ArrayList<Block> placedBlocks= new ArrayList<Block>();
	private static ArrayList<Block> moveBlocks = new ArrayList<Block>();
	private int color;
	private Point coords;
	
	public Block(int color, int x, int y) {
		coords.setLocation(x, y);
		this.color = color;
		moveBlocks.add(this);
	}
	
	public static void placeBlocks() {
		for(int i = moveBlocks.size() - 1; i >= moveBlocks.size(); i--) {
			placedBlocks.add(moveBlocks.get(i));
			moveBlocks.remove(i);
		}
	}
	
	public static void moveDown() {
		for(Block block : moveBlocks) {
			block.changeY((int) block.getXY().getY() - 1);
		}
	}
	//Left is false, right is true
	public static void moveSide(boolean direction) {
		if(direction) {
			for(Block block : moveBlocks) {
				block.changeX((int) block.getXY().getX() + 1);
			}
			return;
		}
		for(Block block : moveBlocks) {
			block.changeX((int) block.getXY().getX() - 1);
		}
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