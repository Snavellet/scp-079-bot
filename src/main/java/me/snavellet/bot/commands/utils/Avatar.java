package me.snavellet.bot.commands.utils;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.utils.CommandUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

public class Avatar extends Command {

	public Avatar() {
		this.name = "avatar";
		this.aliases = new String[]{"av", "pfp"};
		this.cooldown = 3;
		this.help = "Gets the avatar of a mentioned member.";
	}

	@Override
	protected void execute(@NotNull CommandEvent event) {

		User author = event.getAuthor();

		Color color = CommandUtils.getRandomItem(
				Color.CYAN,
				Color.ORANGE,
				Color.GREEN,
				Color.MAGENTA
		);

		EmbedBuilder embedBuilder = new EmbedBuilder()
				.setDescription("Avatar:")
				.setColor(color);

		String avatarSizeQuery = "?size=1024";

		List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

		MessageEmbed embed;

		if(!(mentionedMembers.size() >= 1)) {
			embed = embedBuilder
					.setTitle(author.getAsTag())
					.setImage(author.getEffectiveAvatarUrl() + avatarSizeQuery)
					.build();
		} else {
			User user = mentionedMembers.get(0).getUser();

			embed = embedBuilder
					.setTitle(user.getAsTag())
					.setImage(user.getEffectiveAvatarUrl() + avatarSizeQuery)
					.build();
		}

		event.reply(embed);

	}
}
