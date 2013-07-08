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

/**
 * A class representing a GeoIP exception.
 * @author Alex
 *
 */
public class GeoIpException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new GeoIP exception instance with the specified message.
	 * @param message The exception message.
	 */
	public GeoIpException(String message) {
		super(message);
	}
	
	/**
	 * Creates a new GeoIP exception instance with the specified message and inner exception.
	 * @param message The exception message.
	 * @param exception The inner exception.
	 */
	public GeoIpException(String message, Exception exception) {
		super(message, exception);
	}
}
