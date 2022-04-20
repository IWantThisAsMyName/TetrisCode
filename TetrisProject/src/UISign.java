import java.awt.Image;

public class UISign extends UIElement {
	private Image signBackground;
	private String text;
	private int textX;
	private int textY;

	public UISign(int x, int y, int width, int height, int textX, int textY, String text) {
		super(x, y, width, height);
		this.text = text;
		this.textY = textY;
		this.textX = textX;
	}

	public void updateText(String text) {
		this.text = text;
	}
}
