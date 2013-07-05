package edu.upf.nets.mercury.geoip.data;

/**
 * A class representing a database exception.
 * @author Alex
 *
 */
public class DbException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Creates a database exception instance with the specified message. 
	 * @param message The exception message.
	 */
	public DbException(String message) {
		super(message);
	}
	
	/**
	 * Creates a database exception instance with the specified message and cause. 
	 * @param message The exception message.
	 * @param cause the cause for this exception.
	 */
	public DbException(String message, Throwable cause) {
		super(message, cause);
	}
}
