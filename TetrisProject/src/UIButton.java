import java.awt.Color;
import java.awt.Graphics;

public class UIButton extends UIElement {

	public UIButton(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public UIButton(int[] x, int[] y) {
		super();
	}
	
	public void hover(Graphics g, double x, double y) {
		if (insideBlock(x, y)) {
			System.out.println("Detected");
			g.setColor(new Color(255, 255, 0, 100));
			g.fillRect(getX(), getY(), getWidth(), getHeight());
		}
	}
	
}
