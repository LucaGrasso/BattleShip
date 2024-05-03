package model.observer;

/**
 * Project BattleShip
 * Author  lgras  on  03/05/2024
 * Version 1.0
 * Description This interface is used to notify the score changes.
 * This is a part of the Observer pattern.
 */
public interface ScoreListener {
    void onScoreUpdate();
}
