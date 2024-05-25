package application;

import controller.GameController;

/**
 * @Author Luca Grasso
 * @Matricola 294612
 * @Progetto PMO
 * @Data 03/05/2024
 * @Descrizione Questa classe rappresenta il punto di partenza dell'applicazione.
 * 			Inizializza il controller del gioco.
 * 			La classe GameController si occupa di gestire il gioco.
 *
 * @Version 1.0
 */
public class App {

	public static void main(String[] args) {
		new GameController();
	}

}
