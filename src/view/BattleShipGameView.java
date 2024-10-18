package view;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Direction;
import model.ShipType;
/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */
public class BattleShipGameView {
	private final GameFrame gameFrame;
	private SettingsJFrame SettingsJFrame;
	private String playerName;

	public BattleShipGameView() {
		gameFrame = new GameFrame();
		gameFrame.launch(this.askPlayerName());
		gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameFrame.setVisible(true);
	}

	public String askPlayerName() {
		playerName = JOptionPane.showInputDialog("Enter your nickname/name?");

		return playerName;
	}

	public void openSettingsJFrame() {
		SettingsJFrame = new SettingsJFrame();
		SettingsJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		SettingsJFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				enableSettingsButton();  // Il pulsante delle impostazioni sarà riabilitato qui
			}
		});
		SettingsJFrame.setVisible(true);
	}

	public void enableSettingsButton() {
		this.getGameFrame().enableSettingsButton();
	}

	public String getPlayerName() {
		return playerName;
	}

	public GameBoardJPanel getGameBoardPanel1() {
		return this.getGameFrame().getGameBoardJPanel1();
	}

	public GameBoardJPanel getGameBoardPanel2() {
		return this.getGameFrame().getGameBoardJPanel2();
	}

	public int getGameBoard1Size() {
		return this.getGameBoardPanel1().getSquares().size();
	}

	public int getGameBoard2Size() {
		return this.getGameBoardPanel2().getSquares().size();
	}

	public boolean isClickedInGameBoard2(int i, int x, int y) {
		return this.getGameBoardPanel2().getSquares().get(i).isClicked(x, y);
	}

	public boolean isClickedInGameBoard1(int i, int x, int y) {
		return this.getGameBoardPanel1().getSquares().get(i).isClicked(x, y);
	}

	public void colorShipGameBoardPanel1(int number, Color color) {
		this.getGameBoardPanel1().setColor(number, color);
	}

	public void colorRemoveShipGameBoardPanel1(int number, Color borderColor, Color color) {
		this.getGameBoardPanel1().setColor(number, borderColor, color);
	}

	public void colorShipGameBoardPanel2(int number, Color color) {
		this.getGameBoardPanel2().setColor(number, color);
	}

	public boolean getSquareBusyGameBoardPanel1(int number) {
		return this.getGameBoardPanel1().getSquares().get(number).getBusy();
	}

	public boolean getSquareBusyGameBoardPanel2(int number) {
		return this.getGameBoardPanel2().getSquares().get(number).getBusy();
	}

	public void setSquareGameBoardPanel2_Occupied(int number) {
		this.getGameBoardPanel2().getSquares().get(number).setBusy();
	}

	public ShipType getSelectedShipType() {
		return this.getGameFrame().getShipBoardPanel().getSelectedShipType();
	}

	public Direction getSelectedDirection() {
		return this.getGameFrame().getShipBoardPanel().getSelectedDirection();
	}

	public GameFrame getGameFrame() {
		return gameFrame;
	}

	public void showError(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public void disableStartButton() {
		this.getGameFrame().disableStartButton();
	}

	public boolean isStartButtonEnabled() {
		return this.getGameFrame().isStartButtonEnabled();
	}

	public void disableSettingsButton() {
		this.getGameFrame().disableSettingsButton();
	}

	public boolean isSettingsButtonEnabled() {
		return this.getGameFrame().isSettingsButtonEnabled	 ();
	}

	public void disableGameBoardJPanel1() {
		this.getGameFrame().disableGameBoardJPanel1();
	}

	public boolean isGameBoardJPanel1Enabled() {
		return this.getGameFrame().isGameBoardJPanel1Enabled();
	}

	public void updateNameFieldHuman(String n) {
 		this.getGameFrame().updateNameFieldHuman(n);
	}

	public void updateNameFieldComputer(String n) {
		this.getGameFrame().updateNameFieldComputer(n);
	}

	public void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public void closeApplication() {
		this.getGameFrame().closeApplication();
	}

	public boolean isShipsVisible() {
		return this.SettingsJFrame.shipsVisible();
	}

	public SettingsJFrame getSettingsJFrame() {
		return this.SettingsJFrame;
	}
	
}
