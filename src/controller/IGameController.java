package controller;

/**
 * Interfaccia per il controller del gioco di Battaglia Navale.
 * Definisce i metodi necessari per gestire la logica di gioco e l'interazione tra il modello e la vista.
 */
public interface IGameController {

    /**
     * Configura il gioco, inizializzando vista e modello.
     */
    void setUpGame();

    /**
     * Aggiorna il campo del nome del giocatore umano.
     */
    void updateNameFieldHuman();

    /**
     * Aggiorna il campo del nome del computer.
     */
    void updateNameFieldComputer();

    /**
     * Metodo che gestisce la fine del gioco.
     */
    void endGame();
}