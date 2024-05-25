package model.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import model.DomainException;
import model.strategy.PlaceShipStrategy;

/**
 *
 * @author Luca Grasso
 * @version 1.0
 *
 *
 * This is a classic approach of the Factory Pattern
 * where a method (getPlaceShipStrategy()) returns an instance of some base type
 * or interface (PlaceShipStrategy), with the actual subtype instantiated depending
 * on the logic enclosed within the method. This enables flexibility as you can decide
 * the exact type of object needed at runtime.
 * */


public class PlaceShipFactory {
    private final Properties properties;

    public PlaceShipFactory() {
        this.properties = readPropertiesFile("src/strategyProperties.properties");
    }

    private Properties readPropertiesFile(String filePath) {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        } catch (IOException e) {
            throw new DomainException("Cannot read properties file: " + filePath, e);
        }
        return properties;
    }

    public PlaceShipStrategy getPlaceShipStrategy() {
        String className = properties.getProperty("placeShipStrategy");
        return createStrategyInstance(className);
    }

    private PlaceShipStrategy createStrategyInstance(String className) {
        try {
            return (PlaceShipStrategy) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new DomainException("Failed to create strategy instance for className: " + className, e);
        }
    }
}
