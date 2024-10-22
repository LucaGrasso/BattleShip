package model;

/**
 * Classe che rappresenta un'eccezione specifica del dominio.
 * Questa eccezione viene lanciata per segnalare errori specifici nel contesto del dominio dell'applicazione.
 *
 * @version 1.0
 */
public class DomainException extends RuntimeException {

	/**
	 * Costruttore di default che crea una nuova eccezione con il messaggio "DomainException occurred".
	 */
	public DomainException() {
		this("DomainException occurred");
	}

	/**
	 * Costruttore che consente di specificare un messaggio di errore personalizzato.
	 *
	 * @param message Il messaggio di errore.
	 */
	public DomainException(String message) {
		this(message, null);
	}

	/**
	 * Costruttore che consente di specificare un messaggio di errore e una causa.
	 *
	 * @param message   Il messaggio di errore.
	 * @param exception L'eccezione che ha causato questa eccezione.
	 */
	public DomainException(String message, Exception exception) {
		super(message, exception);
	}
}