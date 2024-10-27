package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.Direction;
import model.ShipType;

/**
 * Pannello per la selezione delle navi e della loro direzione.
 * Permette di selezionare il tipo di nave e la sua direzione,
 * nonché tenere traccia del numero di navi disponibili.
 *
 * @version 1.2
 */
public class ShipBoardJPanel extends JPanel {

	// Componenti del pannello
	private JComboBox<ShipType> possibleShipsBox;
	private JLabel shipsTypeCountLabel;
	private JLabel shipsTotalCountLabel;
	private JLabel legendLabel;

	private ShipType selectedShipType = ShipType.AIRCRAFT_CARRIER;
	private Direction shipDirection = Direction.VERTICAL;

	private static final String SHIP_TYPE_COUNT_LABEL_TEXT = "Available >>  ";
	private static final String SHIP_TOTAL_COUNT_LABEL_TEXT = "Total Ship >>  ";
	private static final String LEGEND_LABEL_TEXT =
			"<html>Right-click to remove a ship<br>Use the left button to place a ship</html>";

	// La mappa per tenere traccia delle navi usate
	private final Map<ShipType, Integer> usedShipsMap = new HashMap<>();

	/**
	 * Costruttore che inizializza i componenti del pannello.
	 */
	public ShipBoardJPanel() {
		// Inizializzazione componenti
		this.possibleShipsBox = new JComboBox<>(ShipType.getAllShipTypes().toArray(new ShipType[0]));
		this.shipsTypeCountLabel = new JLabel(SHIP_TYPE_COUNT_LABEL_TEXT + 0);
		this.shipsTotalCountLabel = new JLabel(SHIP_TOTAL_COUNT_LABEL_TEXT + 0);
		this.legendLabel = new JLabel(LEGEND_LABEL_TEXT);
		initializeComponents();
	}

	/**
	 * Inizializza i componenti del pannello.
	 */
	private void initializeComponents() {

		JLabel directionLabel = new JLabel("Direction:");
		JRadioButton verticalRadioButton = new JRadioButton("Vertical", true);
		JRadioButton horizontalRadioButton = new JRadioButton("Horizontal");

		ButtonGroup group = new ButtonGroup();
		group.add(horizontalRadioButton);
		group.add(verticalRadioButton);

		// Aggiorna la JLabel quando viene selezionata una nuova nave
		possibleShipsBox.addActionListener(e -> {
			selectedShipType = (ShipType) possibleShipsBox.getSelectedItem();
			assert selectedShipType != null;
			updateShipTypeCountLabel(selectedShipType);
		});

		verticalRadioButton.addActionListener(e -> shipDirection = Direction.VERTICAL);
		horizontalRadioButton.addActionListener(e -> shipDirection = Direction.HORIZONTAL);

		// Layout dei componenti
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;

		// Aggiunta dei componenti al pannello
		c.gridx = 0;
		c.gridy = 0;
		add(shipsTotalCountLabel, c);

		c.gridy = 3;
		c.insets = new Insets(10, 0, 0, 0);
		add(possibleShipsBox, c);

		c.gridx = 1;
		c.insets = new Insets(0, 10, 0, 0);
		add(this.shipsTypeCountLabel, c);

		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(5, 0, 0, 0);
		add(directionLabel, c);

		c.gridy = 5;
		add(horizontalRadioButton, c);

		c.gridx = 1;
		add(verticalRadioButton, c);

		// Aggiunta della leggenda
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		c.insets = new Insets(10, 0, 0, 0);
		add(legendLabel, c);

		// Inizializza la JLabel con il valore della nave di default
		updateShipTypeCountLabel(selectedShipType);
	}

	/**
	 * Metodo per il reset del pannello delle navi, rimuovendo tutti i componenti.
	 */
	public void reset() {
		// Logica per resettare la disposizione delle navi
		this.removeAll();  // Rimuove tutte le navi e resetta il pannello
		this.possibleShipsBox = new JComboBox<>(ShipType.getAllShipTypes().toArray(new ShipType[0]));
		this.shipsTypeCountLabel = new JLabel(SHIP_TYPE_COUNT_LABEL_TEXT + 0);
		this.shipsTotalCountLabel = new JLabel(SHIP_TOTAL_COUNT_LABEL_TEXT + 0);
		this.legendLabel = new JLabel(LEGEND_LABEL_TEXT);
		initializeComponents();

	}

	/**
	 * Aggiorna l'etichetta che mostra il numero di navi disponibili per un tipo di nave specifico.
	 *
	 * @param shipType il tipo di nave selezionato
	 */
	private void updateShipTypeCountLabel(ShipType shipType) {
		int availableShips = shipType.getNumberOfAllowedShips() - getUsedShipsCount(shipType);
		shipsTypeCountLabel.setText(SHIP_TYPE_COUNT_LABEL_TEXT + availableShips);
	}

	/**
	 * Restituisce il numero di navi usate per un tipo di nave specifico.
	 *
	 * @param shipType il tipo di nave
	 * @return il numero di navi usate per il tipo di nave
	 */
	private int getUsedShipsCount(ShipType shipType) {
		return usedShipsMap.getOrDefault(shipType, 0);
	}

	/**
	 * Imposta l'etichetta con il numero di navi disponibili per un tipo di nave specifico.
	 *
	 * @param type il tipo di nave
	 * @param count il numero di navi usate
	 */
	public void setShipSingleCountLabel(ShipType type, int count) {
		usedShipsMap.put(type, count);
		if (type.equals(getSelectedShipType())) {
			updateShipTypeCountLabel(type);
		}
	}

	/**
	 * Imposta l'etichetta con il numero totale di navi sul tabellone.
	 *
	 * @param count il numero totale di navi
	 */
	public void setShipTotalCountLabel(int count) {
		shipsTotalCountLabel.setText(SHIP_TOTAL_COUNT_LABEL_TEXT + count);
	}

	/**
	 * Restituisce il tipo di nave selezionato.
	 *
	 * @return il tipo di nave selezionato
	 */
	public ShipType getSelectedShipType() {
		return this.selectedShipType;
	}

	/**
	 * Restituisce la direzione della nave selezionata.
	 *
	 * @return la direzione della nave selezionata
	 */
	public Direction getSelectedDirection() {
		return this.shipDirection;
	}
}