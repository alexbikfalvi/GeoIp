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

import edu.upf.nets.mercury.mongo.DbObject;


/**
 * A class representing the configuration object stored in the MongoDb database.
 * @author Alex
 *
 */
public class GeoIpConfig extends DbObject {
	private final static String urlCountryIpv4Default = "http://geolite.maxmind.com/download/geoip/database/GeoLiteCountry/GeoIP.dat.gz";
	private final static String urlCountryIpv6Default = "http://geolite.maxmind.com/download/geoip/database/GeoIPv6.dat.gz";
	private final static String urlCityIpv4Default = "http://geolite.maxmind.com/download/geoip/database/GeoLiteCity.dat.gz";
	private final static String urlCityIpv6Default = "http://geolite.maxmind.com/download/geoip/database/GeoLiteCityv6-beta/GeoLiteCityv6.dat.gz";
	private final static String urlAsIpv4Default = "http://download.maxmind.com/download/geoip/database/asnum/GeoIPASNum.dat.gz";
	private final static String urlAsIpv6Default = "http://download.maxmind.com/download/geoip/database/asnum/GeoIPASNumv6.dat.gz";

	private final static String nameCountryIpv4Default = "CountryIpv4";
	private final static String nameCountryIpv6Default = "CountryIpv6";
	private final static String nameCityIpv4Default = "CityIpv4";
	private final static String nameCityIpv6Default = "CityIpv6";
	private final static String nameAsIpv4Default = "AsIpv4";
	private final static String nameAsIpv6Default = "AsIpv6";
	
	// Public fields.
	
	public String urlCountryIpv4;
	public String urlCountryIpv6;
	public String urlCityIpv4;
	public String urlCityIpv6;
	public String urlAsIpv4;
	public String urlAsIpv6;
	
	public String nameCountryIpv4;
	public String nameCountryIpv6;
	public String nameCityIpv4;
	public String nameCityIpv6;
	public String nameAsIpv4;
	public String nameAsIpv6;
	
	/**
	 * Creates a new configuration instance using the default data and having the current date.
	 */
	public GeoIpConfig() {
		this.urlCountryIpv4 = GeoIpConfig.urlCountryIpv4Default;
		this.urlCountryIpv6 = GeoIpConfig.urlCountryIpv6Default;
		this.urlCityIpv4 = GeoIpConfig.urlCityIpv4Default;
		this.urlCityIpv6 = GeoIpConfig.urlCityIpv6Default;
		this.urlAsIpv4 = GeoIpConfig.urlAsIpv4Default;
		this.urlAsIpv6 = GeoIpConfig.urlAsIpv6Default;
		
		this.nameCountryIpv4 = GeoIpConfig.nameCountryIpv4Default;
		this.nameCountryIpv6 = GeoIpConfig.nameCountryIpv6Default;
		this.nameCityIpv4 = GeoIpConfig.nameCityIpv4Default;
		this.nameCityIpv6 = GeoIpConfig.nameCityIpv6Default;
		this.nameAsIpv4 = GeoIpConfig.nameAsIpv4Default;
		this.nameAsIpv6 = GeoIpConfig.nameAsIpv6Default;
	}
}
