import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class UIElement {
	/*
	 * Types: 0: Sign, displays a string text 1: Button, self explanatory 2: Image,
	 * just displays an image 3:
	 */
	private int x;
	private int y;
	private int width;
	private int height;
	
	public UIElement() {
		
	}
	
	public UIElement(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	protected boolean insideBlock(double x, double y) {
		return (x > this.x && x < this.x + width) && (y > this.y && y < this.y + height);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	protected int getWidth() {
		return width;
	}
	protected int getHeight() {
		return height;
	}
	
	public void hover(Graphics g, double x, double y) {
		return;
	}
	
	public void paint(Graphics g) {
		return;
	}
}
