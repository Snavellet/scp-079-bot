package me.snavellet.bot.utils.enums;

import me.snavellet.bot.utils.ConfigUtils;

import java.io.IOException;

public enum API {

	CAT("https://api.thecatapi.com/v1/images/search"),
	CAT_FACT("https://cat-fact.herokuapp.com/facts/random"),
	DOG("https://random.dog/woof.json"),
	WIT_MESSAGE("https://api.wit.ai/message?q="),
	URL_SHORTEN("https://cleanuri.com/api/v1/shorten"),
	MEME("https://meme-api.herokuapp.com/gimme?nsfw=false"),
	ERROR("an error occurred, please try again later!"),
	APOD(getNasaEndpoint("https://api.nasa.gov/planetary" +
			"/apod?api_key="));
	private final String value;

	API(String value) {
		this.value = value;
	}

	public static String getNasaEndpoint(String url) {
		try {
			return url + new ConfigUtils().getNasaApiKey();
		} catch(IOException ioException) {
			ioException.printStackTrace();
			return null;
		}
	}

	public String getValue() {
		return value;
	}
}
