package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta una nave all'interno del gioco.
 * Contiene le informazioni come la posizione, il tipo di nave,
 * la direzione e le posizioni colpite.
 *
 * @version 1.0
 */
public class Ship implements IShip {

	private final ArrayList<Integer> shipNumbers = new ArrayList<>();
	private final ArrayList<Integer> shipNumbersHit = new ArrayList<>();
	private final Direction shipDirection;
	private final ShipType shipType;

	/**
	 * Costruttore per la classe Ship.
	 *
	 * @param shipType      Il tipo di nave.
	 * @param shipDirection La direzione della nave.
	 * @param startPosition La posizione iniziale della nave.
	 */
	public Ship(ShipType shipType, Direction shipDirection, int startPosition) {
		this.shipDirection = shipDirection;
		this.shipType = shipType;
		setAllShipNumbers(startPosition);
	}

	/**
	 * Verifica se un determinato numero è presente nella nave.
	 *
	 * @param number il numero da verificare.
	 * @return true se il numero è presente nella nave, altrimenti false.
	 */
	@Override
	public boolean isNumberInShip(int number) {
		return shipNumbers.contains(number);
	}

	/**
	 * Imposta tutte le posizioni occupate dalla nave a partire dalla posizione iniziale.
	 *
	 * @param startPosition la posizione iniziale della nave.
	 */
	private void setAllShipNumbers(int startPosition) {
		int numberBoxes = shipType.getNumberBoxes();
		if (shipDirection.equals(Direction.HORIZONTAL)) {
			int endPosition = startPosition + (10 * (numberBoxes - 1));
			if (endPosition >= 100) {
				throw new DomainException("The ship cannot be placed horizontally!");
			}
			for (int i = startPosition; i <= endPosition; i += 10) {
				shipNumbers.add(i);
			}
		} else {
			int endPosition = startPosition + numberBoxes - 1;
			if ((endPosition % 10) <= (startPosition % 10)) {
				throw new DomainException("The ship cannot be placed vertically!");
			}
			for (int i = startPosition; i <= endPosition; i++) {
				shipNumbers.add(i);
			}
		}
	}

	/**
	 * Restituisce una lista di numeri che rappresentano le posizioni circostanti alla nave.
	 *
	 * @return una lista di numeri rappresentanti le posizioni circostanti la nave.
	 */
	@Override
	public ArrayList<Integer> getNumbersRandomShip() {
		ArrayList<Integer> numbersRandom = new ArrayList<>();

		int firstNumber = shipNumbers.get(0);
		int lastNumber = shipNumbers.get(shipNumbers.size() - 1);

		for (Integer integer : shipNumbers) {
			if (integer.equals(firstNumber) || integer.equals(lastNumber)) {
				addHorizontalAndVerticalNumbers(numbersRandom, integer);
			}
			if (integer > firstNumber && integer < lastNumber) {
				addMiddleHorizontalAndVerticalNumbers(numbersRandom, integer);
			}
		}

		return numbersRandom;
	}

	private void addHorizontalAndVerticalNumbers(List<Integer> numbersRandom, Integer integer) {
		int[] adjustments = {-1, 1, -10, 10, -11, -9, 9, 11};
		for (int adj : adjustments) {
			int newNumber = integer + adj;
			if (isValidPosition(newNumber, integer)) {
				numbersRandom.add(newNumber);
			}
		}
	}

	private void addMiddleHorizontalAndVerticalNumbers(List<Integer> numbersRandom, Integer integer) {
		int[] adjustments = {-1, 1, -10, 10};
		for (int adj : adjustments) {
			int newNumber = integer + adj;
			if (isValidPosition(newNumber, integer)) {
				numbersRandom.add(newNumber);
			}
		}
	}

	private boolean isValidPosition(int position, int reference) {
		return position >= 0 && position < 100 && Math.abs(position / 10 - reference / 10) <= 1 && Math.abs(position % 10 - reference % 10) <= 1;
	}

	/**
	 * Aggiunge il numero colpito alla lista delle posizioni colpite.
	 *
	 * @param hitNumber il numero colpito.
	 */
	@Override
	public void addNumberHit(int hitNumber) {
		if (!shipNumbers.contains(hitNumber)) {
			throw new DomainException("Cannot hit because it is not a ship number (Ship class)");
		}
		shipNumbersHit.add(hitNumber);
	}

	/**
	 * Restituisce la lista delle posizioni occupate dalla nave.
	 *
	 * @return la lista delle posizioni occupate dalla nave.
	 */
	@Override
	public ArrayList<Integer> getShipNumbers() {
		return new ArrayList<>(shipNumbers);
	}

	/**
	 * Restituisce la lista delle posizioni colpite della nave.
	 *
	 * @return la lista delle posizioni colpite della nave.
	 */
	@Override
	public ArrayList<Integer> getShipNumbersHit() {
		return new ArrayList<>(shipNumbersHit);
	}

	/**
	 * Restituisce la direzione della nave.
	 *
	 * @return la direzione della nave.
	 */
	@Override
	public Direction getShipDirection() {
		return shipDirection;
	}

	/**
	 * Restituisce il tipo di nave.
	 *
	 * @return il tipo di nave.
	 */
	@Override
	public ShipType getShipType() {
		return shipType;
	}
}