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

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	private boolean held = false;
	public void paint(Graphics g) {
		Board.setGraphics(g);
		g.fillRect(-10, -10, 500, 900);
		for(Block[] arr : Board.getBlocks()) {
			for(Block b : arr) {
				try {
					b.paint(g);
				} catch (Exception e) {}
			}
		}
		for(Block b : Board.getMoveBlocks()) {
			b.paint(g);
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
		Timer t = new Timer(16, this);
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
		System.out.println(arg0.getKeyCode());
		switch (arg0.getKeyCode()) {
		case 39: // right
			Board.moveSide(true);
			break;

		case 37: // left
			Board.moveSide(false);
			break;

		case 32: // instant drop, space bar
			break;

		case 38: // rotate, up key
			if(!held) {
				Board.rotatePiece();
				held = true;
			}
			break;

		case 40: // soft drop, down key
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == 38) {
			held = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}