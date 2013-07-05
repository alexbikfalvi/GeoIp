package edu.upf.nets.mercury.geoip.data;

import java.util.Date;


/**
 * A class representing the configuration object stored in the MongoDb database.
 * @author Alex
 *
 */
public class DbConfig extends DbObject {
	private final static String urlCountryIpv4Default = "http://geolite.maxmind.com/download/geoip/database/GeoLiteCountry/GeoIP.dat.gz";
	private final static String urlCountryIpv6Default = "http://geolite.maxmind.com/download/geoip/database/GeoIPv6.dat.gz";
	private final static String urlCityIpv4Default = "http://geolite.maxmind.com/download/geoip/database/GeoLiteCity.dat.gz";
	private final static String urlCityIpv6Default = "http://geolite.maxmind.com/download/geoip/database/GeoLiteCityv6-beta/GeoLiteCityv6.dat.gz";
	private final static String urlAsIpv4Default = "http://download.maxmind.com/download/geoip/database/asnum/GeoIPASNum.dat.gz";
	private final static String urlAsIpv6Default = "http://download.maxmind.com/download/geoip/database/asnum/GeoIPASNumv6.dat.gz";

	// Public fields.
	
	private Date date;
	public String urlCountryIpv4;
	public String urlCountryIpv6;
	public String urlCityIpv4;
	public String urlCityIpv6;
	public String urlAsIpv4;
	public String urlAsIpv6;
	
	/**
	 * Creates a new configuration instance using the default data and having the current date.
	 */
	public DbConfig() {
		this.date = new Date();
		this.urlCountryIpv4 = DbConfig.urlCountryIpv4Default;
		this.urlCountryIpv6 = DbConfig.urlCountryIpv6Default;
		this.urlCityIpv4 = DbConfig.urlCityIpv4Default;
		this.urlCityIpv6 = DbConfig.urlCityIpv6Default;
		this.urlAsIpv4 = DbConfig.urlAsIpv4Default;
		this.urlAsIpv6 = DbConfig.urlAsIpv6Default;
	}
}
