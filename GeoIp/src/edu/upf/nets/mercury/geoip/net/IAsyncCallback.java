package edu.upf.nets.mercury.geoip.net;

/**
 * An interface for classes used for the callback of asynchronous operations.
 * @author Alex
 *
 */
public interface IAsyncCallback {
	/**
	 * A method called when an asynchronous operation has completed.
	 * @param result The result of the asynchronous operation.
	 */
	public void callback(IAsyncResult result);
}
