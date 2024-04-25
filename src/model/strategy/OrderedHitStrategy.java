package model.strategy;
/**
 * 
 * @author  Luca Grasso
 * @version 1.0
 *
 */
public class OrderedHitStrategy implements HitShipStrategy {

	private int lastInteger = -1;

	@Override
	public int hitShip() {
		if (lastInteger < 99) {
			lastInteger++;
		}
		return lastInteger;

	}

}
