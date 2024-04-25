package model.strategy;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */
public class RandomHitShipStrategy implements HitShipStrategy {

	private final List<Integer> alreadyHit = new ArrayList<>();

	@Override
	public int hitShip() {
		int result = -1;

		while (result == -1) {
			int temp = (int) (Math.random() * 100);
			if (alreadyHit.size() == 100) {
				result = 99;
			}
			if (!(alreadyHit.contains(temp))) {
				alreadyHit.add(temp);
				result = temp;
			}
		}
		return result;
	}

}
