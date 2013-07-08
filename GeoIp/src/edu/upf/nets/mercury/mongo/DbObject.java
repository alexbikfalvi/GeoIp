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

package edu.upf.nets.mercury.mongo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * A class representing a database object to be stored in MongoDB.
 * @author Alex
 *
 */
public class DbObject {
	private ObjectId _id = null;
	
	/**
	 * Creates a MongoDB object corresponding to the current object. 
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public final BasicDBObject get() throws IllegalArgumentException, IllegalAccessException {
		// Create an empty database object.
		BasicDBObject object = new BasicDBObject();
		// Set the object ID field, if the current field.
		if (null != this._id) {
			object.append("_id", this._id);
		}
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
		// Get the object ID.
		Object id = object.get("_id");
		if (null != id) {
			this._id = (ObjectId) id;
		}
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
		// If the object is null, return null.
		if (null == object) return null;
		// Create a new instance of the target class.
		T value = c.newInstance();
		// Set all fields of the value object.
		value.set(object);
		// Return value.
		return value;
	}
}
