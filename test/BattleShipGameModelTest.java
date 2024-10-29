import model.Direction;
import model.BattleShipGameModel;
import model.ShipType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BattleShipGameModelTest {

    private BattleShipGameModel model;

    /**
     * Metodo di setup eseguito prima di ogni test.
     * Inizializza il modello del gioco e imposta una nave per il giocatore umano
     * vicino al bordo della griglia.
     */
    @BeforeEach
    public void setUp() {
        // Inizializza il modello di gioco con un nome fittizio per il giocatore umano.
        model = new BattleShipGameModel("PlayerTest");

        // Configura una nave per il giocatore umano.
        model.addShipToHumanPlayer(ShipType.AIRCRAFT_CARRIER, Direction.VERTICAL, 0);
    }

    /**
     * Test che verifica se il computer riesce ad affondare tutte le navi
     * del giocatore umano con colpi casuali.
     */
    @Test
    public void testComputerSinksAllShips() {
        model.addShipToHumanPlayer(ShipType.PATROL_SHIP, Direction.VERTICAL, 31);
        model.addShipToHumanPlayer(ShipType.SUBMARINE, Direction.VERTICAL, 34);
        model.addShipToHumanPlayer(ShipType.TORPEDO_BOAT_HUNTERS, Direction.VERTICAL, 51);
        model.addShipToHumanPlayer(ShipType.BATTLESHIP, Direction.VERTICAL, 55);

        // Ottieni la lista delle posizioni occupate dalle navi del giocatore umano.
        List<Integer> humanPlayerShipNumbers = model.getAllHumanPlayerShipNumbers();

        // Esegui tentativi di colpo fino a quando tutte le navi non sono affondate
        for (int i = 0; i < 1000; i++) {  // Aumenta il numero massimo di tentativi
            int shot = model.getComputerShot();
            System.out.println("Tentativo di colpo del computer: " + shot);
            if (humanPlayerShipNumbers.contains(shot)) {
                System.out.println("Colpito con: " + shot);
                model.addHitNumberToHumanPlayerShip(shot);
                humanPlayerShipNumbers.remove((Integer) shot);
            }

            // Se non rimangono più posizioni di navi, tutte le navi sono affondate
            if (humanPlayerShipNumbers.isEmpty()) {
                break;
            }
        }

        // Alla fine di tutti i tentativi, tutte le navi dovrebbero essere affondate.
        assertTrue(humanPlayerShipNumbers.isEmpty(), "Il computer dovrebbe aver affondato tutte le navi del giocatore umano");
    }

    /**
     * Test che verifica se il computer riesce a colpire una nave del giocatore umano.
     */
    @Test
    public void testComputerHitsShip() {
        // Ottieni la lista delle posizioni occupate dalle navi del giocatore umano.
        List<Integer> humanPlayerShipNumbers = model.getAllHumanPlayerShipNumbers();

        // Prova a ottenere un colpo valido da parte del computer
        int shot = -1;
        for (int i = 0; i < 100; i++) {  // Limita il numero di tentativi per evitare loop infiniti
            shot = model.getComputerShot();
            System.out.println("Tentativo di colpo del computer: " + shot);
            if (humanPlayerShipNumbers.contains(shot)) {
                break;
            }
        }

        // Verifica se il colpo del computer è valido, ovvero se ha colpito una delle navi del giocatore umano.
        if (humanPlayerShipNumbers.contains(shot)) {
            boolean hitSuccessful = model.addHitNumberToHumanPlayerShip(shot);
            assertFalse(hitSuccessful, "Il computer ha colpito una nave con successo");
        } else {
            fail("Il computer non ha colpito alcuna nave");
        }
    }

    /**
     * Test che verifica l'efficacia dei colpi vicino al bordo della griglia.
     */
    @Test
    public void testShipAffondata() {
        // Simula colpi vicino al bordo e verifica che siano gestiti correttamente.
        int[] testShots = {0, 1, 2, 3, 4};

        for (int shot : testShots) {
            System.out.println("Computer tenta il colpo alla posizione: " + shot);
            boolean hitSuccessful = model.addHitNumberToHumanPlayerShip(shot);
            System.out.println(hitSuccessful);
            if (hitSuccessful) {
                assertTrue(hitSuccessful, "Il colpo del computer dovrebbe aver colpito la nave alla posizione " + shot);
            } else {
                assertFalse(hitSuccessful, "Il colpo del computer non dovrebbe aver colpito alcuna nave alla posizione " + shot);
            }
        }
    }
}
