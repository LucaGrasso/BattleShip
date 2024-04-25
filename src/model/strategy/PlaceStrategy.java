package model.strategy;
/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */
public enum PlaceStrategy {
	RANDOM("model.strategy.RandomPlaceShipStrategy"),
	HARD("model.strategy.HardPlaceShipStrategy");

	private final String fullClassName;

	PlaceStrategy(String fullClassName) {
		this.fullClassName = fullClassName;
	}

	public String getFullClassName() {
		return this.fullClassName;
	}

}
