package model.strategy;

/**
 * Enumerazione che definisce le diverse strategie che il giocatore computer può utilizzare per colpire le navi.
 *
 * @author Luca Grasso
 * @version 1.0
 */
public enum HitStrategy {

	/**
	 * Strategia random per colpire le navi.
	 */
	RANDOM("model.strategy.RandomHitShipStrategy"),

	/**
	 * Strategia ordinata per colpire le navi.
	 */
	ORDERED("model.strategy.OrderedHitShipStrategy"),

	/**
	 * Strategia avanzata per colpire le navi.
	 */
	HARD("model.strategy.HardHitShipStrategy"),

	/**
	 * Strategia intelligente per colpire le navi.
	 */
	INTELLIGENT("model.strategy.IntelligentHitShipStrategy"); // Aggiunto


	private final String fullClassName;

	/**
	 * Costruttore per l'enum HitStrategy.
	 *
	 * @param fullClassName il nome completo della classe della strategia.
	 */
	HitStrategy(String fullClassName) {
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