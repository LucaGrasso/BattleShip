package view;

import model.ShipType;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Classe che rappresenta il frame principale del gioco, contenente tutti i pannelli di visualizzazione.
 * Gestisce l'interfaccia utente per la visualizzazione delle griglie di gioco, i pulsanti di controllo e le etichette di nome.
 *
 * @version 1.0
 */
public class GameFrame extends JFrame {

	private GameBoardJPanel panel1;
	private GameBoardJPanel panel2;
	private final JPanel namePanel1 = new JPanel();
	private final JPanel namePanel2 = new JPanel();
	private ShipBoardJPanel shipBordJPanel;

	private JButton startButton;
	private JButton settingsButton;

	private JLabel name1Label;
	private JLabel name2Label;

	public final static int HEIGHT_FRAME = 500; // Almeno 400
	public final static int WIDTH_FRAME = (int) (HEIGHT_FRAME * 2.5);
	public final static int PANEL_SIZE = (int) (HEIGHT_FRAME * 4 / 5);
	public final static int NUM_COLUMNS = 10;

	/**
	 * Costruttore del frame di gioco.
	 */
	public GameFrame() {
		super();
	}

	/**
	 * Metodo di lancio del frame di gioco.
	 *
	 * @param name Il nome del giocatore.
	 */
	public void launch(String name) {
		// Posiziona il frame al centro dello schermo
		this.setSize(WIDTH_FRAME, HEIGHT_FRAME);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(null);
		this.setTitle("Sea battle");

		this.completeSettingsShipBordJPanel();
		this.completeSettingsNamePanel1(name);
		this.completeSettingsNamePanel2();
		this.completeSettingsGameBoardPanel1();
		this.completeSettingsGameBoardPanel2();
		this.setStartButton();
		this.setSettingsButton();
	}

	/**
	 * Imposta il pulsante delle impostazioni.
	 */
	private void setSettingsButton() {
		this.settingsButton = new JButton("Institutions");
		this.settingsButton.setLocation(25, PANEL_SIZE - 10);
		this.settingsButton.setSize(new Dimension(350, 40));
		this.add(this.settingsButton);
	}

	/**
	 * Imposta il pulsante di inizio.
	 */
	private void setStartButton() {
		this.startButton = new JButton("Start");
		this.startButton.setLocation(25, PANEL_SIZE - 50);
		this.startButton.setSize(new Dimension(350, 40));
		this.add(startButton);
	}

	/**
	 * Abilita il pulsante delle impostazioni.
	 */
	public void enableSettingsButton() {
		if (!this.settingsButton.isEnabled()) {
			this.settingsButton.setEnabled(true);
		}
	}

	/**
	 * Aggiunge un listener per eventi di clic del mouse al pulsante Start.
	 *
	 * @param listener Il listener da aggiungere.
	 */
	public void addMouseClickListenerToStartButton(MouseListener listener) {
		this.startButton.addMouseListener(listener);
	}

	/**
	 * Aggiunge un listener per eventi di clic del mouse al pulsante Settings.
	 *
	 * @param listener Il listener da aggiungere.
	 */
	public void addMouseClickListenerToSettingsButton(MouseListener listener) {
		this.settingsButton.addMouseListener(listener);
	}

	/**
	 * Configura le impostazioni del pannello delle navi.
	 */
	private void completeSettingsShipBordJPanel() {
		this.shipBordJPanel = new ShipBoardJPanel();
		this.shipBordJPanel.setSize(new Dimension(PANEL_SIZE - 25, PANEL_SIZE / 2));
		this.shipBordJPanel.setLocation(25, 50);
		this.add(shipBordJPanel);
	}

	/**
	 * Configura le impostazioni del primo pannello della griglia di gioco.
	 */
	private void completeSettingsGameBoardPanel1() {
		panel1 = new GameBoardJPanel((PANEL_SIZE / NUM_COLUMNS), NUM_COLUMNS);
		panel1.setBackground(Color.GRAY);
		panel1.setSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
		panel1.setLocation(PANEL_SIZE, 50);
		panel1.setBorder(BorderFactory.createLoweredBevelBorder());
		this.add(panel1);
	}

	/**
	 * Configura le impostazioni del secondo pannello della griglia di gioco.
	 */
	private void completeSettingsGameBoardPanel2() {
		panel2 = new GameBoardJPanel((PANEL_SIZE / NUM_COLUMNS), NUM_COLUMNS);
		panel2.setBackground(Color.GRAY);
		panel2.setSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
		panel2.setLocation(2 * PANEL_SIZE + 25, 50);
		panel2.setBorder(BorderFactory.createLoweredBevelBorder());
		this.add(panel2);
	}

