package model.state;

import model.BattleShipGame;
import model.DomainException;
/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */
public class StartedGameState implements GameState {
	private final BattleShipGame game;

	public StartedGameState(BattleShipGame game) {
		this.game = game;
	}

	@Override
	public void newGame() {
		this.game.setCurrentGameState(this.game.getNewGameState());
	}

	@Override
	public void start() {
		throw new DomainException("There's already a game going on");
	}

}
