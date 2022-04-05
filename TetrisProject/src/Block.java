import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.util.ArrayList;

public class Block {
	private Point coords;
	private boolean rotateCenter;
	private Image image;
	private AffineTransform tx;
	public Block(int color, int x, int y, boolean rotateCenter) {
		coords = new Point(x, y);
		this.rotateCenter = rotateCenter;
		switch (color) {
		case 0:
			image = getImage("imgs/blueSquare.png");
			break;
		case 1:
			image = getImage("imgs/cyanSquare.png");
			break;
		case 2:
			image = getImage("imgs/redSquare.png");
			break;
		case 3:
			image = getImage("imgs/greenSquare.png");
			break;
		case 4:
			image = getImage("imgs/orangeSquare.png");
			break;
		case 5:
			image = getImage("imgs/purpleSquare.png");
			break;
		case 6:
			image = getImage("imgs/yellowSquare.png");
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
	
	public Image image() {
		return image;
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