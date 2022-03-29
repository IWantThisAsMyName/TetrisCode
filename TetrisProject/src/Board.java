import java.util.ArrayList;

public class Board {
	private static Block[][] placedBlocks = new Block[21][10];
	private static ArrayList<Block> moveBlocks = new ArrayList<Block>();
	public Board() {}
	
	public void newPiece() {
		int random = (int) Math.random() * 7;
		//I Piece
		if(random == 6) {
			moveBlocks.add(new Block(0, 5, 0, false));
			moveBlocks.add(new Block(0, 6, 0, true));
			moveBlocks.add(new Block(0, 7, 0, true));
			moveBlocks.add(new Block(0, 8, 0, false));
			return;
		}
		//O Piece
		if(random == 5) {
			moveBlocks.add(new Block(1, 6, 0, false));
			moveBlocks.add(new Block(1, 5, 0, false));
			moveBlocks.add(new Block(1, 6, 1, false));
			moveBlocks.add(new Block(1, 5, 1, false));
			return;
		}
		//J Piece
		if(random == 4) {
			moveBlocks.add(new Block(2, 5, 0, false));
			moveBlocks.add(new Block(2, 6, 0, true));
			moveBlocks.add(new Block(2, 7, 0, false));
			moveBlocks.add(new Block(2, 5, 1, false));
			return;
		}
		//L Piece
		if(random == 3) {
			moveBlocks.add(new Block(3, 5, 0, false));
			moveBlocks.add(new Block(3, 6, 0, true));
			moveBlocks.add(new Block(3, 7, 1, false));
			moveBlocks.add(new Block(3, 5, 0, false));
			return;
		}
		//S Piece
		if(random == 2) {
			moveBlocks.add(new Block(4, 5, 0, false));
			moveBlocks.add(new Block(4, 6, 0, true));
			moveBlocks.add(new Block(4, 6, 1, false));
			moveBlocks.add(new Block(4, 7, 1, false));
			return;
		}
		//Z Piece
		if(random == 1) {
			moveBlocks.add(new Block(5, 5, 1, false));
			moveBlocks.add(new Block(5, 6, 1, false));
			moveBlocks.add(new Block(5, 6, 0, true));
			moveBlocks.add(new Block(5, 7, 0, false));
			return;
		}
		//T Piece
		moveBlocks.add(new Block(5, 5, 0, false));
		moveBlocks.add(new Block(5, 6, 1, false));
		moveBlocks.add(new Block(5, 6, 0, true));
		moveBlocks.add(new Block(5, 7, 0, false));
		
	}
	
	public static void placeBlocks() {
		for (int i = moveBlocks.size() - 1; i >= moveBlocks.size(); i--) {
			placedBlocks[(int) moveBlocks.get(i).getXY().getY()][(int) moveBlocks.get(i).getXY().getX()] = moveBlocks.get(i);
			moveBlocks.remove(i);
		}
		if(checkFail()) {
			
		}
	}
	
	public static void moveDown() {
		for (Block block : moveBlocks) {
			block.changeY((int) block.getXY().getY() - 1);
		}
	}
	
	public static void moveSide(boolean direction) {
		if (direction) {
			for (Block block : moveBlocks) {
				block.changeX((int) block.getXY().getX() + 1);
			}
			return;
		}
		for (Block block : moveBlocks) {
			block.changeX((int) block.getXY().getX() - 1);
		}
	}
	
	private static boolean checkFail() {
		for(Block check : placedBlocks[0]) {
			if(check != null) {
				return true;
			}
		}
		return false;
	}
	
	private int centerCnt;
	
	public void rotatePiece() {
		centerCnt = 0;
		for(Block a : moveBlocks) {
			if(a.center()) centerCnt++;
		}
		
		if(centerCnt == 0) {
			return;
		}
		
		if(centerCnt == 2) {
			for(int i = -1; i <= 1; i+=2) {
				for() {
					
				}
				for() {
					
				}
			}
			return;
		}
		
		return;
	}
}