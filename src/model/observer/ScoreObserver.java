/**
 * @Author Luca Grasso
 * @Matricola 294612
 * @Progetto PMO
 * @Data 03/05/2024
 */

package model.observer;

public class ScoreObserver implements ScoreListener {
    private int score;

    public ScoreObserver() {
        this.score = 19;
    }

    @Override
    public void onScoreUpdate() {
        decreaseScore();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private void decreaseScore() {
        this.setScore(score - 1);
    }
}
