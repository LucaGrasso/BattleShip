package model;

/**
 * Classe che rappresenta un giocatore umano nel gioco.
 * Estende la classe BasePlayer.
 * Implementa l'interfaccia {@link Player}.
 * Contiene informazioni specifiche per il giocatore umano.
 *
 * @autor Luca Grasso
 * @version 1.0
 */
public class HumanPlayer extends BasePlayer {

    /**
     * Costruttore di default per HumanPlayer.
     */
    public HumanPlayer() {
        super();
    }

    /**
     * Costruttore per HumanPlayer con nome specificato.
     *
     * @param name Il nome del giocatore
     */
    public HumanPlayer(String name) {
        super(name);
    }
}