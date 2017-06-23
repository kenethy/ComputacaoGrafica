package malhaDeTriangulos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class Pixels extends Canvas {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int height;
	private int width;
	private Color black = new Color(0, 0, 0);
	private Color white = new Color(255, 255, 255);
	private char[][] pixels;

	public Pixels(int h, int w, char[][] px) {
		this.height = h;
		this.width = w;
		this.pixels = px;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		for (int x = 0; x < this.height; x++) {
			for (int y = 0; y < this.width; y++) {
				if (this.pixels[x][y] == 'B')
					g.setColor(white);
				else
					g.setColor(black);
				g.drawLine(y, x, y, x);
			}
		}
	}
}
