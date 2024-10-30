package model.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import model.DomainException;
import model.strategy.PlaceShipStrategy;

/**
 * Classe che implementa il pattern Factory per creare istanze di strategie
 * di posizionamento delle navi.
 *
 * @version 1.1
 */
public class PlaceShipFactory implements IPlaceShipFactory {
    private static final String STRATEGY_PROPERTIES_FILE = "./strategyProperties.properties";
    private final Properties properties;

    /**
     * Costruttore che inizializza le proprietà leggendo dal file di configurazione.
     */
    public PlaceShipFactory() {
        this.properties = new Properties();
        loadProperties();
    }

    /**
     * Legge il file delle proprietà.
     */
    private void loadProperties() {
        try (InputStream input = new FileInputStream(STRATEGY_PROPERTIES_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            throw new DomainException("Cannot read properties file: " + STRATEGY_PROPERTIES_FILE, e);
        }
    }

    /**
     * Restituisce un'istanza della strategia di posizionamento delle navi
     * basata sulle proprietà lette.
     *
     * @return Un'istanza di PlaceShipStrategy.
     */
    public PlaceShipStrategy getPlaceShipStrategy() {
        String className = properties.getProperty("placeShipStrategy");
        if (className == null) {
            throw new DomainException("Property 'placeShipStrategy' not found in " + STRATEGY_PROPERTIES_FILE);
        }
        return createStrategyInstance(className);
    }

    /**
     * Crea un'istanza della strategia di posizionamento delle navi
     * data la classe della strategia.
     *
     * @param className Il nome della classe della strategia.
     * @return Un'istanza di PlaceShipStrategy.
     */
    private PlaceShipStrategy createStrategyInstance(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            if (!PlaceShipStrategy.class.isAssignableFrom(clazz)) {
                throw new DomainException("Class " + className + " does not implement PlaceShipStrategy interface");
            }
            return (PlaceShipStrategy) clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            throw new DomainException("Class not found: " + className, e);
        } catch (ClassCastException | ReflectiveOperationException e) {
            throw new DomainException("Failed to create strategy instance for class: " + className, e);
        }
    }
}