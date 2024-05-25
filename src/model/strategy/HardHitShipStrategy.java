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
    private String direction = null;
    private int rowDirection = -1;
    private int columnDirection = -1;
    private ArrayList<Integer> sequence = null;

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
            hitDirection = null;
            isLastHitSuccessful = false;
            isShipSunk = false;
            firstHitShip = false;
            direction = null;
            rowDirection = -1;
            columnDirection = -1;
            sequence = null;
        }


        // Ho colpito due volte la stessa nave
        if (firstHitShip && isLastHitSuccessful) {
            if (direction.isEmpty()) {
                int[] digitsForDirection = compareDigits(hitDirection, lastHit);
                this.sequence = new ArrayList<>(Arrays.asList(hitDirection, lastHit));
                if (digitsForDirection[0] != -1) {
                    direction = "vertical";
                    this.columnDirection = digitsForDirection[0];
                }
                if (digitsForDirection[1] != -1) {
                    direction = "horizontal";
                    this.rowDirection = digitsForDirection[1];
                }
            }

            int[] sequenceD = sequenceDigits(hitDirection, lastHit);
            if (direction.equals("horizontal")) {


                // Cerchiamo in orizzontale
                if (lastHit % 10 == 0) {
                    // Se siamo al bordo sinistro, cerchiamo a destra
                    result = lastHit + 1;
                } else if (lastHit % 10 == 9) {
                    // Se siamo al bordo destro, cerchiamo a sinistra
                    result = lastHit - 1;
                } else {
                    // Cerchiamo a destra
                    result = lastHit + 1;
                }
            } else if (direction.equals("vertical")) {
                // Cerchiamo in verticale
                if (lastHit / 10 == 0) {
                    // Se siamo al bordo superiore, cerchiamo in basso
                    result = lastHit + 10;
                } else if (lastHit / 10 == 9) {
                    // Se siamo al bordo inferiore, cerchiamo in alto
                    result = lastHit - 10;
                } else {
                    // Cerchiamo in basso
                    result = lastHit + 10;
                }
            }


        }



        if (lastHit != null && isLastHitSuccessful) {
            // Ho colpito una nave per la prima volta
            if (!firstHitShip) firstHitShip = true;
            // verificare se possiamo colpire in quella direzione
            this.hitDirection = lastHit;
            result = getAroundLastHit(lastHit);
            // Aggiungo alla lista di hit
            addHitPosition(result);
        }

        // if (result == -1 && hitDirection != null) {
        // Se non riusciamo a colpire nella nostra direzione attuale, cerchiamo in una nuova direzione
        // Cambiamo la direzione di 90 gradi
        //     hitDirection = (hitDirection + 1) % 4;
        // }


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
     *
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
                    return rowDigit + ++columDigit * 10;
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
        int max = sequence[sequence.length-1];

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
        int[] result = {-1,-1}; // array to hold the result,

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
