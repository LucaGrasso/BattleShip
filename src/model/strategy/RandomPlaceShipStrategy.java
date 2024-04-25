package model.strategy;

import java.util.Random;

import model.Direction;
import model.Ship;
import model.ShipType;
/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */
public class RandomPlaceShipStrategy implements PlaceShipStrategy {

	@Override
	public Ship placeRandomShip() {
		Random random = new Random();
		ShipType shipType = ShipType.values()[random.nextInt(ShipType.values().length)];
		Direction randomDirection = Direction.values()[random.nextInt(Direction.values().length)];
		int randomCoordinateX = (int) (Math.random() * 99);
		return new Ship(shipType, randomDirection, randomCoordinateX);
	}

}
