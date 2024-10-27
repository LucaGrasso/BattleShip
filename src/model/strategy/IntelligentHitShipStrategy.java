package model.strategy;

import java.util.*;

/**
 * Classe che implementa la strategia di colpire navi in modo intelligente.
 * Gestisce colpi basati su logica di adiacenza e orientamento della nave.
 * Assicura che non vengano eseguiti colpi fuori dai limiti della griglia (0-99).
 */
public class IntelligentHitShipStrategy implements HitShipStrategy {

    /** Lista delle posizioni colpite */
    private final List<Integer> hitPositionsList = new ArrayList<>();
    /** Ultima posizione colpita con successo */
    private Integer lastHit = null;
    /** Direzione del colpo */
    private Integer hitDirection;
    /** Indica se l'ultimo colpo è stato un successo */
    private boolean isLastHitSuccessful = false;
    /** Indica se una nave è stata affondata */
    private boolean isShipSunk = false;
    /** Indica se è stato colpito una nave per la prima volta */
    private boolean firstHitShip = false;
    /** Orientamento della nave colpita (orizzontale o verticale) */
    private String shipOrientation = null;
    /** Direzione per righe, usata per colpi orizzontali */
    private int rowDirection = -1;
    /** Direzione per colonne, usata per colpi verticali */
    private int columnDirection = -1;
    /** Sequenza dei numeri delle posizioni colpite */
    private List<Integer> sequenceDigits = new ArrayList<>();
    /** Lista delle posizioni della nave colpita */
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
    public int getHitPositionShip() {
        int result = -1;

        if (isShipSunk) {
            hitShip.add(lastHit);
            addPointNearShipSunk();
            getReset();
        }

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
                    result = processHitsInDirection(rowDirection, true);
                } else if (shipOrientation.equals("vertical")) {
                    result = processHitsInDirection(columnDirection, false);
                }
            }
        } else if (firstHitShip) {
            if (shipOrientation == null) {
                while (result == -1) {
                    int temp = getAroundLastHit(hitDirection);
                    result = addHitPosition(temp);
                }
            } else if (shipOrientation.equals("horizontal")) {
                result = processHitsInDirection(rowDirection, true);
            } else if (shipOrientation.equals("vertical")) {
                result = processHitsInDirection(columnDirection, false);
            }
        }

        if (lastHit != null && isLastHitSuccessful && result == -1) {
            if (!firstHitShip) firstHitShip = true;
            this.hitDirection = lastHit;
            this.hitShip.add(hitDirection);
            while (result == -1) {
                int temp = getAroundLastHit(lastHit);
                result = addHitPosition(temp);
            }
        }

        while (result == -1) {
            int temp = (int) (Math.random() * 100);
            result = addHitPosition(temp);
        }
        return result;
    }

    /**
     * Aggiunge la posizione colpita alla lista, se non è già presente e se è valida.
     *
     * @param hitPosition la posizione da aggiungere.
     * @return la posizione del colpo, o -1 se già presente o non valida.
     */
    private int addHitPosition(int hitPosition) {
        if (hitPosition >= 0 && hitPosition < 100 && !hitPositionsList.contains(hitPosition)) {
            hitPositionsList.add(hitPosition);
            lastHit = hitPosition;
            return hitPosition;
        }
        return -1;
    }

    /**
     * Ottiene una posizione casuale attorno all'ultima posizione colpita, rispettando i limiti della griglia.
     *
     * @param lastHit l'ultima posizione colpita.
     * @return la nuova posizione attorno all'ultima posizione di hit.
     */
    private int getAroundLastHit(int lastHit) {
        Random random = new Random();
        String[] directions = {"up", "down", "right", "left"};
        int rowDigit = lastHit % 10;
        int columnDigit = lastHit / 10;
        int index = random.nextInt(directions.length);

        switch (directions[index]) {
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
        return -1;
    }

    /**
     * Ottiene una posizione adiacente casuale alla sequenza fornita, rispettando i limiti della griglia.
     *
     * @param sequence la sequenza di riferimento.
     * @return una posizione adiacente casuale o -1 se non disponibile.
     */
    private static int getRandomAdjacent(int[] sequence) {
        Arrays.sort(sequence);
        int min = sequence[0];
        int max = sequence[sequence.length - 1];

        List<Integer> options = new ArrayList<>();

        if (min > 0) {
            options.add(min - 1);
        }
        if (max < 9) {
            options.add(max + 1);
        }

        if (options.isEmpty()) {
            return -1;
        }

        Random rand = new Random();
        return options.get(rand.nextInt(options.size()));
    }

    /**
     * Confronta le cifre della direzione del colpo con quelle dell'ultimo colpo.
     *
     * @param hitDirection la direzione del colpo.
     * @param lastHit l'ultimo colpo.
     * @return un array con le cifre che corrispondono.
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
     * Tenta di colpire una nave in base alla direzione e all'ultimo colpo.
     *
     * @param hitDirection la direzione del colpo.
     * @param lastHit l'ultimo colpo.
     * @return la posizione del colpo.
     */
    private int tryHitShip(int hitDirection, int lastHit) {
        int result = -1;
        hitShip.add(lastHit);
        // Usiamo compareDigits per ottenere l'orientamento corretto
        int[] digitsForDirection = compareDigits(hitDirection, lastHit);
        sequenceDigits.add(digitsForDirection[0]);
        sequenceDigits.add(digitsForDirection[1]);

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
     * Esegue i colpi in base alla direzione (orizzontale o verticale) della nave.
     *
     * @param direction la direzione del colpo (riga o colonna).
     * @param isHorizontal se vero, il colpo è orizzontale, altrimenti è verticale.
     * @return la posizione del colpo, o -1 se non valida.
     */
    private int processHitsInDirection(int direction, boolean isHorizontal) {
        int result = -1;
        hitShip.add(lastHit);
        int[] tempSeq = convertListToArray(sequenceDigits);
        int hitTemp = 0;
        while (result == -1) {
            hitTemp = getRandomAdjacent(tempSeq);
            if (hitTemp == -1) break;
            if (isHorizontal) {
                result = addHitPosition(hitTemp * 10 + direction);
            } else {
                result = addHitPosition(direction * 10 + hitTemp);
            }
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
     * Aggiunge i punti vicini alla nave affondata, evitando quelli fuori dai limiti.
     */
    private void addPointNearShipSunk() {
        for (int hitPosition : hitShip) {
            int rowDigit = hitPosition % 10;
            int columnDigit = hitPosition / 10;

            List<Integer> adjacentPoints = new ArrayList<>();
            int up = (rowDigit > 0) ? hitPosition - 1 : -1;
            int down = (rowDigit < 9) ? hitPosition + 1 : -1;
            int right = (columnDigit < 9) ? hitPosition + 10 : -1;
            int left = (columnDigit > 0) ? hitPosition - 10 : -1;

            addIfValid(adjacentPoints, up);
            addIfValid(adjacentPoints, down);
            addIfValid(adjacentPoints, right);
            addIfValid(adjacentPoints, left);

            for (int point : adjacentPoints) {
                if (!hitPositionsList.contains(point)) {
                    hitPositionsList.add(point);
                }
            }
        }
        hitShip.clear();
    }

    /**
     * Aggiunge un punto alla lista se è valido.
     *
     * @param points la lista dei punti.
     * @param point il punto da aggiungere.
     */
    private void addIfValid(List<Integer> points, int point) {
        if (point >= 0 && point < 100) {
            points.add(point);
        }
    }
}
