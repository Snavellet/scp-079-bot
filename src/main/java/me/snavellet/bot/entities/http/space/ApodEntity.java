package me.snavellet.bot.entities.http.space;

import com.google.gson.annotations.SerializedName;

public class ApodEntity {

	private final String title;

	private final String explanation;

	@SerializedName("hdurl")
	private final String hdPicture;

	public ApodEntity(String title, String explanation, String hdPicture) {
		this.title = title;
		this.explanation = explanation;
		this.hdPicture = hdPicture;
	}

	public String getTitle() {
		return title;
	}

	public String getExplanation() {
		return explanation;
	}

	public String getHdPicture() {
		return hdPicture;
	}
}
