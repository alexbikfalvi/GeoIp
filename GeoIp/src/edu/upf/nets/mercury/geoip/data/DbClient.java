package edu.upf.nets.mercury.geoip.data;

import java.net.UnknownHostException;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

/**
 * A class representing the middle-layer to the MongoDB database. 
 * @author Alex
 *
 */
public class DbClient {
	private MongoClient client;
	private DB database;
	
	public final static String collectionConfig = "config";
	public final static String collectionGeo = "geo";
	
	/**
	 * Creates a new MongoDb middle-layer instance, opening a new connection to the server
	 * and opening the specified database.
	 * @param server The MongoDB server.
	 * @param database The MongoDB database.
	 * @throws UnknownHostException
	 */
	public DbClient(String server, String database) throws UnknownHostException {
		// Create a new Mongo client for the give server.
		this.client = new MongoClient(server);
		// Open the specified database.
		this.database = this.client.getDB(database);
	}
	
	/**
	 * Closes the MongoDB connection.
	 */
	public final void close() {
		this.client.close();
	}
	
	/**
	 * Returns the MongoDB database. 
	 * @return The MongoDB database.
	 */
	public final DB getDb() {
		return this.database;
	}
	
	public final void insert(String name, DBObject object) {
		// Get the collection with the specified name.
		DBCollection collection = this.database.getCollection(name);
		// If the collection does not exist.
		if (null == collection) {
			// Create the collection with the specified object.
			collection = this.database.createCollection(name, object);
		}
		else {
			// Insert the element into the existing collection.
			collection.insert(object);
		}
	}
	
	public final long count(String name) {
		// Get the collection with the specified name.
		DBCollection collection = this.database.getCollection(name);
		// If the collection does not exist.
		if (null == collection) {
			return 0;
		}
		else {
			return collection.count();
		}
	}
}
