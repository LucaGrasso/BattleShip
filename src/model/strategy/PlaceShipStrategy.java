package model.strategy;

import model.Ship;

/**
 * Interfaccia che definisce una strategia per piazzare le navi nel gioco.
 * Implementa il metodo necessario per piazzare una nave in una posizione casuale.
 *
 * @version 1.0
 */
public interface PlaceShipStrategy {

	/**
	 * Piazza una nave in una posizione casuale.
	 *
	 * @return una nave posizionata in una posizione casuale.
	 */
	Ship placeRandomShip();
}