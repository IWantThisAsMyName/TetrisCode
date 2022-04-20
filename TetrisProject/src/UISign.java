import java.awt.Image;

public class UISign extends UIElement {
	private Image signBackground;
	private String text;
	public UISign(int x, int y, int width, int height, String text) {
		super(x, y, width, height);
		this.text = text;
	}

	public void updateText(String text) {
		this.text = text;
	}
}	
