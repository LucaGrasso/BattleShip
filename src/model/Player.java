package model;

import model.observer.ScoreListener;
import java.util.List;

/**
 * Interfaccia che rappresenta un giocatore.
 * Fornisce i metodi essenziali che ogni giocatore deve implementare.
 */
public interface Player {

    /**
     * Restituisce il nome del giocatore.
     *
     * @return Il nome del giocatore.
     */
    String getName();

    /**
     * Imposta il nome del giocatore.
     *
     * @param name Il nome da impostare per il giocatore.
     */
    void setName(String name);

    /**
     * Aggiunge un listener per la gestione del punteggio.
     *
     * @param listener Il listener da aggiungere.
     */
    void addScoreListener(ScoreListener listener);

    /**
     * Rimuove un listener per la gestione del punteggio.
     *
     * @param listener Il listener da rimuovere.
     */
    void removeScoreListener(ScoreListener listener);

    /**
     * Aggiunge un colpo a una nave in base al numero dato.
     * Notifica anche i listener del punteggio dopo l'aggiornamento.
     *
     * @param number Il numero del colpo da aggiungere alla nave.
     * @return true se la nave è stata distrutta, altrimenti false.
     */
    boolean addHitToShip(int number);

    /**
     * Restituisce tutte le navi distrutte.
     *
     * @return La lista di tutte le navi distrutte.
     */
    List<Ship> getAllDestroyedShips();

    /**
     * Verifica se il gioco è terminato.
     *
     * @return true se tutte le navi sono distrutte, altrimenti false.
     */
    boolean isGameOver();

    /**
     * Restituisce tutti i numeri delle navi distrutte.
     *
     * @return La lista di tutti i numeri delle navi distrutte.
     */
    List<Integer> getAllNumbersOfDestroyedShips();

    /**
     * Aggiunge un colpo a una nave specifica.
     * Notifica anche i listener del punteggio dopo l'aggiornamento.
     *
     * @param number Il numero del colpo da aggiungere.
     * @param shipToAddHitTo La nave a cui aggiungere il colpo.
     */
    void addHitToShip(int number, Ship shipToAddHitTo);

    /**
     * Restituisce tutte le navi del giocatore.
     *
     * @return La lista di tutte le navi.
     */
    List<Ship> getShips();

    /**
     * Restituisce il numero totale delle navi del giocatore.
     *
     * @return Il numero totale delle navi.
     */
    int getNumberShips();

    /**
     * Restituisce tutti i numeri delle navi del giocatore.
     *
     * @return La lista di tutti i numeri delle navi.
     */
    List<Integer> getAllShipNumbers();

    /**
     * Aggiunge una nave al giocatore.
     *
     * @param ship La nave da aggiungere.
     */
    void addShip(Ship ship);

    /**
     * Aggiunge una nave al giocatore specificando tipo, direzione e posizione.
     *
     * @param shipType Il tipo di nave.
     * @param shipDirection La direzione della nave.
     * @param shipPosition La posizione della nave.
     */
    void addShip(ShipType shipType, Direction shipDirection, int shipPosition);

    /**
     * Rimuove una nave del giocatore specificando tipo, direzione e posizione.
     *
     * @param shipType Il tipo di nave.
     * @param shipDirection La direzione della nave.
     * @param clickPosition La posizione della nave.
     */
    void removeShip(ShipType shipType, Direction shipDirection, int clickPosition);

    /**
     * Restituisce una nave in base al numero dato.
     *
     * @param number Il numero della nave.
     * @return La nave corrispondente al numero, o null se non trovata.
     */
    Ship getShipByNumber(int number);

    /**
     * Restituisce il numero totale delle navi del giocatore umano.
     *
     * @return Il numero totale delle navi.
     */
    int getHumanPlayerShipCount();

    /**
     * Restituisce un array di numeri della nave a partire da un numero dato.
     *
     * @param number Il numero della nave.
     * @return La lista dei numeri della nave, o null se non trovata.
     */
    List<Integer> getShipArrayFromGivenNumber(int number);

    /**
     * Conta il numero di navi di un certo tipo che il giocatore ha già posizionato.
     *
     * @param shipType Il tipo di nave di cui si desidera effettuare il conteggio.
     * @return Il numero di navi del tipo specificato che sono state posizionate.
     */
    long getShipTypeCount(ShipType shipType);
}