package me.snavellet.bot.entities.http.utils.cleanUri;

import com.google.gson.annotations.SerializedName;

public class ShortenedUrlDetails {

	@SerializedName("result_url")
	private final String resultUrl;

	private final String error;

	public ShortenedUrlDetails(String resultUrl, String error) {
		this.resultUrl = resultUrl;
		this.error = error;
	}

	public String getResultUrl() {
		return resultUrl;
	}

	public String getError() {
		return error;
	}
}
