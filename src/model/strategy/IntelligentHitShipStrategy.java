package model.strategy;

import java.util.*;

/**
 * Strategia per colpire una nave in maniera intelligente.
 * Tiene traccia delle posizioni colpite e utilizza un algoritmo
 * per determinare il prossimo colpo in base ai colpi precedenti.
 */
public class IntelligentHitShipStrategy implements HitShipStrategy {
    // Lista delle posizioni colpite
    private final List<Integer> hitPositionsList = new ArrayList<>();
    private Integer lastHit = null; // Ultima posizione colpita
    private Integer hitDirection; // Direzione del colpo
    private boolean isLastHitSuccessful = false; // Stato del colpo precedente
    private boolean isShipSunk = false; // Stato della nave (affondata o meno)
    private boolean firstHitShip = false; // Flag per indicare se si è colpita una nave per la prima volta
    private String shipOrientation = null; // Orientamento della nave (verticale o orizzontale)
    private int rowDirection = -1; // Direzione della riga
    private int columnDirection = -1; // Direzione della colonna
    private List<Integer> sequenceDigits = new ArrayList<>(); // Sequenza di cifre colpite
    private final List<Integer> hitShip = new ArrayList<>(); // Posizioni della nave colpite

    @Override
    public void setLastHitSuccessful(boolean isLastHitSuccessful) {
        this.isLastHitSuccessful = isLastHitSuccessful;
    }

    @Override
    public void setIsShipSunk(boolean isShipSunk) {
        this.isShipSunk = isShipSunk;
    }

    /**
     * Resetta lo stato interno della strategia dopo aver affondato una nave.
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
     * Determina la prossima posizione da colpire basandosi sugli ultimi colpi.
     *
     * @return La posizione del prossimo colpo
     */
    @Override
    public int getHitPositionShip() {
        int result = -1;

        // Se la nave è affondata, resetta la strategia e prepara i colpi successivi
        if (isShipSunk) {
            hitShip.add(lastHit);
            addPointNearShipSunk();
            getReset();
        }

        // Se la nave è stata colpita più volte
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
                        result = addHitPosition(hitTemp * 10 + rowDirection);
                    }
                    sequenceDigits.add(hitTemp);

                } else if (shipOrientation.equals("vertical")) {
                    hitShip.add(lastHit);
                    int[] tempSeq = convertListToArray(sequenceDigits);
                    int hitTemp = 0;
                    while (result == -1) {
                        hitTemp = getRandomAdjacent(tempSeq);
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
                    result = addHitPosition(hitTemp * 10 + rowDirection);
                }
                sequenceDigits.add(hitTemp);

            } else if (shipOrientation.equals("vertical")) {
                int[] tempSeq = convertListToArray(sequenceDigits);
                int hitTemp = 0;
                while (result == -1) {
                    hitTemp = getRandomAdjacent(tempSeq);
                    result = addHitPosition(columnDirection * 10 + hitTemp);
                }
                sequenceDigits.add(hitTemp);
            }

        }

        // Primo colpo riuscito
        if (lastHit != null && isLastHitSuccessful && result == -1) {
            if (!firstHitShip) firstHitShip = true;
            this.hitDirection = lastHit;
            this.hitShip.add(hitDirection);
            while (result == -1) {
                int temp = getAroundLastHit(lastHit);
                result = addHitPosition(temp);
            }
        }

        // Se non si ha una direzione o non si è colpita una nave, si genera un colpo casuale
        while (result == -1) {
            int temp = (int) (Math.random() * 100);
            result = addHitPosition(temp);
        }
        return result;
    }

    /**
     * Aggiunge la posizione di un colpo alla lista se non è già presente.
     *
     * @param hitPosition Posizione del colpo da aggiungere
     * @return La posizione del colpo aggiunta, o -1 se la posizione era già presente
     */
    private int addHitPosition(int hitPosition) {
        if (!(hitPositionsList.contains(hitPosition))) {
            hitPositionsList.add(hitPosition);
            lastHit = hitPosition;
            return hitPosition;
        }
        return -1;
    }

    /**
     * Determina una posizione attorno all'ultimo colpo.
     *
     * @param lastHit Ultima posizione colpita
     * @return La nuova posizione da colpire
     */
    private int getAroundLastHit(int lastHit) {
        Random random = new Random();
        List<String> directions = Arrays.asList("up", "down", "right", "left");
        Collections.shuffle(directions);  // Mescola le direzioni per evitare bias
        int rowDigit = Math.abs(lastHit % 10);
        int columnDigit = Math.abs(lastHit / 10);

        for (String direction : directions) {
            switch (direction) {
                case "up":
                    if (rowDigit > 0) {
                        return lastHit - 1;
                    }
                    break;
                case "down":
                    if (rowDigit < 9) {
                        return lastHit + 1;
                    }
                    break;
                case "right":
                    if (columnDigit < 9) {
                        return lastHit + 10;
                    }
                    break;
                case "left":
                    if (columnDigit > 0) {
                        return lastHit - 10;
                    }
                    break;
            }
        }

        return -1;  // Ritorna di default se nessuna direzione è valida
    }

    /**
     * Restituisce un numero adiacente casuale nella sequenza fornita.
     *
     * @param sequence Sequenza di numeri da cui scegliere
     * @return Numero adiacente casuale
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
            throw new RuntimeException("Nessun numero adiacente disponibile.");
        }

        Random rand = new Random();
        int index = rand.nextInt(options.size());
        return options.get(index);
    }

    /**
     * Confronta le cifre della direzione del colpo con l'ultima posizione colpita.
     *
     * @param hitDirection Direzione del colpo
     * @param lastHit Ultima posizione colpita
     * @return Un array contenente le cifre che corrispondono
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
     * Restituisce la sequenza di cifre da confrontare per determinare la prossima posizione.
     *
     * @param hitDirection Direzione del colpo
     * @param lastHit Ultima posizione colpita
     * @return Un array con le cifre della sequenza
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
     * Prova a colpire la nave in base alla direzione e all'ultima posizione colpita.
     *
     * @param hitDirection Direzione del colpo
     * @param lastHit Ultima posizione colpita
     * @return La posizione del colpo, o -1 se non valida
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
            if (Objects.equals(shipOrientation, "vertical")) result = addHitPosition(columnDirection * 10 + hitTemp);
            if (Objects.equals(shipOrientation, "horizontal")) result = addHitPosition(hitTemp * 10 + rowDirection);
        }
        sequenceDigits.add(hitTemp);
        return result;
    }

    /**
     * Converte una lista di numeri in un array di interi.
     *
     * @param list Lista di numeri
     * @return Un array di interi
     */
    private int[] convertListToArray(List<Integer> list) {
        int[] numArray = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            numArray[i] = list.get(i);
        }
        return numArray;
    }

    /**
     * Aggiunge punti vicini alla nave affondata alla lista di posizioni colpite.
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
            adjacentPoints.add(up);
            adjacentPoints.add(upRight);
            adjacentPoints.add(upLeft);
            adjacentPoints.add(down);
            adjacentPoints.add(downRight);
            adjacentPoints.add(downLeft);
            adjacentPoints.add(right);
            adjacentPoints.add(left);

            for (int hole : adjacentPoints) {
                if (hole == -1) continue;
                if (!(hitPositionsList.contains(hole))) {
                    hitPositionsList.add(hole);
                }
            }
        }
        hitShip.clear();
    }
}
