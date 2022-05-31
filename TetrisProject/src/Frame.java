import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener, Runnable {
	static Thread rotateThread;
	static Thread lockDelay;
	static Thread move;

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Frame f = new Frame();
	}

	private static int[] levelSpeed = { 48, 43, 38, 33, 28, 23, 18, 13, 8, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 2, 2, 2, 2, 2,
			2, 2, 2, 2, 2, 1, 1 };
	private boolean moveRight = false;
	private boolean moveLeft = false;
	private boolean held = false;
	private boolean spaceHeld = false;
	private static boolean moveDown = false;
	private boolean contacting = false;
	private boolean pause = true;
	private boolean hold = false;
	private boolean held3 = false;
	private boolean heldCC = false;
	private ArrayList<UIElement> UI;
	private Point mouseXY;
	private static int state;
	private static Board board;
	private static int score;
	private int frameNum = 0;
	private int downCnt = 0;
	private int leftCnt = 0, rightCnt = 0;
	private int dcLeft, dcRight;
	private static int initLevel;
	private static int lock = 0;
	private static int[] nextBlocks;
	private int startCnt = 0;
	private UIElement bg;
	private ArrayList<UIImage> nextPieces;
	private PieceLine pL;
	public static int heldBlock = -1;

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (state == 0) {
			g.setColor(Color.white);
			g.setColor(Color.blue);
			g.drawString("Tetris", 200, 200);
			for (UIElement ui : UI) {
				ui.hover(g, mouseXY.getX(), mouseXY.getY());
				ui.paint(g);
			}
			g.setColor(new Color(22, 180, 72));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setFont(new Font("Graduate", 80, 80));
			if (initLevel < 10) {
				g2d.drawString("0" + initLevel + "", 543, 650);
			} else {
				g2d.drawString(initLevel + "", 540, 650);
			}
			g2d.drawString("A", 428, 650);
		

		}
		if (state == 1 || state == 2) {
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			bg.paint(g);
			pL.drawPieces(g, nextBlocks);
			pL.drawHeld(g, heldBlock);
			g.setColor(new Color(32, 142, 116));
			g.fillRect(309, 40, 410, 50);
			g.setColor(Color.black);
			g2d.setFont(new Font("Times New Roman", 30, 30));
			g2d.setStroke(new BasicStroke(5));
			g2d.drawRect(311, 42, 405, 765);	
			printSimpleString(score + "", g2d.getFontMetrics().stringWidth(score + ""), 70, 645, g2d);
			try {
				for (Block[] arr : board.getBlocks()) {

					for (Block b : arr) {
						try {
							b.paint(g);
						} catch (Exception e) {
						}
					}
				}
			} catch (Exception e) {

			}
			try {
				for (Block b : board.getGhostBlocks()) {
					b.paint(g);
				}
			} catch (Exception e) {

			}
			try {
				for (Block b : board.getMoveBlocks()) {
					b.paint(g);
				}
			} catch (Exception e) {

			}
			// Draws the pause menu
			if (!pause) {
				g.setColor(Color.gray);
				g.fillRect(75, 200, 250, 400);
				g.setColor(Color.black);
				g.setFont(new Font("Test", 0, 30));
				g.drawString("Game Paused", 110, 250);
			}
			if (state == 2) {
				
			}
		}
		if (state == 4) {
			// System.out.println("waiting " + frameNum);
			if (startCnt >= 180) {
				state = 1;
				startCnt = 0;
			}
			startCnt++;
		}

		try {
			Thread.sleep(16, 666667);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void printSimpleString(String s, int width, int XPos, int YPos, Graphics2D g2d){
        int stringLen = (int)
            g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        int start = width/2 - stringLen/2;
        g2d.drawString(s, start + XPos, YPos);
 }

	public static void addPoints(int points) {
		score += points;
	}

	public static void updateNextBlocks(ArrayList<Integer> gen1, ArrayList<Integer> gen2) {
		for (int i = 0; i < 6; i++) {
			try {
				nextBlocks[i] = gen1.get(i);
			} catch (Exception e) {
				for (int x = 0; x < 6 - i; x++) {
					nextBlocks[x + i] = gen2.get(x);
				}
				i = 7;
			}
		}
	}

	public void run() {
		while (true) {
			while (!pause) {
				try {
					Thread.sleep(16, 666667);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			frameNum++;
			if (moveDown && board.level() <= 18) {
				downCnt++;
				if (downCnt >= 2) {
					if (!board.checkForCollision(0, 0, 1)) {
						contacting = true;
					} else {
						board.moveDown();
						score++;
					}
					downCnt = 0;
				}
			} else if (frameNum >= levelSpeed[board.level()] && !end) {
				if (!board.checkForCollision(0, 0, 1)) {
					contacting = true;
				} else {
					board.moveDown();
					if (moveDown) {
						score++;
					}
				}
				frameNum = 0;
			}
			if (moveRight) {
				if (dcRight >= 8) {
					rightCnt++;
				} else {
					dcRight++;
				}
				if (rightCnt >= 2) {
					board.moveSide(true);
					rightCnt = 0;
				}
			}
			if (moveLeft) {
				if (dcLeft >= 8) {
					leftCnt++;
				} else {
					dcLeft++;
				}
				if (leftCnt >= 2) {
					board.moveSide(false);
					leftCnt = 0;
				}
			}
			try {
				Thread.sleep(16, 666667);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Frame() {
		mouseXY = new Point();
		state = 0;
		score = 0;
		initLevel = 1;
		UI = new ArrayList<UIElement>();
		UI.add(new UIButton(100, 100, 200, 200));
		UI.add(new UIImage(0, 0, 1075, 850, "imgs/menu.png"));
		bg = new UIImage(0, 0, 1024, 850, "imgs/Game screen.png");
		JFrame f = new JFrame("Tetris");
		f.setSize(new Dimension(1024, 879));
		f.add(this);
		f.setResizable(false);
		f.setLayout(new GridLayout(1, 1));
		f.addMouseListener(this);
		f.addKeyListener(this);
		Timer t = new Timer(0, this);
		move = new Thread(this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		nextBlocks = new int[6];
		end = false;
		pL = new PieceLine();

		lockDelay = new Thread(new Runnable() {
			public void run() {
				while (true) {
					while (!pause) {
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
					if (contacting) {
						lock++;
						if (board.checkForCollision(0, 0, 1)) {
							contacting = false;
							lock = 0;
						}
					} else {
						lock = 0;
					}
					if (lock >= 30) {
						board.placeBlocks();
						contacting = false;
					}
					try {
						Thread.sleep(16, 666667);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		Thread mouseTrack = new Thread(new Runnable() {
			public void run() {
				while (true) {
					mouseXY.setLocation(
							MouseInfo.getPointerInfo().getLocation().getX() - f.getLocationOnScreen().getX(),
							MouseInfo.getPointerInfo().getLocation().getY() - f.getLocationOnScreen().getY() - 25);
				}
			}
		});
		mouseTrack.start();

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
		switch (arg0.getKeyCode()) {
		case 39: // right
			if (state == 1) {
				if (!moveRight) {
					rightCnt = 5;
				}
				moveRight = true;
			}
			break;
		case 37: // left
			if (state == 1) {
				if (!moveLeft) {
					leftCnt = 5;
				}
				moveLeft = true;
			}
			break;

		case 32: // instant drop, space bar
			if (!spaceHeld && state == 1) {
				board.hardDrop();
				spaceHeld = true;
			}
			break;

		case 38: // rotate, up key
			if (state == 0) {
				if (initLevel < 19) {
					initLevel++;
				}
			}
			if (!held && state == 1) {
				if (pause) {
					board.rotate();
					held = true;
				}
			}
			break;

		case 40: // soft drop, down key
			if (state == 0) {
				if (initLevel > 1) {
					initLevel--;
				}
			}
			if (state == 1) {
				downCnt = 0;
				moveDown = true;
			}
			break;

		case 67: // Holding
			if (!hold && state == 1) {
				board.holdBlock();
				hold = true;
			}
			break;
		case 27:
			if (!held3 && state == 1) {
				held3 = true;
				if (pause) {
					pause = false;
					board.pause();
				} else {
					pause = true;
					board.pause();
				}
			}
			break;
		case 10:
			if (state == 0)
				startGame();
			break;
		case 17:
			if (!heldCC) {
				board.rotateCClock();
				heldCC = true;
			}
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getKeyCode()) {
		case 39:
			dcRight = 0;
			moveRight = false;
			break;
		case 37:
			dcLeft = 0;
			moveLeft = false;
			break;
		case 32:
			spaceHeld = false;
			break;
		case 38:
			held = false;
			break;
		case 40:
			moveDown = false;
			break;
		case 67:
			hold = false;
			break;
		case 27:
			held3 = false;
			break;
		case 17:
			heldCC = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	private static void startGame() {
		state = 1;
		end = false;
		board = new Board(initLevel);
		rotateThread = new Thread(board);
		rotateThread.start();
		lockDelay.start();
		move.start();
	}

	private static boolean end;

	public static void endGame() {
		moveDown = false;
		state = 2;
		end = true;
	}

}