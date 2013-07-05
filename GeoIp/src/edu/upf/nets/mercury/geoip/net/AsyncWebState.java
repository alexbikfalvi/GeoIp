package edu.upf.nets.mercury.geoip.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

/**
 * A class representing the state of an asynchronous operation.
 * @author Alex
 *
 */
public class AsyncWebState implements IAsyncResult, Runnable {
	
	// Request objects.
	private URL url;
	private IAsyncCallback callback;
	private Exception exception = null;
	private boolean completed = false;
	private final Object lock = new Object();
	
	// Response objects.
	private String responseEncoding = null;
	private byte[] responseData = null;
	
	/**
	 * Creates a new asynchronous web state instance.
	 * @param url The request URL.
	 * @param callback The callback object.
	 */
	public AsyncWebState(URL url, IAsyncCallback callback) {
		this.url = url;
		this.callback = callback;
	}
	
	/**
	 * Completes and returns the output data for this request.
	 * @return The output data.
	 */
	public byte[] getResponse() throws Exception {
		// If the asynchronous request has not completed, wait for it to complete.
		if (!this.completed) {
			this.asyncWait();
		}
		// If an exception occurred, throw the exception.
		if (null != this.exception) throw this.exception;
		// Return the output data.
		return this.responseData;
	}

	/**
	 * Returns the string output data for this request using the response encoding.
	 * @return The output data as string.
	 * @throws Exception
	 */	
	public String getResponseString() throws Exception {
		// If the asynchronous request has not completed, wait for it to complete.
		if (!this.completed) {
			this.asyncWait();
		}
		// If an exception occurred, throw the exception.
		if (null != this.exception) throw this.exception;
		// Convert the data from binary to a string.
		return IOUtils.toString(this.responseData, this.responseEncoding);
	}
	
	/**
	 * Returns the callback object of the asynchronous state.
	 * @return The callback object.
	 */
	public final IAsyncCallback getCallback() {
		return this.callback;
	}
	
	/**
	 * Returns the exception that occurred during the execution of the asynchronous operation.
	 * @return The exception.
	 */
	public final Exception getException() {
		return this.exception;
	}
		
	/**
	 * Returns the URL of the asynchronous state.
	 * @return The URL.
	 */
	public final URL getUrl() {
		return this.url;
	}
	
	/**
	 * Indicates whether the asynchronous operation has completed or not.
	 * @return True if the asynchronous operation has completed or false otherwise.
	 */
	public final boolean isCompleted() {
		synchronized (this.lock) {
			return this.completed;
		}
	}
	
	/**
	 * Waits for the asynchronous operation to complete.
	 * @throws InterruptedException
	 */
	public final void asyncWait() throws InterruptedException {
		synchronized (this.lock) {
			if(!this.completed) this.lock.wait();
			else return;
		}
	}
	
	/**
	 * Waits a specified time interval for the asynchronous operation to complete.
	 * @param timeout The time interval in milliseconds.
	 * @throws InterruptedException
	 */
	public final void asyncWait(long timeout) throws InterruptedException {
		synchronized (this.lock) {
			if(!this.completed) this.lock.wait(timeout);
			else return;
		}		
	}

	/**
	 * This method executes the web request.
	 */
	public final void run() {
		try {
			// Create a new connection for the given URL.
			HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();
			try
			{
				// Execute the request and get the input stream.
				InputStream inputStream = connection.getInputStream();
				// Set the response data.				
				this.responseData = IOUtils.toByteArray(inputStream);
				// Set the response encoding.
				this.responseEncoding = connection.getContentEncoding();
			}
			catch(IOException exception) {
				// Set the exception of the asynchronous state.
				this.exception = exception;
			}
			finally {
				// Close the connection.
				connection.disconnect();
			}
		}
		catch(IOException exception) {
			// Set the exception of the asynchronous state.
			this.exception = exception;
		}
		
		// Set completed to true.
		synchronized (this.lock) {
			this.completed = true;	
		}
		
		// Call the callback method, if not null.
		if (null != this.callback) {
			this.callback.callback(this);
		}
		
		// Notify all threads waiting on the lock object.
		synchronized (this.lock) {
			this.lock.notifyAll();
		}
	}
}
