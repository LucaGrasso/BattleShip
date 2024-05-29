package model;

import model.observer.ScoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author Luca Grasso
 * @version 1.0
 */
public class HumanPlayer {
    private String name;
    private final ArrayList<Ship> ships = new ArrayList<>();
    public static final int MAX_SHIPS = 5;

    private final List<ScoreListener> scoreListeners = new ArrayList<>();

    public void addScoreListener(ScoreListener listener) {
        scoreListeners.add(listener);
    }

    public void removeScoreListener(ScoreListener listener) {
        scoreListeners.remove(listener);
    }

    private void notifyScoreListeners() {
        for (ScoreListener listener : scoreListeners) {
            listener.onScoreUpdate();
        }
    }

    public HumanPlayer() {
        this("defaultName");
    }

    public HumanPlayer(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            this.name = "Player1";
        } else {
            this.name = name;
        }
    }

    public Ship getShipContainsNumber(int shipNumber) {
        for (Ship ship : this.getShips()) {
            if (ship.getShipNumbers().contains(shipNumber)) {
                return ship;
            }
        }
        return null;
    }

    public boolean addHitToShip(int number) {
        // returns if ship was destroyed
        Ship ship = this.getShipContainsNumber(number);
        if (ship == null) {
            throw new DomainException("Ship was not found!");
        }
        ship.addNumberHit(number);
        boolean destroyed = ship.getShipNumbersHit().containsAll(ship.getShipNumbers());
        //this.setChanged();
        this.notifyScoreListeners();

        return destroyed;
    }

    public ArrayList<Ship> getAllDestroyedShips() {
        ArrayList<Ship> destroyedShips = new ArrayList<>();
        for (Ship ship : this.getShips()) {
            if (ship.getShipNumbersHit().containsAll(ship.getShipNumbers())) {
                destroyedShips.add(ship);
            }
        }
        return destroyedShips;
    }

    public boolean isGameOver() {
        return this.getAllDestroyedShips().containsAll(this.getShips());
    }

    public ArrayList<Integer> allNumbersOfDestroyedShips() {
        ArrayList<Integer> destroyedNumbers = new ArrayList<>();
        for (Ship s : this.getAllDestroyedShips()) {
            destroyedNumbers.addAll(s.getShipNumbers());
        }
        return destroyedNumbers;
    }

    public void addHitToShip(int number, Ship shipToAddHitTo) {
        if (shipToAddHitTo == null) {
            throw new DomainException("Ship was not found!");
        }
        shipToAddHitTo.addNumberHit(number);
        this.notifyScoreListeners();
        this.notifyScoreListeners();
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public int getNumberShips() {
        return this.getShips().size();
    }

    public List<Integer> getAllShipNumbers() {
        List<Integer> shipNumbers = new ArrayList<>();
        for (Ship ship : ships) {
            shipNumbers.addAll(ship.getShipNumbers());
        }
        return shipNumbers;
    }

    public void addShip(Ship ship) {
        if (maxNumberShips()) {
            throw new DomainException("You cannot place more than 5 ships!");
        }
        if (!this.maxNumberShipsType(ship)) {
            throw new DomainException("It is no longer possible to place ships of this type!");
        }
        if (!this.checkShipOverlap(ship)) {
            throw new DomainException("This ship is placed too close to another ship!");
        }
        this.ships.add(ship);
    }

    public void addShip(ShipType shipType, Direction shipDirection, int shipPosition) {
        Ship ship = new Ship(shipType, shipDirection, shipPosition);
        this.addShip(ship);
    }

    public void removeShip(ShipType shipType, Direction shipDirection, int clickPosition) {
        List<Ship> tempList = new ArrayList<>(ships);
        tempList.forEach(ship -> {
            if (ship.getShipType().equals(shipType) && ship.getShipDirection().equals(shipDirection) && ship.isNumberInShip(clickPosition)) {
                ships.remove(ship);
            }
        });
    }

    public Ship getShipByNumber(int number) {
        for (Ship ship : ships) {
            if (ship.isNumberInShip(number)) {
                return ship;
            }
        }
        return null;
    }


    public ArrayList<Integer> getShipArrayFromGivenNumber(int number) {
        for (Ship ship : ships) {
            if (ship.isNumberInShip(number)) {
                return ship.getShipNumbers();
            }
        }
        return null;
    }


    public Ship getlastAddedShip() {
        return ships.getLast();
    }

    private boolean maxNumberShips() {
        return ships.size() == MAX_SHIPS;
    }

    private boolean maxNumberShipsType(Ship ship) {
        int shipCount = 0;
        for (Ship s : this.getShips()) {
            if (s.getShipType().equals(ship.getShipType())) {
                shipCount++;
            }
        }
        // true if ship is allowed to add
        return ship.getShipType().getNumberOfAllowedShips() > shipCount;
    }

    private boolean checkShipOverlap(Ship ship) {
        for (Ship s : this.getShips()) {
            for (Integer i : ship.getShipNumbers()) {
                if (s.getNumbersRandomShip().contains(i) || s.getShipNumbers().contains(i)) {
                    return false;
                }
            }
        }
        return true;
    }

}
