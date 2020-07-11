package me.snavellet.bot.utils;

import com.google.gson.Gson;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

@SuppressWarnings("unused")
public class HttpUtils<T> {

	private static final Gson gson = new Gson();
	private static final OkHttpClient client = new OkHttpClient();
	private static final MediaType JSON = MediaType.get("application/json; charset=utf8");
	private final Class<T> classValue;
	private String URL;
	private @Nullable Headers headers;
	private Request request;

	public HttpUtils(@NotNull String URL, Class<T> classValue) {
		this.URL = URL;
		this.request = new Request.Builder().url(URL).build();
		this.classValue = classValue;
	}

	public HttpUtils(String URL, Headers headers, Class<T> classValue) {
		this.URL = URL;
		this.classValue = classValue;
		checkHeaders(headers);
	}

	private void checkHeaders(@Nullable Headers headers) {
		if(headers != null && headers.size() >= 1) {
			this.headers = headers;
			this.request = new Request.Builder().headers(headers).url(URL).build();
		} else {
			this.request = new Request.Builder().url(URL).build();
		}
	}

	private @NotNull Request checkHeadersPost(@Nullable Headers headers,
	                                          @NotNull String json) {

		RequestBody requestBody = RequestBody.create(JSON, json);

		Request request;

		if(headers != null && headers.size() >= 1) {
			request = this.request
					.newBuilder()
					.headers(headers)
					.post(requestBody)
					.build();
		} else {
			request = this.request
					.newBuilder()
					.post(requestBody)
					.build();
		}

		return request;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(@NotNull String URL) {
		this.URL = URL;
		this.request = new Request.Builder().url(URL).build();
	}

	public T get() throws IOException {

		Response response = client.newCall(request).execute();
		assert response.body() != null;
		String responseBody = response.body().string();

		return this.fromJson(responseBody);
	}

	public void asynchronousGet(@NotNull Callback callback) {

		client.newCall(request).enqueue(callback);
	}

	public T post(@NotNull String json) throws IOException {

		Request request = checkHeadersPost(this.headers, json);

		Response response = client.newCall(request).execute();
		assert response.body() != null;
		return this.fromJson(response.body().string());
	}

	public void asynchronousPost(@NotNull String json, @NotNull Callback callback) {

		Request request = checkHeadersPost(this.headers, json);

		client.newCall(request).enqueue(callback);
	}

	public T fromJson(String jsonString) {

		return gson.fromJson(jsonString, classValue);
	}

	public String toJson() {

		return gson.toJson(classValue);
	}

}
