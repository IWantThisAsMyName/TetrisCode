import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class PieceLine {
	private static Image[] images = { getImage("imgs/TPiece.png"), getImage("imgs/SPiece.png"), getImage("imgs/ZPiece.png"),
			getImage("imgs/LPiece.png"), getImage("imgs/JPiece.png"), getImage("imgs/Square.png"), getImage("imgs/IPiece.png") };
	
	public void drawPieces(Graphics g, int[] pieces) {
		g.drawImage(images[pieces[0]], 48, 415, 140, 70, null);
		for(int i = 1; i < 6; i++) {
			g.drawImage(images[pieces[i]], 840, 283 + ((i - 1 )* 65), 120, 60, null);
		}
	}
	
	public void drawHeld(Graphics g, int piece) {
		if(piece == -1) return;
		if(piece == 6) {
			g.drawImage(images[piece], 58, 265, 120, 60, null);
			return;
		}
		g.drawImage(images[piece], 58, 253, 120, 60, null);
	}

	private static Image getImage(String path) {
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
