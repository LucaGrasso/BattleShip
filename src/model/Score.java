/**
 * @Author Luca Grasso
 * @Matricola 294612
 * @Progetto PMO
 * @Data 03/05/2024
 * @Descrizione Questa classe rappresenta il punteggio del giocatore e
 *              del computer e notifica i cambiamenti del punteggio.
 * @Version 1.0
 */

package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Score {

    private int score;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.support.removePropertyChangeListener(listener);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int newScore) {
        int oldScore = this.score;
        this.score = newScore;
        this.support.firePropertyChange("score", oldScore, newScore);
    }
}
