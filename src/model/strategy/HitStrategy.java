package model.strategy;
/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */

// This enum is used to define the different strategies that the computer player can use to hit the ships
// The fullClassName attribute is used to get the full class name of the strategy.

public enum HitStrategy {
	RANDOM("model.strategy.RandomHitShipStrategy"), ORDERED("model.strategy.OrderedHitStrategy"), HARD("model.strategy.HardHitStrategy");

	private final String fullClassName;

	 HitStrategy(String fullClassName) {
		this.fullClassName = fullClassName;
	}

	public String getFullClassName() {
		return this.fullClassName;
	}

}
