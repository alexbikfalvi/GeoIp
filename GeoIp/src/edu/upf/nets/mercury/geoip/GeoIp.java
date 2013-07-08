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

import org.apache.log4j.Logger;

import edu.upf.nets.mercury.mongo.DbClient;
import edu.upf.nets.mercury.mongo.DbException;

/**
 * A class used to download and manage the GeoIP data.
 * @author Alex
 *
 */
public class GeoIp {
	private final static Logger log = Logger.getLogger(GeoIp.class);
	
	private DbClient db = null;
	
	private GeoIpConfig config = null;
	
	private GeoIpDatabase dbCountryIpv4 = null;
	private GeoIpDatabase dbCountryIpv6 = null;
	private GeoIpDatabase dbCityIpv4 = null;
	private GeoIpDatabase dbCityIpv6 = null;
	private GeoIpDatabase dbAsIpv4 = null;
	private GeoIpDatabase dbAsIpv6 = null;
	
	public GeoIp(DbClient db) {
		// Save the database.
		this.db = db;
		// Check if there exists a configuration in the database.
		try {
			if(this.db.count(DbClient.collectionConfig) != 0) {
				// Load the most recent configuration from the database.
				this.onLoadConfig();
				// Log the operation.
				GeoIp.log.info("Loaded the GeoIP configuration from the MongoDB database.");
			}
			else {
				// Use the default configuration.
				this.onDefaultConfig();
				// Log the operation.
				GeoIp.log.info("Loaded the default GeoIP configuration because no data could be found in the MongoDB database.");
			}
			
			// Load the GeoIP databases.
			
			this.dbCountryIpv4 = new GeoIpDatabase(this.config.nameCountryIpv4);
			this.dbCountryIpv6 = new GeoIpDatabase(this.config.nameCountryIpv6);
			this.dbCityIpv4 = new GeoIpDatabase(this.config.nameCityIpv4);
			this.dbCityIpv6 = new GeoIpDatabase(this.config.nameCityIpv6);
			this.dbAsIpv4 = new GeoIpDatabase(this.config.nameAsIpv4);
			this.dbAsIpv6 = new GeoIpDatabase(this.config.nameAsIpv6);
		}
		catch (DbException exception) {
			// Use the default configuration.
			this.onDefaultConfig();
			// Log the operation.
			GeoIp.log.warn("Loaded the default GeoIP configuration because access to the MongoDB database failed.", exception);
		}
	}
	
	// Public methods.
	
	/**
	 * Returns the GeoIP country IPv4 database.
	 * @return The GeoIP database.
	 */
	public final GeoIpDatabase getDatabaseCountryIpv4() {
		return this.dbCountryIpv4;
	}
	
	/**
	 * Returns the GeoIP country IPv6 database.
	 * @return The GeoIP database.
	 */
	public final GeoIpDatabase getDatabaseCountryIpv6() {
		return this.dbCountryIpv6;
	}

	/**
	 * Returns the GeoIP city IPv4 database.
	 * @return The GeoIP database.
	 */
	public final GeoIpDatabase getDatabaseCityIpv4() {
		return this.dbCityIpv4;
	}
	
	/**
	 * Returns the GeoIP city IPv6 database.
	 * @return The GeoIP database.
	 */
	public final GeoIpDatabase getDatabaseCityIpv6() {
		return this.dbCityIpv6;
	}
	
	/**
	 * Returns the GeoIP AS IPv4 database.
	 * @return The GeoIP database.
	 */
	public final GeoIpDatabase getDatabaseAsIpv4() {
		return this.dbAsIpv4;
	}
	
	/**
	 * Returns the GeoIP AS IPv6 database.
	 * @return The GeoIP database.
	 */
	public final GeoIpDatabase getDatabaseAsIpv6() {
		return this.dbAsIpv6;
	}
	
	/**
	 * Returns the GeoIP configuration.
	 * @return The configuration object.
	 */
	public final GeoIpConfig getConfig() {
		return this.config;
	}
	
	/**
	 * Use this method to close the GeoIP instance by saving all changes to the database.
	 */
	public final void close() {
		// Close all databases.
		this.dbCountryIpv4.close();
		this.dbCountryIpv6.close();
		this.dbCityIpv4.close();
		this.dbCityIpv6.close();
		this.dbAsIpv4.close();
		this.dbAsIpv6.close();
		// Save the configuration to the database.
		this.onSaveConfig();
	}
	
	// Protected methods.
	
	/**
	 * An event handler called when loading the GeoIP configuration from the database.
	 */
	protected void onLoadConfig() {
		// Get the most recent database configuration object.
		try {
			// Get the configuration object from the database.
			this.config = this.db.findOne(GeoIpConfig.class, DbClient.collectionConfig);
			// If the configuration object is null, use the default configuration.
			if (null == this.config) {
				this.config = new GeoIpConfig();
			}
		}
		catch (Exception exception) {
			// If an exception occurred, use the default configuration.
			this.onDefaultConfig();
			// Log the event.
			GeoIp.log.warn("Cannot load the GeoIP configuration from the MongoDB database (" + exception.getMessage() + "). Using the default configuration.", exception);
		}
	}
	
	/**
	 * An event handler called when saving the GeoIP configuration to the database.
	 */
	protected void onSaveConfig() {
		try {
			// Save the configuration to the database.
			this.db.save(DbClient.collectionConfig, this.config);		
		}
		catch (Exception exception) {
			// Log the event.
			GeoIp.log.warn("Cannot save the GeoIP configuration to the MongoDB database (" + exception.getMessage() + ").", exception);
		}
	}
	
	/**
	 * An event handler called when using the default configuration.
	 */
	protected void onDefaultConfig() {
		// Create the default configuration.
		this.config = new GeoIpConfig();
	}
}
