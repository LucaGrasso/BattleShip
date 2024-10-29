/**
 * @Author Luca Grasso
 * @Matricola 294612
 * @Progetto PMO
 * @Data 27/05/2024
 */

package model.strategy;

import java.util.*;

/**
 * Implementa una strategia intelligente per colpire le navi nel gioco. Gestisce colpi basati
 * sulla logica di adiacenza e orientamento della nave. Si occupa di affondare le navi
 * cercando colpi efficaci attorno ai precedenti colpi andati a segno.
 */
public class IntelligentHitShipStrategy implements HitShipStrategy {

    /** Lista delle posizioni colpite */
    private final List<Integer> hitPositionsList = new ArrayList<>();
    /** Ultima posizione colpita con successo */
    private Integer lastHit = null;
    /** Direzione attuale del colpo */
    private Integer hitDirection;
    /** Indica se l'ultimo colpo è stato un successo */
    private boolean isLastHitSuccessful = false;
    /** Indica se la nave è stata affondata */
    private boolean isShipSunk = false;
    /** Indica se è stata colpita una nave per la prima volta */
    private boolean firstHitShip = false;
    /** Orientamento della nave colpita (orizzontale o verticale) */
    private String shipOrientation = null;
    /** Direzione delle righe per i colpi orizzontali */
    private int rowDirection = -1;
    /** Direzione delle colonne per i colpi verticali */
    private int columnDirection = -1;
    /** Lista dei numeri colpiti sequenzialmente */
    private List<Integer> sequenceDigits = new ArrayList<>();
    /** Lista delle posizioni di una nave colpita */
    private final List<Integer> hitShip = new ArrayList<>();

    /**
     * Imposta lo stato dell'ultimo colpo (se è stato un successo o meno).
     *
     * @param isLastHitSuccessful indica se l'ultimo colpo è andato a segno.
     */
    @Override
    public void setLastHitSuccessful(boolean isLastHitSuccessful) {
        this.isLastHitSuccessful = isLastHitSuccessful;
    }

    /**
     * Imposta lo stato della nave (affondata o meno).
     *
     * @param isShipSunk indica se la nave è stata affondata.
     */
    @Override
    public void setIsShipSunk(boolean isShipSunk) {
        this.isShipSunk = isShipSunk;
    }

    /**
     * Reimposta la strategia, azzerando tutte le variabili relative allo stato attuale.
     */
    public void getReset() {
        hitDirection = 0;
        isLastHitSuccessful = false;
        isShipSunk = false;
        firstHitShip = false;
        shipOrientation = null;
        rowDirection = -1;
        columnDirection = -1;
        sequenceDigits = new ArrayList<>();
    }

    /**
     * Calcola la posizione del prossimo colpo basandosi sullo stato corrente.
     * Cerca di continuare a colpire nelle vicinanze di una nave già colpita.
     *
     * @return la posizione del prossimo colpo.
     */
    @Override
    public int getHitPositionShip() {
        int result = -1;

        // Se la nave è affondata, resettiamo la strategia
        if (isShipSunk) {
            hitShip.add(lastHit);
            addPointNearShipSunk();
            getReset();
        }

        // Se abbiamo colpito la stessa nave più volte, cerchiamo di identificarne l'orientamento
        if (firstHitShip && isLastHitSuccessful) {
            if (shipOrientation == null) {
                int[] digitsForDirection = compareDigits(hitDirection, lastHit);
                if (digitsForDirection[0] != -1) {
                    shipOrientation = "vertical";
                    columnDirection = digitsForDirection[0];
                    result = tryHitShip(hitDirection, lastHit);
                }
                if (digitsForDirection[1] != -1) {
                    shipOrientation = "horizontal";
                    rowDirection = digitsForDirection[1];
                    result = tryHitShip(hitDirection, lastHit);
                }
            } else {
                result = hitShipBasedOnOrientation(result);
            }
        } else if (firstHitShip) {
            result = hitShipWithUnconfirmedOrientation(result);
        }

        if (lastHit != null && isLastHitSuccessful && result == -1) {
            result = exploreNewDirections(result);
        }

        // Se nessuna nave è stata colpita, generiamo un colpo casuale
        while (result == -1) {
            int temp = (int) (Math.random() * 100);
            result = addHitPosition(temp);
        }
        return result;
    }

    /**
     * Aggiunge la posizione colpita alla lista di colpi, se non è già presente.
     *
     * @param hitPosition la posizione del colpo.
     * @return la posizione del colpo, oppure -1 se già presente.
     */
    private int addHitPosition(int hitPosition) {
        if (!hitPositionsList.contains(hitPosition)) {
            hitPositionsList.add(hitPosition);
            lastHit = hitPosition;
            return hitPosition;
        }
        return -1;
    }

