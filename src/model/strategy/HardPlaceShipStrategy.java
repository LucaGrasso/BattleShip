package model.strategy;

import model.Direction;
import model.Ship;
import model.ShipType;

import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */
public class HardPlaceShipStrategy implements PlaceShipStrategy {

	@Override
	public Ship placeRandomShip() {
		Random random = new Random();
		ShipType shipType = this.getType(random);
		Direction shipOrientation = this.getOrientation(random);
		int coordinateX = this.getSquare();
		return new Ship(shipType, shipOrientation, coordinateX);
	}

	private ShipType getType(Random random) {
		ShipType shipType;
		if (random.nextInt(7) == 0) {
			// 20% chance of bigger ship (aircraft carrier = 0, battleship = 1)
			shipType = ShipType.values()[random.nextInt(1)];
		} else {
			// 80% chance of smaller ship
			// To get randint between min=3 and max=length: random.nextInt(max -
			// min + 1) + min
			shipType = ShipType.values()[random.nextInt(ShipType.values().length - 2 + 1) + 2];
		}
		return shipType;
	}

	private Direction getOrientation(Random random) {
		Direction shipOrientation;
		if (random.nextInt(5) == 0) {
			// 60% chance of vertical
			shipOrientation = Direction.values()[0];
		} else {
			// else horizontal
			shipOrientation = Direction.values()[1];
		}
		return shipOrientation;
	}

	private int getSquare() {
		int shipSquare = (int) (Math.random() * 99);
		// try to avoid column 1, 5 and 10
		ArrayList<Integer> squaresToAvoid = this.getSquaresToAvoid();
		boolean optimalsquare = false;
		while (!optimalsquare) {
			shipSquare = (int) (Math.random() * 99);
			if (!squaresToAvoid.contains(shipSquare)) {
				optimalsquare = true;
			}
		}
		return shipSquare;
	}

	private ArrayList<Integer> getSquaresToAvoid() {
		ArrayList<Integer> squaresToAvoid = new ArrayList<>();
		for (int i = 0; i <= 9; i++) {
			squaresToAvoid.add(i); // colum 1
			squaresToAvoid.add(i + 40); // colum 5
			squaresToAvoid.add(i + 90); // colum 10
		}
		return squaresToAvoid;
	}

}