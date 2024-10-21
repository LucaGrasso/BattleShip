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
 * A class to represent the settings frame for the application.
 * It allows the user to select hit and place strategies and
 * set the visibility of ships.
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
	 * Constructs a SettingsJFrame object and initializes its components.
	 */
	public SettingsJFrame() {
		super();
		this.launch();
	}

	/**
	 * Initializes the frame's properties and components.
	 */
	public void launch() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(WIDTH_FRAME, HEIGHT_FRAME);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(null);
		this.setTitle("Settings/Debugging");

		// Configures and adds components to the frame
		this.completeLabelHitStrategy();
		this.completeBoxHitStrategy();
		this.completeConfirmButton();

		this.completeBoxPlaceStrategy();
		this.completeLabelPlaceStrategy();

		this.completeShipsVisibleLabel();
		this.completeShipsVisibleBox();

		// Adds listeners to components
		this.addActionListenerToHitComboBox();
		this.addActionListenerToPlaceComboBox();
		this.addActionListenerToVisibilityComboBox();
		this.addActionListenerTopConfirmButton();

		this.readSettingsFromFileOrDefaults();

		// Adds a listener to handle window closing event
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}

	/**
	 * Configures and adds the confirm button to the frame.
	 */
	private void completeConfirmButton() {
		this.confirmButton = new JButton("Confirm");
		this.confirmButton.setLocation(25, 400);
		this.confirmButton.setSize(new Dimension(450, 40));
		this.add(confirmButton);
	}

	/**
	 * Initializes and adds the hit strategy JComboBox to the frame.
	 */
	private void completeBoxHitStrategy() {
		this.hitComboBox = new JComboBox<>(HitStrategy.values());
		this.hitComboBox.setLocation(25, 120);
		this.hitComboBox.setSize(new Dimension(450, 40));
		this.add(hitComboBox);
	}

	/**
	 * Configures and adds the hit strategy label to the frame.
	 */
	private void completeLabelHitStrategy() {
		JLabel hitLabel = new JLabel("Choose a shooting strategy:");
		hitLabel.setLocation(25, 75);
		hitLabel.setSize(new Dimension(450, 40));
		this.add(hitLabel);
	}

	/**
	 * Adds an ActionListener to the hit strategy JComboBox.
	 */
	private void addActionListenerToHitComboBox() {
		this.hitComboBox.addActionListener(_ -> writeHitToProperties(((HitStrategy) Objects.requireNonNull(hitComboBox.getSelectedItem())).getFullClassName()));
	}

	/**
	 * Initializes and adds the place strategy JComboBox to the frame.
	 */
	private void completeBoxPlaceStrategy() {
		this.placeComboBox = new JComboBox<>(PlaceStrategy.values());
		this.placeComboBox.setLocation(25, 200);
		this.placeComboBox.setSize(new Dimension(450, 40));
		this.add(placeComboBox);
	}

	/**
	 * Configures and adds the place strategy label to the frame.
	 */
	private void completeLabelPlaceStrategy() {
		JLabel placeLabel = new JLabel("Choose a place ships strategy:");
		placeLabel.setLocation(25, 160);
		placeLabel.setSize(new Dimension(450, 40));
		this.add(placeLabel);
	}

	/**
	 * Adds an ActionListener to the place strategy JComboBox.
	 */
	private void addActionListenerToPlaceComboBox() {
		this.placeComboBox.addActionListener(_ -> writePlaceToProperties(((PlaceStrategy) Objects.requireNonNull(placeComboBox.getSelectedItem())).getFullClassName()));
	}

	/**
	 * Adds an ActionListener to the confirm button to save the selected strategies and visibility.
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
	 * Configures and adds the ships visibility label to the frame.
	 */
	private void completeShipsVisibleLabel() {
		JLabel shipsVisibleLabel = new JLabel("Should the computer ships be visible?:");
		shipsVisibleLabel.setLocation(25, 250);
		shipsVisibleLabel.setSize(new Dimension(450, 40));
		this.add(shipsVisibleLabel);
	}

	/**
	 * Initializes and adds the ships visibility JComboBox to the frame.
	 */
	private void completeShipsVisibleBox() {
		String[] possibilities = { "NO", "YES" };
		this.shipVisibilityBox = new JComboBox<>(possibilities);
		this.shipVisibilityBox.setLocation(25, 290);
		this.shipVisibilityBox.setSize(new Dimension(450, 40));
		this.add(shipVisibilityBox);
	}

	/**
	 * Adds an ActionListener to the ships visibility JComboBox.
	 */
	private void addActionListenerToVisibilityComboBox() {
		this.shipVisibilityBox.addActionListener(_ -> {
			String selected = (String) shipVisibilityBox.getSelectedItem();
			assert selected != null;
			isShipsVisible = selected.equals("YES");
		});
	}

	/**
	 * Returns the visibility status of the ships.
	 *
	 * @return true if ships are visible, false otherwise
	 */
	public boolean shipsVisible() {
		return this.isShipsVisible;
	}

	/**
	 * Writes default values for hit and place strategies to properties file.
	 */
	private void DefaultValuesToProperties() {
		this.writeHitToProperties(HitStrategy.RANDOM.getFullClassName());
		this.writePlaceToProperties(PlaceStrategy.RANDOM.getFullClassName());
	}

	/**
	 * Writes the selected hit strategy to the properties file.
	 *
	 * @param hitStrategy the full class name of the selected hit strategy
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
	 * Writes the selected place strategy to the properties file.
	 *
	 * @param placeShipStrategy the full class name of the selected place strategy
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
	 * Reads settings from the properties file or uses default values if file not found.
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
	 * Sets the hit and place strategies in the JComboBoxes based on the properties file.
	 *
	 * @param hitStrategyClass the full class name of the hit strategy
	 * @param placeStrategyClass the full class name of the place strategy
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