import static org.junit.Assert.*;

import model.Direction;
import org.junit.Before;
import org.junit.Test;
import model.BattleShipGameModel;
import model.ShipType;

import java.util.List;

public class GameControllerTest {

    private BattleShipGameModel model;

    /**
     * Metodo di setup eseguito prima di ogni test.
     * Inizializza il modello del gioco e imposta una nave per il giocatore umano
     * vicino al bordo della griglia.
     */
    @Before
    public void setUp() {
        // Inizializza il modello di gioco con un nome fittizio per il giocatore umano.
        model = new BattleShipGameModel("PlayerTest");

        // Configura una nave per il giocatore umano.
        // In questo esempio, aggiungiamo una nave piccola (PATROL_SHIP) vicino al bordo della griglia,
        // orientata orizzontalmente a partire dalla posizione 1.
        model.addShipToHumanPlayer(ShipType.PATROL_SHIP, Direction.HORIZONTAL, 1);
    }

    /**
     * Test che verifica se il computer riesce a colpire una nave del giocatore umano.
     */
    @Test
    public void testComputerHitsShip() {
        // Genera le navi del computer. Questo metodo posiziona automaticamente le navi
        // per il giocatore computer.
        model.computerGenerateShips();

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

        // Verifica se il colpo del computer è un colpo valido, ovvero se ha colpito
        // una delle navi del giocatore umano.
        if (humanPlayerShipNumbers.contains(shot)) {
            // Se il colpo è valido (cioè colpisce una nave), aggiungi il numero del colpo
            // alla nave corrispondente e verifica che l'operazione sia stata registrata correttamente.
            boolean hitSuccessful = model.addHitNumberToHumanPlayerShip(shot);
            assertTrue("Il computer ha colpito una nave con successo", hitSuccessful);
        } else {
            // Se il colpo non colpisce alcuna nave, fallisce il test.
            fail("Il computer non ha colpito alcuna nave");
        }
    }

    /**
     * Test che verifica se il computer non colpisce una nave del giocatore umano (colpo a vuoto).
     */
    @Test
    public void testComputerMissesShip() {
        // Simula un colpo del computer richiamando la funzione che genera un tiro.
        int shot = -1;
        List<Integer> humanPlayerShipNumbers = model.getAllHumanPlayerShipNumbers();

        // Genera tentativi fino a trovare un colpo che non colpisce una nave
        for (int i = 0; i < 100; i++) {
            shot = model.getComputerShot();
            System.out.println("Tentativo di colpo del computer: " + shot);
            if (!humanPlayerShipNumbers.contains(shot)) {
                break;
            }
        }

        // Verifica che il colpo non colpisca alcuna nave. Se la lista delle navi del giocatore
        // non contiene il numero di posizione del colpo, significa che è un colpo a vuoto.
        if (!humanPlayerShipNumbers.contains(shot)) {
            // Il colpo non ha colpito nessuna nave. Verifica che l'operazione di registrazione
            // del colpo restituisca 'false', indicando che non c'è stato alcun colpo a segno.
            boolean hitSuccessful = model.addHitNumberToHumanPlayerShip(shot);
            assertFalse("Il computer non ha colpito alcuna nave", hitSuccessful);
        } else {
            // Se il colpo ha colpito una nave (anche se non dovrebbe), fallisce il test.
            fail("Il computer ha colpito una nave, ma non doveva");
        }
    }

    /**
     * Test che verifica l'efficacia dei colpi vicino al bordo della griglia.
     */
    @Test
    public void testHitsNearBorder() {
        // Assumiamo che la griglia sia di dimensioni 10x10 (100 posizioni, 0-99).

        // Forziamo il posizionamento di una nave vicino al bordo inferiore.
        model.addShipToHumanPlayer(ShipType.AIRCRAFT_CARRIER, Direction.VERTICAL, 95); // Posto 95 e 96

        // Simula colpi vicino al bordo e verifica che siano gestiti correttamente.
        int[] testShots = {94, 95, 96, 97};

        for (int shot : testShots) {
            System.out.println("Computer tenta il colpo alla posizione: " + shot);
            boolean hitSuccessful = model.addHitNumberToHumanPlayerShip(shot);
            if ((shot == 95 || shot == 96)) {
                assertTrue("Il colpo del computer dovrebbe aver colpito la nave alla posizione " + shot, hitSuccessful);
            } else {
                assertFalse("Il colpo del computer non dovrebbe aver colpito alcuna nave alla posizione " + shot, hitSuccessful);
            }
        }
    }

    /**
     * Test che verifica se il computer riesce ad affondare tutte le navi
     * del giocatore umano con colpi casuali.
     */
    @Test
    public void testComputerSinksAllShips() {
        model.computerGenerateShips();

        // Ottieni la lista delle posizioni occupate dalle navi del giocatore umano.
        List<Integer> humanPlayerShipNumbers = model.getAllHumanPlayerShipNumbers();

        // Esegui tentativi di colpo fino a quando tutte le navi non sono affondate
        // (o fino a raggiungere un numero di tentativi troppo alto).
        for (int i = 0; i < 1000; i++) {  // Aumenta il numero massimo di tentativi
            int shot = model.getComputerShot();
            System.out.println("Tentativo di colpo del computer: " + shot);
            if (humanPlayerShipNumbers.contains(shot)) {
                model.addHitNumberToHumanPlayerShip(shot);
                humanPlayerShipNumbers.remove((Integer) shot);
            }

            // Se non rimangono piu' posizioni di navi, tutte le navi sono affondate
            if (humanPlayerShipNumbers.isEmpty()) {
                break;
            }
        }

        // Alla fine di tutti i tentativi, tutte le navi dovrebbero essere affondate.
        assertTrue("Il computer dovrebbe aver affondato tutte le navi del giocatore umano", humanPlayerShipNumbers.isEmpty());
    }
}