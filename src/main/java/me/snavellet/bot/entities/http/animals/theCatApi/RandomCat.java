package me.snavellet.bot.entities.http.animals.theCatApi;

@SuppressWarnings("unused")
public class RandomCat {

	private final String url;

	public RandomCat(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
