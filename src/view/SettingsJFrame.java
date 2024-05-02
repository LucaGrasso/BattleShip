package view;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.strategy.HitStrategy;
import model.strategy.PlaceStrategy;
/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */
public class SettingsJFrame extends JFrame {

	private JButton confirmButton;

	private JComboBox<HitStrategy> hitComboBox;
	private JComboBox<PlaceStrategy> placeComboBox;
	private JComboBox<String> shipVisibilityBox;

    private boolean isShipsVisible = false;

	public final static int HEIGHT_FRAME = 500;
	public final static int WIDTH_FRAME = 500;

	public SettingsJFrame() {
		super();
		this.launch();
	}

	public void launch() {
		this.DefaultValuesToProperties();

		// Posiziona il frame al centro dello schermo
		this.setSize(WIDTH_FRAME, HEIGHT_FRAME);
		this.setLocationRelativeTo(null);
		//-------
		this.setResizable(false);
		this.setLayout(null);
		this.setTitle("Settings/Debugging");

		this.completeLabelHitStrategy();
		this.completeBoxHitStrategy();
		this.completeConfirmButton();

		this.completeBoxPlaceStrategy();
		this.completeLabelPlaceStrategy();

		this.completeShipsVisibleLabel();
		this.completeShipsVisibleBox();

		this.addActionListenerToHitComboBox();
		this.addActionListenerToPlaceComboBox();
		this.addActionListenerToVisibilityComboBox();
		this.addActionListenerTopConfirmButton();
	}

	private void completeConfirmButton() {
		this.confirmButton = new JButton("Confirm");
		this.confirmButton.setLocation(25, 400);
		this.confirmButton.setSize(new Dimension(450, 40));
		this.add(confirmButton);
	}

	private void completeBoxHitStrategy() {
		this.hitComboBox = new JComboBox<>(HitStrategy.values());
		this.hitComboBox.setLocation(25, 120);
		this.hitComboBox.setSize(new Dimension(450, 40));
		this.add(hitComboBox);
	}

	private void completeLabelHitStrategy() {
        JLabel hitLabel = new JLabel("Choose a shooting strategy:");
		hitLabel.setLocation(25, 75);
		hitLabel.setSize(new Dimension(450, 40));
		this.add(hitLabel);
	}

	private void addActionListenerToHitComboBox() {
		this.hitComboBox.addActionListener(e -> writeHitToProperties(((HitStrategy) hitComboBox.getSelectedItem()).getFullClassName()));
	}

	private void completeBoxPlaceStrategy() {
		this.placeComboBox = new JComboBox<>(PlaceStrategy.values());
		this.placeComboBox.setLocation(25, 200);
		this.placeComboBox.setSize(new Dimension(450, 40));
		this.add(placeComboBox);
	}

	private void completeLabelPlaceStrategy() {
        JLabel placeLabel = new JLabel("Choose a place ships strategy:");
		placeLabel.setLocation(25, 160);
		placeLabel.setSize(new Dimension(450, 40));
		this.add(placeLabel);
	}

	private void addActionListenerToPlaceComboBox() {
		this.placeComboBox.addActionListener(e -> writePlaceToProperties(((PlaceStrategy) placeComboBox.getSelectedItem()).getFullClassName()));
	}

	private void addActionListenerTopConfirmButton() {
		this.confirmButton.addActionListener(e -> setVisible(false));
	}

	private void completeShipsVisibleLabel() {
        JLabel shipsVisibleLabel = new JLabel("Should the computer ships be visible?:");
		shipsVisibleLabel.setLocation(25, 250);
		shipsVisibleLabel.setSize(new Dimension(450, 40));
		this.add(shipsVisibleLabel);
	}

	private void completeShipsVisibleBox() {
		String[] possibilities = { "NO", "YES" };
		this.shipVisibilityBox = new JComboBox<>(possibilities);
		this.shipVisibilityBox.setLocation(25, 290);
		this.shipVisibilityBox.setSize(new Dimension(450, 40));
		this.add(shipVisibilityBox);
	}

	private void addActionListenerToVisibilityComboBox() {
		this.shipVisibilityBox.addActionListener(e -> {
            String selected = (String) shipVisibilityBox.getSelectedItem();
            assert selected != null;
            isShipsVisible = selected.equals("YES");
        });
	}

	public boolean shipsVisible() {
		return this.isShipsVisible;
	}

	private void DefaultValuesToProperties() {
		this.writeHitToProperties(HitStrategy.RANDOM.getFullClassName());
		this.writePlaceToProperties(PlaceStrategy.RANDOM.getFullClassName());
	}

	public void writeHitToProperties(String hitStrategy) {
		FileInputStream in = null;
		try {
			in = new FileInputStream("src/StrategyProperties.properties");
		} catch (FileNotFoundException e) {
			System.out.println("Properties not found! (Institutions)");
		}
		Properties props = new Properties();
		try {
			props.load(in);
            assert in != null;
            in.close();
		} catch (IOException e) {
			System.out.println("Kon properties niet laden (Instellingen)");
		}

		FileOutputStream out = null;
		try {
			out = new FileOutputStream("src/StrategyProperties.properties");
		} catch (FileNotFoundException e) {
			System.out.println("Properties not found! (Institutions)");
		}
		props.setProperty("hitShipStrategy", hitStrategy);
		try {
			props.store(out, null);
            assert out != null;
            out.close();
		} catch (IOException e) {
			System.out.println("Could not save properties (Settings)");
		}

	}

	public void writePlaceToProperties(String placeShipStrategy) {
		FileInputStream in = null;
		try {
			in = new FileInputStream("src/StrategyProperties.properties");
		} catch (FileNotFoundException e) {
			System.out.println("Properties not found! (Institutions)");
		}
		Properties props = new Properties();
		try {
			props.load(in);
            assert in != null;
            in.close();
		} catch (IOException e) {
			System.out.println("Could not save properties (Settings)");
		}

		FileOutputStream out = null;
		try {
			out = new FileOutputStream("src/StrategyProperties.properties");
		} catch (FileNotFoundException e) {
			System.out.println("Properties not found! (Institutions)");
		}
		props.setProperty("placeShipStrategy", placeShipStrategy);
		try {
			props.store(out, null);
            assert out != null;
            out.close();
		} catch (IOException e) {
			System.out.println("Could not save properties (Settings)");
		}
	}

}
