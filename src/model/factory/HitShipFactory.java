package model.factory;

import model.DomainException;
import model.strategy.HitShipStrategy;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Factory per creare le strategie di colpire le navi nel gioco Battaglia Navale.
 *
 * @version 1.0
 */
public class HitShipFactory implements IHitShipFactory {
	private static final String PROPS_PATH = "src/strategyProperties.properties";
	private static final String STRATEGY_PROP = "hitShipStrategy";
	private static final String DEFAULT_STRATEGY = "model.strategy.RandomHitShipStrategy";

	/**
	 * Restituisce un'istanza della strategia di colpire le navi caricando le proprietà
	 * dal file di configurazione.
	 *
	 * @return Un'istanza di HitShipStrategy.
	 */
	public HitShipStrategy getHitShipStrategy() {
		createPropertiesFileIfNotExist();
		Properties properties = loadPropertiesFromFile();
		return createStrategyInstance(properties.getProperty(STRATEGY_PROP));
	}

	/**
	 * Crea il file delle proprietà se non esiste.
	 */
	private void createPropertiesFileIfNotExist() {
		Path propsFilePath = Paths.get(PROPS_PATH);
		if (!Files.exists(propsFilePath)) {
			try (FileWriter writer = new FileWriter(PROPS_PATH)) {
				Properties properties = new Properties();
				properties.setProperty(STRATEGY_PROP, DEFAULT_STRATEGY);
				properties.store(writer, "Properties for BattleShip Game");
			} catch (IOException e) {
				throw new RuntimeException("Error creating properties file", e);
			}
		}
	}

	/**
	 * Carica le proprietà dal file.
	 *
	 * @return Le proprietà lette dal file.
	 */
	private Properties loadPropertiesFromFile() {
		Properties properties = new Properties();
		try (InputStream input = Files.newInputStream(Paths.get(PROPS_PATH))) {
			properties.load(input);
		} catch (IOException e) {
			throw new DomainException("Unable to load properties file: " + PROPS_PATH, e);
		}
		return properties;
	}

	/**
	 * Crea un'istanza della strategia di colpire le navi data la classe della strategia.
	 *
	 * @param className Il nome della classe della strategia.
	 * @return Un'istanza di HitShipStrategy.
	 */
	private HitShipStrategy createStrategyInstance(String className) {
		if (className == null || className.trim().isEmpty()) {
			className = DEFAULT_STRATEGY;
		}
		try {
			Class<?> clazz = Class.forName(className);
			return (HitShipStrategy) clazz.getDeclaredConstructor().newInstance();
		} catch (ClassNotFoundException e) {
			return fallbackStrategy("Class not found: " + className);
		} catch (ReflectiveOperationException | ClassCastException e) {
			throw new DomainException("Failed to create strategy instance for class: " + className, e);
		}
	}

	/**
	 * Metodo di fallback per creare un'istanza della strategia predefinita.
	 *
	 * @param message Il messaggio di errore da loggare.
	 * @return Un'istanza di HitShipStrategy.
	 */
	private HitShipStrategy fallbackStrategy(String message) {
		System.out.println(message + ". Falling back to default strategy.");
		try {
			Class<?> clazz = Class.forName(DEFAULT_STRATEGY);
			return (HitShipStrategy) clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new DomainException("Failed to create default strategy instance: " + DEFAULT_STRATEGY, e);
		}
	}
}