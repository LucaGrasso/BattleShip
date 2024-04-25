package model.state;

import model.BattleShipGame;
import model.DomainException;
/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */
public class NewGameState implements GameState {
	private final BattleShipGame game;

	public NewGameState(BattleShipGame game) {
		this.game = game;
	}

	@Override
	public void newGame() {
		throw new DomainException("Cannot start a new game if not started yet!");
	}

	@Override
	public void start() {
		if (game.getHumanPlayer().getNumberShips() != 5) {
			throw new DomainException("5 ships must be placed!");
		}
		game.setCurrentGameState(game.getStartedGameState());
	}

}
