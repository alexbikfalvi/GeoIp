package edu.upf.nets.mercury.geoip.net;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A class used to execute an asynchronous request for a web resource. 
 * @author Alex
 *
 */
public class AsyncWebRequest {
	
	private ExecutorService executorService;
	
	/**
	 * Creates a new asynchronous web request instance.
	 */
	public AsyncWebRequest() {
		// Create a new cached thread pool.
		this.executorService = Executors.newFixedThreadPool(1);
	}
		
	/**
	 * Begins an asynchronous web request for the specified web URL.
	 * @param url The URL.
	 * @param callback The callback object.
	 * @return The result of the asynchronous operation.
	 */
	public IAsyncResult execute(URL url, IAsyncCallback callback) {
		// Create the state of the asynchronous operation.
		AsyncWebState asyncState = new AsyncWebState(url, callback);
		
		// Begin executing the request on the thread pool.
		this.executorService.execute(asyncState);
		
		// Return the asynchronous state as the asynchronous result.
		return asyncState;
	}
	
	/**
	 * Closes the current request.
	 */
	public void close() {
		this.executorService.shutdown();
	}
}
