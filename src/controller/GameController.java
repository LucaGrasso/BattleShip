package controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import model.BattleShipGameModel;
import model.DomainException;
import view.BattleShipGameView;
/**
 * @author Luca Grasso
 * @version 1.0
 */
public class GameController {
	private BattleShipGameModel model;
	private BattleShipGameView view;

	public GameController() {
		this.setUpGame();
	}

	public void setUpGame() {
		view = new BattleShipGameView();
		model = new BattleShipGameModel(view.getPlayerName());
		view.getGameBoardPanel1().addMouseClickListener(new MouseClickHandler());
        assert view != null; // assert that view is not null to avoid NullPointerException
        view.getGameFrame().addMouseClickListenerToStartButton(new StartButtonHandler());
        assert view != null; // assert that view is not null to avoid NullPointerException
        view.getGameFrame().addMouseClickListenerToSettingsButton(new SettingsHandler());
	}

	private class SettingsHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			if (view.isSettingsButtonEnabled()) {
				view.disableSettingsButton();
				view.openSettingsJFrame();
			}
		}
	}

	private class StartButtonHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			if (view.isStartButtonEnabled()) {
				try {
					model.startGame();
					model.readHitStrategyFromProp();
					model.readPlacesShipStrategyFromProp();
					computerGenerateShips();
					if (view.getSettingsJFrame() != null && view.isShipsVisible()) {
						showGeneratedShips();
					}
					view.disableStartButton();
					view.disableGameBoardJPanel1();
					view.getGameBoardPanel2().addMouseClickListener(new ShootClickHandler());
					updateNameFieldComputer();
					updateNameFieldHuman();
					if (view.isSettingsButtonEnabled()) {
						view.disableSettingsButton();
					}
				} catch (Exception e) {
					view.showError(e.getMessage());
				}
			}
		}
	}

	private class ShootClickHandler extends MouseAdapter {

		ArrayList<Integer> computerShipNumbers = (ArrayList<Integer>) model.getComputerPlayerShipNumbers();
		ArrayList<Integer> humanPlayerShipNumbers = (ArrayList<Integer>) model.getAllHumanPlayerShipNumbers();

		public void mouseClicked(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			int beginNumber = -1;

			for (int i = 0; i < view.getGameBoard2Size(); i++) {
				if (view.isClickedInGameBoard2(i, x, y)) {
					beginNumber = i;
					if (!view.getSquareBusyGameBoardPanel2(beginNumber)) {
						view.setSquareGameBoardPanel2_Occupied(beginNumber);
						if (computerShipNumbers.contains(beginNumber)) {
							if (model.addHitNumberToComputerShip(beginNumber)) {
								for (Integer integer : model.allNumbersOfDestroyedShipsOfComputer()) {
									view.colorShipGameBoardPanel2(integer, Color.RED);
								}
							} else {
								view.colorShipGameBoardPanel2(beginNumber, Color.YELLOW);
							}

						} else {
							view.colorShipGameBoardPanel2(beginNumber, Color.BLUE);
						}
						updateNameFieldHuman();
						if (model.getIfGameOverComputer()) {
							view.showMessage("Game over!\n" + model.getHumanPlayerName() + " won met "
									+ model.getHumanPlayerScore() + " points...");
							endGame();
						} else {
							this.computerShoots();
						}
						break;
					}
				}
			}

		}

		public void computerShoots() {
			int shot = model.getComputerShot();

			if (humanPlayerShipNumbers.contains(shot)) {
				if (model.addHitNumberToHumanPlayerShip(shot)) {
					for (Integer integer : model.allNumbersOfDestroyedShipsOfHumanPlayer()) {
						view.colorShipGameBoardPanel1(integer, Color.RED);
						model.setIsShipSunk(true);
					}
				} else {
					view.colorShipGameBoardPanel1(shot, Color.YELLOW);
					model.setLastHitSuccessful(true);
				}
			} else {
				view.colorShipGameBoardPanel1(shot, Color.BLUE);
				model.setLastHitSuccessful(false);
			}
			updateNameFieldComputer();
			if (model.getIfGameOverHumanPlayer()) {
				view.showMessage("Game over!\n" + model.getComputerPlayerName() + " won met "
						+ model.getComputerPlayerName() + " points...");
				endGame();
			}
		}

	}

	public void updateNameFieldHuman() {
		view.updateNameFieldHuman(model.getHumanPlayerName() + " (" + model.getHumanPlayerScore() + "):");
	}

	public void updateNameFieldComputer() {
		view.updateNameFieldComputer(model.getComputerPlayerName() + " (" + model.getComputerPlayerScore() + "):");
	}

	public void endGame() {
		view.closeApplication();
		this.setUpGame();
	}

	private class MouseClickHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			if (view.isGameBoardJPanel1Enabled()) {

				int x = event.getX();
				int y = event.getY();
				int beginNumber;
				for (int i = 0; i < view.getGameBoard1Size(); i++) {
					if (view.isClickedInGameBoard1(i, x, y)) {
						beginNumber = i;
						try {
							Color color = view.getGameFrame().getGameBoardJPanel1().getColor(beginNumber);

							// TODO: refactor this code to use a switch statement
							// devo cercare per punto preso e se lo trovo devo rimuovere la nave
							if (color.equals(Color.WHITE)) {
								model.removeShipFromHumanPlayer(BeginNumber);
								for (Integer shipNumber : model.getLastAddedShipToHumanPlayer().getShipNumbers()) {
									view.colorRemoveShipGameBoardPanel1(shipNumber, Color.BLACK, Color.LIGHT_GRAY);
								}
								break;
							}

							model.addShipToHumanPlayer(view.getSelectedShipType(), view.getSelectedDirection(),
									beginNumber);
							for (Integer shipNumber : model.getLastAddedShipToHumanPlayer().getShipNumbers()) {
								view.colorShipGameBoardPanel1(shipNumber, Color.WHITE);
							}
						} catch (DomainException e) {
							view.showError(e.getMessage());
						}
						break;
					}
				}
			}
		}
	}

	private void computerGenerateShips() {
		model.computerGenerateShips();
	}

	private void showGeneratedShips() {
		for (Integer number : model.getComputerPlayerShipNumbers()) {
			view.colorShipGameBoardPanel2(number, Color.WHITE);
		}
	}

}
