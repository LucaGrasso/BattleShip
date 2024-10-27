/**
 * @author Luca Grasso
 * @matricola 294612
 * @progetto PMO
 * @data 27/05/2024
 */

package model.strategy;

import java.util.*;

/**
 * Classe che implementa la strategia di colpire navi in modo intelligente.
 */
public class IntelligentHitShipStrategy implements HitShipStrategy {

    private final List<Integer> hitPositionsList = new ArrayList<>();
    private Integer lastHit = null;
    private Integer hitDirection;
    private boolean isLastHitSuccessful = false;
    private boolean isShipSunk = false;
    private boolean firstHitShip = false;
    private String shipOrientation = null;
    private int rowDirection = -1;
    private int columnDirection = -1;
    private List<Integer> sequenceDigits = new ArrayList<>();
    private final List<Integer> hitShip = new ArrayList<>();

    /**
     * Imposta lo stato dell'ultimo colpo (se è stato un successo o meno).
     *
     * @param isLastHitSuccessful lo stato dell'ultimo colpo.
     */
    @Override
    public void setLastHitSuccessful(boolean isLastHitSuccessful) {
        this.isLastHitSuccessful = isLastHitSuccessful;
    }

    /**
     * Imposta lo stato della nave (affondata o meno).
     *
     * @param isShipSunk lo stato della nave.
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
     * Calcola la posizione del prossimo colpo.
     *
     * @return la posizione del prossimo colpo.
     */
    @Override
    /**
     * Calcola la posizione del prossimo colpo.
     *
     * @return la posizione del prossimo colpo.
     */
    public int getHitPositionShip() {
        int result = -1;

        // Se la nave è affondata, resettiamo la strategia
        if (isShipSunk) {
            hitShip.add(lastHit);
            addPointNearShipSunk();
            getReset();
        }

        // Ho colpito due volte la stessa nave
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
                if (shipOrientation.equals("horizontal")) {
                    hitShip.add(lastHit);
                    int[] tempSeq = convertListToArray(sequenceDigits);
                    int hitTemp = 0;
                    while (result == -1) {
                        hitTemp = getRandomAdjacent(tempSeq);
                        if (hitTemp == -1) { // Gestiamo il caso di nessun adiacente trovato
                            result = -1; // o qualche logica alternativa
                            break;
                        }
                        result = addHitPosition(hitTemp * 10 + rowDirection);
                    }
                    sequenceDigits.add(hitTemp);

                } else if (shipOrientation.equals("vertical")) {
                    hitShip.add(lastHit);
                    int[] tempSeq = convertListToArray(sequenceDigits);
                    int hitTemp = 0;
                    while (result == -1) {
                        hitTemp = getRandomAdjacent(tempSeq);
                        if (hitTemp == -1) { // Gestiamo il caso di nessun adiacente trovato
                            result = -1; // o qualche logica alternativa
                            break;
                        }
                        result = addHitPosition(columnDirection * 10 + hitTemp);
                    }
                    sequenceDigits.add(hitTemp);
                }
            }
        } else if (firstHitShip) {
            if (shipOrientation == null) {
                while (result == -1) {
                    int temp = getAroundLastHit(hitDirection);
                    result = addHitPosition(temp);
                }
            } else if (shipOrientation.equals("horizontal")) {
                int[] tempSeq = convertListToArray(sequenceDigits);
                int hitTemp = 0;
                while (result == -1) {
                    hitTemp = getRandomAdjacent(tempSeq);
                    if (hitTemp == -1) { // Gestiamo il caso di nessun adiacente trovato
                        result = -1; // o qualche logica alternativa
                        break;
                    }
                    result = addHitPosition(hitTemp * 10 + rowDirection);
                }
                sequenceDigits.add(hitTemp);

            } else if (shipOrientation.equals("vertical")) {
                int[] tempSeq = convertListToArray(sequenceDigits);
                int hitTemp = 0;
                while (result == -1) {
                    hitTemp = getRandomAdjacent(tempSeq);
                    if (hitTemp == -1) { // Gestiamo il caso di nessun adiacente trovato
                        result = -1; // o qualche logica alternativa
                        break;
                    }
                    result = addHitPosition(columnDirection * 10 + hitTemp);
                }
                sequenceDigits.add(hitTemp);
            }
        }

        if (lastHit != null && isLastHitSuccessful && result == -1) {
            // Ho colpito una nave per la prima volta
            if (!firstHitShip) firstHitShip = true;
            // Verificare se possiamo colpire in quella direzione
            this.hitDirection = lastHit;
            this.hitShip.add(hitDirection);
            while (result == -1) {
                // Generiamo un numero casuale (0-99)
                int temp = getAroundLastHit(lastHit);
                // Aggiungo alla lista di hit
                result = addHitPosition(temp);
            }
        }

        // Se non abbiamo una direzione o non abbiamo colpito una nave, generiamo una hit casuale
        while (result == -1) {
            int temp = (int) (Math.random() * 100);
            result = addHitPosition(temp);
        }
        return result;
    }

    /**
     * Aggiungi la posizione di hit alla lista di hit.
     *
     * @param hitPosition la posizione da aggiungere.
     * @return int la posizione di hit, o -1 se già presente.
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
     * Ottiene una posizione attorno all'ultima posizione di hit.
     *
     * @param lastHit l'ultima posizione di hit.
     * @return la nuova posizione attorno all'ultima posizione di hit.
     */
    private int getAroundLastHit(int lastHit) {
        Random random = new Random();
        String[] directions = {"up", "down", "right", "left"};
        int rowDigit;
        int columnDigit;

        int index = random.nextInt(directions.length);

        if (lastHit >= 0 && lastHit <= 9) {
            rowDigit = lastHit;

            // Border Left of Game
            switch (directions[index]) {
                case "up":
                    return (rowDigit == 0) ? --rowDigit : ++rowDigit;
                case "down":
                    return (rowDigit == 9) ? ++rowDigit : --rowDigit;
                case "right":
                case "left":
                    return 10 + rowDigit;
            }

        } else {
            rowDigit = Math.abs(lastHit % 10);   // 0-9
            columnDigit = Math.abs(lastHit / 10); // 0-9

            switch (directions[index]) {
                case "up":
                    if (rowDigit == 0) return ++rowDigit + columnDigit * 10;
                    return --rowDigit + columnDigit * 10;
                case "down":
                    if (rowDigit == 9) return --rowDigit + columnDigit * 10;
                    return ++rowDigit + columnDigit * 10;
                case "right":
                    if (columnDigit == 9) return rowDigit + --columnDigit * 10;
                    return rowDigit + --columnDigit * 10;
                case "left":
                    if (columnDigit == 0) return rowDigit;
                    return rowDigit + ++columnDigit * 10;
            }
        }
        return -1;
    }

    /**
     * Ottiene una posizione adiacente casuale alla sequenza fornita.
     *
     * @param sequence la sequenza di riferimento.
     * @return una posizione adiacente casuale.
     */
    private static int getRandomAdjacent(int[] sequence) {
        // Ordina l'array e trova il min e il max
        Arrays.sort(sequence);
        int min = sequence[0];
        int max = sequence[sequence.length - 1];

        // Crea una lista per contenere le potenziali opzioni
        List<Integer> options = new ArrayList<>();

        // Se c'è un numero prima del min, aggiungilo alle opzioni
        if (min > 0) {
            options.add(min - 1);
        }

        // Se c'è un numero dopo il max, aggiungilo alle opzioni
        if (max < 9) {
            options.add(max + 1);
        }

        // Se non ci sono opzioni disponibili, restituisce un valore "segnaposto" come -1
        if (options.isEmpty()) {
            return -1;
        }

        // Scegli un numero casuale dalle opzioni
        Random rand = new Random();
        int index = rand.nextInt(options.size());
        return options.get(index);
    }

    /**
     * Confronta le cifre della direzione del colpo con l'ultimo colpo.
     *
     * @param hitDirection la direzione del colpo.
     * @param lastHit l'ultimo colpo.
     * @return un array con le cifre che corrispondono.
     */
    private int[] compareDigits(int hitDirection, int lastHit) {
        // Inizializza con -1 per denotare "nessuna corrispondenza"
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
     * Ottiene la sequenza di cifre dalla direzione del colpo e dall'ultimo colpo.
     *
     * @param hitDirection la direzione del colpo.
     * @param lastHit l'ultimo colpo.
     * @return un array con la sequenza di cifre.
     */
    private int[] sequenceDigits(int hitDirection, int lastHit) {
        int[] result = {-1, -1};

        int firstDigitDirection = Math.abs(hitDirection / 10);
        int secondDigitDirection = Math.abs(hitDirection % 10);

        int firstDigitLastHit = Math.abs(lastHit / 10);
        int secondDigitLastHit = Math.abs(lastHit % 10);

        if (firstDigitDirection != firstDigitLastHit) {
            result[0] = firstDigitDirection;
            result[1] = firstDigitLastHit;
        }
        if (secondDigitDirection != secondDigitLastHit) {
            result[0] = secondDigitDirection;
            result[1] = secondDigitLastHit;
        }
        return result;
    }

    /**
     * Prova a colpire una nave in base alla direzione e all'ultimo colpo.
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
     * Aggiunge i punti vicini alla nave affondata.
     */
    private void addPointNearShipSunk() {
        for (int hitPosition : hitShip) {
            int rowDigit = Math.abs(hitPosition % 10);
            int columnDigit = Math.abs(hitPosition / 10);

            List<Integer> adjacentPoints = new ArrayList<>();
            int up = (rowDigit == 0) ? -1 : hitPosition - 1;
            int upRight = (rowDigit == 0 || columnDigit == 9) ? -1 : hitPosition - 1 + 10;
            int upLeft = (rowDigit == 0 || columnDigit == 0) ? -1 : hitPosition - 1 - 10;
            int down = (rowDigit == 9) ? -1 : hitPosition + 1;
            int downRight = (rowDigit == 9 || columnDigit == 9) ? -1 : hitPosition + 1 + 10;
            int downLeft = (rowDigit == 9 || columnDigit == 0) ? -1 : hitPosition + 1 - 10;
            int right = (columnDigit == 9) ? -1 : hitPosition + 10;
            int left = (columnDigit == 0) ? -1 : hitPosition - 10;

            Collections.addAll(adjacentPoints, up, upRight, upLeft, down, downRight, downLeft, right, left);

            for (int hole : adjacentPoints) {
                if (hole == -1) continue;
                if (!hitPositionsList.contains(hole)) {
                    hitPositionsList.add(hole);
                }
            }
        }
        hitShip.clear();
    }
}