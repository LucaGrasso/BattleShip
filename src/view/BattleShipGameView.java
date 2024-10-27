package view;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Direction;
import model.ShipType;

/**
 * Represents the main view for the BattleShip game.
 * Handles the interaction between the game frame and the settings frame.
 *
 * @version 1.0
 */
public class BattleShipGameView {

	private static BattleShipGameView instance;
	private GameFrame gameFrame;
	private SettingsJFrame settingsJFrame;
	private String playerName;

	/**
	 * Private constructor to implement Singleton pattern.
	 * Launches the game frame and asks the player for their name.
	 */
	private BattleShipGameView() {
		gameFrame = new GameFrame();
		gameFrame.launch(this.askPlayerName());
		gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameFrame.setVisible(true);
	}

	/**
	 * Gets the single instance of BattleShipGameView.
	 *
	 * @return single instance of BattleShipGameView
	 */
	public static synchronized BattleShipGameView getInstance() {
		if (instance == null) {
			instance = new BattleShipGameView();
		}
		return instance;
	}

	public void updateGameBoard() {
		// Resetta i pannelli della griglia e delle navi
		gameFrame.closeApplication();
		gameFrame = new GameFrame();
		gameFrame.launch(this.playerName);
		gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameFrame.setVisible(true);


		//gameFrame.resetGameFrame(playerName);
	}

	/**
	 * Displays a dialog to ask the player for their name.
	 *
	 * @return the player's name
	 */
	public String askPlayerName() {
		this.playerName = JOptionPane.showInputDialog("Enter your nickname/name?");
		return playerName;
	}

