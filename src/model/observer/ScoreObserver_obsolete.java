package model.observer;

import java.util.Observable;
import java.util.Observer;

import model.ComputerPlayer;
import model.HumanPlayer;

/** Deprecated class
 *
 * @author Luca Grasso
 *
 *
 */

// This class is not used in the project
// It is an example of an observer that listens to the score changes of a player
public class ScoreObserver_obsolete implements Observer {
	private int score;

    public ScoreObserver_obsolete(Observable observable) {
		this.score = 19;
        observable.addObserver(this);
	}

	@Override
	public void update(Observable observablePlayer, Object arg) {
		boolean isPlayerInstance = false;
		if (isPlayerInstance) {
			decreaseScore();
		}
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