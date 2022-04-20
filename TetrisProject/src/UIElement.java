import java.awt.Image;

public class UIElement {
	/*
	 * Types: 0: Sign, displays a string text 1: Button, self explanatory 2: Image,
	 * just displays an image 3:
	 */

	private Image hover;
	private int x;
	private int y;
	private int width;
	private int height;

	public UIElement(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean insideBlock(int x, int y) {
		return (x < ) && ()
	}

	protected int getX() {
		return x;
	}

	protected int getY() {
		return y;
	}
}
