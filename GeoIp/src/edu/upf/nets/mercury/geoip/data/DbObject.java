package edu.upf.nets.mercury.geoip.data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * A class representing a database object to be stored in MongoDB.
 * @author Alex
 *
 */
public class DbObject {
	/**
	 * Creates a MongoDB object corresponding to the current object. 
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public final BasicDBObject get() throws IllegalArgumentException, IllegalAccessException {
		// Create an empty database object.
		BasicDBObject object = new BasicDBObject();
		// Get the class of the current object.
		Class<?> c = this.getClass();
		// Get all field for the class of the current object.
		Field[] fields = c.getDeclaredFields();
		// Add all field values using reflection.
		for (Field field : fields) {
			// Get the modifiers of the current field.
			int modifiers = field.getModifiers();			
			// If the field is public, not static and not final.
			if(Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers)) {
				// Get the field name.
				String name = field.getName();
				// Get the field value for the current object.
				Object value = field.get(this);
				// Add the field information to the database object.
				object = object.append(name, value);
			}
		}
		// Return the object.
		return object;
	}
	
	/**
	 * Sets the fields of the current object from the specified database object.
	 * @param object The database object.
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public final void set(DBObject object) throws IllegalArgumentException, IllegalAccessException {
		// Get the class of the current object.
		Class<?> c = this.getClass();
		// Get all field for the class of the current object.
		Field[] fields = c.getDeclaredFields();
		// Add the value to all fields using reflection.
		for (Field field : fields) {
			// If the field is accessible, not static and not final.
			if(field.isAccessible() && !Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
				// Get the name of the current field.
				String name = field.getName();
				// Get the value of the database object.
				Object value = object.get(name);
				// Set the value to the field of the current object.
				field.set(this, value);
			}
		}
	}
	
	/**
	 * Creates a new object from the given database object and of the specified type.
	 * @param object The database object.
	 * @param c The type class.
	 * @return The new object.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public final static <T extends DbObject> T create(DBObject object, Class<T> c) throws InstantiationException, IllegalAccessException {
		// Create a new instance of the target class.
		T value = c.newInstance();
		// Set all fields of the value object.
		value.set(object);
		// Return value.
		return value;
	}
}
