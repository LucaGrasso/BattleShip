package model.strategy;
/**
 * 
 * @author  Luca Grasso
 * @version 1.0
 *
 */
public class OrderedHitShipStrategy implements HitShipStrategy {

	private int lastInteger = -1;

	@Override
	public int getHitPositionShip() {
		if (lastInteger < 99) {
			lastInteger++;
		}
		return lastInteger;

	}

	@Override
	public void setLastHitSuccessful(boolean _lastHitWasSuccessful) {

	}

	@Override
	public void setIsShipSunk(boolean _isShipSunk) {

	}

}
