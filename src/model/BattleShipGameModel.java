package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta il modello del gioco BattleShip.
 * Gestisce le interazioni tra i giocatori (umano e computer) e il gioco.
 *
 * @version 1.0
 */
public class BattleShipGameModel {

	private final BattleShipGame game;

	/**
	 * Costruttore della classe BattleShipGameModel.
	 *
	 * @param playerName Il nome del giocatore umano.
	 */
	public BattleShipGameModel(String playerName) {
		this.game = new BattleShipGame(playerName);
	}

	/**
	 * Reset di tutto il model per iniziare un nuovo gioco.
	 *
	 */

	public void resetGame() {
		this.getGame().resetGame();
	}

	/**
	 * Imposta il nome del giocatore umano.
	 *
	 * @param playerName Il nome del giocatore umano.
	 */
	public void setHumanPlayerName(String playerName) {
		this.getHumanPlayer().setName(playerName);
	}

	/**
	 * Restituisce il nome del giocatore umano.
	 *
	 * @return Il nome del giocatore umano.
	 */
	public String getHumanPlayerName() {
		return this.getHumanPlayer().getName();
	}

	/**
	 * Restituisce il nome del giocatore computer.
	 *
	 * @return Il nome del giocatore computer.
	 */
	public String getComputerPlayerName() {
		return this.getComputerPlayer().getName();
	}

	/**
	 * Restituisce la lista delle navi del giocatore umano.
	 *
	 * @return La lista delle navi del giocatore umano.
	 */
	public List<Ship> getHumanPlayerShip() {
		return this.getHumanPlayer().getShips();
	}

	/**
	 * Aggiunge una nave al giocatore umano.
	 *
	 * @param ship La nave da aggiungere.
	 */
	public void addShipToHumanPlayer(Ship ship) {
		this.getHumanPlayer().addShip(ship);
	}

	/**
	 * Aggiunge una nave al giocatore umano specificando tipo, direzione e posizione.
	 *
	 * @param shipType     Il tipo di nave.
	 * @param shipDirection La direzione della nave.
	 * @param shipPositionX La posizione iniziale della nave.
	 */
	public void addShipToHumanPlayer(ShipType shipType, Direction shipDirection, int shipPositionX) {
		this.getHumanPlayer().addShip(shipType, shipDirection, shipPositionX);
	}

	/**
	 * Rimuove una nave del giocatore umano specificando tipo, direzione e posizione.
	 *
	 * @param shipType     Il tipo di nave.
	 * @param shipDirection La direzione della nave.
	 * @param clickPosition La posizione in cui rimuovere la nave.
	 */
	public void removeShipFromHumanPlayer(ShipType shipType, Direction shipDirection, int clickPosition) {
		this.getHumanPlayer().removeShip(shipType, shipDirection, clickPosition);
	}

	/**
	 * Restituisce una lista di numeri della nave a partire da un determinato numero.
	 *
	 * @param number Il numero da cui partire.
	 * @return Una lista di numeri della nave.
	 */
	public List<Integer> getShipArrayFromGivenNumber(int number) {
		return this.getHumanPlayer().getShipArrayFromGivenNumber(number);
	}

	/**
	 * Restituisce una nave del giocatore umano a partire da un numero specifico.
	 *
	 * @param number Il numero della nave.
	 * @return La nave corrispondente al numero specificato.
	 */
	public Ship getShipFromHumanPlayerByNumber(int number) {
		return this.getHumanPlayer().getShipByNumber(number);
	}

	/**
	 * Restituisce il giocatore umano.
	 *
	 * @return Il giocatore umano.
	 */
	public HumanPlayer getHumanPlayer() {
		return this.game.getHumanPlayer();
	}

	/**
	 * Restituisce l'ultima nave aggiunta al giocatore umano.
	 *
	 * @return L'ultima nave aggiunta al giocatore umano.
	 */
	public Ship getLastAddedShipToHumanPlayer() {
		return this.getHumanPlayer().getlastAddedShip();
	}

	/**
	 * Restituisce il giocatore computer.
	 *
	 * @return Il giocatore computer.
	 */
	public ComputerPlayer getComputerPlayer() {
		return this.game.getComputerPlayer();
	}

	/**
	 * Genera le navi del computer in modo casuale.
	 */
	public void computerGenerateShips() {
		this.getComputerPlayer().setShipsFromStrategy();
	}

	/**
	 * Restituisce una lista di numeri delle navi del giocatore computer.
	 *
	 * @return Una lista di numeri delle navi del giocatore computer.
	 */
	public List<Integer> getComputerPlayerShipNumbers() {
		return this.getComputerPlayer().getAllShipNumbers();
	}

	/**
	 * Restituisce una lista di numeri di tutte le navi del giocatore umano.
	 *
	 * @return Una lista di numeri di tutte le navi del giocatore umano.
	 */
	public List<Integer> getAllHumanPlayerShipNumbers() {
		return this.getHumanPlayer().getAllShipNumbers();
	}

