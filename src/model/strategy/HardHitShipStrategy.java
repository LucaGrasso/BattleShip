/**
 * @Author Luca Grasso
 * @Matricola 294612
 * @Progetto PMO
 * @Data 04/05/2024
 */

package model.strategy;

import java.util.ArrayList;
import java.util.List;


public class HardHitShipStrategy implements HitShipStrategy {
    private final List<Integer> alreadyHit = new ArrayList<>();
    private Integer lastHit = null;
    private Integer hitDirection = null;
    private boolean isLastHitSuccessful = false;
    private boolean isShipSunk = false;

    @Override
    public void setLastHitSuccessful(boolean isLastHitSuccessful) {
        this.isLastHitSuccessful = isLastHitSuccessful;
    }

    @Override
    public void setIsShipSunk(boolean isShipSunk) {
        this.isShipSunk = isShipSunk;
    }

    @Override
    public int hitShip() {
        int result = -1;


        // Se abbiamo una nave target e la nave è stata affondata, reset the strategy
        //if (currentTarget != null && currentTarget.isSunk()) {
        //    resetStrategy(); // Aggiungi un metodo che resetta `alreadyHit`, `lastHit`, e `hitDirection`
        // }

        // se abbiamo colpito una nave, cerchiamo in quella direzione
        if (lastHit != null) {
            // verificar se possiamo colpire in quella direzione
            result = checkHitInDirection(lastHit, hitDirection);

            if (result == -1 && hitDirection != null) {
                // Se non riusciamo a colpire nella nostra direzione attuale, cerchiamo in una nuova direzione
                // Cambiamo la direzione di 90 gradi
                hitDirection = (hitDirection + 1) % 4;
            }

            result = checkHitInDirection(lastHit, hitDirection);
        }

        // se non abbiamo una direzione o non abbiamo colpito una nave, generiamo una posizione casuale
        while (result == -1) {
            int temp = (int) (Math.random() * 100);
            if (alreadyHit.size() == 100) {
                result = 99;
            }
            if (!(alreadyHit.contains(temp))) {
                alreadyHit.add(temp);
                result = temp;
                lastHit = temp;
            }
        }
        return result;
    }

    private int checkHitInDirection(int startPosition, Integer direction) {
        // implementare l'algoritmo per verificare se sia possibile colpire in quella direzione
        // sia a partire dalla posizione di partenza, possiamo colpire una nave
        return -1;
    }

}
