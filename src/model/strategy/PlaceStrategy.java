package model.strategy;
/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */

	// This enum is used to define the different strategies that the computer player can use to place the ships
	// The fullClassName attribute is used to get the full class name of the strategy.

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
