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
	private Color[][] color;
	private char[][] pixels;

	public Pixels(int h, int w, char[][] px, Color[][] c) {
		this.height = h;
		this.width = w;
		this.pixels = px;
		this.color = c;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				if (this.pixels[y][x] == 'B') {
					g.setColor(this.color[y][x]);
				} else
					g.setColor(black);
				g.drawLine(x, y, x, y);
			}
		}
	}
}
