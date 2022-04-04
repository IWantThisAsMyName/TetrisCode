import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;

public class Block {
	private Point coords;
	private boolean rotateCenter;
	private Image image;

	public Block(int color, int x, int y, boolean rotateCenter) {
		coords.setLocation(x, y);
		this.rotateCenter = rotateCenter;
		switch (color) {
		case 0:
			image = getImage("");
			break;
		case 1:
			image = getImage("");
			break;
		case 2:
			image = getImage("");
			break;
		case 3:
			image = getImage("");
			break;
		case 4:
			image = getImage("");
			break;
		case 5:
			image = getImage("");
			break;
		case 6:
			image = getImage("");
			break;
		}
	}

	public Point getXY() {
		return coords;
	}

	public void changeX(int newX) {
		coords.setLocation(newX, coords.getY());
	}
	
	
	public void changeY(int newY) {
		coords.setLocation(coords.getX(), newY);
	}

	public boolean center() {
		return rotateCenter;
	}

	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Block.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}