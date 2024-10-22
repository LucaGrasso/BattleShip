package model.factory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import model.DomainException;
import model.strategy.HitShipStrategy;

/**
 * Factory per creare le strategie di colpire le navi nel gioco Battaglia Navale.
 *
 * @version 1.0
 */
public class HitShipFactory {
	private static final String PROPS_PATH = "src/strategyProperties.properties";
	private static final String STRATEGY_PROP = "hitShipStrategy";

	/**
	 * Restituisce un'istanza della strategia di colpire le navi caricando le proprietà
	 * dal file di configurazione.
	 *
	 * @return Un'istanza di HitShipStrategy.
	 */
	public HitShipStrategy getHitShipStrategy() {
		createPropertiesFileIfNotExist();
		Properties properties = loadPropertiesFromFile();
		return createStrategyInstance(properties);
	}

	/**
	 * Crea il file delle proprietà se non esiste.
	 */
	private void createPropertiesFileIfNotExist() {
		Path propsFilePath = Paths.get(PROPS_PATH);
		if (!Files.exists(propsFilePath)) {
			try (FileWriter writer = new FileWriter(PROPS_PATH)) {
				Properties properties = new Properties();
				properties.setProperty(STRATEGY_PROP, "full.qualified.class.name");
				properties.store(writer, "Properties for BattleShip Game");
				writer.flush();
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
	private Properties loadPropertiesFromFile(){
		Properties properties = new Properties();
		try (InputStream input = Files.newInputStream(new File("src/strategyProperties.properties").toPath())) {
			properties.load(input);
		} catch (IOException e) {
			throw new DomainException("Properties file not found (HitShipFactory)", e);
		}
		return properties;
	}

	/**
	 * Crea un'istanza della strategia di colpire le navi data la classe della strategia.
	 *
	 * @param properties Le proprietà caricate.
	 * @return Un'istanza di HitShipStrategy.
	 */
	private HitShipStrategy createStrategyInstance(Properties properties){
		String className = properties.getProperty("hitShipStrategy");
		try {
			Class<?> hitShipStrategyClass = Class.forName(className);
			return (HitShipStrategy) hitShipStrategyClass.getDeclaredConstructor().newInstance();

		} catch (ClassNotFoundException e) {
			// La classe non esiste allora ritorno la classe RandomHitShipStrategy che è la classe di default
			try {
				System.out.println("For hitShipStrategy --" + className + " is an invalid setup.");
				Class<?> hitShipStrategyClass = Class.forName("model.strategy.RandomHitShipStrategy");
				return (HitShipStrategy) hitShipStrategyClass.getDeclaredConstructor().newInstance();
			} catch (Exception e1) {
				throw new DomainException("strategy not found (HitShipFactory)", e1);
			}

		} catch (Exception e) {
			throw new DomainException("strategy not found (HitShipFactory)", e);
		}
	}
}