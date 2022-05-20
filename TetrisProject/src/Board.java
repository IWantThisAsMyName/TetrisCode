import java.util.ArrayList;

public class Board implements Runnable {
	private Block[][] placedBlocks;
	private ArrayList<Block> moveBlocks;
	private int heldBlock;
	private int current;
	private int level;
	private int rotateState;
	private int linesCleared;
	private boolean heldMove;
	private boolean tetris;
	private int initLevel;
	private boolean rotateCclock;
	private ArrayList<Block> ghostPiece;
	private Thread ghost;
	// First number is X, second is Y
	// For a clockwise check multiply values by -1
	private final int normalWallKick[][][] = {
			/* Position 0 -> 1 */ { { 0, 0 }, { 1, 0 }, { 1, -1 }, { 0, 2 }, { 1, 2 } },
			/* Position 1 -> 2 */ { { 0, 0 }, { -1, 0 }, { 1, -1 }, { 0, -2 }, { -1, -2 } },
			/* Position 2 -> 3 */ { { 0, 0 }, { -1, 0 }, { -1, -1 }, { 0, 2 }, { -1, 2 } },
			/* Position 3 -> 0 */ { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 0, -2 }, { 1, -2 } }, };
	private final int wallKickIPiece[][][] = { { { 0, 0 }, { 2, 0 }, { -1, 0 }, { 2, 1 }, { -1, -2 } },
			{ { 0, 0 }, { 1, 0 }, { -2, 0 }, { 1, -2 }, { -2, 1 } },
			{ { 0, 0 }, { -2, 0 }, { 1, 0 }, { -2, -1 }, { 1, 2 } },
			{ { 0, 0 }, { -1, 0 }, { 2, 0 }, { -1, 2 }, { 2, -1 } }, };
	private ArrayList<Integer> primaryGen;
	private ArrayList<Integer> secondaryGen;
	private ArrayList<Boolean> basicCheck;
	private ArrayList<Integer> lineRemoval;

	public Board(int level) {
		placedBlocks = new Block[23][10];
		moveBlocks = new ArrayList<Block>();
		primaryGen = new ArrayList<Integer>();
		secondaryGen = new ArrayList<Integer>();
		basicCheck = new ArrayList<Boolean>();
		lineRemoval = new ArrayList<Integer>();
		ghostPiece = new ArrayList<>();
		ghost = new Thread(new Runnable() {
			public void run() {
				ghostPiece.add(null);
				ghostPiece.add(null);
				ghostPiece.add(null);
				ghostPiece.add(null);
				while (true) {
					while (!run) {
						try {
							Thread.sleep(16, 666667);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					while (end) {
						try {
							Thread.sleep(16, 666667);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(16, 66667);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		ghost.start();
		linesCleared = 0;
		heldBlock = -1;
		heldMove = false;
		tetris = false;
		end = false;
		for (int i = 0; i < 7; i++) {
			basicCheck.add(true);
		}
		this.level = level;
		initLevel = level;
		generatePieces();
		newPiece();
	}

	private void generatePieces() {
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

	private ArrayList<Integer> new7Bag() {
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

	private void newPiece() {
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
			ghostPiece();
			return;
		}
		// O Piece
		if (piece == 5) {
			moveBlocks.add(new Block(1, 5, 0, false));
			moveBlocks.add(new Block(1, 4, 0, false));
			moveBlocks.add(new Block(1, 5, 1, false));
			moveBlocks.add(new Block(1, 4, 1, false));
			rotateState = 0;
			ghostPiece();
			return;
		}
		// L Piece
		if (piece == 4) {
			moveBlocks.add(new Block(2, 3, 1, false));
			moveBlocks.add(new Block(2, 4, 1, true));
			moveBlocks.add(new Block(2, 5, 1, false));
			moveBlocks.add(new Block(2, 3, 0, false));
			rotateState = 0;
			ghostPiece();
			return;
		}
		// J Piece
		if (piece == 3) {
			moveBlocks.add(new Block(3, 3, 1, false));
			moveBlocks.add(new Block(3, 4, 1, true));
			moveBlocks.add(new Block(3, 5, 0, false));
			moveBlocks.add(new Block(3, 5, 1, false));
			rotateState = 0;
			ghostPiece();
			return;
		}
		// S Piece
		if (piece == 2) {
			moveBlocks.add(new Block(4, 3, 0, false));
			moveBlocks.add(new Block(4, 4, 0, true));
			moveBlocks.add(new Block(4, 4, 1, false));
			moveBlocks.add(new Block(4, 5, 1, false));
			rotateState = 0;
			ghostPiece();
			return;
		}
		// Z Piece
		if (piece == 1) {
			moveBlocks.add(new Block(5, 3, 1, false));
			moveBlocks.add(new Block(5, 4, 1, false));
			moveBlocks.add(new Block(5, 4, 0, true));
			moveBlocks.add(new Block(5, 5, 0, false));
			rotateState = 0;
			ghostPiece();
			return;
		}
		// T Piece
		moveBlocks.add(new Block(6, 3, 1, false));
		moveBlocks.add(new Block(6, 4, 0, false));
		moveBlocks.add(new Block(6, 4, 1, true));
		moveBlocks.add(new Block(6, 5, 1, false));
		ghostPiece();
	}

	public ArrayList<Block> getMoveBlocks() {
		return moveBlocks;
	}

	public Block[][] getBlocks() {
		return placedBlocks;
	}

	public void placeBlocks() {
		int y;
		boolean above = true;
		for (int i = moveBlocks.size() - 1; i >= 0; i--) {
			y = moveBlocks.get(i).getY();
			placedBlocks[y][(int) moveBlocks.get(i).getX()] = moveBlocks.get(i);
			if (y > 2) {
				above = false;
			}
			if (checkUnique(y) && lineIsFull(y)) {
				lineRemoval.add(y);
			}
			moveBlocks.remove(i);
		}
		if (above) {
			Frame.endGame();
			return;
		}
		if (!lineRemoval.isEmpty()) {
			removeLines();
		}
		checkFail();
		generatePieces();
		newPiece();
		Frame.updateNextBlocks(primaryGen, secondaryGen);
		heldMove = false;
	}

	private void updateLevel() {
		if (linesCleared / 10 >= initLevel) {
			level = linesCleared / 10;
			return;
		}
		level = initLevel;
	}

	private boolean checkUnique(int i) {
		for (int b : lineRemoval) {
			if (b == i) {
				return false;
			}
		}
		return true;
	}

	public void moveDown() {
		if (checkForCollision(0, 0, 1) && moveBlocks.size() == 4) {
			for (Block block : moveBlocks) {
				block.changeY(block.getY() + 1);
			}
		}
	}

	public void moveSide(boolean direction) {
		if (direction) {
			if (checkForCollision(1, 0, 0)) {
				for (Block block : moveBlocks) {
					block.changeX(block.getX() + 1);
				}
			}
			ghostPiece();
			return;
		}
		if (checkForCollision(0, 1, 0)) {
			for (Block block : moveBlocks) {
				block.changeX(block.getX() - 1);

			}
			ghostPiece();
		}
	}

	private boolean checkFail() {

		return false;
	}

	public void rotatePiece(ArrayList<Block> blocks, int mod) {
		int centerCnt;
		int x, y;
		Block center = null;
		if (mod == 1) {
			rotateState++;
			if (rotateState == 4) {
				rotateState = 0;
			}
		} else {
			rotateState--;
			if (rotateState == -1) {
				rotateState = 3;
			}
		}
		centerCnt = 0;
		for (Block a : blocks) {
			if (a.center()) {
				centerCnt++;
			}
		}
		
		if (centerCnt == 0) {
			return;
		}
		if (centerCnt == 2) {
			if (mod == 1) {
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
					ghostPiece();
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
					ghostPiece();
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
					ghostPiece();
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
					ghostPiece();
					return;
				}
			} else {
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
				if (mod == -1 && x != 0 && y != 0) {
					if (x == 1 && y == 1) {
						b.changeY(b.getY() - 2);
					}
					if (x == -1 && y == 1) {
						b.changeX(b.getX() + 2);
					}
					if (x == -1 && y == -1) {
						b.changeY(b.getY() + 2);
					}
					if (x == 1 && y == -1) {
						b.changeX(b.getX() - 2);
					}
				} else if (x != 0 && y != 0) {
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
						b.changeY(b.getY() + (1 * mod));
					}
					if (x == -1) {
						b.changeX(b.getX() + 1);
						b.changeY(b.getY() - (1 * mod));
					}
					if (y == 1) {
						b.changeX(b.getX() - (1 * mod));
						b.changeY(b.getY() - 1);
					}
					if (y == -1) {
						b.changeX(b.getX() + (1 * mod));
						b.changeY(b.getY() + 1);
					}
				}
			}
		}
		ghostPiece();
	}

	private void removeLines() {
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
		switch (c) {
		case 1:
			linesCleared += 1;
			Frame.addPoints(100 * (level + 1));
			tetris = false;
			break;
		case 2:
			linesCleared += 2;
			Frame.addPoints(300 * (level + 1));
			tetris = false;
			break;
		case 3:
			linesCleared += 3;
			Frame.addPoints(500 * (level + 1));
			tetris = false;
			break;
		case 4:
			linesCleared += 4;
			if (tetris) {
				Frame.addPoints(1200 * (level + 1));
			} else {
				Frame.addPoints(800 * (level + 1));
				tetris = true;
			}
			break;
		}
		for (int i = 0; i < c; i++) {
			lineRemoval.remove(0);
		}
		updateLevel();
	}

	public boolean checkForCollision(int xR, int xL, int y) {
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

	public void holdBlock() {
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
		generatePieces();
		newPiece();
		heldMove = true;
	}

	public void hardDrop() {
		while (true) {
			if (checkForCollision(0, 0, 1)) {
				moveDown();
				Frame.addPoints(2);
			} else {
				placeBlocks();
				return;
			}
		}
	}

	private boolean lineIsFull(int index) {
		for (int i = 0; i < 10; i++) {
			if (placedBlocks[index][i] == null) {
				return false;
			}
		}
		return true;
	}

	public Block getPlacedBlock(int x, int y) {
		return placedBlocks[y][x];
	}

	public int level() {
		return level;
	}

	private boolean rotate;

	public void rotate() {
		rotate = true;
	}

	private ArrayList<Block> rotateCheck = new ArrayList<Block>();

	private boolean run = true;

	public void pause() {
		if (run) {
			run = false;
			return;
		}
		run = true;
	}

	public void run() {
		int r;
		rotateCheck.add(null);
		rotateCheck.add(null);
		rotateCheck.add(null);
		rotateCheck.add(null);
		while (true) {
			while (!run) {
				try {
					Thread.sleep(16, 666667);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			while (end) {
				try {
					Thread.sleep(16, 666667);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (rotate && moveBlocks.size() == 4) {
				copy();
				if (moveBlocks.get(0).line()) {
					r = lineWallKick(1);
					if (r != -1) {
						addChanges(wallKickIPiece[rotateState][r], 1);
						rotatePiece(moveBlocks, 1);
					}
				} else {
					r = wallKickCheck(1);
					if (r != -1) {
						addChanges(normalWallKick[rotateState][r], 1);
						rotatePiece(moveBlocks, 1);

					}

				}
				rotate = false;
			}
			if (rotateCclock && moveBlocks.size() == 4) {
				copy();
				if (moveBlocks.get(0).line()) {
					r = lineWallKick(-1);
					if (r != -1) {
						addChanges(wallKickIPiece[rotateState][r], -1);
						rotatePiece(moveBlocks, -1);
					}
				} else {
					r = wallKickCheck(-1);
					if (r != -1) {
						addChanges(normalWallKick[rotateState][r], -1);
						rotatePiece(moveBlocks, -1);
					}

				}
				rotateCclock = false;
			}
			try {
				Thread.sleep(16, 666667);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	// True will copy moveBlocks to rotateCheck, false will copy rotateCheck to
	// moveBlocks
	private void copy() {
		int i = 0;
		for (Block block : moveBlocks) {
			rotateCheck.set(i, new Block(block));
			i++;
		}
		return;
	}

	private boolean legal(int[] changes, int mod) {
		for (Block b : rotateCheck) {
			try {
				if (placedBlocks[b.getY() + changes[1] * mod][b.getX() + changes[0] * mod] != null) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	private void addChanges(int[] changes, int mod) {
		for (Block b : moveBlocks) {
			b.changeX(b.getX() + (changes[0] * mod));
			b.changeY(b.getY() + (changes[1] * mod));
		}
	}

	private boolean subtract;

	private int lineWallKick(int mod) {
		int rotate = 0;
		subtract = true;
		rotatePiece(rotateCheck, mod);
		for (int i = 0; i < 5; i++) {
			if (legal(wallKickIPiece[rotateState][i], mod)) {
				return rotate;
			}
			rotate++;
		}
		return -1;
	}

	private int wallKickCheck(int mod) {
		// Check 0 (Default Check)
		int rotate = 0;
		rotatePiece(rotateCheck, mod);
		for (int i = 0; i < 5; i++) {
			if (legal(normalWallKick[rotateState][i], mod)) {
				return rotate;
			}
			rotate++;
		}
		return -1;
	}

	public void rotateCClock() {
		rotateCclock = true;
	}

	private boolean end;

	public void end() {
		end = true;
	}

	private void ghostPiece(){
		int i = 0;
		try {
		for(Block b : moveBlocks) {
			ghostPiece.set(i, new Block(true, b.getX(), b.getY() - 1, false));
			i++;
		}
		} catch (Exception e) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ghostPiece();
		}
		ghostLow();
	}

	public void ghostLow() {
		while(true) {
			for(Block b : ghostPiece) {
				try {
					if (placedBlocks[b.getY() + 1][b.getX()] != null) {
						return;
					} 
				} catch (Exception NPE) {
					return;
				}
			}
			for(Block b : ghostPiece) {
				
				b.changeY(b.getY() + 1);
			}
		}
	}
	
	public ArrayList<Block> getGhostBlocks(){
		return ghostPiece;
	}
}