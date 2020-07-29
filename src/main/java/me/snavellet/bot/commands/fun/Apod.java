package me.snavellet.bot.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.entities.http.space.ApodEntity;
import me.snavellet.bot.utils.ColorUtils;
import me.snavellet.bot.utils.CommandUtils;
import me.snavellet.bot.utils.HttpUtils;
import me.snavellet.bot.utils.enums.API;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;

public class Apod extends Command {

	public Apod() {
		this.name = "apod";
		this.help = "Gets the astronomy picture of the day from NASA.";
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
	}

	@Override
	protected void execute(CommandEvent event) {

		CommandUtils commandUtils = new CommandUtils(event);

		Color color = ColorUtils.getRandomColor();

		HttpUtils<ApodEntity> http = new HttpUtils<>(API.APOD.getValue(),
				ApodEntity.class);

		http.asynchronousGet(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
				commandUtils.reply(API.ERROR.getValue());
			}

			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

				assert response.body() != null;
				ApodEntity apod = http.fromJson(response.body().string());

				MessageEmbed embed = new EmbedBuilder()
						.setImage(apod.getHdPicture())
						.setTitle(apod.getTitle())
						.setDescription(apod.getExplanation())
						.setColor(color)
						.setFooter("Have this awesome astronomy picture of the day!")
						.build();

				event.getChannel().sendMessage(embed).submit();
			}
		});
	}
}
