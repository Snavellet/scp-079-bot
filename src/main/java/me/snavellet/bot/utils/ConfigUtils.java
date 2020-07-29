package me.snavellet.bot.utils;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class ConfigUtils {

	private static final Properties properties = new Properties();
	private static final @Nullable InputStream propertiesFile =
			ConfigUtils.class.getClassLoader().getResourceAsStream(
					"config/config.properties");

	public ConfigUtils() throws IOException {
		properties.load(propertiesFile);
	}

	public static @Nullable URL getHibernateConfigFile() {
		return ConfigUtils.class.getClassLoader().getResource(
				"hibernate/hibernate.cfg.xml");
	}

	public String getToken() {
		return properties.getProperty("token");
	}

	public String getPrefix() {
		return properties.getProperty("default_prefix");
	}

	public String getOwnerId() {
		return properties.getProperty("owner_id");
	}

	public String getNasaApiKey() {
		return properties.getProperty("nasa_api_key");
	}
}
