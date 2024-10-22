/**
 * @author Luca Grasso
 * @matricola 294612
 * @progetto PMO
 * @data 03/05/2024
 */

package model.observer;

/**
 * Classe che implementa l'interfaccia ScoreListener per osservare e gestire
 * i cambiamenti del punteggio.
 *
 * @version 1.0
 */
public class ScoreObserver implements ScoreListener {
    private int score;

    /**
     * Costruttore che inizializza il punteggio a 19.
     */
    public ScoreObserver() {
        this.score = 19;
    }

    /**
     * Metodo invocato quando il punteggio deve essere aggiornato.
     */
    @Override
    public void onScoreUpdate() {
        decreaseScore();
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
     * Imposta il punteggio a un valore specificato.
     *
     * @param score Il nuovo punteggio.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Decrementa il punteggio di uno.
     */
    private void decreaseScore() {
        this.setScore(score - 1);
    }
}