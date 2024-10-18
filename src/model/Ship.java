package model;

import java.util.ArrayList;
/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */
public class Ship {

	private final ArrayList<Integer> shipNumbers = new ArrayList<>();
	private final ArrayList<Integer> shipNumbersHit = new ArrayList<>();
	private Direction shipDirection;
	private ShipType shipType;

	public Ship(ShipType shipType, Direction shipDirection, int startPosition) {
		this.setShipDirection(shipDirection);
		this.setShipType(shipType);
		this.setAllShipNumber(startPosition);
	}

	public int getShipPosition() {
		return shipNumbers.getFirst();
	}

	public boolean isNumberInShip(int number) {
		return shipNumbers.contains(number);
	}

	private void setAllShipNumber(int startPosition) {
		if (this.getShipDirection().equals(Direction.HORIZONTAL)) {
			int endPosition = startPosition + (10 * (shipType.getNumberBoxes() - 1));
			if (endPosition < 100) {
				for (int i = startPosition; i <= endPosition; i += 10) {
					shipNumbers.add(i);
				}
			} else {
				throw new DomainException("The ship cannot be placed horizontally!");
			}
		} else {
			int endPosition = startPosition + shipType.getNumberBoxes() - 1;
			if ((endPosition % 10) > (startPosition % 10)) {
				for (int i = startPosition; i <= endPosition; i++) {
					shipNumbers.add(i);
				}
			} else {
				throw new DomainException("The ship cannot be placed vertically!");
			}
		}
	}

	public ArrayList<Integer> getNumbersRandomShip() {
		ArrayList<Integer> numbersRandom = new ArrayList<>();

		int firstNumber = this.getShipNumbers().getFirst();
		int lastNumber = this.getShipNumbers().getLast();

		if (this.getShipDirection().equals(Direction.HORIZONTAL)) {
			for (Integer integer : this.getShipNumbers()) {
				if (integer == firstNumber) {
					if ((integer % 10) != 0) {
						numbersRandom.add(integer - 1);
					}
					if ((integer % 10) != 9) {
						numbersRandom.add(integer + 1);
					}
					if (integer - 10 > 0) {
						numbersRandom.add(integer - 10);
					}
					if ((integer - 10) > 0 && (integer % 10) != 0) {
						numbersRandom.add(integer - 11);
					}
					if ((integer - 10) > 0 && (integer % 10) != 9) {
						numbersRandom.add(integer - 9);
					}
				} else if (integer == lastNumber) {
					if ((integer + 10) < 100) {
						numbersRandom.add(integer + 10);
					}
					if ((integer % 10) != 0) {
						numbersRandom.add(integer - 1);
					}
					if ((integer % 10) != 9) {
						numbersRandom.add(integer + 1);
					}
					if ((integer + 10) < 100 && (integer % 10) != 0) {
						numbersRandom.add(integer + 9);
					}
					if ((integer + 10) < 100 && (integer % 10) != 9) {
						numbersRandom.add(integer + 11);
					}
				} else {
					if ((integer % 10) != 0) {
						numbersRandom.add(integer - 1);
					}
					if ((integer % 10) != 9) {
						numbersRandom.add(integer + 1);
					}
				}
			}
		} else {
			for (Integer integer : this.getShipNumbers()) {
				if (integer == firstNumber) {
					if ((integer % 10) != 0) {
						numbersRandom.add(integer - 1);
					}
					if ((integer + 10) < 100) {
						numbersRandom.add(integer + 10);
					}
					if ((integer - 10) > 0) {
						numbersRandom.add(integer - 10);
					}
					if ((integer % 10) != 0 && (integer - 10) > 0) {
						numbersRandom.add(integer - 11);
					}
					if ((integer % 10) != 0 && (integer + 10) < 100) {
						numbersRandom.add(integer + 9);
					}

				} else if (integer == lastNumber) {
					if ((integer + 10) < 100) {
						numbersRandom.add(integer + 10);
					}
					if ((integer - 10) > 0) {
						numbersRandom.add(integer - 10);
					}
					if ((integer % 10) != 9) {
						numbersRandom.add(integer + 1);
					}
					if ((integer % 10) != 9 && (integer - 10) > 0) {
						numbersRandom.add(integer - 9);
					}
					if ((integer % 10) != 9 && (integer + 10) < 100) {
						numbersRandom.add(integer + 11);
					}
				} else {
					if ((integer + 10) < 100) {
						numbersRandom.add(integer + 10);
					}
					if ((integer - 10) > 0) {
						numbersRandom.add(integer - 10);
					}
				}
			}
		}
		return numbersRandom;
	}

	public void addNumberHit(int hitNumber) {
		if (!this.getShipNumbers().contains(hitNumber)) {
			throw new DomainException("Cannot hit because it is not a ship number (Ship class)");
		}
		this.getShipNumbersHit().add(hitNumber);
	}

	private void setShipDirection(Direction shipDirection) {
		this.shipDirection = shipDirection;
	}

	private void setShipType(ShipType shipType) {
		this.shipType = shipType;
	}

	public ArrayList<Integer> getShipNumbers() {
		return shipNumbers;
	}

	public ArrayList<Integer> getShipNumbersHit() {
		return shipNumbersHit;
	}

	public Direction getShipDirection() {
		return shipDirection;
	}

	public ShipType getShipType() {
		return shipType;
	}



}
