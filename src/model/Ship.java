package model;

import java.util.ArrayList;

/**
 * Classe che rappresenta una nave all'interno del gioco.
 * Contiene le informazioni come la posizione, il tipo di nave,
 * la direzione e le posizioni colpite.
 *
 * @version 1.0
 */
public class Ship {

	private final ArrayList<Integer> shipNumbers = new ArrayList<>();
	private final ArrayList<Integer> shipNumbersHit = new ArrayList<>();
	private Direction shipDirection;
	private ShipType shipType;

	/**
	 * Costruttore per la classe Ship.
	 *
	 * @param shipType      Il tipo di nave.
	 * @param shipDirection La direzione della nave.
	 * @param startPosition La posizione iniziale della nave.
	 */
	public Ship(ShipType shipType, Direction shipDirection, int startPosition) {
		this.setShipDirection(shipDirection);
		this.setShipType(shipType);
		this.setAllShipNumber(startPosition);
	}

	/**
	 * Restituisce la posizione della nave.
	 *
	 * @return la posizione iniziale della nave.
	 */
	public int getShipPosition() {
		return shipNumbers.get(0); // was getFirst, corrected
	}

	/**
	 * Verifica se un determinato numero è presente nella nave.
	 *
	 * @param number il numero da verificare.
	 * @return true se il numero è presente nella nave, altrimenti false.
	 */
	public boolean isNumberInShip(int number) {
		return shipNumbers.contains(number);
	}

	/**
	 * Imposta tutte le posizioni occupate dalla nave a partire dalla posizione iniziale.
	 *
	 * @param startPosition la posizione iniziale della nave.
	 */
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

	/**
	 * Restituisce una lista di numeri che rappresentano le posizioni circostanti alla nave.
	 *
	 * @return una lista di numeri rappresentanti le posizioni circostanti la nave.
	 */
	public ArrayList<Integer> getNumbersRandomShip() {
		ArrayList<Integer> numbersRandom = new ArrayList<>();

		int firstNumber = this.getShipNumbers().get(0); // was getFirst, corrected
		int lastNumber = this.getShipNumbers().get(this.getShipNumbers().size() - 1); // was getLast, corrected

		for (Integer integer : this.getShipNumbers()) {
			if (integer.equals(firstNumber) || integer.equals(lastNumber)) {
				addHorizontalAndVerticalNumbers(numbersRandom, integer);
			}
			if (integer > firstNumber && integer < lastNumber) {
				addMiddleHorizontalAndVerticalNumbers(numbersRandom, integer);
			}
		}

		return numbersRandom;
	}

	private void addHorizontalAndVerticalNumbers(
			ArrayList<Integer> numbersRandom, Integer integer) {
		if ((integer % 10) != 0) {
			numbersRandom.add(integer - 1);
		}
		if ((integer % 10) != 9) {
			numbersRandom.add(integer + 1);
		}
		if (integer - 10 >= 0) {
			numbersRandom.add(integer - 10);
		}
		if (integer + 10 < 100) {
			numbersRandom.add(integer + 10);
		}
		if ((integer - 10) >= 0 && (integer % 10) != 0) {
			numbersRandom.add(integer - 11);
		}
		if ((integer - 10) >= 0 && (integer % 10) != 9) {
			numbersRandom.add(integer - 9);
		}
		if ((integer + 10) < 100 && (integer % 10) != 0) {
			numbersRandom.add(integer + 9);
		}
		if ((integer + 10) < 100 && (integer % 10) != 9) {
			numbersRandom.add(integer + 11);
		}
	}

	private void addMiddleHorizontalAndVerticalNumbers(
			ArrayList<Integer> numbersRandom, Integer integer) {
		if ((integer % 10) != 0) {
			numbersRandom.add(integer - 1);
		}
		if ((integer % 10) != 9) {
			numbersRandom.add(integer + 1);
		}
		if (integer - 10 >= 0) {
			numbersRandom.add(integer - 10);
		}
		if (integer + 10 < 100) {
			numbersRandom.add(integer + 10);
		}
	}

	/**
	 * Aggiunge il numero colpito alla lista delle posizioni colpite.
	 *
	 * @param hitNumber il numero colpito.
	 */
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

	/**
	 * Restituisce la lista delle posizioni occupate dalla nave.
	 *
	 * @return la lista delle posizioni occupate dalla nave.
	 */
	public ArrayList<Integer> getShipNumbers() {
		return shipNumbers;
	}

	/**
	 * Restituisce la lista delle posizioni colpite della nave.
	 *
	 * @return la lista delle posizioni colpite della nave.
	 */
	public ArrayList<Integer> getShipNumbersHit() {
		return shipNumbersHit;
	}

	/**
	 * Restituisce la direzione della nave.
	 *
	 * @return la direzione della nave.
	 */
	public Direction getShipDirection() {
		return shipDirection;
	}

	/**
	 * Restituisce il tipo di nave.
	 *
	 * @return il tipo di nave.
	 */
	public ShipType getShipType() {
		return shipType;
	}
}