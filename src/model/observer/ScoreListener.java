package model.observer;

/**
 * Interfaccia utilizzata per notificare i cambiamenti del punteggio.
 * Fa parte del pattern Observer.
 *
 * @author lgras
 * @version 1.0
 * @date 03/05/2024
 */
public interface ScoreListener {

    /**
     * Metodo chiamato per aggiornare il punteggio.
     */
    void onScoreUpdate();
}