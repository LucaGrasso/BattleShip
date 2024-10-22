package model.state;

/**
 * Interfaccia che rappresenta lo stato del gioco nel pattern State.
 *
 * @version 1.0
 */
public interface GameState {

	/**
	 * Metodo da chiamare per iniziare una nuova partita.
	 */
	void newGame();

	/**
	 * Metodo da chiamare per avviare il gioco.
	 */
	void start();
}