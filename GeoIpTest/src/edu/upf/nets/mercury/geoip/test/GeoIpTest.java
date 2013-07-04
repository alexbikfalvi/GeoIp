package edu.upf.nets.mercury.geoip.test;

import java.net.MalformedURLException;
import java.net.URL;

import edu.upf.nets.mercury.geoip.net.AsyncWebRequest;
import edu.upf.nets.mercury.geoip.net.IAsyncCallback;
import edu.upf.nets.mercury.geoip.net.IAsyncResult;

/**
 * A class used to test the GeoIp pack
 * @author Alex
 *
 */
public class GeoIpTest implements IAsyncCallback {
	
	/**
	 * The main method.
	 * @param args
	 */
	public static void main(String[] args) {
		GeoIpTest test = new GeoIpTest();
		
		test.execute();
	}
	
	public void execute() {
		try {
			// Create the request URL.
			URL url = new URL("http://www.google.com/");
			
			// Create the web request.
			AsyncWebRequest request = new AsyncWebRequest();
			
			// Begin the request.
			IAsyncResult result = request.execute(url, this);
			
			// Wait for the request to complete.
			result.asyncWait();
			
			// Close the request.
			request.close();
		}
		catch(MalformedURLException exception) {
			System.err.println(exception.getMessage());
		}
		catch(Exception exception) {
			System.err.println(exception.getMessage());
		}		
	}
	
	@Override
	public void callback(IAsyncResult result) {
		// TODO Auto-generated method stub
		try {
			// Complete the request.
			String data = result.getResponseString();
			
			// Display the data.
			System.out.println(data);			
		}
		catch(Exception exception) {
			System.err.println(exception.getMessage());
		}
	}
}
