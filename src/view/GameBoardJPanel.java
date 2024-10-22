package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Classe che rappresenta il pannello di gioco, composto da una griglia di quadrati.
 * Permette la gestione e la visualizzazione della griglia di gioco.
 *
 * @version 1.0
 */
public class GameBoardJPanel extends JPanel {
	private final List<Square> gameSquares = new ArrayList<>();

	/**
	 * Costruttore del pannello di gioco.
	 *
	 * @param squareLength La lunghezza di ogni quadrato della griglia.
	 * @param boardSize La dimensione (numero di quadrati) della griglia.
	 */
	public GameBoardJPanel(int squareLength, int boardSize) {
		int y = 0;
		int x = 0;
		// Creazione della griglia di quadrati
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				gameSquares.add(new Square(x + squareLength * i, y + squareLength * j, squareLength, Color.LIGHT_GRAY));
			}
		}
	}

	/**
	 * Aggiunge un listener per gli eventi di clic del mouse.
	 *
	 * @param listener Il listener da aggiungere.
	 */
	public void addMouseClickListener(MouseListener listener) {
		this.addMouseListener(listener);
	}

	/**
	 * Restituisce la lista dei quadrati della griglia.
	 *
	 * @return La lista dei quadrati della griglia.
	 */
	public List<Square> getSquares() {
		return gameSquares;
	}

	/**
	 * Imposta il colore del bordo di un quadrato specifico.
	 *
	 * @param nr L'indice del quadrato nella lista.
	 * @param squareBorderColor Il colore del bordo da impostare.
	 */
	public void setColor(int nr, Color squareBorderColor) {
		gameSquares.get(nr).setBorderColor(squareBorderColor);
		gameSquares.get(nr).setColor(squareBorderColor);
		this.repaint();
	}

	/**
	 * Imposta il colore del bordo e il colore interno di un quadrato specifico.
	 *
	 * @param nr L'indice del quadrato nella lista.
	 * @param squareBorderColor Il colore del bordo da impostare.
	 * @param squareColor Il colore interno da impostare.
	 */
	public void setColor(int nr, Color squareBorderColor, Color squareColor) {
		gameSquares.get(nr).setBorderColor(squareBorderColor);
		gameSquares.get(nr).setColor(squareColor);
		this.repaint();
	}

	/**
	 * Restituisce il colore interno di un quadrato specifico.
	 *
	 * @param nr L'indice del quadrato nella lista.
	 * @return Il colore interno del quadrato.
	 */
	public Color getColor(int nr) {
		return gameSquares.get(nr).getColor();
	}

	/**
	 * Override del metodo paintComponent per disegnare la griglia di gioco.
	 *
	 * @param g Il contesto grafico utilizzato per disegnare i componenti.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		// Disegna ogni quadrato della griglia
		for (Square currentSquare : gameSquares) {
			currentSquare.paint(g);
		}
	}
}