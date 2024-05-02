package model;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */
public class BattleShipGameModel {

	private BattleShipGame game;

	public BattleShipGameModel(String naam) {
		this.game = new BattleShipGame(naam);
	}

	public void setHumanPlayerName(String naam) {
		this.getHumanPlayer().setName(naam);
	}

	public String getHumanPlayerName() {
		return this.getHumanPlayer().getName();
	}

	public String getComputerPlayerName() {
		return this.getComputerPlayer().getName();
	}

	public ArrayList<Ship> getHumanPlayerShepen() {
		return this.getHumanPlayer().getShips();
	}

	public void addShipToHumanPlayer(Ship ship) {
		this.getHumanPlayer().addShip(ship);
	}

	public void addShipToHumanPlayer(ShipType schipType, Direction richting, int beginVakje) {
		this.getHumanPlayer().addShip(schipType, richting, beginVakje);
	}

	public HumanPlayer getHumanPlayer() {
		return this.game.getHumanPlayer();
	}

	public Ship getLastAddedShipToHumanPlayer() {
		return this.getHumanPlayer().getlastAddedShip();
	}

	public ComputerPlayer getComputerPlayer() {
		return this.game.getComputerPlayer();
	}

	public void computerGenerateShips() {
		this.getComputerPlayer().setShipsFromStrategy();
	}

	public List<Integer> getComputerPlayerShipNumbers() {
		return this.getComputerPlayer().getAllShipNumbers();
	}

	public List<Integer> getAllHumanPlayerShipNumbers() {
		return this.getHumanPlayer().getAllShipNumbers();
	}

	public boolean addHitNumberToComputerShip(int nummer) {
		return this.getComputerPlayer().addHitToShip(nummer);
	}

	public boolean addHitNumberToHumanPlayerShip(int nummer) {
		return this.getHumanPlayer().addHitToShip(nummer);
	}

	public BattleShipGame getGame() {
		return game;
	}

	public void startGame() {
		this.getGame().start();
	}

	public void newGame() {
		this.getGame().newGame();
	}

	public ArrayList<Integer> allNumbersfDestroyedShipsOfComputer() {
		return this.getComputerPlayer().allNumbersOfDestroyedShips();
	}

	public ArrayList<Integer> allNumbersfDestroyedShipsOfHumanPlayer() {
		return this.getHumanPlayer().allNumbersOfDestroyedShips();
	}

	public int getComputerShot() {
		return this.getGame().getComputerPlayer().hitShip();
	}

	public int getHumanPlayerScore() {
		return this.getGame().getScoreHumanPlayer().getScore();
	}

	public int getComputerPlayerScore() {
		return this.getGame().getScoreComputerPlayer().getScore();
	}

	public boolean getIfGameOverHumanPlayer() {
		return this.getHumanPlayer().isGameOver();
	}

	public boolean getIfGameOverComputer() {
		return this.getComputerPlayer().isGameOver();
	}

	public void readHitStrategyFromProp() {
		this.getComputerPlayer().readHitStrategyFromProp();
	}

	public void readPlacesShipStrategyFromProp() {
		this.getComputerPlayer().readPlaceShipFromProp();
	}

}
