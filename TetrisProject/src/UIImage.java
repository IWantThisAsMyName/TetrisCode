import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class UIImage extends UIElement {
	private Image img;
	public UIImage(int x, int y, int width, int height, String image) {
		super(x, y, width, height);
		img = getImage(image);
	}

	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
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
