package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */
public enum ShipType {
    AIRCRAFT_CARRIER(5, 1),
    SLAGSCHIP 		(4, 2),
    ONDERZEEER		(3, 3),
    TORPEDOBOOTJAGER(3, 3),
    PATROUILLESCHIP	(2, 4);

	private final int length;
	private final int numberOfAllowedShips;

	ShipType(int length, int numberOfAllowedShips) {
		this.length = length;
		this.numberOfAllowedShips = numberOfAllowedShips;
	}

	public int getNumberBoxes() {
		return length;
	}

	public int getNumberOfAllowedShips() {
		return numberOfAllowedShips;
	}

	public static ArrayList<ShipType> getAllShipTypes() {
		ArrayList<ShipType> allShipTypes = new ArrayList<>();
        Collections.addAll(allShipTypes, ShipType.values());
		return allShipTypes;
	}

}
