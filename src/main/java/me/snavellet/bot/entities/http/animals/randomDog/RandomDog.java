package me.snavellet.bot.entities.http.animals.randomDog;

public class RandomDog {

	private final int fileSizeBytes;
	private final String url;

	public RandomDog(int fileSizeBytes, String url) {
		this.fileSizeBytes = fileSizeBytes;
		this.url = url;
	}

	public int getFileSizeBytes() {
		return fileSizeBytes;
	}

	public String getUrl() {
		return url;
	}
}