	/**
	 * Restituisce il primo pannello della griglia di gioco.
	 *
	 * @return Il primo pannello della griglia di gioco.
	 */
	public GameBoardJPanel getGameBoardJPanel1() {
		return panel1;
	}

	/**
	 * Restituisce il secondo pannello della griglia di gioco.
	 *
	 * @return Il secondo pannello della griglia di gioco.
	 */
	public GameBoardJPanel getGameBoardJPanel2() {
		return panel2;
	}

	/**
	 * Restituisce il pannello delle navi.
	 *
	 * @return Il pannello delle navi.
	 */
	public ShipBoardJPanel getShipBoardPanel() {
		return shipBordJPanel;
	}

	/**
	 * Configura il pannello del nome del primo giocatore.
	 *
	 * @param name Il nome del primo giocatore.
	 */
	public void completeSettingsNamePanel1(String name) {
		if (name == null || name.isEmpty()) {
			name = "Player1";
		}
		this.name1Label = new JLabel(name + ":", SwingConstants.LEFT);
		namePanel1.add(name1Label);
		namePanel1.setSize(new Dimension(100, 25));
		namePanel1.setLocation(PANEL_SIZE, 25);
		this.add(namePanel1);
	}

	/**
	 * Aggiorna il campo del nome del giocatore umano.
	 *
	 * @param n Il nuovo nome del giocatore umano.
	 */
	public void updateNameFieldHuman(String n) {
		this.name1Label.setText(n);
	}

	/**
	 * Aggiorna il campo del nome del computer.
	 *
	 * @param n Il nuovo nome del computer.
	 */
	public void updateNameFieldComputer(String n) {
		this.name2Label.setText(n);
	}

	/**
	 * Configura il pannello del nome del secondo giocatore (computer).
	 */
	public void completeSettingsNamePanel2() {
		this.name2Label = new JLabel("Computer:");
		namePanel2.add(name2Label);
		namePanel2.setSize(new Dimension(100, 25));
		namePanel2.setLocation(2 * PANEL_SIZE + 25, 25);
		this.add(namePanel2);
	}

	/**
	 * Disabilita il pulsante Start.
	 */
	public void disableStartButton() {
		this.startButton.setEnabled(false);
	}

	/**
	 * Disabilita il pulsante Settings.
	 */
	public void disableSettingsButton() {
		this.settingsButton.setEnabled(false);
	}

	/**
	 * Verifica se il pulsante Start è abilitato.
	 *
	 * @return True se il pulsante Start è abilitato, altrimenti false.
	 */
	public boolean isStartButtonEnabled() {
		return this.startButton.isEnabled();
	}

	/**
	 * Verifica se il pulsante Settings è abilitato.
	 *
	 * @return True se il pulsante Settings è abilitato, altrimenti false.
	 */
	public boolean isSettingsButtonEnabled() {
		return this.settingsButton.isEnabled();
	}

	/**
	 * Disabilita il primo pannello della griglia di gioco.
	 */
	public void disableGameBoardJPanel1() {
		this.getGameBoardJPanel1().setEnabled(false);
	}

	/**
	 * Verifica se il primo pannello della griglia di gioco è abilitato.
	 *
	 * @return True se il primo pannello della griglia di gioco è abilitato, altrimenti false.
	 */
	public boolean isGameBoardJPanel1Enabled() {
		return this.getGameBoardJPanel1().isEnabled();
	}

	/**
	 * Chiude l'applicazione.
	 */
	public void closeApplication() {
		setVisible(false);
		dispose();
	}

	/**
	 * Imposta l'etichetta con il numero totale di navi sul pannello delle navi.
	 *
	 * @param count Il numero totale di navi.
	 */
	public void setShipTotalCountLabel(int count) {
		this.shipBordJPanel.setShipTotalCountLabel(count);
	}

	/**
	 * Imposta l'etichetta con il numero di navi disponibili per un tipo di nave specifico sul pannello delle navi.
	 *
	 * @param type Il tipo di nave.
	 * @param count Il numero di navi usate.
	 */
	public void setShipSingleCountLabel(ShipType type, int count) {
		this.shipBordJPanel.setShipSingleCountLabel(type, count);
	}
}