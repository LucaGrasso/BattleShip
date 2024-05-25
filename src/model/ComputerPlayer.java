package model;

import model.strategy.HitShipStrategy;
import model.strategy.PlaceShipStrategy;
import model.factory.HitShipFactory;
import model.factory.PlaceShipFactory;
/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */
public class ComputerPlayer extends HumanPlayer {

	public PlaceShipStrategy shipStrategy;
	public HitShipStrategy hitShipStrategy;

	public ComputerPlayer() {
		super("Computer");
		this.readHitStrategyFromProp();
		this.readPlaceShipFromProp();
	}

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

	public int hitShip() {
		return hitShipStrategy.hitShip();
	}

	public void setLastHitSuccessful(boolean hitSuccessful) {
		hitShipStrategy.setLastHitSuccessful(hitSuccessful);
	}

	public void setIsShipSunk(boolean isShipSunk) {
		hitShipStrategy.setIsShipSunk(isShipSunk);
	}

	public void readHitStrategyFromProp() {
		hitShipStrategy = new HitShipFactory().getHitShipStrategy();
	}

	public void readPlaceShipFromProp() {
		shipStrategy = new PlaceShipFactory().getPlaceShipStrategy();
	}

}
