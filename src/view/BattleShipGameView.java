package view;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Direction;
import model.ShipType;

/**
 * Rappresenta la vista principale per il gioco BattleShip.
 * Gestisce l'interazione tra il frame di gioco e il frame delle impostazioni.
 *
 * @version 1.0
 */
public class BattleShipGameView implements IBattleShipGameView {

	private static BattleShipGameView instance;
	private GameFrame gameFrame;
	private SettingsJFrame settingsJFrame;
	private String playerName;

	/**
	 * Costruttore privato per implementare il pattern Singleton.
	 * Lancia il frame di gioco e chiede al giocatore il suo nome.
	 */
	private BattleShipGameView() {
		gameFrame = new GameFrame();
		gameFrame.launch(this.askPlayerName());
		gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameFrame.setVisible(true);
	}

	/**
	 * Ottiene l'unica istanza di BattleShipGameView.
	 *
	 * @return l'unica istanza di BattleShipGameView
	 */
	public static synchronized BattleShipGameView getInstance() {
		if (instance == null) {
			instance = new BattleShipGameView();
		}
		return instance;
	}

	/**
	 * Aggiorna il tabellone di gioco. Ad esempio, quando il gioco viene resettato.
	 */
	public void updateGameBoard() {
		// Resetta i pannelli della griglia e delle navi
		gameFrame.closeApplication();
		gameFrame = new GameFrame();
		gameFrame.launch(this.playerName);
		gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameFrame.setVisible(true);
	}

	/**
	 * Mostra una finestra di dialogo per chiedere il nome del giocatore.
	 *
	 * @return il nome del giocatore
	 */
	public String askPlayerName() {
		this.playerName = JOptionPane.showInputDialog("Inserisci il tuo nickname/nome:");
		return playerName;
	}

