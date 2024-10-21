package model;

import model.observer.ScoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luca Grasso
 * @version 1.0
 */
public class HumanPlayer {
    private String name;
    private final List<Ship> ships = new ArrayList<>();
    // Numero massimo di navi che un giocatore può posizionare sul campo di gioco
    protected static final int MAX_SHIPS = 5;

    private final List<ScoreListener> scoreListeners = new ArrayList<>();
    // Aggiungo un Listener per la gestione del punteggio
    public void addScoreListener(ScoreListener listener) {
        scoreListeners.add(listener);
    }
    // Rimuovo un Listener per la gestione del punteggio
    public void removeScoreListener(ScoreListener listener) {
        scoreListeners.remove(listener);
    }
    // Notifico tutti i listener del punteggio che è stato aggiornato il punteggio
    private void notifyScoreListeners() {
        for (ScoreListener listener : scoreListeners) {
            listener.onScoreUpdate();
        }
    }

    // Costruttore di default per HumanPlayer con nome di default
    public HumanPlayer() {
        this("defaultName");
    }

    // Costruttore per HumanPlayer con nome specificato dall'utente
    public HumanPlayer(String name) {
        this.setName(name);
    }

    // Restituisce il nome del giocatore umano corrente
    public String getName() {
        return name;
    }

    // Imposto il nome del giocatore umano corrente con il nome specificato
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            this.name = "Player1";
        } else {
            this.name = name;
        }
    }

    // Restituisce la nave contenente il numero specificato della sua dimensione
    public Ship getShipBySizeNumber(int shipSize) {
        for (Ship ship : this.getShips()) {
            if (ship.getShipNumbers().contains(shipSize)) {
                return ship;
            }
        }
        return null;
    }

    public boolean addHitToShip(int number) {
        // returns if ship was destroyed
        Ship ship = this.getShipBySizeNumber(number);
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

    public List<Integer> getAllNumbersOfDestroyedShips() {
        List<Integer> destroyedNumbers = new ArrayList<>();
        for (Ship s : getAllDestroyedShips()) {
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

    public List<Ship> getShips() {
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
        if (ships.size() == MAX_SHIPS) {
            throw new DomainException("You cannot place more than 5 ships!");
        }
        if (!isShipTypeAllowed(ship)) {
            throw new DomainException("It is no longer possible to place ships of this type!");
        }
        if (isShipOverlapping(ship)) {
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

    public int getHumanPlayerShipCount() {
        return ships.size();
    }


    public List<Integer> getShipArrayFromGivenNumber(int number) {
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

    private boolean isShipTypeAllowed(Ship ship) {
        long shipCount = ships.stream()
                .filter(s -> s.getShipType().equals(ship.getShipType()))
                .count();
        return ship.getShipType().getNumberOfAllowedShips() > shipCount;
    }

    /**
     * Conta il numero di navi di un certo tipo che il giocatore ha già posizionato
     *
     * @param shipType Il tipo di nave di cui si desidera effettuare il conteggio
     * @return Il numero di navi del tipo specificato che sono state posizionate
     */
    public long getShipTypeCount(ShipType shipType) {
        return ships.stream()
                .filter(s -> s.getShipType().equals(shipType))
                .count();
    }

    // Verifica se la nave si sovrappone a un'altra nave già posizionata sul campo di gioco
    // Restituisce true se la nave non si sovrappone a nessun'altra nave, altrimenti false
    private boolean isShipOverlapping(Ship ship) {
        for (Ship s : ships) {
            for (Integer number : ship.getShipNumbers()) {
                if (s.getNumbersRandomShip().contains(number) || s.getShipNumbers().contains(number)) {
                    return true;
                }
            }
        }
        return false;
    }

}