	/**
	 * Opens the settings frame.
	 */
	public void openSettingsJFrame() {
		settingsJFrame = SettingsJFrame.getInstance();
		settingsJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Rimozione eventuali listener già esistenti
		for (WindowListener listener : settingsJFrame.getWindowListeners()) {
			settingsJFrame.removeWindowListener(listener);
		}

		settingsJFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				enableSettingsButton();  // Il pulsante delle impostazioni sarà riabilitato qui
			}
		});

		settingsJFrame.setVisible(true);
	}

	/**
	 * Enables the settings button in the game frame.
	 */
	public void enableSettingsButton() {
		this.getGameFrame().enableSettingsButton();
	}

	/**
	 * Returns the player's name.
	 *
	 * @return the player's name
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Returns the first game board panel.
	 *
	 * @return the first game board panel
	 */
	public GameBoardJPanel getGameBoardPanel1() {
		return this.getGameFrame().getGameBoardJPanel1();
	}

	/**
	 * Returns the second game board panel.
	 *
	 * @return the second game board panel
	 */
	public GameBoardJPanel getGameBoardPanel2() {
		return this.getGameFrame().getGameBoardJPanel2();
	}

	/**
	 * Returns the size of the first game board.
	 *
	 * @return the size of the first game board
	 */
	public int getGameBoard1Size() {
		return this.getGameBoardPanel1().getSquares().size();
	}

	/**
	 * Returns the size of the second game board.
	 *
	 * @return the size of the second game board
	 */
	public int getGameBoard2Size() {
		return this.getGameBoardPanel2().getSquares().size();
	}

	/**
	 * Checks if a square in the second game board is clicked.
	 *
	 * @param i the index of the square
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return true if the square is clicked, false otherwise
	 */
	public boolean isClickedInGameBoard2(int i, int x, int y) {
		return this.getGameBoardPanel2().getSquares().get(i).isClicked(x, y);
	}

	/**
	 * Checks if a square in the first game board is clicked.
	 *
	 * @param i the index of the square
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return true if the square is clicked, false otherwise
	 */
	public boolean isClickedInGameBoard1(int i, int x, int y) {
		return this.getGameBoardPanel1().getSquares().get(i).isClicked(x, y);
	}

	/**
	 * Sets the color of a ship in the first game board.
	 *
	 * @param number the number of the ship
	 * @param color the color to set
	 */
	public void colorShipGameBoardPanel1(int number, Color color) {
		this.getGameBoardPanel1().setColor(number, color);
	}

	/**
	 * Sets the color of a ship and its border in the first game board.
	 *
	 * @param number the number of the ship
	 * @param borderColor the border color to set
	 * @param color the color to set
	 */
	public void colorRemoveShipGameBoardPanel1(int number, Color borderColor, Color color) {
		this.getGameBoardPanel1().setColor(number, borderColor, color);
	}

	/**
	 * Sets the color of a ship in the second game board.
	 *
	 * @param number the number of the ship
	 * @param color the color to set
	 */
	public void colorShipGameBoardPanel2(int number, Color color) {
		this.getGameBoardPanel2().setColor(number, color);
	}

	/**
	 * Checks if a square in the first game board is busy.
	 *
	 * @param number the number of the square
	 * @return true if the square is busy, false otherwise
	 */
	public boolean getSquareBusyGameBoardPanel1(int number) {
		return this.getGameBoardPanel1().getSquares().get(number).getBusy();
	}

	/**
	 * Checks if a square in the second game board is busy.
	 *
	 * @param number the number of the square
	 * @return true if the square is busy, false otherwise
	 */
	public boolean getSquareBusyGameBoardPanel2(int number) {
		return this.getGameBoardPanel2().getSquares().get(number).getBusy();
	}

	/**
	 * Sets a square in the second game board as occupied.
	 *
	 * @param number the number of the square
	 */
	public void setSquareGameBoardPanel2_Occupied(int number) {
		this.getGameBoardPanel2().getSquares().get(number).setBusy();
	}

	/**
	 * Returns the selected ship type.
	 *
	 * @return the selected ship type
	 */
	public ShipType getSelectedShipType() {
		return this.getGameFrame().getShipBoardPanel().getSelectedShipType();
	}

	/**
	 * Returns the selected direction.
	 *
	 * @return the selected direction
	 */
	public Direction getSelectedDirection() {
		return this.getGameFrame().getShipBoardPanel().getSelectedDirection();
	}

	/**
	 * Returns the game frame.
	 *
	 * @return the game frame
	 */
	public GameFrame getGameFrame() {
		return gameFrame;
	}

	/**
	 * Displays an error message.
	 *
	 * @param message the error message to display
	 */
	public void showError(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * Disables the start button.
	 */
	public void disableStartButton() {
		this.getGameFrame().disableStartButton();
	}

	/**
	 * Checks if the start button is enabled.
	 *
	 * @return true if the start button is enabled, false otherwise
	 */
	public boolean isStartButtonEnabled() {
		return this.getGameFrame().isStartButtonEnabled();
	}

	/**
	 * Disables the settings button.
	 */
	public void disableSettingsButton() {
		this.getGameFrame().disableSettingsButton();
	}

	/**
	 * Checks if the settings button is enabled.
	 *
	 * @return true if the settings button is enabled, false otherwise
	 */
	public boolean isSettingsButtonEnabled() {
		return this.getGameFrame().isSettingsButtonEnabled();
	}

	/**
	 * Disables the first game board panel.
	 */
	public void disableGameBoardJPanel1() {
		this.getGameFrame().disableGameBoardJPanel1();
	}

	/**
	 * Checks if the first game board panel is enabled.
	 *
	 * @return true if the first game board panel is enabled, false otherwise
	 */
	public boolean isGameBoardJPanel1Enabled() {
		return this.getGameFrame().isGameBoardJPanel1Enabled();
	}

	/**
	 * Updates the human player's name field.
	 *
	 * @param n the new name
	 */
	public void updateNameFieldHuman(String n) {
		this.getGameFrame().updateNameFieldHuman(n);
	}

	/**
	 * Updates the computer player's name field.
	 *
	 * @param n the new name
	 */
	public void updateNameFieldComputer(String n) {
		this.getGameFrame().updateNameFieldComputer(n);
	}

	/**
	 * Displays a message to the user.
	 *
	 * @param message the message to display
	 */
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * Closes the application.
	 */
	public void closeApplication() {
		this.getGameFrame().closeApplication();
	}

	/**
	 * Checks if the ships are visible.
	 *
	 * @return true if the ships are visible, false otherwise
	 */
	public boolean isShipsVisible() {
		return settingsJFrame.shipsVisible();
	}

	/**
	 * Returns the settings frame.
	 *
	 * @return the settings frame
	 */
	public SettingsJFrame getSettingsJFrame() {
		return settingsJFrame;
	}
}