package model;

import java.util.List;

/**
 * Interfaccia che rappresenta il modello del gioco BattleShip.
 * Definisce le interazioni tra i giocatori (umano e computer) e il gioco.
 *
 * @version 1.0
 */
public interface IBattleShipGameModel {



    /**
     * Restituisce il nome del giocatore umano.
     *
     * @return Il nome del giocatore umano.
     */
    String getHumanPlayerName();

    /**
     * Restituisce il nome del giocatore computer.
     *
     * @return Il nome del giocatore computer.
     */
    String getComputerPlayerName();


    /**
     * Aggiunge una nave al giocatore umano specificando tipo, direzione e posizione.
     *
     * @param shipType     Il tipo di nave.
     * @param shipDirection La direzione della nave.
     * @param shipPositionX La posizione iniziale della nave.
     */
    void addShipToHumanPlayer(ShipType shipType, Direction shipDirection, int shipPositionX);

    /**
     * Rimuove una nave del giocatore umano specificando tipo, direzione e posizione.
     *
     * @param shipType     Il tipo di nave.
     * @param shipDirection La direzione della nave.
     * @param clickPosition La posizione in cui rimuovere la nave.
     */
    void removeShipFromHumanPlayer(ShipType shipType, Direction shipDirection, int clickPosition);

    /**
     * Restituisce una lista di numeri della nave a partire da un determinato numero.
     *
     * @param number Il numero da cui partire.
     * @return Una lista di numeri della nave.
     */
    List<Integer> getShipArrayFromGivenNumber(int number);

    /**
     * Restituisce una nave del giocatore umano a partire da un numero specifico.
     *
     * @param number Il numero della nave.
     * @return La nave corrispondente al numero specificato.
     */
    Ship getShipFromHumanPlayerByNumber(int number);

    /**
     * Restituisce il giocatore umano.
     *
     * @return Il giocatore umano.
     */
    HumanPlayer getHumanPlayer();

    /**
     * Restituisce l'ultima nave aggiunta al giocatore umano.
     *
     * @return L'ultima nave aggiunta al giocatore umano.
     */
    Ship getLastAddedShipToHumanPlayer();

    /**
     * Restituisce il giocatore computer.
     *
     * @return Il giocatore computer.
     */
    ComputerPlayer getComputerPlayer();

    /**
     * Genera le navi del computer in modo casuale.
     */
    void computerGenerateShips();

    /**
     * Restituisce una lista di numeri delle navi del giocatore computer.
     *
     * @return Una lista di numeri delle navi del giocatore computer.
     */
    List<Integer> getComputerPlayerShipNumbers();

    /**
     * Restituisce una lista di numeri di tutte le navi del giocatore umano.
     *
     * @return Una lista di numeri di tutte le navi del giocatore umano.
     */
    List<Integer> getAllHumanPlayerShipNumbers();

    /**
     * Aggiunge un numero di colpo alla nave del computer.
     *
     * @param number Il numero del colpo.
     * @return True se il colpo è stato aggiunto, altrimenti False.
     */
    boolean addHitNumberToComputerShip(int number);

    /**
     * Aggiunge un numero di colpo alla nave del giocatore umano.
     *
     * @param hitNumber Il numero del colpo.
     * @return True se il colpo è stato aggiunto, altrimenti False.
     */
    boolean addHitNumberToHumanPlayerShip(int hitNumber);

    /**
     * Restituisce il gioco BattleShip.
     *
     * @return Il gioco BattleShip.
     */
    BattleShipGame getGame();

    /**
     * Avvia il gioco.
     */
    void startGame();

    /**
     * Restituisce una lista dei numeri di tutte le navi distrutte del computer.
     *
     * @return Una lista di numeri di tutte le navi distrutte del computer.
     */
    List<Integer> allNumbersOfDestroyedShipsOfComputer();

    /**
     * Restituisce una lista dei numeri di tutte le navi distrutte del giocatore umano.
     *
     * @return Una lista di numeri di tutte le navi distrutte del giocatore umano.
     */
    List<Integer> allNumbersOfDestroyedShipsOfHumanPlayer();

    /**
     * Restituisce il numero del colpo effettuato dal computer.
     *
     * @return Il numero del colpo effettuato dal computer.
     */
    int getComputerShot();

    /**
     * Imposta se l'ultimo colpo del computer è stato un successo.
     *
     * @param lastShot True se l'ultimo colpo è stato un successo, altrimenti False.
     */
    void setLastHitSuccessful(boolean lastShot);

    /**
     * Imposta se una nave è stata affondata.
     *
     * @param isShipSunk True se una nave è stata affondata, altrimenti False.
     */
    void setIsShipSunk(boolean isShipSunk);

    /**
     * Restituisce il punteggio del giocatore umano.
     *
     * @return Il punteggio del giocatore umano.
     */
    int getHumanPlayerScore();

    /**
     * Restituisce il punteggio del giocatore computer.
     *
     * @return Il punteggio del giocatore computer.
     */
    int getComputerPlayerScore();

    /**
     * Verifica se il gioco è terminato per il giocatore umano.
     *
     * @return True se il gioco è terminato per il giocatore umano, altrimenti False.
     */
    boolean getIfGameOverHumanPlayer();

    /**
     * Verifica se il gioco è terminato per il computer.
     *
     * @return True se il gioco è terminato per il computer, altrimenti False.
     */
    boolean getIfGameOverComputer();

    /**
     * Legge la strategia dei colpi dal file di proprietà.
     */
    void readHitStrategyFromProp();

    /**
     * Legge la strategia di posizionamento delle navi dal file di proprietà.
     */
    void readPlacesShipStrategyFromProp();

    /**
     * Restituisce il conteggio delle navi del giocatore umano.
     *
     * @return Il conteggio delle navi del giocatore umano.
     */
    int getHumanPlayerShipCount();

    /**
     * Restituisce il conteggio delle navi di un determinato tipo del giocatore umano.
     *
     * @param type Il tipo di nave.
     * @return Il conteggio delle navi del tipo specificato.
     */
    long getShipTypeCount(ShipType type);
}