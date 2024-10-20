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
 *
 * @author Luca Grasso
 * @version 1.2
 */
public class ShipBoardJPanel extends JPanel {

	// Componenti del pannello
	private final JComboBox<ShipType> possibleShipsBox;
	private final JLabel shipsTypeCountLabel;
	private final JLabel shipsTotalCountLabel;

	private ShipType selectedShipType = ShipType.AIRCRAFT_CARRIER;
	private Direction shipDirection = Direction.VERTICAL;

	private static final String SHIP_TYPE_COUNT_LABEL_TEXT = "Available >>  ";
	private static final String SHIP_TOTAL_COUNT_LABEL_TEXT = "Total Ship >>  ";

	// La mappa per tenere traccia delle navi usate
	private final Map<ShipType, Integer> usedShipsMap = new HashMap<>();

    public ShipBoardJPanel() {
		// Inizializzazione componenti
		this.possibleShipsBox = new JComboBox<>(ShipType.getAllShipTypes().toArray(new ShipType[0]));
		this.shipsTypeCountLabel = new JLabel(SHIP_TYPE_COUNT_LABEL_TEXT + 0);
		this.shipsTotalCountLabel = new JLabel(SHIP_TOTAL_COUNT_LABEL_TEXT + 0);
		initializeComponents();
	}

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

		// Inizializza la JLabel con il valore della nave di default
		updateShipTypeCountLabel(selectedShipType);
	}

	private void updateShipTypeCountLabel(ShipType shipType) {
		int availableShips = shipType.getNumberOfAllowedShips() - getUsedShipsCount(shipType);
		shipsTypeCountLabel.setText(SHIP_TYPE_COUNT_LABEL_TEXT + availableShips);
	}

	private int getUsedShipsCount(ShipType shipType) {
		return usedShipsMap.getOrDefault(shipType, 0);
	}

	/**
	 * Aggiorna la label che mostra il numero di navi disponibili per tipo di nave.
	 */
	public void setShipSingleCountLabel(ShipType type, int count) {
		usedShipsMap.put(type, count);
		updateShipTypeCountLabel(type);
	}

	/**
	 * Aggiorna la label che mostra il numero di navi disponibili totalmente sul tabellone.
	 */
	public void setShipTotalCountLabel(int count) {
        shipsTotalCountLabel.setText(SHIP_TOTAL_COUNT_LABEL_TEXT + count);
	}

	/**
	 * Restituisce il tipo di nave selezionato.
	 *
	 * @return Ship type selectedShip type
	 */
	public ShipType getSelectedShipType() {
		return this.selectedShipType;
	}

	/**
	 * Restituisce la direzione della nave selezionata.
	 *
	 * @return Direction della nave selezionata
	 */
	public Direction getSelectedDirection() {
		return this.shipDirection;
	}
}