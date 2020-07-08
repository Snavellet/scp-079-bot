package me.snavellet.bot.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.entities.http.animals.theCatApi.RandomCat;
import me.snavellet.bot.utils.CommandUtils;
import me.snavellet.bot.utils.HttpUtils;
import me.snavellet.bot.utils.enums.Api;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;

public class Cat extends Command {

	public Cat() {
		this.name = "cat";
		this.aliases = new String[]{"catpic"};
		this.cooldown = 3;
		this.help = "Gets a random cat image.";
	}

	@Override
	protected void execute(CommandEvent event) {

		Color color = CommandUtils.getRandomItem(
				Color.CYAN,
				Color.BLUE,
				Color.YELLOW,
				Color.RED,
				Color.MAGENTA
		);

		User author = event.getAuthor();

		HttpUtils<RandomCat[]> http = new HttpUtils<>(Api.CAT.getValue(),
				RandomCat[].class);

		http.asynchronousGet(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
				CommandUtils.reply(author.getId(), event.getChannel(),
						Api.ERROR.getValue());
			}

			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
				RandomCat randomCat =
						http.fromJson(response.body().string())[0];

				MessageEmbed embed = new EmbedBuilder()
						.setTitle("Here's a sentient cat made by me!")
						.setColor(color)
						.setAuthor(author.getAsTag(), null, author.getEffectiveAvatarUrl())
						.setImage(randomCat.getUrl())
						.build();

				event.getChannel().sendMessage(embed).submit();
			}
		});
	}
}
