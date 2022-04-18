import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
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
	private boolean moveDown = false;
	private boolean contacting = false;
	private boolean pause = true;
	private boolean hold = false;
	private boolean held3 = false;
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

	public void paint(Graphics g) {
		if (state == 0) {
			g.setColor(Color.blue);
			g.drawString("Tetris", 200, 200);
		}
		if (state == 1 || state == 2) {
			g.drawString(score + "", 10, 10);
			g.fillRect(-10, -10, 500, 900);
			for (Block[] arr : board.getBlocks()) {
				for (Block b : arr) {
					try {
						b.paint(g);
					} catch (Exception e) {
					}
				}
			}
			for (Block b : board.getMoveBlocks()) {
				b.paint(g);
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
				g.setColor(Color.gray);
				g.fillRect(75, 200, 250, 400);
				g.setColor(Color.black);
				g.setFont(new Font("Test", 0, 30));
				g.drawString("Game Over", 110, 250);
			}
		}
		try {
			Thread.sleep(16, 666667);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
					}
					score += 1;
					downCnt = 0;
				}
			} else if (frameNum >= levelSpeed[board.level()] && !end) {
				if (!board.checkForCollision(0, 0, 1)) {
					contacting = true;
				} else {
					board.moveDown();
				}
				frameNum = 0;
			}
			if (moveRight) {
				rightCnt++;
				if (rightCnt >= 5) {
					board.moveSide(true);
					rightCnt = 0;
				}
			}
			if (moveLeft) {
				leftCnt++;
				if (leftCnt >= 5) {
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
		state = 0;
		score = 0;
		initLevel = 0;
		JFrame f = new JFrame("Tetris");
		f.setSize(new Dimension(415, 840));
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
		System.out.println(arg0.getKeyCode());
		switch (arg0.getKeyCode()) {
		case 39: // right
			if (state == 0) {
				if (initLevel < 19) {
					initLevel++;
				}
			}
			if (state == 1) {
				if (!moveRight) {
					rightCnt = 5;
				}
				moveRight = true;
			}
			break;

		case 37: // left
			if (state == 0) {
				if(initLevel > 0) {
					initLevel--;
				}
			}
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
			if (!held && state == 1) {
				if (pause) {
					board.rotate();
					held = true;
				}
			}
			break;

		case 40: // soft drop, down key
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
			if(state == 0) startGame();
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getKeyCode()) {
		case 39:
			moveRight = false;
			break;
		case 37:
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
		state = 2;
		end = true;
	}

}