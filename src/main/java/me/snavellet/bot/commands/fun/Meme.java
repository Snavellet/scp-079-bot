package me.snavellet.bot.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.entities.http.fun.memes.RandomMeme;
import me.snavellet.bot.utils.ColorUtils;
import me.snavellet.bot.utils.CommandUtils;
import me.snavellet.bot.utils.HttpUtils;
import me.snavellet.bot.utils.enums.API;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;

public class Meme extends Command {

	public Meme() {
		this.name = "meme";
		this.aliases = new String[]{"memes"};
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
		this.help = "Gets a random meme.";
	}

	@Override
	protected void execute(@NotNull CommandEvent event) {

		CommandUtils commandUtils = new CommandUtils(event);

		Color color = ColorUtils.getRandomColor();

		User author = event.getAuthor();

		HttpUtils<RandomMeme> http =
				new HttpUtils<>(API.MEME.getValue(), RandomMeme.class);

		http.asynchronousGet(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
				commandUtils.reply(API.ERROR.getValue());
			}

			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
				assert response.body() != null;
				RandomMeme result = http.fromJson(response.body().string());
				MessageEmbed embed = new EmbedBuilder()
						.setTitle(result.getTitle(), result.getPostLink())
						.setColor(color)
						.setAuthor(author.getName(), null, author.getEffectiveAvatarUrl())
						.setImage(result.getUrl())
						.build();

				event.getChannel().sendMessage(embed).submit();
			}
		});
	}
}
