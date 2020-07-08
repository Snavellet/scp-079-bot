package me.snavellet.bot.entities.http.fun.memes;

public class RandomMeme {

	private final String postLink;
	private final String title;
	private final String url;

	public RandomMeme(String postLink, String title, String url) {
		this.postLink = postLink;
		this.title = title;
		this.url = url;
	}

	public String getPostLink() {
		return postLink;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}
}
