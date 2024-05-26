package model.strategy;
/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */
public interface HitShipStrategy { ;

	int getHitPositionShip();

	void setLastHitSuccessful(boolean _lastHitWasSuccessful);

	void setIsShipSunk(boolean _isShipSunk);
}
