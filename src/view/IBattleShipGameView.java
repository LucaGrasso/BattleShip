package view;

import java.awt.Color;
import javax.swing.JFrame;
import model.Direction;
import model.ShipType;

/**
 * Rappresenta l'interfaccia per la vista principale del gioco BattleShip.
 * Definisce vari metodi per interagire con il frame di gioco e il frame delle impostazioni.
 *
 * @version 1.0
 */
public interface IBattleShipGameView {

    /**
     * Aggiorna il tabellone di gioco. Ad esempio, quando il gioco viene resettato.
     */
    void updateGameBoard();

    /**
     * Mostra una finestra di dialogo per chiedere il nome del giocatore.
     *
     * @return il nome del giocatore
     */
    String askPlayerName();

    /**
     * Apre il frame delle impostazioni.
     */
    void openSettingsJFrame();

    /**
     * Abilita il pulsante delle impostazioni nel frame di gioco.
     */
    void enableSettingsButton();

    /**
     * Restituisce il nome del giocatore.
     *
     * @return il nome del giocatore
     */
    String getPlayerName();

    /**
     * Restituisce il primo pannello del tabellone di gioco.
     *
     * @return il primo pannello del tabellone di gioco
     */
    GameBoardJPanel getGameBoardPanel1();

    /**
     * Restituisce il secondo pannello del tabellone di gioco.
     *
     * @return il secondo pannello del tabellone di gioco
     */
    GameBoardJPanel getGameBoardPanel2();

    /**
     * Restituisce la dimensione del primo tabellone di gioco.
     *
     * @return la dimensione del primo tabellone di gioco
     */
    int getGameBoard1Size();

    /**
     * Restituisce la dimensione del secondo tabellone di gioco.
     *
     * @return la dimensione del secondo tabellone di gioco
     */
    int getGameBoard2Size();

    /**
     * Verifica se una casella nel secondo tabellone di gioco è cliccata.
     *
     * @param i l'indice della casella
     * @param x la coordinata x
     * @param y la coordinata y
     * @return true se la casella è cliccata, false altrimenti
     */
    boolean isClickedInGameBoard2(int i, int x, int y);

    /**
     * Verifica se una casella nel primo tabellone di gioco è cliccata.
     *
     * @param i l'indice della casella
     * @param x la coordinata x
     * @param y la coordinata y
     * @return true se la casella è cliccata, false altrimenti
     */
    boolean isClickedInGameBoard1(int i, int x, int y);

    /**
     * Imposta il colore di una nave nel primo tabellone di gioco.
     *
     * @param number il numero della nave
     * @param color il colore da impostare
     */
    void colorShipGameBoardPanel1(int number, Color color);

    /**
     * Imposta il colore di una nave e del suo bordo nel primo tabellone di gioco.
     *
     * @param number il numero della nave
     * @param borderColor il colore del bordo da impostare
     * @param color il colore da impostare
     */
    void colorRemoveShipGameBoardPanel1(int number, Color borderColor, Color color);

    /**
     * Imposta il colore di una nave nel secondo tabellone di gioco.
     *
     * @param number il numero della nave
     * @param color il colore da impostare
     */
    void colorShipGameBoardPanel2(int number, Color color);

    /**
     * Verifica se una casella nel primo tabellone di gioco è occupata.
     *
     * @param number il numero della casella
     * @return true se la casella è occupata, false altrimenti
     */
    boolean getSquareBusyGameBoardPanel1(int number);

    /**
     * Verifica se una casella nel secondo tabellone di gioco è occupata.
     *
     * @param number il numero della casella
     * @return true se la casella è occupata, false altrimenti
     */
    boolean getSquareBusyGameBoardPanel2(int number);

    /**
     * Imposta una casella nel secondo tabellone di gioco come occupata.
     *
     * @param number il numero della casella
     */
    void setSquareGameBoardPanel2_Occupied(int number);

    /**
     * Restituisce il tipo di nave selezionato.
     *
     * @return il tipo di nave selezionato
     */
    ShipType getSelectedShipType();

    /**
     * Restituisce la direzione selezionata.
     *
     * @return la direzione selezionata
     */
    Direction getSelectedDirection();

    /**
     * Restituisce il frame di gioco.
     *
     * @return il frame di gioco
     */
    GameFrame getGameFrame();

    /**
     * Mostra un messaggio di errore.
     *
     * @param message il messaggio di errore da mostrare
     */
    void showError(String message);

    /**
     * Disabilita il pulsante di avvio.
     */
    void disableStartButton();

    /**
     * Verifica se il pulsante di avvio è abilitato.
     *
     * @return true se il pulsante di avvio è abilitato, false altrimenti
     */
    boolean isStartButtonEnabled();

    /**
     * Disabilita il pulsante delle impostazioni.
     */
    void disableSettingsButton();

    /**
     * Verifica se il pulsante delle impostazioni è abilitato.
     *
     * @return true se il pulsante delle impostazioni è abilitato, false altrimenti
     */
    boolean isSettingsButtonEnabled();

    /**
     * Disabilita il primo pannello del tabellone di gioco.
     */
    void disableGameBoardJPanel1();

    /**
     * Verifica se il primo pannello del tabellone di gioco è abilitato.
     *
     * @return true se il primo pannello del tabellone di gioco è abilitato, false altrimenti
     */
    boolean isGameBoardJPanel1Enabled();

    /**
     * Aggiorna il campo del nome del giocatore umano.
     *
     * @param n il nuovo nome
     */
    void updateNameFieldHuman(String n);

    /**
     * Aggiorna il campo del nome del giocatore computer.
     *
     * @param n il nuovo nome
     */
    void updateNameFieldComputer(String n);

    /**
     * Mostra un messaggio all'utente.
     *
     * @param message il messaggio da mostrare
     */
    void showMessage(String message);

    /**
     * Chiude l'applicazione.
     */
    void closeApplication();

    /**
     * Verifica se le navi sono visibili.
     *
     * @return true se le navi sono visibili, false altrimenti
     */
    boolean isShipsVisible();

    /**
     * Restituisce il frame delle impostazioni.
     *
     * @return il frame delle impostazioni
     */
    SettingsJFrame getSettingsJFrame();
}