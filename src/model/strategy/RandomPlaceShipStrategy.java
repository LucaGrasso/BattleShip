package model.strategy;

import java.util.Random;

import model.Direction;
import model.Ship;
import model.ShipType;

/**
 * Classe che implementa una strategia casuale per piazzare le navi.
 *
 * @version 1.0
 */
public class RandomPlaceShipStrategy implements PlaceShipStrategy {

	/**
	 * Piazza una nave in una posizione casuale.
	 *
	 * @return una nave posizionata in una posizione casuale.
	 */
	@Override
	public Ship placeRandomShip() {
		Random random = new Random();
		ShipType shipType = ShipType.values()[random.nextInt(ShipType.values().length)];
		Direction randomDirection = Direction.values()[random.nextInt(Direction.values().length)];
		int randomCoordinateX = (int) (Math.random() * 99);
		return new Ship(shipType, randomDirection, randomCoordinateX);
	}

}