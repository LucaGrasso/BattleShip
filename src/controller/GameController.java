package controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List; // Usa l'interfaccia List invece di ArrayList

import model.*;
import view.BattleShipGameView;

/**
 * Controller per il gioco di Battaglia Navale.
 * Gestisce la logica di gioco e l'interazione tra il modello e la vista.
 *
 * @version 1.0
 */
public class GameController {
	private BattleShipGameModel model;
	private BattleShipGameView view;

	/**
	 * Costruttore che imposta il gioco.
	 */
	public GameController() {
		this.setUpGame();
	}

	/**
	 * Metodo che configura il gioco, inizializzando vista e modello.
	 */
	public void setUpGame() {
		view = new BattleShipGameView();
		model = new BattleShipGameModel(view.getPlayerName());
		view.getGameBoardPanel1().addMouseClickListener(new MouseClickHandler());
		view.getGameFrame().addMouseClickListenerToStartButton(new StartButtonHandler());
		view.getGameFrame().addMouseClickListenerToSettingsButton(new SettingsHandler());
	}

	/**
	 * Gestore per i clic sul pulsante delle impostazioni.
	 */
	private class SettingsHandler extends MouseAdapter {
		/**
		 * Metodo gestore per l'evento di clic del mouse.
		 * @param event Evento del clic del mouse.
		 */
		public void mouseClicked(MouseEvent event) {
			if (view.isSettingsButtonEnabled()) {
				view.disableSettingsButton();
				view.openSettingsJFrame();
			}
		}
	}

	/**
	 * Gestore per i clic sul pulsante di avvio.
	 */
	private class StartButtonHandler extends MouseAdapter {
		/**
		 * Metodo gestore per l'evento di clic del mouse.
		 * @param event Evento del clic del mouse.
		 */
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

	/**
	 * Gestore per i clic per sparare.
	 */
	private class ShootClickHandler extends MouseAdapter {

		List<Integer> computerShipNumbers = model.getComputerPlayerShipNumbers();
		List<Integer> humanPlayerShipNumbers = model.getAllHumanPlayerShipNumbers();

		/**
		 * Metodo gestore per l'evento di clic del mouse.
		 * @param event Evento del clic del mouse.
		 */
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
								for (Integer shipNumber : model.allNumbersOfDestroyedShipsOfComputer()) {
									view.colorShipGameBoardPanel2(shipNumber, Color.RED);
								}
							} else {
								view.colorShipGameBoardPanel2(beginNumber, Color.YELLOW);
							}
						} else {
							view.colorShipGameBoardPanel2(beginNumber, Color.BLUE);
						}
						updateNameFieldHuman();
						if (model.getIfGameOverComputer()) {
							view.showMessage("Game over!\n" + model.getHumanPlayerName() + " won with "
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

		/**
		 * Metodo che gestisce lo sparo del computer.
		 */
		public void computerShoots() {
			int shot = model.getComputerShot();

			if (humanPlayerShipNumbers.contains(shot)) {
				if (model.addHitNumberToHumanPlayerShip(shot)) {
					for (Integer shipNumber : model.allNumbersOfDestroyedShipsOfHumanPlayer()) {
						view.colorShipGameBoardPanel1(shipNumber, Color.RED);
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
				view.showMessage("Game over!\n" + model.getComputerPlayerName() + " won with "
						+ model.getComputerPlayerScore() + " points...");
				endGame();
			}
		}
	}

	/**
	 * Aggiorna il campo del nome del giocatore umano.
	 */
	public void updateNameFieldHuman() {
		view.updateNameFieldHuman(model.getHumanPlayerName() + " (" + model.getHumanPlayerScore() + "):");
	}

	/**
	 * Aggiorna il campo del nome del computer.
	 */
	public void updateNameFieldComputer() {
		view.updateNameFieldComputer(model.getComputerPlayerName() + " (" + model.getComputerPlayerScore() + "):");
	}

	/**
	 * Termina il gioco e reimposta la partita.
	 */
	public void endGame() {
		view.closeApplication();
		this.setUpGame();
	}

	/**
	 * Gestore per i clic del mouse sul pannello di gioco del giocatore 1.
	 */
	private class MouseClickHandler extends MouseAdapter {
		/**
		 * Metodo gestore per l'evento di clic del mouse.
		 * @param event Evento del clic del mouse.
		 */
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
							// Clic destro del mouse per rimuovere la nave
							if (event.getButton() == MouseEvent.BUTTON3) {
								if (color.equals(Color.WHITE)) {
									Ship ship = model.getShipFromHumanPlayerByNumber(beginNumber);
									if (ship != null) {
										Direction direction = ship.getShipDirection();
										ShipType type = ship.getShipType();
										Integer firstNumber = ship.getShipNumbers().getFirst();

										for (Integer shipNumber : model.getShipArrayFromGivenNumber(beginNumber)) {
											view.colorRemoveShipGameBoardPanel1(shipNumber, Color.BLACK, Color.LIGHT_GRAY);
										}

										model.removeShipFromHumanPlayer(type, direction, firstNumber);
										// Aggiorno l'etichetta
										view.getGameFrame()
												.setShipTotalCountLabel(model.getHumanPlayerShipCount());
										view.getGameFrame()
												.setShipSingleCountLabel(type, (int) model.getShipTypeCount(type));

										break;
									}
								}
							} else { // Aggiungi la nave con il clic sinistro
								model.addShipToHumanPlayer(view.getSelectedShipType(), view.getSelectedDirection(),
										beginNumber);
								for (Integer shipNumber : model.getLastAddedShipToHumanPlayer().getShipNumbers()) {
									view.colorShipGameBoardPanel1(shipNumber, Color.WHITE);
								}
								// Aggiorna l'etichetta
								view.getGameFrame().setShipTotalCountLabel(model.getHumanPlayerShipCount());
								view.getGameFrame().setShipSingleCountLabel(view.getSelectedShipType(),
										(int) model.getShipTypeCount(view.getSelectedShipType()));

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

	/**
	 * Genera le navi per il computer.
	 */
	private void computerGenerateShips() {
		model.computerGenerateShips();
	}

	/**
	 * Mostra le navi generate.
	 */
	private void showGeneratedShips() {
		for (Integer number : model.getComputerPlayerShipNumbers()) {
			view.colorShipGameBoardPanel2(number, Color.WHITE);
		}
	}
}