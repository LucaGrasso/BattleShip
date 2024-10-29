package model;

import model.observer.ScoreListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe base che rappresenta un giocatore nel gioco.
 * Contiene informazioni sulle navi del giocatore, il nome e la gestione dei punteggi tramite listener.
 * Implementa l'interfaccia {@link Player}.
 */
public abstract class BasePlayer implements Player {
    private String name;
    private final List<Ship> ships = new ArrayList<>();
    protected static final int MAX_SHIPS = 5;

    private final List<ScoreListener> scoreListeners = new ArrayList<>();

    /**
     * Costruttore di default per BasePlayer con nome di default.
     */
    public BasePlayer() {
        this("defaultName");
    }

    /**
     * Costruttore per BasePlayer con nome specificato dall'utente.
     *
     * @param name Il nome del giocatore
     */
    public BasePlayer(String name) {
        this.setName(name);
    }

    /**
     * Resetta il giocatore.
     */
    public void reset() {
        // Svuota la lista delle navi e listener del punteggio
        ships.clear();
        scoreListeners.clear();
    }

    /**
     * Restituisce il nome del giocatore corrente.
     *
     * @return Il nome del giocatore
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome del giocatore corrente con il nome specificato.
     *
     * @param name Il nome da impostare per il giocatore
     */
    @Override
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            this.name = "Player1";
        } else {
            this.name = name;
        }
    }

    /**
     * Aggiunge un listener per la gestione del punteggio.
     *
     * @param listener Il listener da aggiungere
     */
    @Override
    public void addScoreListener(ScoreListener listener) {
        scoreListeners.add(listener);
    }

    /**
     * Rimuove un listener per la gestione del punteggio.
     *
     * @param listener Il listener da rimuovere
     */
    @Override
    public void removeScoreListener(ScoreListener listener) {
        scoreListeners.remove(listener);
    }

    /**
     * Notifica tutti i listener del punteggio che c'è stato un aggiornamento del punteggio.
     */
    protected void notifyScoreListeners() {
        for (ScoreListener listener : scoreListeners) {
            listener.onScoreUpdate();
        }
    }

    /**
     * Aggiunge un colpo a una nave in base al numero dato.
     * Notifica anche i listener del punteggio dopo l'aggiornamento.
     *
     * @param number Il numero del colpo da aggiungere alla nave
     * @return true se la nave è stata distrutta, altrimenti false
     */
    @Override
    public boolean addHitToShip(int number) {
        Ship ship = this.getShipByNumber(number);
        if (ship == null) {
            throw new DomainException("Ship was not found!");
        }
        ship.addNumberHit(number);
        boolean destroyed = ship.getShipNumbersHit().containsAll(ship.getShipNumbers());
        this.notifyScoreListeners();
        return destroyed;
    }

    /**
     * Restituisce tutte le navi distrutte.
     *
     * @return La lista di tutte le navi distrutte
     */
    @Override
    public List<Ship> getAllDestroyedShips() {
        List<Ship> destroyedShips = new ArrayList<>();
        for (Ship ship : this.getShips()) {
            if (ship.getShipNumbersHit().containsAll(ship.getShipNumbers())) {
                destroyedShips.add(ship);
            }
        }
        return destroyedShips;
    }

    /**
     * Verifica se il gioco è terminato.
     *
     * @return true se tutte le navi sono distrutte, altrimenti false
     */
    @Override
    public boolean isGameOver() {
        return this.getAllDestroyedShips().containsAll(this.getShips());
    }

    /**
     * Restituisce tutti i numeri delle navi distrutte.
     *
     * @return La lista di tutti i numeri delle navi distrutte
     */
    @Override
    public List<Integer> getAllNumbersOfDestroyedShips() {
        List<Integer> destroyedNumbers = new ArrayList<>();
        for (Ship s : getAllDestroyedShips()) {
            destroyedNumbers.addAll(s.getShipNumbers());
        }
        return destroyedNumbers;
    }

    /**
     * Aggiunge un colpo a una nave specifica.
     * Notifica anche i listener del punteggio dopo l'aggiornamento.
     *
     * @param number Il numero del colpo da aggiungere
     * @param shipToAddHitTo La nave a cui aggiungere il colpo
     */
    @Override
    public void addHitToShip(int number, Ship shipToAddHitTo) {
        if (shipToAddHitTo == null) {
            throw new DomainException("Ship was not found!");
        }
        shipToAddHitTo.addNumberHit(number);
        this.notifyScoreListeners();
    }

    /**
     * Restituisce tutte le navi del giocatore.
     *
     * @return La lista di tutte le navi
     */
    @Override
    public List<Ship> getShips() {
        return ships;
    }

    /**
     * Restituisce il numero totale delle navi del giocatore.
     *
     * @return Il numero totale delle navi
     */
    @Override
    public int getNumberShips() {
        return this.getShips().size();
    }

    /**
     * Restituisce tutti i numeri delle navi del giocatore.
     *
     * @return La lista di tutti i numeri delle navi
     */
    @Override
    public List<Integer> getAllShipNumbers() {
        List<Integer> shipNumbers = new ArrayList<>();
        for (Ship ship : ships) {
            shipNumbers.addAll(ship.getShipNumbers());
        }
        return shipNumbers;
    }

    /**
     * Aggiunge una nave al giocatore.
     *
     * @param ship La nave da aggiungere
     */
    @Override
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

    /**
     * Aggiunge una nave al giocatore specificando tipo, direzione e posizione.
     *
     * @param shipType Il tipo di nave
     * @param shipDirection La direzione della nave
     * @param shipPosition La posizione della nave
     */
    @Override
    public void addShip(ShipType shipType, Direction shipDirection, int shipPosition) {
        Ship ship = new Ship(shipType, shipDirection, shipPosition);
        this.addShip(ship);
    }

    /**
     * Rimuove una nave del giocatore specificando tipo, direzione e posizione.
     *
     * @param shipType Il tipo di nave
     * @param shipDirection La direzione della nave
     * @param clickPosition La posizione della nave
     */
    @Override
    public void removeShip(ShipType shipType, Direction shipDirection, int clickPosition) {
        List<Ship> tempList = new ArrayList<>(ships);
        tempList.forEach(ship -> {
            if (ship.getShipType().equals(shipType) && ship.getShipDirection().equals(shipDirection) && ship.isNumberInShip(clickPosition)) {
                ships.remove(ship);
            }
        });
    }

    /**
     * Restituisce una nave in base al numero dato.
     *
     * @param number Il numero della nave
     * @return La nave corrispondente al numero, o null se non trovata
     */
    @Override
    public Ship getShipByNumber(int number) {
        return ships.stream()
                .filter(ship -> ship.isNumberInShip(number))
                .findFirst()
                .orElse(null);
    }

    /**
     * Restituisce l'ultima nave aggiunta alla lista delle navi del giocatore.
     *
     * @return L'ultima nave aggiunta, o null se la lista è vuota
     */
    public Ship getlastAddedShip() {
        return ships.getLast();
    }

    /**
     * Verifica se è permesso aggiungere una nave del tipo specificato.
     *
     * @param ship La nave da verificare
     * @return true se è permesso aggiungere la nave, altrimenti false
     */
    private boolean isShipTypeAllowed(Ship ship) {
        long shipCount = ships.stream()
                .filter(s -> s.getShipType().equals(ship.getShipType()))
                .count();
        return ship.getShipType().getNumberOfAllowedShips() > shipCount;
    }

    /**
     * Conta il numero di navi di un certo tipo che il giocatore ha già posizionato.
     *
     * @param shipType Il tipo di nave di cui si desidera effettuare il conteggio
     * @return Il numero di navi del tipo specificato che sono state posizionate
     */
    @Override
    public long getShipTypeCount(ShipType shipType) {
        return ships.stream()
                .filter(s -> s.getShipType().equals(shipType))
                .count();
    }

    /**
     * Verifica se la nave si sovrappone a un'altra nave già posizionata sul campo di gioco.
     *
     * @param ship La nave da verificare
     * @return true se la nave si sovrappone a un'altra nave, altrimenti false
     */
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

    /**
     * Restituisce un array di numeri della nave a partire da un numero dato.
     *
     * @param number Il numero della nave
     * @return La lista dei numeri della nave, o null se non trovata
     */
    @Override
    public List<Integer> getShipArrayFromGivenNumber(int number) {
        for (Ship ship : ships) {
            if (ship.isNumberInShip(number)) {
                return ship.getShipNumbers();
            }
        }
        return null;
    }

    /**
     * Restituisce il numero totale delle navi del giocatore umano.
     *
     * @return Il numero totale delle navi
     */
    @Override
    public int getHumanPlayerShipCount() {
        return ships.size();
    }
}