/**
 * @Author Luca Grasso
 * @Matricola 294612
 * @Progetto PMO
 * @Data 04/05/2024
 */

package model.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class HardHitShipStrategy implements HitShipStrategy {
    private final List<Integer> hitPositionsList = new ArrayList<>();
    private Integer lastHit = null;
    private Integer hitDirection = null;
    private boolean isLastHitSuccessful = false;
    private boolean isShipSunk = false;
    private boolean firstHitShip = false;

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

        // Se abbiamo una nave target e la nave è stata affondata, reset the strategy
        //if (currentTarget != null && currentTarget.isSunk()) {
        //    resetStrategy(); // Aggiungi un metodo che resetta `alreadyHit`, `lastHit`, e `hitDirection`
        // }
        // se abbiamo colpito una nave, cerchiamo in quella direzione

        if (firstHitShip && isLastHitSuccessful) {


            result = getAroundLastHit(lastHit);

        }


        if (lastHit != null && isLastHitSuccessful) {
            // Ho colpito una nave per la prima volta
            if (!firstHitShip) firstHitShip = true;
            // verificare se possiamo colpire in quella direzione
            hitDirection = lastHit;
            result = getAroundLastHit(lastHit);
        }

           // if (result == -1 && hitDirection != null) {
                // Se non riusciamo a colpire nella nostra direzione attuale, cerchiamo in una nuova direzione
                // Cambiamo la direzione di 90 gradi
           //     hitDirection = (hitDirection + 1) % 4;
           // }


        // se non abbiamo una direzione o non abbiamo colpito una nave, generiamo una posizione casuale
        while (result == -1) {
            int temp = (int) (Math.random() * 100);
            if (hitPositionsList.size() == 100) {
                result = 99;
            }
            if (!(hitPositionsList.contains(temp))) {
                hitPositionsList.add(temp);
                result = temp;
                lastHit = temp;
            }
        }
        return result;
    }



    private int getAroundLastHit(int lastHit){
        Random random = new Random();
        String[] directions = {"up", "down", "right", "left"};
        int rowDigit ;
        int columDigit;

        int index = random.nextInt(directions.length);

        if(lastHit >= 0 && lastHit <= 9) {
            rowDigit = lastHit;

            // Border Left of Game
            switch (directions[index]) {
                case "up":
                    return  (rowDigit == 0) ? --rowDigit : ++rowDigit;
                case "down":
                    return  (rowDigit == 9) ? ++rowDigit : --rowDigit;
                case "right", "left":
                    return 10 + rowDigit;
            }

        } else {

            rowDigit   = Math.abs(lastHit % 10);   // 0-9
            columDigit = Math.abs(lastHit / 10);   // 0-9

            switch (directions[index]) {
                case "up":
                    if (rowDigit == 0) return  ++rowDigit  + columDigit * 10;
                    return  --rowDigit  + columDigit * 10;
                case "down":
                    if (rowDigit == 9) return  --rowDigit  + columDigit * 10;
                    return  ++rowDigit  + columDigit * 10;
                case "right":
                    if (columDigit == 9) return  rowDigit  + --columDigit * 10;
                    return  rowDigit  + ++columDigit * 10;
                case "left":
                    if (columDigit == 1) return  rowDigit;
                    return  rowDigit  + ++columDigit * 10;
            }
        }

        return -1;
    }


    public int[] compareDigits(int hitDirection, int lastHit) {
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

}
