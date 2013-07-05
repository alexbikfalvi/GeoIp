package edu.upf.nets.mercury.geoip.data;

import java.net.UnknownHostException;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

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
	
	/**
	 * Finds one object in the specified collection.
	 * @param c The class type for the searched object.
	 * @param name The collection name.
	 * @return The object from the collection, or null if the collection is empty.
	 * @throws DbException
	 */
	public final <T extends DbObject> T findOne(Class<T> c, String name) throws DbException {
		try {
			// Get the collection with the specified name.
			DBCollection collection = this.database.getCollection(name);
			// If the collection does not exist.
			if (null == collection) {
				// Throw a database exception.
				throw new DbException("The collection \'" + name + "\' was not found.");
			}
			else {
				// Return 
				return DbObject.create(collection.findOne(), c);
			}
		}
		catch (Exception exception) {
			// Throw a database exception.
			throw new DbException("The database operation failed.", exception);
		}
	}

	/**
	 * Inserts the database object in the specified collection. The collection is created if
	 * it does not exist.
	 * @param name The collection name.
	 * @param object The object to insert.
	 * @throws DbException
	 */
	public final void insert(String name, DbObject object) throws DbException {
		try {
			// Get the collection with the specified name.
			DBCollection collection = this.database.getCollection(name);
			// If the collection does not exist.
			if (null == collection) {
				// Create the collection with the specified object.
				collection = this.database.createCollection(name, object.get());
			}
			else {
				// Insert the element into the existing collection.
				collection.insert(object.get());
			}
		}
		catch (Exception exception) {
			// Throw a database exception.
			throw new DbException("The database operation failed.", exception);
		}
	}
	
	/**
	 * Returns the number of objects in the specified collection.
	 * @param name The collection name.
	 * @return The number of elements in the collection, or zero if the collection does not exist.
	 * @throws DbException
	 */
	public final long count(String name) throws DbException {
		try {
			// Get the collection with the specified name.
			DBCollection collection = this.database.getCollection(name);
			// If the collection does not exist.
			if (null == collection) {
				// Return zero.
				return 0;
			}
			else {
				// Otherwise, return the collection count.
				return collection.count();
			}
		}
		catch (Exception exception) {
			// Throw a database exception.
			throw new DbException("The database operation failed.", exception);
		}
	}
}
