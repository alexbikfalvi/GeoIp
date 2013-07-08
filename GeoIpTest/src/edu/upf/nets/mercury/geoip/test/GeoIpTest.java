package edu.upf.nets.mercury.geoip.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import com.maxmind.geoip.Country;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

import edu.upf.nets.mercury.mongo.DbClient;

import edu.upf.nets.mercury.geoip.GeoIp;
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
		
		test.executeGeoIp();
	}
	
	public void executeAsyncWeb() {
		try {
			// Create the request URL.
			URL url = new URL("http://www.google.com/");
			
			// Create the web request.
			AsyncWebRequest request = new AsyncWebRequest();
			
			// Begin the request.
			IAsyncResult result = request.execute(url, this, null);
			
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
	
	public void executeMaxMind() {
		try {
			LookupService ls = new LookupService("c:/Users/Alex/Projects/Mercury/GeoIp/data/GeoLiteCity.dat");
			
			//Country country = ls.getCountry("151.38.39.114");
			Location location = ls.getLocation("151.38.39.114");
			
			//System.out.println(country.getCode());
			//System.out.println(country.getName());
			System.out.println(location.countryName);
			System.out.println(location.countryName);
			System.out.println(location.countryCode);
			System.out.println(location.latitude);
			System.out.println(location.longitude);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void executeGeoIp() {
		// The MongoDB client object.
		DbClient db;
		try {
			// Create the MongoDB client object.
			db = new DbClient("localhost", "geoip");
			try {
				// Create the GeoIP database object.
				GeoIp geoIp = new GeoIp(db);
				
				// Update a database with data from the GeoIP database.
				try {
					// Execute the update without a callback function.
					IAsyncResult result = geoIp.getDatabaseCountryIpv4().update(geoIp.getConfig().urlCountryIpv4, null);
					// Wait for the update to complete.
					result.asyncWait();
					Country country = geoIp.getDatabaseCountryIpv4().getService().getCountry("151.38.39.114");
					System.out.println(country.getName());
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				catch (MalformedURLException e) {
					e.printStackTrace();
				}
				
				// Close the GeoIP database object.
				geoIp.close();
			}
			finally {
				// Close the MongoDB database object.
				db.close();
			}
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void callback(IAsyncResult result) {
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
