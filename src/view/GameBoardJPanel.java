package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Luca Grasso
 * @version 1.0
 *
 */

public class GameBoardJPanel extends JPanel {
	private final List<Square> gameSquares = new ArrayList<>();

	public GameBoardJPanel(int squareLength, int boardSize) {
		int y = 0;
		int x = 0;
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				gameSquares.add(new Square(x + squareLength * i, y + squareLength * j, squareLength, Color.LIGHT_GRAY));
			}
		}

	}

	public void addMouseClickListener(MouseListener listener) {
		this.addMouseListener(listener);
	}

	public List<Square> getSquares() {
		return gameSquares;
	}

	public void setColor(int nr, Color squareBorderColor) {
		gameSquares.get(nr).setBorderColor(squareBorderColor);
		gameSquares.get(nr).setColor(squareBorderColor);
		this.repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		for (Square currentSquare : gameSquares) {
			currentSquare.paint(g);
		}
	}
}
