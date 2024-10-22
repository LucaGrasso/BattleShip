package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Classe che rappresenta un singolo quadrato nella griglia di gioco.
 * Contiene informazioni sul colore del quadrato, la posizione e se è occupato.
 * Fornisce metodi per disegnare il quadrato e verificare clic.
 *
 * @version 1.0
 */
public class Square {

	private final int x;
	private final int y;
	private final int offset;
	private Color color;
	public boolean busy;
	private Color borderColor;

	/**
	 * Costruttore della classe Square.
	 *
	 * @param x La coordinata x del quadrato.
	 * @param y La coordinata y del quadrato.
	 * @param offset La dimensione del quadrato.
	 * @param color Il colore del quadrato.
	 */
	public Square(int x, int y, int offset, Color color) {
		this.x = x;
		this.y = y;
		this.offset = offset;
		this.color = color;
		this.busy = false;
		this.borderColor = Color.BLACK;
	}

	/**
	 * Restituisce il colore del quadrato.
	 *
	 * @return Il colore del quadrato.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Imposta il colore del bordo del quadrato.
	 *
	 * @param borderColor Il colore del bordo da impostare.
	 */
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	/**
	 * Imposta il colore del quadrato.
	 *
	 * @param color Il colore da impostare.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Verifica se il quadrato è occupato.
	 *
	 * @return True se il quadrato è occupato, altrimenti false.
	 */
	public boolean getBusy() {
		return busy;
	}

	/**
	 * Segna il quadrato come occupato.
	 */
	public void setBusy() {
		this.busy = true;
	}

	/**
	 * Disegna il quadrato.
	 *
	 * @param g Il contesto grafico utilizzato per disegnare il quadrato.
	 */
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, offset, offset);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g.setColor(borderColor);
		g.drawRect(x, y, offset, offset);
	}

	/**
	 * Verifica se il quadrato è stato cliccato.
	 *
	 * @param x La coordinata x del clic.
	 * @param y La coordinata y del clic.
	 * @return True se il quadrato è stato cliccato, altrimenti false.
	 */
	public boolean isClicked(int x, int y) {
		return this.x <= x && this.x + offset >= x && this.y <= y && this.y + offset >= y;
	}
}