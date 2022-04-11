import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener, Runnable {
	Thread rotateThread;

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Frame f = new Frame();
		Board.newPiece();
	}

	private static int[] levelSpeed = { 48, 43, 38, 33, 28, 23, 18, 13, 8, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 2, 2, 2, 2, 2,
			2, 2, 2, 2, 2, 1, 1 };
	private boolean moveRight = false;
	private boolean moveLeft = false;
	private boolean held = false;
	private boolean moveDown = false;
	private boolean contacting = false;
	private int frameNum = 0;
	private int downCnt = 0;
	private int leftCnt = 0, rightCnt = 0;
	private static int lock = 0;

	public void paint(Graphics g) {
		g.fillRect(-10, -10, 500, 900);
		for (Block[] arr : Board.getBlocks()) {
			for (Block b : arr) {
				try {
					b.paint(g);
				} catch (Exception e) {
				}
			}
		}
		for (Block b : Board.getMoveBlocks()) {
			b.paint(g);
		}
		try {
			Thread.sleep(16, 666667);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			frameNum++;
			if (moveDown && Board.level() <= 18) {
				downCnt++;
				if (downCnt >= 2) {
					Board.moveDown();
					downCnt = 0;
				}
			} else if (frameNum >= levelSpeed[Board.level()]) {
				if (!Board.checkForCollision(0, 0, 1)) {
					contacting = true;
				} else {
					Board.moveDown();
				}
				frameNum = 0;
			}
			if (moveRight) {
				rightCnt++;
				if (rightCnt >= 5) {
					Board.moveSide(true);
					rightCnt = 0;
				}
			}
			if (moveLeft) {
				leftCnt++;
				if (leftCnt >= 5) {
					Board.moveSide(false);
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
		Board board = new Board(0);
		rotateThread = new Thread(board);
		rotateThread.start();
		JFrame f = new JFrame("Tetris");
		f.setSize(new Dimension(405, 880));
		f.add(this);
		f.setResizable(false);
		f.setLayout(new GridLayout(1, 1));
		f.addMouseListener(this);
		f.addKeyListener(this);
		Timer t = new Timer(0, this);
		Thread b = new Thread(this);
		b.start();
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		Thread lockDelay = new Thread(new Runnable() {
			public void run() {
				while (true) {
					if (contacting) {
						lock++;
						if (!Board.checkForCollision(0, 0, 1)) {
							System.out.println("Off the bottom");
							contacting = false;
						}
					} else {
						lock = 0;
					}
					if (lock >= 30) {
						System.out.println("Placing block");
						Board.placeBlocks();
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
		lockDelay.start();

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
			moveRight = true;
			break;

		case 37: // left
			moveLeft = true;
			break;

		case 32: // instant drop, space bar
			break;

		case 38: // rotate, up key
			if (!held) {
				Board.rotate();
				held = true;
			}
			break;

		case 40: // soft drop, down key
			downCnt = 0;
			moveDown = true;
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
			break;
		case 38:
			held = false;
			break;
		case 40:
			moveDown = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
