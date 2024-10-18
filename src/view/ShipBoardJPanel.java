package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.Direction;
import model.ShipType;
/**
 *
 * @author Luca Grasso
 * @version 1.0
 *
 */
public class ShipBoardJPanel extends JPanel {

    private final JComboBox<Object> possibleShipsBox;
    private ShipType selectedShipType = ShipType.AIRCRAFT_CARRIER;
	private Direction shipDirection = Direction.VERTICAL;

	public ShipBoardJPanel() {
		JLabel shipsLabel = new JLabel("Available ships:");
		List<ShipType> listShipTypes = ShipType.getAllShipTypes();
		this.possibleShipsBox = new JComboBox<>(listShipTypes.toArray(new ShipType[0]));

		JLabel directionLabel = new JLabel("Direction:");
		JRadioButton verticalRadioButton = new JRadioButton("Vertical", true);
		JRadioButton horizontalRadioButton = new JRadioButton("Horizontal");

		// Gruppo di bottoni per il radio button
		ButtonGroup group = new ButtonGroup();
		group.add(horizontalRadioButton);
		group.add(verticalRadioButton);

		// Aggiungi azioni ai componenti
		possibleShipsBox.addActionListener(e -> selectedShipType = (ShipType) possibleShipsBox.getSelectedItem());
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

		c.gridy = 2;
		add(directionLabel, c);

		c.gridy = 3;
		add(horizontalRadioButton, c);

		c.gridx = 1;
		add(verticalRadioButton, c);
	}


	/**
	 * Returns the selected ship type
	 *
	 * @return Ship type selectedShip type
	 */
	public ShipType getSelectedShipType() {
		return this.selectedShipType;
	}

	/**
	 * Returns the selected ship type
	 * 
	 * @return boolean shipHorizontal
	 */
	public Direction getSelectedDirection() {
		return this.shipDirection;
	}

}
