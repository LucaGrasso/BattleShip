package model.factory;

import model.strategy.HitShipStrategy;

/**
 * Interfaccia per la Factory che crea strategie di colpire le navi.
 *
 * @version 1.0
 */
public interface IHitShipFactory {
    /**
     * Restituisce un'istanza della strategia di colpire le navi.
     *
     * @return Un'istanza di HitShipStrategy.
     */
    HitShipStrategy getHitShipStrategy();
}