    /**
     * Cerca una posizione attorno all'ultimo colpo.
     *
     * @param lastHit l'ultima posizione colpita.
     * @return la nuova posizione da colpire.
     */
    private int getAroundLastHit(int lastHit) {
        Random random = new Random();
        List<String> directions = Arrays.asList("up", "down", "right", "left");
        Collections.shuffle(directions);  // Mischia per evitare di colpire sempre in una direzione predefinita
        int rowDigit = Math.abs(lastHit % 10);
        int columnDigit = Math.abs(lastHit / 10);

        for (String direction : directions) {
            switch (direction) {
                case "up":
                    if (rowDigit > 0) return lastHit - 1;
                    break;
                case "down":
                    if (rowDigit < 9) return lastHit + 1;
                    break;
                case "right":
                    if (columnDigit < 9) return lastHit + 10;
                    break;
                case "left":
                    if (columnDigit > 0) return lastHit - 10;
                    break;
            }
        }
        return -1;  // Se nessuna direzione è valida
    }

    /**
     * Cerca una posizione adiacente casuale alla sequenza di colpi fornita.
     *
     * @param sequence la sequenza di riferimento.
     * @return una posizione adiacente casuale.
     */
    private static int getRandomAdjacent(int[] sequence) {
        Arrays.sort(sequence);
        int min = sequence[0];
        int max = sequence[sequence.length - 1];

        List<Integer> options = new ArrayList<>();
        if (min > 0) options.add(min - 1);
        if (max < 9) options.add(max + 1);

        if (options.isEmpty()) throw new RuntimeException("No adjacent numbers available.");

        Random rand = new Random();
        return options.get(rand.nextInt(options.size()));
    }

    /**
     * Confronta le cifre della direzione del colpo con quelle dell'ultimo colpo.
     *
     * @param hitDirection la direzione del colpo.
     * @param lastHit l'ultimo colpo.
     * @return un array contenente le cifre corrispondenti.
     */
    private int[] compareDigits(int hitDirection, int lastHit) {
        int[] result = {-1, -1};

        int firstDigitDirection = Math.abs(hitDirection / 10);
        int secondDigitDirection = Math.abs(hitDirection % 10);
        int firstDigitLastHit = Math.abs(lastHit / 10);
        int secondDigitLastHit = Math.abs(lastHit % 10);

        if (firstDigitDirection == firstDigitLastHit) {
            result[0] = firstDigitDirection;
        } else if (secondDigitDirection == secondDigitLastHit) {
            result[1] = secondDigitDirection;
        }
        return result;
    }

    /**
     * Cerca di colpire una nave in base alla direzione e all'ultimo colpo.
     *
     * @param hitDirection la direzione del colpo.
     * @param lastHit l'ultimo colpo.
     * @return la posizione del colpo.
     */
    private int tryHitShip(int hitDirection, int lastHit) {
        int result = -1;
        hitShip.add(lastHit);
        int[] sequenceDigitsResult = sequenceDigits(hitDirection, lastHit);
        sequenceDigits.add(sequenceDigitsResult[0]);
        sequenceDigits.add(sequenceDigitsResult[1]);
        int hitTemp = 0;
        while (result == -1) {
            hitTemp = getRandomAdjacent(sequenceDigits.stream().mapToInt(i -> i).toArray());
            if (Objects.equals(shipOrientation, "vertical"))
                result = addHitPosition(columnDirection * 10 + hitTemp);
            if (Objects.equals(shipOrientation, "horizontal"))
                result = addHitPosition(hitTemp * 10 + rowDirection);
        }
        sequenceDigits.add(hitTemp);
        return result;
    }

    /**
     * Converte una lista di interi in un array.
     *
     * @param list la lista da convertire.
     * @return l'array risultante.
     */
    private int[] convertListToArray(List<Integer> list) {
        int[] numArray = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            numArray[i] = list.get(i);
        }
        return numArray;
    }

    /**
     * Aggiunge le posizioni adiacenti a una nave affondata, evitando di colpire fuori dai limiti.
     */
    private void addPointNearShipSunk() {
        for (int hitPosition : hitShip) {
            int rowDigit = Math.abs(hitPosition % 10);
            int columnDigit = Math.abs(hitPosition / 10);

            List<Integer> adjacentPoints = new ArrayList<>();
            adjacentPoints.add((rowDigit == 0) ? -1 : hitPosition - 1);
            adjacentPoints.add((rowDigit == 0 || columnDigit == 9) ? -1 : hitPosition - 1 + 10);
            adjacentPoints.add((rowDigit == 0 || columnDigit == 0) ? -1 : hitPosition - 1 - 10);
            adjacentPoints.add((rowDigit == 9) ? -1 : hitPosition + 1);
            adjacentPoints.add((rowDigit == 9 || columnDigit == 9) ? -1 : hitPosition + 1 + 10);
            adjacentPoints.add((rowDigit == 9 || columnDigit == 0) ? -1 : hitPosition + 1 - 10);
            adjacentPoints.add((columnDigit == 9) ? -1 : hitPosition + 10);
            adjacentPoints.add((columnDigit == 0) ? -1 : hitPosition - 10);

            for (int hole : adjacentPoints) {
                if (hole != -1 && !hitPositionsList.contains(hole)) {
                    hitPositionsList.add(hole);
                }
            }
        }
        hitShip.clear();
    }
}
