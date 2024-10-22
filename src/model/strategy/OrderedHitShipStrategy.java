package model.strategy;

/**
 * Classe che implementa una strategia ordinata per colpire le navi.
 *
 * @author Luca Grasso
 * @version 1.0
 */
public class OrderedHitShipStrategy implements HitShipStrategy {

	private int lastInteger = -1;

	/**
	 * Calcola la prossima posizione per colpire una nave in modo ordinato.
	 *
	 * @return la posizione del prossimo colpo.
	 */
	@Override
	public int getHitPositionShip() {
		if (lastInteger < 99) {
			lastInteger++;
		}
		return lastInteger;
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