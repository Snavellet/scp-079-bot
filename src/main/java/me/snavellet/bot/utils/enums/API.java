package me.snavellet.bot.utils.enums;

public enum API {

	CAT("https://api.thecatapi.com/v1/images/search"),
	CAT_FACT("https://cat-fact.herokuapp.com/facts/random"),
	DOG("https://random.dog/woof.json"),
	WIT_MESSAGE("https://api.wit.ai/message?q="),
	URL_SHORTEN("https://cleanuri.com/api/v1/shorten"),
	MEME("https://meme-api.herokuapp.com/gimme?nsfw=false"),
	ERROR("an error occurred, please try again later!");

	private final String value;

	API(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
