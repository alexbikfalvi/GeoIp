package edu.upf.nets.mercury.geoip;

import org.apache.log4j.Logger;

import com.mongodb.DBObject;
import edu.upf.nets.mercury.geoip.data.DbClient;
import edu.upf.nets.mercury.geoip.data.DbConfig;

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
		if(this.db.count(DbClient.collectionConfig) != 0) {
			// Load the most recent configuration from the database.
			this.onLoadConfig();
		}
		else {
			// Use the default configuration.
			this.onDefaultConfig();
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
	
	public void updateGeoLiteCountry() {
		
	}
	
	// Protected methods.
	
	/**
	 * An event handler called when loading the geo-IP configuration from the database.
	 */
	protected void onLoadConfig() {
		// Get the most recent database configuration object.
	}
	
	/**
	 * An event handler called when saving the geo-IP configuration to the database.
	 */
	protected void onSaveConfig() {
		try {
			// Get the database object corresponding to the configuration object.
			DBObject object = this.config.get();
			// Save the configuration to the database.
			this.db.insert(DbClient.collectionConfig, object);		
		}
		catch (IllegalArgumentException exception) {
			GeoIp.log.error("Cannot save the GeoIP configuration to the MongoDB database (" + exception.getMessage() + ").", exception);
		}
		catch (IllegalAccessException exception) {
			GeoIp.log.error("Cannot save the GeoIP configuration to the MongoDB database (" + exception.getMessage() + ").", exception);
		}
		catch (Exception exception) {
			GeoIp.log.error("Cannot save the GeoIP configuration to the MongoDB database (" + exception.getMessage() + ").", exception);
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
