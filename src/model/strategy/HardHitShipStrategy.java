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

        if (lastHit != null && isLastHitSuccessful) {
            // Ho colpito una nave per la prima volta
            if (!firstHitShip) firstHitShip = true;
            // verificare se possiamo colpire in quella direzione
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

        int firstDigit = Math.abs(lastHit / 10);
        int secondDigit = Math.abs(lastHit % 10);

        return lastHit;
    }






}
