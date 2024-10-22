package model.strategy;

/**
 * Enumerazione che definisce le diverse strategie che il giocatore computer può utilizzare per piazzare le navi.
 *
 * @author Luca Grasso
 * @version 1.0
 */
public enum PlaceStrategy {

	/**
	 * Strategia random per piazzare le navi.
	 */
	RANDOM("model.strategy.RandomPlaceShipStrategy"),

	/**
	 * Strategia avanzata per piazzare le navi.
	 */
	HARD("model.strategy.HardPlaceShipStrategy");

	private final String fullClassName;

	/**
	 * Costruttore per l'enum PlaceStrategy.
	 *
	 * @param fullClassName il nome completo della classe della strategia.
	 */
	PlaceStrategy(String fullClassName) {
		this.fullClassName = fullClassName;
	}

	/**
	 * Ottiene il nome completo della classe della strategia.
	 *
	 * @return il nome completo della classe della strategia.
	 */
	public String getFullClassName() {
		return this.fullClassName;
	}

}