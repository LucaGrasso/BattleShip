package model.strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che implementa una strategia casuale per colpire le navi.
 *
 * @author Luca Grasso
 * @version 1.0
 */
public class RandomHitShipStrategy implements HitShipStrategy {

	private final List<Integer> alreadyHit = new ArrayList<>();

	/**
	 * Calcola una posizione casuale per colpire una nave.
	 *
	 * @return la posizione del prossimo colpo.
	 */
	@Override
	public int getHitPositionShip() {
		int result = -1;

		while (result == -1) {
			int temp = (int) (Math.random() * 100);
			if (alreadyHit.size() == 100) {
				result = 99;
			}
			if (!alreadyHit.contains(temp)) {
				alreadyHit.add(temp);
				result = temp;
			}
		}
		return result;
	}

	/**
	 * Imposta lo stato dell'ultimo colpo (se è stato un successo o meno).
	 *
	 * @param _lastHitWasSuccessful lo stato dell'ultimo colpo.
	 */
	@Override
	public void setLastHitSuccessful(boolean _lastHitWasSuccessful) {
		// Metodo non utilizzato in questa strategia
	}

	/**
	 * Imposta lo stato della nave (affondata o meno).
	 *
	 * @param _isShipSunk lo stato della nave.
	 */
	@Override
	public void setIsShipSunk(boolean _isShipSunk) {
		// Metodo non utilizzato in questa strategia
	}
}