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
import java.util.Optional;

public class Avatar extends Command {

	public Avatar() {
		this.name = "avatar";
		this.aliases = new String[]{"av", "pfp"};
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
		this.help = "Gets the avatar of a mentioned member.";
		this.arguments = CommandUtils.ARGUMENTS_GENERAL;
	}

	@Override
	protected void execute(@NotNull CommandEvent event) {

		CommandUtils commandUtils = new CommandUtils(event);

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

		Optional<List<String>> ids = commandUtils.getMentionsAndIdsAndNames();

		MessageEmbed embed;

		if(ids.isEmpty()) {
			commandUtils.reply(CommandUtils.ARGUMENTS_MISSING);
		} else {
			Optional<List<String>> args = commandUtils.getArgs();

			if(args.isPresent() && args.get().get(0).equalsIgnoreCase("me")) {
				embed = embedBuilder
						.setTitle(author.getAsTag())
						.setImage(author.getEffectiveAvatarUrl() + avatarSizeQuery)
						.build();
			} else {
				Optional<Member> member =
						Optional.ofNullable(event
								.getGuild()
								.getMemberById(ids.get().get(0)));

				if(member.isEmpty()) {
					commandUtils.reply("that user doesn't exist anymore!");
					return;
				}

				User user = member.get().getUser();

				embed = embedBuilder
						.setTitle(user.getAsTag())
						.setImage(user.getEffectiveAvatarUrl() + avatarSizeQuery)
						.build();

			}
			event.reply(embed);
		}
	}
}
