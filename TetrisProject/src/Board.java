import java.util.ArrayList;

public class Board implements Runnable {
	private static Block[][] placedBlocks = new Block[23][10];
	private static ArrayList<Block> moveBlocks = new ArrayList<Block>();
	private static int heldBlock;
	private static int current;
	private static int level;
	private static int rotateState;
	private static int score;
	private static int linesCleared;
	private static boolean heldMove;
	// First number is X, second is Y
	// For a clockwise check multiply values by -1
	private static final int normalWallKick[][][] = {
			/* Position 0 -> 1 */ { { 0, 0 }, { 1, 0 }, { 1, -1 }, { 0, 2 }, { 1, 2 } },
			/* Position 1 -> 2 */ { { 0, 0 }, { -1, 0 }, { 1, -1 }, { 0, -2 }, { -1, -2 } },
			/* Position 2 -> 3 */ { { 0, 0 }, { -1, 0 }, { -1, -1 }, { 0, 2 }, { -1, 2 } },
			/* Position 3 -> 0 */ { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 0, -2 }, { 1, -2 } }, };
	private static final int wallKickIPiece[][][] = { { { 0, 0 }, { 2, 0 }, { -1, 0 }, { 2, 1 }, { -1, -2 } },
			{ { 0, 0 }, { 1, 0 }, { -2, 0 }, { 1, -2 }, { -2, 1 } },
			{ { 0, 0 }, { -2, 0 }, { 1, 0 }, { -2, -1 }, { 1, 2 } },
			{ { 0, 0 }, { -1, 0 }, { 2, 0 }, { -1, 2 }, { 2, -1 } }, };
	private static ArrayList<Integer> primaryGen;
	private static ArrayList<Integer> secondaryGen;
	private static ArrayList<Boolean> basicCheck;
	private static ArrayList<Integer> lineRemoval;

	@SuppressWarnings("static-access")
	public Board(int level) {
		primaryGen = new ArrayList<Integer>();
		secondaryGen = new ArrayList<Integer>();
		basicCheck = new ArrayList<Boolean>();
		lineRemoval = new ArrayList<Integer>();
		score = 0;
		linesCleared = 0;
		heldBlock = -1;
		heldMove = false;
		for (int i = 0; i < 7; i++) {
			basicCheck.add(true);
		}
		this.level = level;
	}

	public static void start() {
		generatePieces();
		newPiece();
	}

	private static void generatePieces() {
		if (primaryGen.isEmpty() && secondaryGen.isEmpty()) {
			primaryGen = new7Bag();
			secondaryGen = new7Bag();
			return;
		}

		if (primaryGen.size() == 0) {
			for (int i = 0; i < 7; i++) {
				primaryGen.add(secondaryGen.get(i));
			}
			secondaryGen = new7Bag();
			return;
		}
		return;
	}

	private static ArrayList<Integer> new7Bag() {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		int random;
		while (true) {
			if (temp.size() == 7) {
				for (int i = 0; i < 7; i++) {
					basicCheck.set(i, true);
				}
				return temp;
			}
			random = (int) (Math.random() * 7);
			if (basicCheck.get(random)) {
				temp.add(random);
				basicCheck.set(random, false);
			}

		}

	}

	private static void newPiece() {
		int piece = primaryGen.get(0);
		current = piece;
		primaryGen.remove(0);
		// I Piece
		if (piece == 6) {
			moveBlocks.add(new Block(0, 3, 0, false, true));
			moveBlocks.add(new Block(0, 4, 0, true));
			moveBlocks.add(new Block(0, 5, 0, true));
			moveBlocks.add(new Block(0, 6, 0, false));
			rotateState = 0;
			return;
		}
		// O Piece
		if (piece == 5) {
			moveBlocks.add(new Block(1, 5, 0, false));
			moveBlocks.add(new Block(1, 4, 0, false));
			moveBlocks.add(new Block(1, 5, 1, false));
			moveBlocks.add(new Block(1, 4, 1, false));
			rotateState = 0;
			return;
		}
		// L Piece
		if (piece == 4) {
			moveBlocks.add(new Block(2, 3, 1, false));
			moveBlocks.add(new Block(2, 4, 1, true));
			moveBlocks.add(new Block(2, 5, 1, false));
			moveBlocks.add(new Block(2, 3, 0, false));
			rotateState = 0;
			return;
		}
		// J Piece
		if (piece == 3) {
			moveBlocks.add(new Block(3, 3, 1, false));
			moveBlocks.add(new Block(3, 4, 1, true));
			moveBlocks.add(new Block(3, 5, 0, false));
			moveBlocks.add(new Block(3, 5, 1, false));
			rotateState = 0;
			return;
		}
		// S Piece
		if (piece == 2) {
			moveBlocks.add(new Block(4, 3, 0, false));
			moveBlocks.add(new Block(4, 4, 0, true));
			moveBlocks.add(new Block(4, 4, 1, false));
			moveBlocks.add(new Block(4, 5, 1, false));
			rotateState = 0;
			return;
		}
		// Z Piece
		if (piece == 1) {
			moveBlocks.add(new Block(5, 3, 1, false));
			moveBlocks.add(new Block(5, 4, 1, false));
			moveBlocks.add(new Block(5, 4, 0, true));
			moveBlocks.add(new Block(5, 5, 0, false));
			rotateState = 0;
			return;
		}
		// T Piece
		moveBlocks.add(new Block(6, 3, 1, false));
		moveBlocks.add(new Block(6, 4, 0, false));
		moveBlocks.add(new Block(6, 4, 1, true));
		moveBlocks.add(new Block(6, 5, 1, false));
	}

	public static ArrayList<Block> getMoveBlocks() {
		return moveBlocks;
	}

	public static Block[][] getBlocks() {
		return placedBlocks;
	}

	public static void placeBlocks() {
		int y;
		for (int i = moveBlocks.size() - 1; i >= 0; i--) {
			y = moveBlocks.get(i).getY();
			placedBlocks[y][(int) moveBlocks.get(i).getX()] = moveBlocks.get(i);
			if (checkUnique(y) && lineIsFull(y)) {
				lineRemoval.add(y);
			}
			moveBlocks.remove(i);
		}
		if (!lineRemoval.isEmpty()) {
			removeLines();
		}
		checkFail();
		generatePieces();
		newPiece();
		heldMove = false;
	}

	private static boolean checkUnique(int i) {
		for (int b : lineRemoval) {
			if (b == i) {
				return false;
			}
		}
		return true;
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
				if (subtract) {
					rotateState--;
					subtract = false;
				}
				if (rotateState < 0) {
					rotateState = 3;
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
				if (subtract) {
					rotateState--;
					subtract = false;
				}
				if (rotateState < 0) {
					rotateState = 3;
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
				if (subtract) {
					rotateState--;
					subtract = false;
				}
				if (rotateState < 0) {
					rotateState = 3;
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
				if (subtract) {
					rotateState--;
					subtract = false;
				}
				if (rotateState < 0) {
					rotateState = 3;
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

	private static void removeLines() {
		// 21 is the bottom of the screen, 1 is the top
		lineRemoval.sort(null);
		for (int i : lineRemoval) {
			for (int x = 0; x < 10; x++) {
				placedBlocks[i][x] = null;
			}

			for (int y = i; y >= 2; y--) {
				for (int x = 0; x < 10; x++) {
					placedBlocks[y][x] = placedBlocks[y - 1][x];
					if (placedBlocks[y - 1][x] != null) {
						placedBlocks[y][x].changeY(placedBlocks[y][x].getY() + 1);
					}
					placedBlocks[y - 1][x] = null;
				}
			}
		}
		int c = lineRemoval.size();
		switch(c) {
		case 1:
			linesCleared += 1;
			break;
		case 2:
			linesCleared += 3;
			break;
		case 3:
			linesCleared += 5;
			break;
		case 4:
			linesCleared += 8;
			break;
		}
		for (int i = 0; i < c; i++) {
			lineRemoval.remove(0);
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

	public static void holdBlock() {
		if (heldMove) {
			return;
		}

		if (heldBlock != -1) {
			primaryGen.add(0, heldBlock);
			for (int i = 3; i >= 0; i--) {
				moveBlocks.remove(i);
			}
			heldBlock = current;
			newPiece();
			heldMove = true;
			return;
		}
		heldBlock = current;
		for (int i = 3; i >= 0; i--) {
			moveBlocks.remove(i);
		}
		newPiece();
		heldMove = true;
	}

	public static void hardDrop() {
		while (true) {
			if (checkForCollision(0, 0, 1)) {
				moveDown();
			} else {
				placeBlocks();
				return;
			}
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
				copy();
				if (moveBlocks.get(0).line()) {
					r = lineWallKick();
					if (r != -1) {
						addChanges(wallKickIPiece[rotateState][r]);
						rotatePiece(moveBlocks);
					}
				} else {
					r = wallKickCheck();
					if (r != -1) {
						addChanges(normalWallKick[rotateState][r]);
						rotatePiece(moveBlocks);
					}

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
	private static void copy() {
		int i = 0;
		for (Block block : moveBlocks) {
			rotateCheck.set(i, new Block(block));
			i++;
		}
		return;
	}

	private static boolean legal(int[] changes) {
		for (Block b : rotateCheck) {
			try {
				if (placedBlocks[b.getY() + changes[1]][b.getX() + changes[0]] != null) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	private static void addChanges(int[] changes) {
		for (Block b : moveBlocks) {
			b.changeX(b.getX() + changes[0]);
			b.changeY(b.getY() + changes[1]);
		}
	}

	private static boolean subtract;

	private static int lineWallKick() {
		int rotate = 0;
		subtract = true;
		rotatePiece(rotateCheck);
		for (int i = 0; i < 5; i++) {
			if (legal(wallKickIPiece[rotateState][i])) {
				return rotate;
			}
			rotate++;
		}
		return -1;
	}

	private static int wallKickCheck() {
		// Check 0 (Default Check)
		int rotate = 0;
		rotatePiece(rotateCheck);
		for (int i = 0; i < 5; i++) {
			if (legal(normalWallKick[rotateState][i])) {
				return rotate;
			}
			rotate++;
		}
		return -1;
	}
}