	/**
	 * Apre il frame delle impostazioni.
	 */
	public void openSettingsJFrame() {
		settingsJFrame = SettingsJFrame.getInstance();
		settingsJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Rimozione eventuale di listener già esistenti
		for (WindowListener listener : settingsJFrame.getWindowListeners()) {
			settingsJFrame.removeWindowListener(listener);
		}

		settingsJFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				enableSettingsButton();  // Il pulsante delle impostazioni sarà riabilitato qui
			}
		});

		settingsJFrame.setVisible(true);
	}

	/**
	 * Abilita il pulsante delle impostazioni nel frame di gioco.
	 */
	public void enableSettingsButton() {
		this.getGameFrame().enableSettingsButton();
	}

	/**
	 * Restituisce il nome del giocatore.
	 *
	 * @return il nome del giocatore
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Restituisce il primo pannello del tabellone di gioco.
	 *
	 * @return il primo pannello del tabellone di gioco
	 */
	public GameBoardJPanel getGameBoardPanel1() {
		return this.getGameFrame().getGameBoardJPanel1();
	}

	/**
	 * Restituisce il secondo pannello del tabellone di gioco.
	 *
	 * @return il secondo pannello del tabellone di gioco
	 */
	public GameBoardJPanel getGameBoardPanel2() {
		return this.getGameFrame().getGameBoardJPanel2();
	}

	/**
	 * Restituisce la dimensione del primo tabellone di gioco.
	 *
	 * @return la dimensione del primo tabellone di gioco
	 */
	public int getGameBoard1Size() {
		return this.getGameBoardPanel1().getSquares().size();
	}

	/**
	 * Restituisce la dimensione del secondo tabellone di gioco.
	 *
	 * @return la dimensione del secondo tabellone di gioco
	 */
	public int getGameBoard2Size() {
		return this.getGameBoardPanel2().getSquares().size();
	}

	/**
	 * Verifica se una casella nel secondo tabellone di gioco è cliccata.
	 *
	 * @param i l'indice della casella
	 * @param x la coordinata x
	 * @param y la coordinata y
	 * @return true se la casella è cliccata, false altrimenti
	 */
	public boolean isClickedInGameBoard2(int i, int x, int y) {
		return this.getGameBoardPanel2().getSquares().get(i).isClicked(x, y);
	}

	/**
	 * Verifica se una casella nel primo tabellone di gioco è cliccata.
	 *
	 * @param i l'indice della casella
	 * @param x la coordinata x
	 * @param y la coordinata y
	 * @return true se la casella è cliccata, false altrimenti
	 */
	public boolean isClickedInGameBoard1(int i, int x, int y) {
		return this.getGameBoardPanel1().getSquares().get(i).isClicked(x, y);
	}

	/**
	 * Imposta il colore di una nave nel primo tabellone di gioco.
	 *
	 * @param number il numero della nave
	 * @param color il colore da impostare
	 */
	public void colorShipGameBoardPanel1(int number, Color color) {
		this.getGameBoardPanel1().setColor(number, color);
	}

	/**
	 * Imposta il colore di una nave e del suo bordo nel primo tabellone di gioco.
	 *
	 * @param number il numero della nave
	 * @param borderColor il colore del bordo da impostare
	 * @param color il colore da impostare
	 */
	public void colorRemoveShipGameBoardPanel1(int number, Color borderColor, Color color) {
		this.getGameBoardPanel1().setColor(number, borderColor, color);
	}

	/**
	 * Imposta il colore di una nave nel secondo tabellone di gioco.
	 *
	 * @param number il numero della nave
	 * @param color il colore da impostare
	 */
	public void colorShipGameBoardPanel2(int number, Color color) {
		this.getGameBoardPanel2().setColor(number, color);
	}

	/**
	 * Verifica se una casella nel primo tabellone di gioco è occupata.
	 *
	 * @param number il numero della casella
	 * @return true se la casella è occupata, false altrimenti
	 */
	public boolean getSquareBusyGameBoardPanel1(int number) {
		return this.getGameBoardPanel1().getSquares().get(number).getBusy();
	}

	/**
	 * Verifica se una casella nel secondo tabellone di gioco è occupata.
	 *
	 * @param number il numero della casella
	 * @return true se la casella è occupata, false altrimenti
	 */
	public boolean getSquareBusyGameBoardPanel2(int number) {
		return this.getGameBoardPanel2().getSquares().get(number).getBusy();
	}

	/**
	 * Imposta una casella nel secondo tabellone di gioco come occupata.
	 *
	 * @param number il numero della casella
	 */
	public void setSquareGameBoardPanel2_Occupied(int number) {
		this.getGameBoardPanel2().getSquares().get(number).setBusy();
	}

	/**
	 * Restituisce il tipo di nave selezionato.
	 *
	 * @return il tipo di nave selezionato
	 */
	public ShipType getSelectedShipType() {
		return this.getGameFrame().getShipBoardPanel().getSelectedShipType();
	}

	/**
	 * Restituisce la direzione selezionata.
	 *
	 * @return la direzione selezionata
	 */
	public Direction getSelectedDirection() {
		return this.getGameFrame().getShipBoardPanel().getSelectedDirection();
	}

	/**
	 * Restituisce il frame di gioco.
	 *
	 * @return il frame di gioco
	 */
	public GameFrame getGameFrame() {
		return gameFrame;
	}

	/**
	 * Mostra un messaggio di errore.
	 *
	 * @param message il messaggio di errore da mostrare
	 */
	public void showError(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * Disabilita il pulsante di avvio.
	 */
	public void disableStartButton() {
		this.getGameFrame().disableStartButton();
	}

	/**
	 * Verifica se il pulsante di avvio è abilitato.
	 *
	 * @return true se il pulsante di avvio è abilitato, false altrimenti
	 */
	public boolean isStartButtonEnabled() {
		return this.getGameFrame().isStartButtonEnabled();
	}

	/**
	 * Disabilita il pulsante delle impostazioni.
	 */
	public void disableSettingsButton() {
		this.getGameFrame().disableSettingsButton();
	}

	/**
	 * Verifica se il pulsante delle impostazioni è abilitato.
	 *
	 * @return true se il pulsante delle impostazioni è abilitato, false altrimenti
	 */
	public boolean isSettingsButtonEnabled() {
		return this.getGameFrame().isSettingsButtonEnabled();
	}

	/**
	 * Disabilita il primo pannello del tabellone di gioco.
	 */
	public void disableGameBoardJPanel1() {
		this.getGameFrame().disableGameBoardJPanel1();
	}

	/**
	 * Verifica se il primo pannello del tabellone di gioco è abilitato.
	 *
	 * @return true se il primo pannello del tabellone di gioco è abilitato, false altrimenti
	 */
	public boolean isGameBoardJPanel1Enabled() {
		return this.getGameFrame().isGameBoardJPanel1Enabled();
	}

	/**
	 * Aggiorna il campo del nome del giocatore umano.
	 *
	 * @param n il nuovo nome
	 */
	public void updateNameFieldHuman(String n) {
		this.getGameFrame().updateNameFieldHuman(n);
	}

	/**
	 * Aggiorna il campo del nome del giocatore computer.
	 *
	 * @param n il nuovo nome
	 */
	public void updateNameFieldComputer(String n) {
		this.getGameFrame().updateNameFieldComputer(n);
	}

	/**
	 * Mostra un messaggio all'utente.
	 *
	 * @param message il messaggio da mostrare
	 */
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * Chiude l'applicazione.
	 */
	public void closeApplication() {
		this.getGameFrame().closeApplication();
	}

	/**
	 * Verifica se le navi sono visibili.
	 *
	 * @return true se le navi sono visibili, false altrimenti
	 */
	public boolean isShipsVisible() {
		return settingsJFrame.shipsVisible();
	}

	/**
	 * Restituisce il frame delle impostazioni.
	 *
	 * @return il frame delle impostazioni
	 */
	public SettingsJFrame getSettingsJFrame() {
		return settingsJFrame;
	}
}