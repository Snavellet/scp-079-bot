package me.snavellet.bot.commands.utils;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.entities.http.utils.cleanUri.ShortenedUrlDetails;
import me.snavellet.bot.utils.CommandUtils;
import me.snavellet.bot.utils.HttpUtils;
import me.snavellet.bot.utils.enums.Api;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UrlShorten extends Command {

	public UrlShorten() {
		this.name = "urlshorten";
		this.aliases = new String[]{"shortenurl"};
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
		this.help = "Shortens any url given.";
		this.arguments = "[link to shorten]";
	}

	@Override
	protected void execute(@NotNull CommandEvent event) {

		CommandUtils commandUtils = new CommandUtils(event);

		Optional<List<String>> args = commandUtils.getArgs();

		HttpUtils<ShortenedUrlDetails> http =
				new HttpUtils<>(Api.URL_SHORTEN.getValue(), ShortenedUrlDetails.class);


		if(args.isEmpty()) {
			commandUtils.reply(CommandUtils.ARGUMENTS_MISSING);
		} else {
			String json = "{\"url\": \"" + args.get().get(0) + "\"}";

			http.asynchronousPost(json, new Callback() {
				@Override
				public void onFailure(@NotNull Call call, @NotNull IOException e) {
					commandUtils.reply(Api.ERROR.getValue());
				}

				@Override
				public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

					assert response.body() != null;
					ShortenedUrlDetails result = http.fromJson(response.body().string());

					if(result.getError() != null) {
						commandUtils.reply("please provide a valid URL!");
					} else {
						commandUtils.reply("here's your shortened link: " + result.getResultUrl());
					}
				}
			});
		}
	}
}
