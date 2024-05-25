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

	private final BattleShipGame game;

	public BattleShipGameModel(String playerName) {
		this.game = new BattleShipGame(playerName);
	}

	public void setHumanPlayerName(String playerName) {
		this.getHumanPlayer().setName(playerName);
	}

	public String getHumanPlayerName() {
		return this.getHumanPlayer().getName();
	}

	public String getComputerPlayerName() {
		return this.getComputerPlayer().getName();
	}

	public ArrayList<Ship> getHumanPlayerShip() {
		return this.getHumanPlayer().getShips();
	}

	public void addShipToHumanPlayer(Ship ship) {
		this.getHumanPlayer().addShip(ship);
	}

	public void addShipToHumanPlayer(ShipType shipType, Direction shipDirection, int shipPositionX) {
		this.getHumanPlayer().addShip(shipType, shipDirection, shipPositionX);
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

	public boolean addHitNumberToComputerShip(int number) {
		return this.getComputerPlayer().addHitToShip(number);
	}

	public boolean addHitNumberToHumanPlayerShip(int hitNumber) {
		return this.getHumanPlayer().addHitToShip(hitNumber);
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

	public ArrayList<Integer> allNumbersOfDestroyedShipsOfComputer() {
		return this.getComputerPlayer().allNumbersOfDestroyedShips();
	}

	public ArrayList<Integer> allNumbersOfDestroyedShipsOfHumanPlayer() {
		return this.getHumanPlayer().allNumbersOfDestroyedShips();
	}

	public int getComputerShot() {
		return this.getGame().getComputerPlayer().hitShip();
	}

	public void setLastHitSuccessful(boolean lastShot) {
		this.getGame().getComputerPlayer().setLastHitSuccessful(lastShot);
	}

	public void setIsShipSunk(boolean isShipSunk) {
		this.getGame().getComputerPlayer().setIsShipSunk(isShipSunk);
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