	/**
	 * Aggiunge un numero di colpo alla nave del computer.
	 *
	 * @param number Il numero del colpo.
	 * @return True se il colpo è stato aggiunto, otherwise False.
	 */
	public boolean addHitNumberToComputerShip(int number) {
		return this.getComputerPlayer().addHitToShip(number);
	}

	/**
	 * Aggiunge un numero di colpo alla nave del giocatore umano.
	 *
	 * @param hitNumber Il numero del colpo.
	 * @return True se il colpo è stato aggiunto, otherwise False.
	 */
	public boolean addHitNumberToHumanPlayerShip(int hitNumber) {
		return this.getHumanPlayer().addHitToShip(hitNumber);
	}

	/**
	 * Restituisce il gioco BattleShip.
	 *
	 * @return Il gioco BattleShip.
	 */
	public BattleShipGame getGame() {
		return game;
	}

	/**
	 * Avvia il gioco.
	 */
	public void startGame() {
		this.getGame().start();
	}

	/**
	 * Inizia un nuovo gioco.
	 */
	public void newGame() {
		this.getGame().newGame();
	}

	/**
	 * Restituisce una lista dei numeri di tutte le navi distrutte del computer.
	 *
	 * @return Una lista di numeri di tutte le navi distrutte del computer.
	 */
	public List<Integer> allNumbersOfDestroyedShipsOfComputer() {
		return this.getComputerPlayer().getAllNumbersOfDestroyedShips();
	}

	/**
	 * Restituisce una lista dei numeri di tutte le navi distrutte del giocatore umano.
	 *
	 * @return Una lista di numeri di tutte le navi distrutte del giocatore umano.
	 */
	public List<Integer> allNumbersOfDestroyedShipsOfHumanPlayer() {
		return this.getHumanPlayer().getAllNumbersOfDestroyedShips();
	}

	/**
	 * Restituisce il numero del colpo effettuato dal computer.
	 *
	 * @return Il numero del colpo effettuato dal computer.
	 */
	public int getComputerShot() {
		return this.getGame().getComputerPlayer().hitShip();
	}

	/**
	 * Imposta se l'ultimo colpo del computer è stato un successo.
	 *
	 * @param lastShot True se l'ultimo colpo è stato un successo, otherwise False.
	 */
	public void setLastHitSuccessful(boolean lastShot) {
		this.getGame().getComputerPlayer().setLastHitSuccessful(lastShot);
	}

	/**
	 * Imposta se una nave è stata affondata.
	 *
	 * @param isShipSunk True se una nave è stata affondata, otherwise False.
	 */
	public void setIsShipSunk(boolean isShipSunk) {
		this.getGame().getComputerPlayer().setIsShipSunk(isShipSunk);
	}

	/**
	 * Restituisce il punteggio del giocatore umano.
	 *
	 * @return Il punteggio del giocatore umano.
	 */
	public int getHumanPlayerScore() {
		return this.getGame().getScoreHumanPlayer().getScore();
	}

	/**
	 * Restituisce il punteggio del giocatore computer.
	 *
	 * @return Il punteggio del giocatore computer.
	 */
	public int getComputerPlayerScore() {
		return this.getGame().getScoreComputerPlayer().getScore();
	}

	/**
	 * Verifica se il gioco è terminato per il giocatore umano.
	 *
	 * @return True se il gioco è terminato per il giocatore umano, otherwise False.
	 */
	public boolean getIfGameOverHumanPlayer() {
		return this.getHumanPlayer().isGameOver();
	}

	/**
	 * Verifica se il gioco è terminato per il computer.
	 *
	 * @return True se il gioco è terminato per il computer, otherwise False.
	 */
	public boolean getIfGameOverComputer() {
		return this.getComputerPlayer().isGameOver();
	}

	/**
	 * Legge la strategia dei colpi dal file di proprietà.
	 */
	public void readHitStrategyFromProp() {
		this.getComputerPlayer().readHitStrategyFromProp();
	}

	/**
	 * Legge la strategia di posizionamento delle navi dal file di proprietà.
	 */
	public void readPlacesShipStrategyFromProp() {
		this.getComputerPlayer().readPlaceShipFromProp();
	}

	/**
	 * Restituisce il conteggio delle navi del giocatore umano.
	 *
	 * @return Il conteggio delle navi del giocatore umano.
	 */
	public int getHumanPlayerShipCount() {
		return this.getHumanPlayer().getHumanPlayerShipCount();
	}

	/**
	 * Restituisce il conteggio delle navi di un determinato tipo del giocatore umano.
	 *
	 * @param type Il tipo di nave.
	 * @return Il conteggio delle navi del tipo specificato.
	 */
	public long getShipTypeCount(ShipType type) {
		return this.getHumanPlayer().getShipTypeCount(type);
	}
}