package model.state;

import model.BattleShipGame;
import model.DomainException;

/**
 * Classe che rappresenta lo stato del gioco quando è in fase di preparazione per una nuova partita.
 *
 * @version 1.0
 */
public class NewGameState implements GameState {
	private final BattleShipGame game;

	/**
	 * Costruttore che inizializza lo stato del gioco con un'istanza di BattleShipGame.
	 *
	 * @param game L'istanza del gioco da associare a questo stato.
	 */
	public NewGameState(BattleShipGame game) {
		this.game = game;
	}

	/**
	 * Lancia un'eccezione poiché non è possibile iniziare una nuova partita se quella corrente non è ancora iniziata.
	 */
	@Override
	public void newGame() {
		throw new DomainException("Cannot start a new game if not started yet!");
	}

	/**
	 * Avvia il gioco se tutte le 5 navi sono state posizionate, altrimenti lancia un'eccezione.
	 */
	@Override
	public void start() {
		if (game.getHumanPlayer().getNumberShips() != 5) {
			throw new DomainException("5 ships must be placed!");
		}
		game.setCurrentGameState(game.getStartedGameState());
	}
}