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
 * @version 1.0
 */
public class PlaceShipFactory {
    private final Properties properties;

    /**
     * Costruttore che inizializza le proprietà leggendo dal file di configurazione.
     */
    public PlaceShipFactory() {
        this.properties = readPropertiesFile();
    }

    /**
     * Legge il file delle proprietà.
     *
     * @return Le proprietà lette dal file.
     */
    private Properties readPropertiesFile() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("src/strategyProperties.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new DomainException("Cannot read properties file: " + "src/strategyProperties.properties", e);
        }
        return properties;
    }

    /**
     * Restituisce un'istanza della strategia di posizionamento delle navi
     * basata sulle proprietà lette.
     *
     * @return Un'istanza di PlaceShipStrategy.
     */
    public PlaceShipStrategy getPlaceShipStrategy() {
        String className = properties.getProperty("placeShipStrategy");
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
            return (PlaceShipStrategy) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new DomainException("Failed to create strategy instance for className: " + className, e);
        }
    }
}