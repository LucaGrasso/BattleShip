package model.strategy;

/**
 * Interfaccia che definisce una strategia per colpire le navi.
 * Implementa i metodi necessari per calcolare la posizione del colpo e gestire lo stato del gioco.
 *
 * @version 1.0
 */
public interface HitShipStrategy {

	/**
	 * Calcola la posizione del prossimo colpo.
	 *
	 * @return la posizione del prossimo colpo.
	 */
	int getHitPositionShip();

	/**
	 * Imposta lo stato dell'ultimo colpo (se è stato un successo o meno).
	 *
	 * @param _lastHitWasSuccessful lo stato dell'ultimo colpo.
	 */
	void setLastHitSuccessful(boolean _lastHitWasSuccessful);

	/**
	 * Imposta lo stato della nave (affondata o meno).
	 *
	 * @param _isShipSunk lo stato della nave.
	 */
	void setIsShipSunk(boolean _isShipSunk);
}