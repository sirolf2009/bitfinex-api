package com.sirolf2009.bitfinex;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sirolf2009.bitfinex.exceptions.BitfinexInitializationException;

public abstract class Config {

	private static final String schemeKey = "scheme";
	private static final String schemeDefault = "https";
	private static final String hostKey = "host";
	private static final String hostDefault = "api.bitfinex.com";
	private static final String basePathKey = "path";
	private static final String basePathDefault = "/v1";
	private static final String keyFileLocKey = "keyfile";
	private static final String keyFileLocDefault = System.getProperty("user.home")+"/.bitfinex/keys";

	public static String scheme;
	public static String host;
	public static String basePath;
	public static String baseUrl;
	public static String keyFileLoc;
	
	public static Keys keys;
	
	public static String getOrCreateString(AbstractFileConfiguration configuration, String key, String defaultValue) {
		if(configuration.containsKey(key)) {
			return configuration.getString(key);
		} else {
			try {
				configuration.addProperty(key, defaultValue);
				configuration.save();
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}
	
	public static void loadConfig(String location) throws BitfinexInitializationException {
		try {
			new File(location).createNewFile();
			AbstractFileConfiguration config = new PropertiesConfiguration(location);
			scheme = getOrCreateString(config, schemeKey, schemeDefault);
			host = getOrCreateString(config, hostKey, hostDefault);
			basePath = getOrCreateString(config, basePathKey, basePathDefault);
			baseUrl = scheme+"://"+host+basePath;
			keyFileLoc = getOrCreateString(config, keyFileLocKey, keyFileLocDefault);
			File keyFile = new File(keyFileLoc);
			try {
				keys = new Gson().fromJson(FileUtils.readFileToString(keyFile), Keys.class);
			} catch(IOException e) {
				if(!keyFile.exists()) {
					keyFile.getParentFile().mkdirs();
					FileUtils.writeStringToFile(keyFile, new Gson().toJson(new Keys("public", "private")));
					throw new BitfinexInitializationException("Key file "+keyFile+" did not exist. I created it for you", e);
				} else {
					throw new BitfinexInitializationException("Key file "+keyFile+" could not be read", e);
				}
			} catch(JsonSyntaxException e) {
				throw new BitfinexInitializationException("Key file "+keyFile+" has an invalid format", e);
			}
		} catch (IOException e) {
			throw new BitfinexInitializationException("Could not read or create the properties file", e);
		} catch (ConfigurationException e) {
			throw new BitfinexInitializationException("Could not parse the properties file", e);
		}
	}
	
	static {
		try {
			loadConfig("./bitfinex_api.properties");
		} catch (BitfinexInitializationException e) {
			e.printStackTrace();
		}
	}

}
