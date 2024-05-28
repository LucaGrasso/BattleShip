package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
/**
 *
 * @author Luca Grasso
 * @version 1.0
 *
 */
public class Square {

	private final int x;
    private final int y;
    private final int offset;
	private Color color;
	public boolean busy;
	private Color borderColor;

	public Square(int x, int y, int offset, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.offset = offset;
		this.color = color;
		busy = false;
		borderColor = Color.BLACK;
	}

	public Color getColor() {
		return color;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor(Color color) {
		return this.color;
	}

	public boolean getBusy() {
		return busy;
	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, offset, offset);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g.setColor(borderColor);
		g.drawRect(x, y, offset, offset);
	}

	public boolean isClicked(int x, int y) {
        return this.x <= x && this.x + offset >= x && this.y <= y && this.y + offset >= y;
	}

	public void setBusy() {
		this.busy = true;
	}

}

