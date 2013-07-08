/* 
 * Copyright (C) 2013 Alex Bikfalvi
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package edu.upf.nets.mercury.geoip;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;

import com.maxmind.geoip.LookupService;

import edu.upf.nets.mercury.geoip.net.AsyncWebRequest;
import edu.upf.nets.mercury.geoip.net.AsyncWebState;
import edu.upf.nets.mercury.geoip.net.IAsyncCallback;
import edu.upf.nets.mercury.geoip.net.IAsyncResult;

/**
 * A class representing a specific GeoIP database
 * @author Alex
 *
 */
public class GeoIpDatabase implements IAsyncCallback {
	
	private String name = null; 
	private LookupService service = null;
	
	private AsyncWebRequest request = new AsyncWebRequest(); 
	
	/**
	 * Creates a new GeoIP database instance.
	 * @param The collection name used by this database.
	 */
	public GeoIpDatabase(String name) {
		this.name = name;
	}
	
	public final LookupService getService() {
		return this.service;
	}
	
	public void load() {
		
	}
	
	public void save() {
		
	}
	
	public void close() {
		this.request.close();
	}
	
	/**
	 * Updates the current database using data from the specified URL. The download is performed
	 * asynchronously. 
	 * @param urlString The url from where to download the GeoIP database.
	 * @param callback The callback object.
	 * @return The result of the asyncrhonous operation.
	 * @throws MalformedURLException
	 */
	public IAsyncResult update(String urlString, IAsyncCallback callback) throws MalformedURLException {
		// Create the URL for this request.
		URL url = new URL(urlString);
		// Execute the asynchronous request for the specified URL, using the user callback object as user state.
		return this.request.execute(url, this, callback);
	}

	/**
	 * An event handler called when the asynchronous web request has completed execution.
	 * @param The asynchronous result.
	 */
	public void callback(IAsyncResult result) {
		// Get the state of the asynchronous request.
		AsyncWebState state = (AsyncWebState)result;
		try {
			// Try and process the data.
			this.process(state.getResponse());
		}
		catch (Exception e) {
			// Catch all exceptions.
		}
		finally {
			// Get the user callback function.
			IAsyncCallback callback = (IAsyncCallback)state.getState();
			// If the callback is not null.
			if (null != callback) {
				// Call the user callback method.
				callback.callback(result);
			}
		}
	}
	
	/**
	 * Processes the GZIP data and loads it into the current database.
	 * @param data The GeoIP data.
	 */
	private void process(byte[] data) throws GeoIpException {
		// Create an input stream from the byte array data.
		InputStream inputStream = new ByteArrayInputStream(data);
		
		try {
			// Create a GZIP input stream.
			GZIPInputStream zipStream = new GZIPInputStream(inputStream);
			// Create a temporary file for this database.
			File file = File.createTempFile(this.name, ".tmp");
			file.deleteOnExit();
			// Copy the data from.
			IOUtils.copy(zipStream, new FileOutputStream(file));
			// Location service.
			this.service = new LookupService(file);
		}
		catch (IOException exception) {
			throw new GeoIpException("Could not UNZIP the received data file.", exception);
		}
	}
}
