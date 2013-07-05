package edu.upf.nets.mercury.geoip;

import org.apache.log4j.Logger;

import edu.upf.nets.mercury.geoip.data.DbClient;
import edu.upf.nets.mercury.geoip.data.DbConfig;
import edu.upf.nets.mercury.geoip.data.DbException;

/**
 * A class used to download and manage the GeoIP data.
 * @author Alex
 *
 */
public class GeoIp {
	private final static Logger log = Logger.getLogger(GeoIp.class);
	
	private DbClient db = null;
	
	private DbConfig config = null;
	
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
	 * Use this method to close the geo-IP instance by saving all changes to the database.
	 */
	public final void close() {
		// Save the configuration to the database.
		this.onSaveConfig();
	}
	
	// Protected methods.
	
	/**
	 * An event handler called when loading the geo-IP configuration from the database.
	 */
	protected void onLoadConfig() {
		// Get the most recent database configuration object.
		try {
			// Get the configuration object from the database.
			this.config = this.db.findOne(DbConfig.class, DbClient.collectionConfig);
			// If the configuration object is null, use the default configuration.
			if (null == this.config) {
				this.config = new DbConfig();
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
	 * An event handler called when saving the geo-IP configuration to the database.
	 */
	protected void onSaveConfig() {
		try {
			// Save the configuration to the database.
			this.db.insert(DbClient.collectionConfig, this.config);		
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
		this.config = new DbConfig();
	}
}
