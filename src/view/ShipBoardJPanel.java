package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

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
 * @version 1.1
 */
public class ShipBoardJPanel extends JPanel {

	private final JComboBox<ShipType> possibleShipsBox;
	private final JLabel shipsCountLabel;
	private ShipType selectedShipType = ShipType.AIRCRAFT_CARRIER;
	private Direction shipDirection = Direction.VERTICAL;

	public ShipBoardJPanel() {
		JLabel shipsLabel = new JLabel("Available ships:");

		List<ShipType> listShipTypes = ShipType.getAllShipTypes();
		this.possibleShipsBox = new JComboBox<>(listShipTypes.toArray(new ShipType[0]));
		this.shipsCountLabel = new JLabel();

		// Inizializzazione della label con il conteggio iniziale
		updateShipCountLabel(selectedShipType);

		JLabel directionLabel = new JLabel("Direction:");
		JRadioButton verticalRadioButton = new JRadioButton("Vertical", true);
		JRadioButton horizontalRadioButton = new JRadioButton("Horizontal");

		// Gruppo di bottoni per il radio button
		ButtonGroup group = new ButtonGroup();
		group.add(horizontalRadioButton);
		group.add(verticalRadioButton);

		// Aggiungi azioni ai componenti
		possibleShipsBox.addActionListener(e -> {
			selectedShipType = (ShipType) possibleShipsBox.getSelectedItem();
            assert selectedShipType != null;
            updateShipCountLabel(selectedShipType);
		});

		verticalRadioButton.addActionListener(e -> shipDirection = Direction.VERTICAL);
		horizontalRadioButton.addActionListener(e -> shipDirection = Direction.HORIZONTAL);

		// Impostazione layout
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;

		// Aggiunta dei componenti al pannello
		c.gridx = 0;
		c.gridy = 0;
		add(shipsLabel, c);

		c.gridy = 1;
		add(possibleShipsBox, c);

		c.gridx = 1;
		c.insets = new Insets(0, 10, 0, 0);  // Aggiungi margine a sinistra di 10 pixel
		add(shipsCountLabel, c);

		c.gridx = 0;
		c.gridy = 2;
		add(directionLabel, c);

		c.gridy = 3;
		add(horizontalRadioButton, c);

		c.gridx = 1;
		add(verticalRadioButton, c);
	}

	/**
	 * Aggiorna la label che mostra il numero di navi disponibili.
	 *
	 * @param shipType Il tipo di nave selezionata.
	 */
	private void updateShipCountLabel(ShipType shipType) {
		// Ottieni il numero di navi dal modello
		int count = shipType.getNumberOfAllowedShips();
		shipsCountLabel.setText("Available: " + count);
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