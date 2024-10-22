package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Enumerazione che rappresenta i tipi di navi disponibili nel gioco.
 * Ogni tipo di nave ha una lunghezza e un numero massimo di navi consentite.
 *
 * @version 1.0
 */
public enum ShipType {
	AIRCRAFT_CARRIER(5, 1),
	BATTLESHIP(4, 2),
	SUBMARINE(3, 3),
	TORPEDO_BOAT_HUNTERS(3, 3),
	PATROL_SHIP(2, 4);

	private final int length;
	private final int numberOfAllowedShips;

	/**
	 * Costruttore per l'enumerazione ShipType.
	 *
	 * @param length               la lunghezza della nave.
	 * @param numberOfAllowedShips il numero massimo di navi consentite di questo tipo.
	 */
	ShipType(int length, int numberOfAllowedShips) {
		this.length = length;
		this.numberOfAllowedShips = numberOfAllowedShips;
	}

	/**
	 * Restituisce il numero di caselle occupate dalla nave.
	 *
	 * @return la lunghezza della nave.
	 */
	public int getNumberBoxes() {
		return length;
	}

	/**
	 * Restituisce il numero massimo di navi consentite di questo tipo.
	 *
	 * @return il numero massimo di navi consentite.
	 */
	public int getNumberOfAllowedShips() {
		return numberOfAllowedShips;
	}

	/**
	 * Restituisce una lista di tutti i tipi di navi.
	 *
	 * @return una lista contenente tutti i tipi di navi.
	 */
	public static ArrayList<ShipType> getAllShipTypes() {
		ArrayList<ShipType> allShipTypes = new ArrayList<>();
		Collections.addAll(allShipTypes, ShipType.values());
		return allShipTypes;
	}
}