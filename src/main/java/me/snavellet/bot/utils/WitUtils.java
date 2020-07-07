package me.snavellet.bot.utils;

import me.snavellet.bot.entities.http.tech.ai.wit.EntitiesWit;
import me.snavellet.bot.entities.http.tech.ai.wit.MessageWit;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import okhttp3.Headers;
import org.decimal4j.util.DoubleRounder;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.IOException;
import java.util.Optional;

public class WitUtils {

	public static void checkSkid(@Nonnull MessageReceivedEvent event, Headers headers) throws IOException {
		Message message = event.getMessage();
		String messageContent = message.getContentDisplay();
		ConfigUtils configUtils = new ConfigUtils();

		Optional<Member> tempOwner =
				Optional.ofNullable(event
						.getGuild()
						.getMemberById(configUtils.getOwnerId()));

		if(tempOwner.isEmpty()) return;

		User owner = tempOwner.get().getUser();
		String guildId = event.getGuild().getId();
		String channelId = event.getChannel().getId();
		String messageId = message.getId();
		String messageLink = "https://discordapp.com/channels/" +
				guildId +
				"/" +
				channelId +
				"/" +
				messageId;

		EmbedBuilder skidEmbed = new EmbedBuilder()
				.addField("Tag: ", message.getAuthor().getAsTag(), false)
				.addField("MessageWit content: ", messageContent, false)
				.setAuthor(message.getAuthor().getName(), messageLink,
						message.getAuthor().getAvatarUrl())
				.setThumbnail(message.getGuild().getIconUrl());


		HttpUtils<MessageWit> http =
				new HttpUtils<>("https://api.wit.ai/message?q=" + messageContent,
						headers, MessageWit.class);

		try {
			MessageWit result =
					http.get();

			EntitiesWit entities = result.getEntities();

			if(entities.getPositive() != null) {
				owner.openPrivateChannel().submit().thenAcceptAsync(privateChannel -> {
					int confidence = (int) (DoubleRounder.round(entities
							.getPositive()
							.get(0)
							.getConfidence(), 2) * 100);

					MessageEmbed newEmbed = skidEmbed
							.setColor(Color.CYAN)
							.addField("Skid: ", "True", false)
							.addField("Confidence: ", confidence + "%", false).build();

					privateChannel.sendMessage(newEmbed).submit();
				});
			} else if(entities.getNegative() != null) {
				owner.openPrivateChannel().submit().thenAcceptAsync(privateChannel -> {
					int confidence = (int) (DoubleRounder.round(entities
							.getNegative()
							.get(0)
							.getConfidence(), 2) * 100);

					MessageEmbed newEmbed = skidEmbed
							.setColor(Color.RED)
							.addField("Skid: ", "False", false)
							.addField("Confidence: ", confidence + "%", false).build();

					privateChannel.sendMessage(newEmbed).submit();
				});
			}
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
