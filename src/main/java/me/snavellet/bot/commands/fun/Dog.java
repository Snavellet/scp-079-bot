package me.snavellet.bot.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.entities.http.animals.randomDog.RandomDog;
import me.snavellet.bot.utils.CommandUtils;
import me.snavellet.bot.utils.HttpUtils;
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
		this.cooldown = 3;
	}

	@Override
	protected void execute(@NotNull CommandEvent event) {

		Color color = CommandUtils.getRandomItem(
				Color.GRAY,
				Color.YELLOW,
				Color.CYAN,
				Color.pink
		);

		User author = event.getAuthor();

		HttpUtils<RandomDog> http = new HttpUtils<>("https://random.dog/woof.json",
				RandomDog.class);

		http.asynchronousGet(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
				CommandUtils.reply(author.getId(), event.getChannel(), "an error " +
						"occurred, please try again later!");
			}

			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

				RandomDog dog = http.fromJson(response.body().string());

				MessageEmbed embed = new EmbedBuilder()
						.setImage(dog.getUrl())
						.setColor(color)
						.setAuthor(author.getAsTag(), null, author.getEffectiveAvatarUrl())
						.setFooter("Have this sentient dog made by me!")
						.build();

				event.getChannel().sendMessage(embed).submit();
			}
		});


	}
}
