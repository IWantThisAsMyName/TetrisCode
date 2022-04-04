
public class Main {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Frame f = new Frame();
		Board board = new Board();
		Thread drawSquare = new Thread(board);
		drawSquare.start();
		Block b = new Block(1, 10, 10, false);
	}

}
