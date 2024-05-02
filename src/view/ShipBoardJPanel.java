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

    public ShipType selectedShipType = ShipType.AIRCRAFT_CARRIER;
	public Direction shipDirection = Direction.VERTICAL;

	public ShipBoardJPanel() {
        JLabel shipsLabel = new JLabel("Available ships:");
		List<ShipType> listShipTypes = ShipType.getAllShipTypes();
		this.possibleShipsBox = new JComboBox<>(listShipTypes.toArray());
        JLabel directionLabel = new JLabel("Direction:");
        JRadioButton verticalRadioButton = new JRadioButton("Vertical", true);
        JRadioButton horizontalRadioButton = new JRadioButton("Horizontal");

        ButtonGroup group = new ButtonGroup();
		group.add(horizontalRadioButton);
		group.add(verticalRadioButton);

		possibleShipsBox.addActionListener(e -> selectedShipType = (ShipType) possibleShipsBox.getSelectedItem());

		verticalRadioButton.addActionListener(e -> shipDirection = Direction.VERTICAL);

		horizontalRadioButton.addActionListener(e -> shipDirection = Direction.HORIZONTAAL);

		this.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		this.add(shipsLabel, c);

		c.gridx = 0;
		c.gridy = 1;

		this.add(possibleShipsBox, c);

		c.gridx = 0;
		c.gridy = 2;
		this.add(directionLabel, c);

		c.gridy = 3;
		this.add(horizontalRadioButton, c);
		c.gridx = 1;
		c.gridy = 3;
		this.add(verticalRadioButton, c);

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
