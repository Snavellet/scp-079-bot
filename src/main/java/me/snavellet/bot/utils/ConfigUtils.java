package me.snavellet.bot.utils;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {

	private static final Properties properties = new Properties();
	private static final @Nullable InputStream propertiesFile =
			ConfigUtils.class.getClassLoader().getResourceAsStream(
					"config/config.properties");

	public ConfigUtils() throws IOException {
		properties.load(propertiesFile);
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

	public String getWitAccessToken() {
		return properties.getProperty("wit_access_token");
	}
}