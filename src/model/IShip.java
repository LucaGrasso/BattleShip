package model;

import java.util.List;

/**
 * Interfaccia per definire le operazioni di una nave nel gioco di Battaglia Navale.
 */

public interface IShip {
    /**
     * Verifica se un determinato numero è presente nella nave.
     *
     * @param number il numero da verificare.
     * @return true se il numero è presente nella nave, altrimenti false.
     */
    boolean isNumberInShip(int number);

    /**
     * Restituisce una lista di numeri che rappresentano le posizioni circostanti alla nave.
     *
     * @return una lista di numeri rappresentanti le posizioni circostanti la nave.
     */
    List<Integer> getNumbersRandomShip();

    /**
     * Aggiunge il numero colpito alla lista delle posizioni colpite.
     *
     * @param hitNumber il numero colpito.
     */
    void addNumberHit(int hitNumber);

    /**
     * Restituisce la lista delle posizioni occupate dalla nave.
     *
     * @return la lista delle posizioni occupate dalla nave.
     */
    List<Integer> getShipNumbers();

    /**
     * Restituisce la lista delle posizioni colpite della nave.
     *
     * @return la lista delle posizioni colpite della nave.
     */
    List<Integer> getShipNumbersHit();

    /**
     * Restituisce la direzione della nave.
     *
     * @return la direzione della nave.
     */
    Direction getShipDirection();

    /**
     * Restituisce il tipo di nave.
     *
     * @return il tipo di nave.
     */
    ShipType getShipType();
}