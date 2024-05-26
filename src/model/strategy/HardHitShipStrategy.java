/**
 * @Author Luca Grasso
 * @Matricola 294612
 * @Progetto PMO
 * @Data 04/05/2024
 */

package model.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class HardHitShipStrategy implements HitShipStrategy {
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

    @Override
    public void setLastHitSuccessful(boolean isLastHitSuccessful) {
        this.isLastHitSuccessful = isLastHitSuccessful;
    }

    @Override
    public void setIsShipSunk(boolean isShipSunk) {
        this.isShipSunk = isShipSunk;
    }

    @Override
    public int getHitPositionShip() {
        int result = -1;


        // Se la nave è affondata, resettiamo la strategia
        if (isShipSunk) {
            hitDirection = 0;
            isLastHitSuccessful = false;
            isShipSunk = false;
            firstHitShip = false;
            shipOrientation = null;
            rowDirection = -1;
            columnDirection = -1;
            sequenceDigits = null;
        }


        // Ho colpito due volte la stessa nave
        if (firstHitShip && isLastHitSuccessful) {
            if (shipOrientation == null) {
                int[] digitsForDirection = compareDigits(hitDirection, lastHit);
                if (digitsForDirection[0] != -1) {
                    shipOrientation = "vertical";
                    columnDirection = digitsForDirection[0];
                    int[] temp = sequenceDigits(hitDirection, lastHit);
                    sequenceDigits.add(temp[0]);
                    sequenceDigits.add(temp[1]);
                    int hitTemp = 0;
                    while (result == -1) {
                        hitTemp = getRandomAdjacent(sequenceDigits.stream().mapToInt(i -> i).toArray());
                        result = addHitPosition(columnDirection * 10 + hitTemp);
                    }
                    sequenceDigits.add(hitTemp);
                }
                if (digitsForDirection[1] != -1) {
                    shipOrientation = "horizontal";
                    rowDirection = digitsForDirection[1];
                    int[] temp = sequenceDigits(hitDirection, lastHit);
                    sequenceDigits.add(temp[0]);
                    sequenceDigits.add(temp[1]);
                    int hitTemp = 0;
                    while (result == -1) {
                        hitTemp = getRandomAdjacent(sequenceDigits.stream().mapToInt(i -> i).toArray());
                        result = addHitPosition( hitTemp * 10 + rowDirection);
                    }
                    sequenceDigits.add(hitTemp);
                }
            } else {
                if (shipOrientation.equals("horizontal")) {
                    int[] tempSeq = new int[sequenceDigits.size()];
                    for (int i = 0; i < sequenceDigits.size(); i++) {
                        tempSeq[i] = sequenceDigits.get(i);
                    }
                    int temp = getRandomAdjacent(tempSeq);
                    result = temp * 10 + rowDirection;
                    addHitPosition(result);
                } else if (shipOrientation.equals("vertical")) {
                    int[] tempSeq = new int[sequenceDigits.size()];
                    for (int i = 0; i < sequenceDigits.size(); i++) {
                        tempSeq[i] = sequenceDigits.get(i);
                    }
                    int temp = getRandomAdjacent(tempSeq);
                    result = columnDirection * 10 + temp;
                    addHitPosition(result);
                }
            }
        } else if (firstHitShip) {
            while (result == -1) {
                int temp = getAroundLastHit(hitDirection);
                result = addHitPosition(temp);
            }
        }


        if (lastHit != null && isLastHitSuccessful && result == -1) {
            // Ho colpito una nave per la prima volta
            if (!firstHitShip) firstHitShip = true;
            // verificare se possiamo colpire in quella direzione
            this.hitDirection = lastHit;
            while (result == -1) {
                // Generiamo un numero casuale (0-99
                int temp = getAroundLastHit(lastHit);
                // Aggiungo alla lista di hit
                result = addHitPosition(temp);
            }
        }

        // se non abbiamo una direzione o non abbiamo colpito una nave, generiamo una hit casuale
        while (result == -1) {
            int temp = (int) (Math.random() * 100);
            result = addHitPosition(temp);
        }
        return result;
    }

    /**
     * Aggiungi la posizione di hit alla lista di hit
     *
     * @param hitPosition
     * @return int hitPosition
     */
    private int addHitPosition(int hitPosition) {
        if (!(hitPositionsList.contains(hitPosition))) {
            hitPositionsList.add(hitPosition);
            lastHit = hitPosition;
            return hitPosition;
        }
        return -1;
    }


    private int getAroundLastHit(int lastHit) {
        Random random = new Random();
        String[] directions = {"up", "down", "right", "left"};
        int rowDigit;
        int columDigit;

        int index = random.nextInt(directions.length);

        if (lastHit >= 0 && lastHit <= 9) {
            rowDigit = lastHit;

            // Border Left of Game
            switch (directions[index]) {
                case "up":
                    return (rowDigit == 0) ? --rowDigit : ++rowDigit;
                case "down":
                    return (rowDigit == 9) ? ++rowDigit : --rowDigit;
                case "right", "left":
                    return 10 + rowDigit;
            }

        } else {

            rowDigit = Math.abs(lastHit % 10);   // 0-9
            columDigit = Math.abs(lastHit / 10);   // 0-9

            switch (directions[index]) {
                case "up":
                    if (rowDigit == 0) return ++rowDigit + columDigit * 10;
                    return --rowDigit + columDigit * 10;
                case "down":
                    if (rowDigit == 9) return --rowDigit + columDigit * 10;
                    return ++rowDigit + columDigit * 10;
                case "right":
                    if (columDigit == 9) return rowDigit + --columDigit * 10;
                    return rowDigit + --columDigit * 10;
                case "left":
                    if (columDigit == 1) return rowDigit;
                    return rowDigit + ++columDigit * 10;
            }
        }

        return -1;
    }


    private static int getRandomAdjacent(int[] sequence) {

        // Sort the array and find the min and max
        Arrays.sort(sequence);
        int min = sequence[0];
        int max = sequence[sequence.length - 1];

        // Create a list to hold potential numbers to choose from
        List<Integer> options = new ArrayList<>();

        // If there is a number before min, add it to options
        if (min > 0) {
            options.add(min - 1);
        }

        // If there is a number after max, add it to options
        if (max < 9) {
            options.add(max + 1);
        }

        // If there are no options (i.e., the sequence spans the full 0-9 range),
        // throw an exception or handle this case as needed
        if (options.isEmpty()) {
            throw new RuntimeException("No adjacent numbers available.");
        }

        // Choose a random number from the options
        Random rand = new Random();
        int index = rand.nextInt(options.size());
        return options.get(index);
    }


    /**
     * Compare the digits of the hitDirection with the currentHit
     *
     * @param hitDirection
     * @param lastHit
     * @return an array with the digits that match
     */
    private int[] compareDigits(int hitDirection, int lastHit) {
        //initialize with -1 to denote "no match"
        int[] result = {-1, -1}; // array to hold the result,

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

    private int[] sequenceDigits(int hitDirection, int lastHit) {
        //initialize with -1 to denote "no match"
        int[] result = {-1, -1}; // array to hold the result,

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

}
