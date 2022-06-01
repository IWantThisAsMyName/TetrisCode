import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;

public class UIButton extends UIElement {
	private Polygon poly;
	private int[] xCoords, yCoords;
	private static Point origin = new Point(0, 0);

	public UIButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		poly = null;
	}

	public UIButton(int[] x, int[] y) {
		super();
		xCoords = x;
		yCoords = y;
		poly = new Polygon(x, y, x.length);
	}

	public void hover(Graphics g, double x, double y) {
		g.setColor(new Color(255, 255, 0, 100));
		if (insideBlock(x, y)) {
			System.out.println("inside");
			if (poly == null) {
				System.out.println("hover");
				g.fillRect(getX(), getY(), getWidth(), getHeight());
				return;
			}
			return;
		}
		if (poly != null) {
			Rectangle box = poly.getBounds();
			if (box.contains(new Point((int) x, (int) y))) {
				if (inside(x, y)) {
					g.fillPolygon(poly);
				}
			}
		}
	}
	
	
	private boolean inside(double x, double y) {
		Point p = new Point((int) x, (int) y);
		int intsersectCnt = 0;
		for (int i = 0; i < poly.npoints - 1; i++) {
			if (doIntersect(new Point(xCoords[i], yCoords[i]), new Point(xCoords[i + 1], yCoords[i + 1]), p, origin)) {
				intsersectCnt++;
			}
		}
		if (doIntersect(new Point(xCoords[0], yCoords[0]),
				new Point(xCoords[xCoords.length - 1], yCoords[xCoords.length - 1]), p, origin)) {
			intsersectCnt++;
		}
		return intsersectCnt % 2 == 1;
	}

	private boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
		// Find the four orientations needed for general and
		// special cases
		int o1 = orientation(p1, q1, p2);
		int o2 = orientation(p1, q1, q2);
		int o3 = orientation(p2, q2, p1);
		int o4 = orientation(p2, q2, q1);

		// General case
		if (o1 != o2 && o3 != o4)
			return true;

		// Special Cases
		// p1, q1 and p2 are collinear and p2 lies on segment p1q1
		if (o1 == 0 && onSegment(p1, p2, q1))
			return true;

		// p1, q1 and q2 are collinear and q2 lies on segment p1q1
		if (o2 == 0 && onSegment(p1, q2, q1))
			return true;

		// p2, q2 and p1 are collinear and p1 lies on segment p2q2
		if (o3 == 0 && onSegment(p2, p1, q2))
			return true;

		// p2, q2 and q1 are collinear and q1 lies on segment p2q2
		if (o4 == 0 && onSegment(p2, q1, q2))
			return true;

		return false; // Doesn't fall in any of the above cases
	}

	private static int orientation(Point p, Point q, Point r) {
		// See https://www.geeksforgeeks.org/orientation-3-ordered-points/
		// for details of below formula.
		int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

		if (val == 0)
			return 0; // collinear

		return (val > 0) ? 1 : 2; // clock or counterclock wise
	}

	private static boolean onSegment(Point p, Point q, Point r) {
		if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) && q.y <= Math.max(p.y, r.y)
				&& q.y >= Math.min(p.y, r.y))
			return true;

		return false;
	}
}
