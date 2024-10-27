package model.factory;

import model.strategy.PlaceShipStrategy;

/**
 * Interfaccia per la Factory che crea strategie di posizionamento delle navi.
 *
 * @version 1.0
 */
public interface IPlaceShipFactory {
    /**
     * Restituisce un'istanza della strategia di posizionamento delle navi.
     *
     * @return Un'istanza di PlaceShipStrategy.
     */
    PlaceShipStrategy getPlaceShipStrategy();
}