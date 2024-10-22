package model.strategy;

import model.Direction;
import model.Ship;
import model.ShipType;

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe che implementa una strategia complessa per piazzare le navi.
 * La strategia prevede la scelta di navi più grandi più raramente,
 * favorendo la disposizione verticale delle navi e cercando di evitare
 * alcune colonne specifiche.
 *
 * @version 1.0
 */
public class HardPlaceShipStrategy implements PlaceShipStrategy {

	/**
	 * Piazza una nave in una posizione casuale secondo delle regole predefinite.
	 *
	 * @return una nave posizionata in una posizione casuale.
	 */
	@Override
	public Ship placeRandomShip() {
		Random random = new Random();
		ShipType shipType = this.getType(random);
		Direction shipOrientation = this.getOrientation(random);
		int coordinateX = this.getSquare();
		return new Ship(shipType, shipOrientation, coordinateX);
	}

	/**
	 * Seleziona un tipo di nave casualmente, con una probabilità maggiore
	 * di ottenere navi più piccole.
	 *
	 * @param random l'oggetto Random utilizzato per la selezione.
	 * @return il tipo di nave selezionato.
	 */
	private ShipType getType(Random random) {
		ShipType shipType;
		if (random.nextInt(7) == 0) {
			// 20% di possibilità di ottenere una nave più grande (portaerei = 0, corazzata = 1)
			shipType = ShipType.values()[random.nextInt(2)];
		} else {
			// 80% di possibilità di ottenere una nave più piccola
			shipType = ShipType.values()[random.nextInt(ShipType.values().length - 2) + 2];
		}
		return shipType;
	}

	/**
	 * Seleziona casualmente l'orientamento della nave, con una probabilità
	 * maggiore di ottenere un orientamento verticale.
	 *
	 * @param random l'oggetto Random utilizzato per la selezione.
	 * @return l'orientamento della nave selezionato.
	 */
	private Direction getOrientation(Random random) {
		Direction shipOrientation;
		if (random.nextInt(5) == 0) {
			// 20% di possibilità di ottenere un orientamento verticale
			shipOrientation = Direction.values()[0];
		} else {
			// Altrimenti orizzontale
			shipOrientation = Direction.values()[1];
		}
		return shipOrientation;
	}

	/**
	 * Seleziona una posizione casuale per la nave, cercando di evitare
	 * alcune colonne specifiche.
	 *
	 * @return la posizione selezionata per la nave.
	 */
	private int getSquare() {
		int shipSquare = (int) (Math.random() * 99);
		// Cercare di evitare le colonne 1, 5 e 10
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

	/**
	 * Restituisce una lista delle posizioni da evitare.
	 *
	 * @return una lista delle posizioni da evitare.
	 */
	private ArrayList<Integer> getSquaresToAvoid() {
		ArrayList<Integer> squaresToAvoid = new ArrayList<>();
		for (int i = 0; i <= 9; i++) {
			squaresToAvoid.add(i); // Colonna 1
			squaresToAvoid.add(i + 40); // Colonna 5
			squaresToAvoid.add(i + 90); // Colonna 10
		}
		return squaresToAvoid;
	}

}