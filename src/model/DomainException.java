package model;
/**
 * 
 * @author Luca Grasso
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
public class DomainException extends RuntimeException {

	public DomainException(String message, Exception exception) {
		super(message, exception);
	}

	public DomainException(String message) {
		super(message);
	}

	public DomainException() {
		super();
	}

}
