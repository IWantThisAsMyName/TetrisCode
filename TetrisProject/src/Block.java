import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Block {
	private int x;
	private int y;
	private boolean rotateCenter;
	private Image image;

	public Block(int color, int x, int y, boolean rotateCenter) {
		this.x = x;
		this.y = y + 4;
		this.rotateCenter = rotateCenter;
		switch (color) {
		case 0:
			image = getImage("imgs/cyanSquare.png");
			break;
		case 1:
			image = getImage("imgs/yellowSquare.png");
			break;
		case 2:
			image = getImage("imgs/blueSquare.png");
			break;
		case 3:
			image = getImage("imgs/orangeSquare.png");
			break;
		case 4:
			image = getImage("imgs/greenSquare.png");
			break;
		case 5:
			image = getImage("imgs/redSquare.png");
			break;
		case 6:
			image = getImage("imgs/purpleSquare.png");
			break;
		}
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public void changeX(int x) {
		this.x = x;
	}

	public void changeY(int y) {
		this.y = y;
	}

	public boolean center() {
		return rotateCenter;
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

	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(image, x * 40, y * 40, 40, 40, null);
	}

}