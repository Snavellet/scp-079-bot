package me.snavellet.bot.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.entities.http.animals.randomDog.RandomDog;
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

public class Dog extends Command {

	public Dog() {
		this.name = "dog";
		this.help = "Gets a random dog image.";
		this.aliases = new String[]{"doggo"};
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
	}

	@Override
	protected void execute(@NotNull CommandEvent event) {

		CommandUtils commandUtils = new CommandUtils(event);

		Color color = CommandUtils.getRandomItem(
				Color.GRAY,
				Color.YELLOW,
				Color.CYAN,
				Color.pink
		);

		User author = event.getAuthor();

		HttpUtils<RandomDog> http = new HttpUtils<>(Api.DOG.getValue(),
				RandomDog.class);

		http.asynchronousGet(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
				commandUtils.reply(Api.ERROR.getValue());
			}

			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

				assert response.body() != null;
				RandomDog dog = http.fromJson(response.body().string());

				MessageEmbed embed = new EmbedBuilder()
						.setImage(dog.getUrl())
						.setColor(color)
						.setAuthor(author.getName(), null, author.getEffectiveAvatarUrl())
						.setFooter("Have this sentient dog made by me!")
						.build();

				event.getChannel().sendMessage(embed).submit();
			}
		});


	}
}
