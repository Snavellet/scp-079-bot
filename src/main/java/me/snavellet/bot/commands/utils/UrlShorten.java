package me.snavellet.bot.commands.utils;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.entities.http.utils.cleanUri.ShortenedUrlDetails;
import me.snavellet.bot.utils.CommandUtils;
import me.snavellet.bot.utils.HttpUtils;
import me.snavellet.bot.utils.enums.Api;
import net.dv8tion.jda.api.entities.User;
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
		this.cooldown = 3;
		this.help = "Shortens any url given.";
	}

	@Override
	protected void execute(@NotNull CommandEvent event) {

		HttpUtils<ShortenedUrlDetails> http =
				new HttpUtils<>(Api.URL_SHORTEN.getValue(), ShortenedUrlDetails.class);

		Optional<List<String>> args = CommandUtils.getArgs(event.getArgs());

		User author = event.getAuthor();

		if(args.isEmpty()) {
			CommandUtils.reply(author.getId(), event.getChannel(),
					CommandUtils.ARGUMENTS_MISSING);
		} else {
			String json = "{\"url\": \"" + args.get().get(0) + "\"}";

			http.asynchronousPost(json, new Callback() {
				@Override
				public void onFailure(@NotNull Call call, @NotNull IOException e) {
					CommandUtils.reply(author.getId(), event.getChannel(),
							Api.ERROR.getValue());
				}

				@Override
				public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

					ShortenedUrlDetails result = http.fromJson(response.body().string());

					if(result.getError() != null) {
						CommandUtils.reply(author.getId(), event.getChannel(),
								"please provide a valid URL!");
					} else {
						CommandUtils.reply(author.getId(), event.getChannel(),
								"here's your shortened link: " + result.getResultUrl());
					}
				}
			});
		}
	}
}
