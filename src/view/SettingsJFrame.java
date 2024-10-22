package view;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.strategy.HitStrategy;
import model.strategy.PlaceStrategy;

/**
 * Classe per rappresentare il frame delle impostazioni dell'applicazione.
 * Permette all'utente di selezionare le strategie di hit e place
 * e di impostare la visibilità delle navi.
 *
 * @version 1.0
 */
public class SettingsJFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton confirmButton;
	private JComboBox<HitStrategy> hitComboBox;
	private JComboBox<PlaceStrategy> placeComboBox;
	private JComboBox<String> shipVisibilityBox;

	private boolean isShipsVisible = false;

	public final static int HEIGHT_FRAME = 500;
	public final static int WIDTH_FRAME = 500;

	/**
	 * Costruisce un oggetto SettingsJFrame e inizializza i suoi componenti.
	 */
	public SettingsJFrame() {
		super();
		this.launch();
	}

	/**
	 * Inizializza le proprietà e i componenti del frame.
	 */
	public void launch() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(WIDTH_FRAME, HEIGHT_FRAME);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(null);
		this.setTitle("Settings/Debugging");

		// Configura e aggiunge i componenti al frame
		this.completeLabelHitStrategy();
		this.completeBoxHitStrategy();
		this.completeConfirmButton();

		this.completeBoxPlaceStrategy();
		this.completeLabelPlaceStrategy();

		this.completeShipsVisibleLabel();
		this.completeShipsVisibleBox();

		// Aggiunge gli ascoltatori ai componenti
		this.addActionListenerToHitComboBox();
		this.addActionListenerToPlaceComboBox();
		this.addActionListenerToVisibilityComboBox();
		this.addActionListenerTopConfirmButton();

		this.readSettingsFromFileOrDefaults();

		// Aggiunge un ascoltatore per gestire l'evento di chiusura della finestra
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}

	/**
	 * Configura e aggiunge il pulsante di conferma al frame.
	 */
	private void completeConfirmButton() {
		this.confirmButton = new JButton("Confirm");
		this.confirmButton.setLocation(25, 400);
		this.confirmButton.setSize(new Dimension(450, 40));
		this.add(confirmButton);
	}

	/**
	 * Inizializza e aggiunge il JComboBox della strategia di hit al frame.
	 */
	private void completeBoxHitStrategy() {
		this.hitComboBox = new JComboBox<>(HitStrategy.values());
		this.hitComboBox.setLocation(25, 120);
		this.hitComboBox.setSize(new Dimension(450, 40));
		this.add(hitComboBox);
	}

	/**
	 * Configura e aggiunge l'etichetta della strategia di hit al frame.
	 */
	private void completeLabelHitStrategy() {
		JLabel hitLabel = new JLabel("Choose a shooting strategy:");
		hitLabel.setLocation(25, 75);
		hitLabel.setSize(new Dimension(450, 40));
		this.add(hitLabel);
	}

	/**
	 * Aggiunge un ActionListener al JComboBox della strategia di hit.
	 */
	private void addActionListenerToHitComboBox() {
		this.hitComboBox.addActionListener(_ -> writeHitToProperties(((HitStrategy) Objects.requireNonNull(hitComboBox.getSelectedItem())).getFullClassName()));
	}

	/**
	 * Inizializza e aggiunge il JComboBox della strategia di place al frame.
	 */
	private void completeBoxPlaceStrategy() {
		this.placeComboBox = new JComboBox<>(PlaceStrategy.values());
		this.placeComboBox.setLocation(25, 200);
		this.placeComboBox.setSize(new Dimension(450, 40));
		this.add(placeComboBox);
	}

	/**
	 * Configura e aggiunge l'etichetta della strategia di place al frame.
	 */
	private void completeLabelPlaceStrategy() {
		JLabel placeLabel = new JLabel("Choose a place ships strategy:");
		placeLabel.setLocation(25, 160);
		placeLabel.setSize(new Dimension(450, 40));
		this.add(placeLabel);
	}

	/**
	 * Aggiunge un ActionListener al JComboBox della strategia di place.
	 */
	private void addActionListenerToPlaceComboBox() {
		this.placeComboBox.addActionListener(_ -> writePlaceToProperties(((PlaceStrategy) Objects.requireNonNull(placeComboBox.getSelectedItem())).getFullClassName()));
	}

	/**
	 * Aggiunge un ActionListener al pulsante di conferma per salvare le strategie selezionate e la visibilità.
	 */
	private void addActionListenerTopConfirmButton() {
		this.confirmButton.addActionListener(_ -> {
			HitStrategy selectedHitStrategy = (HitStrategy) hitComboBox.getSelectedItem();
			if (selectedHitStrategy != null) {
				writeHitToProperties(selectedHitStrategy.getFullClassName());
			}

			PlaceStrategy selectedPlaceStrategy = (PlaceStrategy) placeComboBox.getSelectedItem();
			if (selectedPlaceStrategy != null) {
				writePlaceToProperties(selectedPlaceStrategy.getFullClassName());
			}
			this.dispose();
		});
	}

	/**
	 * Configura e aggiunge l'etichetta della visibilità delle navi al frame.
	 */
	private void completeShipsVisibleLabel() {
		JLabel shipsVisibleLabel = new JLabel("Should the computer ships be visible?:");
		shipsVisibleLabel.setLocation(25, 250);
		shipsVisibleLabel.setSize(new Dimension(450, 40));
		this.add(shipsVisibleLabel);
	}

	/**
	 * Inizializza e aggiunge il JComboBox della visibilità delle navi al frame.
	 */
	private void completeShipsVisibleBox() {
		String[] possibilities = { "NO", "YES" };
		this.shipVisibilityBox = new JComboBox<>(possibilities);
		this.shipVisibilityBox.setLocation(25, 290);
		this.shipVisibilityBox.setSize(new Dimension(450, 40));
		this.add(shipVisibilityBox);
	}

	/**
	 * Aggiunge un ActionListener al JComboBox della visibilità delle navi.
	 */
	private void addActionListenerToVisibilityComboBox() {
		this.shipVisibilityBox.addActionListener(_ -> {
			String selected = (String) shipVisibilityBox.getSelectedItem();
			assert selected != null;
			isShipsVisible = selected.equals("YES");
		});
	}

	/**
	 * Restituisce lo stato di visibilità delle navi.
	 *
	 * @return true se le navi sono visibili, false altrimenti.
	 */
	public boolean shipsVisible() {
		return this.isShipsVisible;
	}

	/**
	 * Scrive i valori di default per le strategie di hit e place nel file di proprietà.
	 */
	private void DefaultValuesToProperties() {
		this.writeHitToProperties(HitStrategy.RANDOM.getFullClassName());
		this.writePlaceToProperties(PlaceStrategy.RANDOM.getFullClassName());
	}

	/**
	 * Scrive la strategia di hit selezionata nel file di proprietà.
	 *
	 * @param hitStrategy il nome completo della classe della strategia di hit selezionata.
	 */
	public void writeHitToProperties(String hitStrategy) {
		Properties props = new Properties();
		try (FileInputStream in = new FileInputStream("src/StrategyProperties.properties")) {
			props.load(in);
		} catch (IOException e) {
			System.out.println("Properties not found! (Institutions)");
		}

		try (FileOutputStream out = new FileOutputStream("src/StrategyProperties.properties")) {
			props.setProperty("hitShipStrategy", hitStrategy);
			props.store(out, null);
		} catch (IOException e) {
			System.out.println("Could not save properties (Settings)");
		}
	}

	/**
	 * Scrive la strategia di place selezionata nel file di proprietà.
	 *
	 * @param placeShipStrategy il nome completo della classe della strategia di place selezionata.
	 */
	public void writePlaceToProperties(String placeShipStrategy) {
		Properties props = new Properties();
		try (FileInputStream in = new FileInputStream("src/StrategyProperties.properties")) {
			props.load(in);
		} catch (IOException e) {
			System.out.println("Could not save properties (Settings)");
		}

		try (FileOutputStream out = new FileOutputStream("src/StrategyProperties.properties")) {
			props.setProperty("placeShipStrategy", placeShipStrategy);
			props.store(out, null);
		} catch (IOException e) {
			System.out.println("Could not save properties (Settings)");
		}
	}

	/**
	 * Legge le impostazioni dal file di proprietà o utilizza i valori di default se il file non viene trovato.
	 */
	public void readSettingsFromFileOrDefaults() {
		Properties props = new Properties();
		try (FileInputStream in = new FileInputStream("src/StrategyProperties.properties")) {
			props.load(in);
			String hitStrategyClass = props.getProperty("hitShipStrategy");
			String placeStrategyClass = props.getProperty("placeShipStrategy");
			setHitAndPlaceStrategiesBasedOnProperties(hitStrategyClass, placeStrategyClass);
		} catch (FileNotFoundException e) {
			System.out.println("Properties file not found! Default settings would be used.");
			DefaultValuesToProperties();
		} catch (IOException e) {
			System.out.println("Error reading properties file! Default settings would be used.");
			DefaultValuesToProperties();
		}
	}

	/**
	 * Imposta le strategie di hit e place nei JComboBox in base al file di proprietà.
	 *
	 * @param hitStrategyClass il nome completo della classe della strategia di hit.
	 * @param placeStrategyClass il nome completo della classe della strategia di place.
	 */
	private void setHitAndPlaceStrategiesBasedOnProperties(String hitStrategyClass, String placeStrategyClass) {
		for (HitStrategy hitStrategy : HitStrategy.values()) {
			if (hitStrategy.getFullClassName().equals(hitStrategyClass)) {
				this.hitComboBox.setSelectedItem(hitStrategy);
				break;
			}
		}

		for (PlaceStrategy placeStrategy : PlaceStrategy.values()) {
			if (placeStrategy.getFullClassName().equals(placeStrategyClass)) {
				this.placeComboBox.setSelectedItem(placeStrategy);
				break;
			}
		}
	}
}