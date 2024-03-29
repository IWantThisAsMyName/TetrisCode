import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class Block {
	private int x;
	private int y;
	private boolean rotateCenter;
	private boolean line;
	private Image image;

	public Block(int color, int x, int y, boolean rotateCenter) {
		this.x = x;
		this.y = y + 1;
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
	
	public Block(boolean ghostPiece, int x, int y, boolean center) {
		this.x = x;
		this.y = y + 1;
		image = getImage("imgs/ghost.png");
	}
	
	public Block(int color, int x, int y, boolean rotateCenter, boolean line) {
		this.x = x;
		this.y = y + 1;
		this.rotateCenter = rotateCenter;
		this.line = line;
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
	
	public Block(Block old) {
		x = old.getX();
		y = old.getY();
		rotateCenter = old.center();
		image = old.image();
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Image image() {
		return image;
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
	
	public boolean line() {
		return line;
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
		if(y >= 4) {
			g2D.drawImage(image, 314 + (x * 40), 5 + (y - 3) * 40, 40, 40, null);
		}
	}

}
