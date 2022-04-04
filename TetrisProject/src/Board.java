import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.applet.Applet;

public class Board extends Applet implements Runnable {
	private static Block[][] placedBlocks = new Block[21][10];
	private static ArrayList<Block> moveBlocks = new ArrayList<Block>();
	private int rotateState;
	Graphics g;
	public Board() {
	}

	
	public void run() {
		for(Block[] arr : placedBlocks) {
			for(Block pBlock : arr) {
				pBlock.paint(g);
			}
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void newPiece() {
		int random = (int) Math.random() * 7;
		rotateState = 0;
		// I Piece
		if (random == 6) {
			moveBlocks.add(new Block(0, 5, 0, false));
			moveBlocks.add(new Block(0, 6, 0, true));
			moveBlocks.add(new Block(0, 7, 0, true));
			moveBlocks.add(new Block(0, 8, 0, false));
			return;
		}
		// O Piece
		if (random == 5) {
			moveBlocks.add(new Block(1, 6, 0, false));
			moveBlocks.add(new Block(1, 5, 0, false));
			moveBlocks.add(new Block(1, 6, 1, false));
			moveBlocks.add(new Block(1, 5, 1, false));
			return;
		}
		// J Piece
		if (random == 4) {
			moveBlocks.add(new Block(2, 5, 0, false));
			moveBlocks.add(new Block(2, 6, 0, true));
			moveBlocks.add(new Block(2, 7, 0, false));
			moveBlocks.add(new Block(2, 5, 1, false));
			return;
		}
		// L Piece
		if (random == 3) {
			moveBlocks.add(new Block(3, 5, 0, false));
			moveBlocks.add(new Block(3, 6, 0, true));
			moveBlocks.add(new Block(3, 7, 1, false));
			moveBlocks.add(new Block(3, 5, 0, false));
			return;
		}
		// S Piece
		if (random == 2) {
			moveBlocks.add(new Block(4, 5, 0, false));
			moveBlocks.add(new Block(4, 6, 0, true));
			moveBlocks.add(new Block(4, 6, 1, false));
			moveBlocks.add(new Block(4, 7, 1, false));
			return;
		}
		// Z Piece
		if (random == 1) {
			moveBlocks.add(new Block(5, 5, 1, false));
			moveBlocks.add(new Block(5, 6, 1, false));
			moveBlocks.add(new Block(5, 6, 0, true));
			moveBlocks.add(new Block(5, 7, 0, false));
			return;
		}
		// T Piece
		moveBlocks.add(new Block(6, 5, 0, false));
		moveBlocks.add(new Block(6, 6, 1, false));
		moveBlocks.add(new Block(6, 6, 0, true));
		moveBlocks.add(new Block(6, 7, 0, false));

	}

	public static void placeBlocks() {
		for (int i = moveBlocks.size() - 1; i >= moveBlocks.size(); i--) {
			placedBlocks[(int) moveBlocks.get(i).getXY().getY()][(int) moveBlocks.get(i).getXY().getX()] = moveBlocks
					.get(i);
			moveBlocks.remove(i);
		}
		if (checkFail()) {

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
		for (Block check : placedBlocks[0]) {
			if (check != null) {
				return true;
			}
		}
		return false;
	}

	private int centerCnt;
	private int x, y, mod;
	private Block center;

	public void rotatePiece() {
		rotateState++;
		if (rotateState == 4) {
			rotateState = 0;
		}
		centerCnt = 0;
		for (Block a : moveBlocks) {
			if (a.center())
				centerCnt++;
		}

		if (centerCnt == 0) {
			return;
		}

		if (centerCnt == 2) {
			for (int i = -1; i <= 1; i += 2) {
				while (i == 1) {
				}
			}
		}

		if (centerCnt == 2) {
			if (rotateState == 0) {
				for (Block b : moveBlocks) {
					y = -1;
					x = -2;
					b.changeX((int) (b.getXY().getX() + x));
					b.changeY((int) (b.getXY().getY() + y));
					x++;
					y++;

				}
				if (rotateState == 1) {
					for (Block b : moveBlocks) {
						y = 2;
						x = 1;
						b.changeX((int) (b.getXY().getX() + x));
						b.changeY((int) (b.getXY().getY() + y));
						x--;
						y--;
					}
					return;
				}
				if (rotateState == 2) {
					for (Block b : moveBlocks) {
						y = 1;
						x = 2;
						b.changeX((int) (b.getXY().getX() + x));
						b.changeY((int) (b.getXY().getY() + y));
						x--;
						y--;

					}
					return;
				}
				if (rotateState == 3) {
					for (Block b : moveBlocks) {
						y = -1;
						x = -2;
						b.changeX((int) (b.getXY().getX() + x));
						b.changeY((int) (b.getXY().getY() + y));
						x++;
						y++;
					}
					return;
				}
			}

			for (Block b : moveBlocks) {
				if (b.center()) {
					center = b;
				}
			}

			for (Block b : moveBlocks) {
				if (!b.center()) {
					x = (int) (b.getXY().getX() - center.getXY().getX());
					y = (int) (b.getXY().getY() - center.getXY().getY());
					mod = 1;
					if (x != 0 && y != 0) {
						if (y == 1) {
							mod = -1;
						}
						if (x == 1) {
							b.changeY((int) (b.getXY().getY() + (2 * mod)));
						} else {
							b.changeX((int) (b.getXY().getX() + (2 * mod)));
						}
					} else {
						if (x == 1) {
							b.changeX((int) b.getXY().getX() - 1);
							b.changeY((int) b.getXY().getY() + 1);
						}
						if (x == -1) {
							b.changeX((int) b.getXY().getX() + 1);
							b.changeY((int) b.getXY().getY() - 1);
						}
						if (y == 1) {
							b.changeX((int) b.getXY().getX() - 1);
							b.changeY((int) b.getXY().getY() - 1);
						}
						if (y == -1) {
							b.changeX((int) b.getXY().getX() + 1);
							b.changeY((int) b.getXY().getY() + 1);
						}
					}
				}
			}
		}
	}

	private static void clearLines(int[] arr) {
		ArrayList<Integer> remove = new ArrayList<Integer>();
		for (int i : arr) {
			if (lineIsFull(i)) {
				remove.add(i);
			}
		}

		if (remove.size() == 0) {
			return;
		}

		for (int r : remove) {

		}
	}

	private static boolean lineIsFull(int index) {
		for (int i = 0; i < 10; i++) {
			if (placedBlocks[index][i] == null) {
				return false;
			}
		}
		return true;
	}
	
	public static Block getPlacedBlock(int x, int y) {
		return placedBlocks[y][x];
	}
}