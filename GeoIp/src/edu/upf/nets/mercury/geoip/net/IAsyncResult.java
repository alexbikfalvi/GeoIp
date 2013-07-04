package edu.upf.nets.mercury.geoip.net;

import java.net.URL;

/**
 * An interface representing the result of an asynchronous operation.
 * @author Alex
 *
 */
public interface IAsyncResult extends Runnable {
	/**
	 * Returns the binary output data for this request.
	 * @return The output data as binary.
	 * @throws Exception
	 */
	public byte[] getResponse() throws Exception;
	
	/**
	 * Returns the string output data for this request using the response encoding.
	 * @return The output data as string.
	 * @throws Exception
	 */
	public String getResponseString() throws Exception; 
	
	/**
	 * Returns the callback object of the asynchronous state.
	 * @return The callback object.
	 */
	public IAsyncCallback getCallback();
	
	/**
	 * Returns the exception that occurred during the execution of the asynchronous operation.
	 * @return The exception.
	 */
	public Exception getException();
		
	/**
	 * Returns the URL of the asynchronous state.
	 * @return The URL.
	 */
	public URL getUrl();
	
	/**
	 * Indicates whether the asynchronous operation has completed or not.
	 * @return True if the asynchronous operation has completed or false otherwise.
	 */
	public boolean isCompleted();
	
	/**
	 * Waits for the asynchronous operation to complete.
	 */
	public void asyncWait() throws InterruptedException;

	/**
	 * This method executes the web request.
	 */
	public void run();
}
