package model;

import model.strategy.HitShipStrategy;
import model.strategy.PlaceShipStrategy;
import model.factory.HitShipFactory;
import model.factory.PlaceShipFactory;

/**
 * Rappresenta un giocatore computer nel gioco.
 * Eredita dalla classe BasePlayer.
 * Implementa strategie per piazzare e colpire le navi.
 *
 * @autor
 * @version 1.0
 */
public class ComputerPlayer extends BasePlayer {

	public PlaceShipStrategy shipStrategy;
	public HitShipStrategy hitShipStrategy;

	/**
	 * Costruttore per ComputerPlayer.
	 * Inizializza il giocatore con nome "Computer".
	 * Legge le strategie di colpire e piazzare navi da proprietà.
	 */
	public ComputerPlayer() {
		super("Computer");
		this.readHitStrategyFromProp();
		this.readPlaceShipFromProp();
	}

	/**
	 * Utilizzare una strategia per piazzare navi sul campo di gioco.
	 * Continua fino a quando non si raggiunge il numero massimo di navi.
	 * Gestisce eventuali eccezioni stampando un messaggio di errore.
	 */
	public void setShipsFromStrategy() {
		while (this.getShips().size() != MAX_SHIPS) {
			try {
				Ship generatedShip = shipStrategy.placeRandomShip();
				this.addShip(generatedShip);
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	/**
	 * Restituisce la posizione del colpo usando una strategia.
	 *
	 * @return La posizione del colpo
	 */
	public int hitShip() {
		return hitShipStrategy.getHitPositionShip();
	}

	/**
	 * Imposta se l'ultimo colpo è stato un successo.
	 *
	 * @param hitSuccessful true se il colpo ha colpito una nave, altrimenti false
	 */
	public void setLastHitSuccessful(boolean hitSuccessful) {
		hitShipStrategy.setLastHitSuccessful(hitSuccessful);
	}

	/**
	 * Imposta se una nave è stata affondata.
	 *
	 * @param isShipSunk true se una nave è stata affondata, altrimenti false
	 */
	public void setIsShipSunk(boolean isShipSunk) {
		hitShipStrategy.setIsShipSunk(isShipSunk);
	}

	/**
	 * Legge la strategia di colpire le navi dalle proprietà utilizzando una factory.
	 */
	public void readHitStrategyFromProp() {
		hitShipStrategy = new HitShipFactory().getHitShipStrategy();
	}

	/**
	 * Legge la strategia di piazzare le navi dalle proprietà utilizzando una factory.
	 */
	public void readPlaceShipFromProp() {
		shipStrategy = new PlaceShipFactory().getPlaceShipStrategy();
	}

}