package model;

import model.observer.ScoreObserver;
import model.state.GameState;
import model.state.NewGameState;
import model.state.StartedGameState;

/**
 * Classe che rappresenta il gioco BattleShip.
 * Gestisce i giocatori, gli osservatori del punteggio e gli stati del gioco.
 *
 * @version 1.0
 */
public class BattleShipGame {
	private final HumanPlayer humanPlayer;
	private final ComputerPlayer computerPlayer;

	private final ScoreObserver scoreHumanPlayer;
	private final ScoreObserver scoreComputerPlayer;

	private final GameState newGameState = new NewGameState(this);
	private final GameState startedGameState = new StartedGameState(this);
	private GameState currentGameState = newGameState;

	/**
	 * Costruttore della classe BattleShipGame.
	 *
	 * @param name Il nome del giocatore umano.
	 */
	public BattleShipGame(String name) {
		humanPlayer = new HumanPlayer(name);
		computerPlayer = new ComputerPlayer();
		scoreHumanPlayer = new ScoreObserver();
		scoreComputerPlayer = new ScoreObserver();

		// Aggiungi scoreObserver come listener di humanPlayer e computerPlayer
		humanPlayer.addScoreListener(scoreHumanPlayer);
		computerPlayer.addScoreListener(scoreComputerPlayer);
	}

	/**
	 * Restituisce lo stato corrente del gioco.
	 *
	 * @return lo stato corrente del gioco.
	 */
	public GameState getCurrentGameState() {
		return this.currentGameState;
	}

	/**
	 * Imposta lo stato corrente del gioco.
	 *
	 * @param gameState Il nuovo stato del gioco.
	 */
	public void setCurrentGameState(GameState gameState) {
		this.currentGameState = gameState;
	}

	/**
	 * Restituisce il giocatore umano.
	 *
	 * @return il giocatore umano.
	 */
	public HumanPlayer getHumanPlayer() {
		return humanPlayer;
	}

	/**
	 * Restituisce il giocatore computer.
	 *
	 * @return il giocatore computer.
	 */
	public ComputerPlayer getComputerPlayer() {
		return computerPlayer;
	}

	/**
	 * Restituisce lo stato del nuovo gioco.
	 *
	 * @return lo stato del nuovo gioco.
	 */
	public GameState getNewGameState() {
		return newGameState;
	}

	/**
	 * Restituisce lo stato del gioco iniziato.
	 *
	 * @return lo stato del gioco iniziato.
	 */
	public GameState getStartedGameState() {
		return startedGameState;
	}

	/**
	 * Avvia il gioco cambiando lo stato a "gioco iniziato".
	 */
	public void start() {
		this.currentGameState.start();
	}

	/**
	 * Inizia un nuovo gioco cambiando lo stato a "nuovo gioco".
	 */
	public void newGame() {
		this.currentGameState.newGame();
	}

	/**
	 * Restituisce l'osservatore del punteggio del giocatore umano.
	 *
	 * @return l'osservatore del punteggio del giocatore umano.
	 */
	public ScoreObserver getScoreHumanPlayer() {
		return this.scoreHumanPlayer;
	}

	/**
	 * Restituisce l'osservatore del punteggio del giocatore computer.
	 *
	 * @return l'osservatore del punteggio del giocatore computer.
	 */
	public ScoreObserver getScoreComputerPlayer() {
		return this.scoreComputerPlayer;
	}
}