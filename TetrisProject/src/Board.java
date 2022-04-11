import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.applet.Applet;

public class Board implements Runnable {
	private static Block[][] placedBlocks = new Block[21][10];
	private static ArrayList<Block> moveBlocks = new ArrayList<Block>();
	private static int level;
	private static int rotateState;
	// First number is X, second is Y
	// For a clockwise check multiply values by -1
	private static final int normalWallKick[][][] = {
			/* Position 0 -> 1 */ { { 0, 0 }, { 1, 0 }, { 1, -1 }, { 0, 2 }, { 1, 2 } },
			/* Position 1 -> 2 */ { { 0, 0 }, { -1, 0 }, { 1, -1 }, { 0, -2 }, { -1, -2 } },
			/* Position 2 -> 3 */ { { 0, 0 }, { -1, 0 }, { -1, -1 }, { 0, 2 }, { -1, 2 } },
			/* Position 3 -> 0 */ { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 0, -2 }, { 1, -2 } }, };
	private static final int wallKickIPiece[][][] = { { { -1, 0 }, { -2, 0 }, { 1, 0 }, { 2, 0 }, { 0, 1 } },
			{ { 0, 1 }, { 0, 2 }, { 0, -1 }, { 0, -2 }, { -1, 0 } },
			{ { 1, 0 }, { 2, 0 }, { -1, 0 }, { -2, 0 }, { 0, -1 } },
			{ { 0, 1 }, { 0, 2 }, { 0, -1 }, { 0, -2 }, { 1, 0 } }, };

	public Board(int level) {
		this.level = level;
	}

	public static void newPiece() {
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
		for (int i = moveBlocks.size() - 1; i >= 0; i--) {
			placedBlocks[(int) moveBlocks.get(i).getY()][(int) moveBlocks.get(i).getX()] = moveBlocks.get(i);
			moveBlocks.remove(i);

		}
		System.out.println(moveBlocks.size());
	}

	public static void moveDown() {
		if (checkForCollision(0, 0, 1)) {
			for (Block block : moveBlocks) {
				block.changeY(block.getY() + 1);
			}
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

	public static void rotatePiece(ArrayList<Block> blocks) {
		int centerCnt;
		int x, y, mod;
		Block center = null;
		rotateState++;
		if (rotateState == 4) {
			rotateState = 0;
		}
		centerCnt = 0;
		for (Block a : blocks) {
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

				for (Block b : blocks) {
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
				for (Block b : blocks) {
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
				for (Block b : blocks) {
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
				for (Block b : blocks) {
					b.changeX((b.getX() + x));
					b.changeY((b.getY() + y));
					x++;
					y--;

				}
				return;
			}
		}

		for (

		Block b : blocks) {
			if (b.center()) {
				center = b;
			}
		}

		for (Block b : blocks) {
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

	public static int level() {
		return level;
	}

	private static boolean rotate;

	public static void rotate() {
		rotate = true;
	}

	private static ArrayList<Block> rotateCheck = new ArrayList<Block>();

	public void run() {
		int r;
		rotateCheck.add(null);
		rotateCheck.add(null);
		rotateCheck.add(null);
		rotateCheck.add(null);
		while (true) {
			if (rotate) {
				copy(true);
				r = wallKickCheck();
				if (r >= 0) {
					addChanges(normalWallKick[rotateState][r]);
					rotatePiece(moveBlocks);
					
				}
				rotate = false;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	// True will copy moveBlocks to rotateCheck, false will copy rotateCheck to
	// moveBlocks
	private static void copy(boolean copyDirection) {
		int i = 0;
		if (copyDirection) {
			for (Block block : moveBlocks) {
				rotateCheck.set(i, new Block(block));
				i++;
			}
			return;
		}
		for (Block block : rotateCheck) {
			moveBlocks.set(i, new Block(block));
			i++;
		}
		return;
	}
	
	private static boolean illegal(int modX, int modY) {
		for (Block b : rotateCheck) {
			System.out.println(b.getX() +", " + b.getY());
			try {
				if (placedBlocks[b.getY() + modY][b.getX() + modX] != null) {
					System.out.println("Rotation failed: Block occupied");
					return true;
				}
			} catch (Exception e) {
				System.out.println("Rotation failed: Out of bounds");
				return true;
			}
		}
		System.out.println("No problems");
		return false;
	}
	
	private static void addChanges(int[] changes) {
		for(Block b : moveBlocks) {
			b.changeX(b.getX() + changes[0]);
			b.changeY(b.getY() + changes[1]);
		}
	}

	private static int wallKickCheck() {
		// Check 0 (Default Check)
		int rotate = 0;
		rotatePiece(rotateCheck);
		for (int i = 0; i < 5; i++) {
			if (!illegal(normalWallKick[rotateState][i][0], normalWallKick[rotateState][i][1])) {
				System.out.println(rotate);
				return rotate;
			}
			rotate++;
		}
		return -1;
	}
}
