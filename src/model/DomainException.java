package model;
/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */
public class DomainException extends RuntimeException {

	public DomainException() {
		this("DomainException occurred");
	}

	public DomainException(String message) {
		this(message, null);
	}

	public DomainException(String message, Exception exception) {
		super(message, exception);
	}

}
