import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener, Runnable {
	private static int[] levelSpeed = { 48, 43, 38, 33, 28, 23, 18, 13, 8, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 2, 2, 2, 2, 2,
			2, 2, 2, 2, 2, 1, 1 };
	private boolean moveRight = false;
	private boolean moveLeft = false;
	private boolean held = false;
	private boolean moveDown = false;
	private int frameNum = 0;
	private int downCnt = 0;

	public void paint(Graphics g) {
		Board.setGraphics(g);
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
			if(moveDown && Board.level() <= 18) {
				downCnt++;
				if(downCnt >= 2) {
					Board.moveDown();
					downCnt = 0;
				}
			} else if (frameNum >= levelSpeed[Board.level()]) {
				Board.moveDown();
				frameNum = 0;
			}
			if (moveRight) {
				Board.moveSide(true);
			}
			if (moveLeft) {
				Board.moveSide(false);
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
		JFrame f = new JFrame("Tetris");
		f.setSize(new Dimension(405, 800));
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
				Board.rotatePiece();
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