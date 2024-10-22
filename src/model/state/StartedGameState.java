package model.state;

import model.BattleShipGame;
import model.DomainException;

/**
 * Classe che rappresenta lo stato del gioco quando è in corso.
 *
 * @version 1.0
 */
public class StartedGameState implements GameState {
	private final BattleShipGame game;

	/**
	 * Costruttore che inizializza lo stato del gioco con un'istanza di BattleShipGame.
	 *
	 * @param game L'istanza del gioco da associare a questo stato.
	 */
	public StartedGameState(BattleShipGame game) {
		this.game = game;
	}

	/**
	 * Inizia una nuova partita reimpostando lo stato del gioco allo stato di nuova partita.
	 */
	@Override
	public void newGame() {
		this.game.setCurrentGameState(this.game.getNewGameState());
	}

	/**
	 * Lancia un'eccezione poiché è già in corso una partita.
	 */
	@Override
	public void start() {
		throw new DomainException("There's already a game going on");
	}
}