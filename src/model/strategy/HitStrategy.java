package model.strategy;
/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */
public enum HitStrategy {
	RANDOM("model.strategy.RandomHitShipStrategy"), ORDERED("model.strategy.OrderedHitStrategy");

	private final String fullClassName;

	 HitStrategy(String fullClassName) {
		this.fullClassName = fullClassName;
	}

	public String getFullClassName() {
		return this.fullClassName;
	}

}
