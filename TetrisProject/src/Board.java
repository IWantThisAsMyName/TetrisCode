import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.applet.Applet;

public class Board {
	private static Block[][] placedBlocks = new Block[21][10];
	private static ArrayList<Block> moveBlocks = new ArrayList<Block>();
	private static int[] levelSpeed = {48, 43, 38, 33, 28, 23, 18, 13, 8, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1};
	private static int level;
	private static int rotateState;
	private static Graphics g2;

	public Board(int level) {
		this.level = level;
	}

	public void newPiece() {
		int random = (int) (Math.random() * 7);
		rotateState = 0;
		// I Piece
		if (random == 6) {
			moveBlocks.add(new Block(0, 3, 0, false));
			moveBlocks.add(new Block(0, 4, 0, true));
			moveBlocks.add(new Block(0, 5, 0, true));
			moveBlocks.add(new Block(0, 6, 0, false));
			return;
		}
		// O Piece
		if (random == 5) {
			moveBlocks.add(new Block(1, 5, 0, false));
			moveBlocks.add(new Block(1, 4, 0, false));
			moveBlocks.add(new Block(1, 5, 1, false));
			moveBlocks.add(new Block(1, 4, 1, false));
			return;
		}
		// J Piece
		if (random == 4) {
			moveBlocks.add(new Block(2, 3, 0, false));
			moveBlocks.add(new Block(2, 4, 0, true));
			moveBlocks.add(new Block(2, 5, 0, false));
			moveBlocks.add(new Block(2, 3, 1, false));
			return;
		}
		// L Piece
		if (random == 3) {
			moveBlocks.add(new Block(3, 3, 0, false));
			moveBlocks.add(new Block(3, 4, 0, true));
			moveBlocks.add(new Block(3, 5, 1, false));
			moveBlocks.add(new Block(3, 5, 0, false));
			return;
		}
		// S Piece
		if (random == 2) {
			moveBlocks.add(new Block(4, 3, 0, false));
			moveBlocks.add(new Block(4, 4, 0, true));
			moveBlocks.add(new Block(4, 4, 1, false));
			moveBlocks.add(new Block(4, 5, 1, false));
			return;
		}
		// Z Piece
		if (random == 1) {
			moveBlocks.add(new Block(5, 3, 1, false));
			moveBlocks.add(new Block(5, 4, 1, false));
			moveBlocks.add(new Block(5, 4, 0, true));
			moveBlocks.add(new Block(5, 5, 0, false));
			return;
		}
		// T Piece
		moveBlocks.add(new Block(6, 3, 0, false));
		moveBlocks.add(new Block(6, 4, 1, false));
		moveBlocks.add(new Block(6, 4, 0, true));
		moveBlocks.add(new Block(6, 5, 0, false));

	}

	public static ArrayList<Block> getMoveBlocks() {
		return moveBlocks;
	}

	public static Block[][] getBlocks() {
		return placedBlocks;
	}

	public static void placeBlocks() {
		for (int i = moveBlocks.size() - 1; i >= moveBlocks.size(); i--) {
			placedBlocks[(int) moveBlocks.get(i).getY()][(int) moveBlocks.get(i).getX()] = moveBlocks.get(i);
			moveBlocks.remove(i);
		}
		if (checkFail()) {

		}
	}

	public static void moveDown() {
		for (Block block : moveBlocks) {
			block.changeY(block.getY() - 1);
		}
	}

	public static void moveSide(boolean direction) {
		if (direction) {
			if (checkForCollision(1, 0, 0)) {
				for (Block block : moveBlocks) {
					block.changeX(block.getX() + 1);
				}
			}
			return;
		}
		if (checkForCollision(0, 1, 0)) {
			for (Block block : moveBlocks) {
				block.changeX(block.getX() - 1);

			}
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

	public static void rotatePiece() {
		int centerCnt;
		int x, y, mod;
		Block center = null;
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
			if (rotateState == 0) {
				y = -2;
				x = -2;
				for (Block b : moveBlocks) {
					b.changeX((b.getX() + x));
					b.changeY((b.getY() + y));
					x++;
					y++;
				}
				return;
			}
			if (rotateState == 1) {
				y = -1;
				x = 1;
				for (Block b : moveBlocks) {
					b.changeX((b.getX() + x));
					b.changeY((b.getY() + y));
					x--;
					y++;
				}
				return;
			}
			if (rotateState == 2) {
				y = 2;
				x = 2;
				for (Block b : moveBlocks) {
					b.changeX((b.getX() + x));
					b.changeY((b.getY() + y));
					x--;
					y--;
				}
				return;
			}
			if (rotateState == 3) {
				y = 1;
				x = -1;
				for (Block b : moveBlocks) {
					b.changeX((b.getX() + x));
					b.changeY((b.getY() + y));
					x++;
					y--;

				}
				return;
			}
		}

		for (

		Block b : moveBlocks) {
			if (b.center()) {
				center = b;
			}
		}

		for (Block b : moveBlocks) {
			if (!b.center()) {
				x = (b.getX() - center.getX());
				y = (b.getY() - center.getY());

				if (x != 0 && y != 0) {
					if (x == 1 && y == 1) {
						b.changeX(b.getX() - 2);
					}
					if (x == -1 && y == 1) {
						b.changeY(b.getY() - 2);
					}
					if (x == -1 && y == -1) {
						b.changeX(b.getX() + 2);
					}
					if (x == 1 && y == -1) {
						b.changeY(b.getY() + 2);
					}

				} else {
					if (x == 1) {
						b.changeX(b.getX() - 1);
						b.changeY(b.getY() + 1);
					}
					if (x == -1) {
						b.changeX(b.getX() + 1);
						b.changeY(b.getY() - 1);
					}
					if (y == 1) {
						b.changeX(b.getX() - 1);
						b.changeY(b.getY() - 1);
					}
					if (y == -1) {
						b.changeX(b.getX() + 1);
						b.changeY(b.getY() + 1);
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

	public static boolean checkForCollision(int xR, int xL, int y) {
		for (Block block : moveBlocks) {
			try {
				if (placedBlocks[block.getY() + y][block.getX() + xR - xL] != null) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
			if (xL == 0) {

				if (block.getX() + xR > 10) {

					return false;
				}
			} else if (block.getX() - xL == -1) {

				return false;
			}
		}
		return true;
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

	public static void setGraphics(Graphics g) {
		g2 = g;
	}
}