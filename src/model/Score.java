/**
 * Rappresenta il punteggio del giocatore e del computer e notifica i cambiamenti del punteggio.
 *
 * @Author Luca Grasso
 * @Matricola 294612
 * @Progetto PMO
 * @Data 03/05/2024
 * @Version 1.0
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Score {

    private int score;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Aggiunge un listener che ascolta i cambiamenti delle proprietà.
     *
     * @param listener Il listener di proprietà da aggiungere.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    /**
     * Rimuove un listener che ascolta i cambiamenti delle proprietà.
     *
     * @param listener Il listener di proprietà da rimuovere.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.support.removePropertyChangeListener(listener);
    }

    /**
     * Restituisce il punteggio corrente.
     *
     * @return Il punteggio corrente.
     */
    public int getScore() {
        return score;
    }

    /**
     * Imposta un nuovo punteggio e notifica i listener del cambiamento avvenuto.
     *
     * @param newScore Il nuovo punteggio da impostare.
     */
    public void setScore(int newScore) {
        int oldScore = this.score;
        this.score = newScore;
        this.support.firePropertyChange("score", oldScore, newScore);
    }
}