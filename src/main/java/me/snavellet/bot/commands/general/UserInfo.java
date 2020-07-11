package me.snavellet.bot.commands.general;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.utils.CommandUtils;
import me.snavellet.bot.utils.UserUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class UserInfo extends Command {

	public UserInfo() {
		this.name = "userinfo";
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
		this.aliases = new String[]{"infouser", "infomember", "memberinfo"};
		this.arguments = CommandUtils.ARGUMENTS_GENERAL;
		this.help = "Gets the info of a specified member.";
	}

	@Override
	protected void execute(@NotNull CommandEvent event) {

		CommandUtils commandUtils = new CommandUtils(event);

		Optional<List<String>> ids = commandUtils.getMentionsAndIdsAndNames();

		if(ids.isEmpty()) {
			commandUtils.reply(CommandUtils.ARGUMENTS_MISSING);
		} else if(ids.get().size() >= 1) {
			Optional<Member> member = commandUtils.memberExistsById(ids.get().get(0));

			if(member.isEmpty()) {
				commandUtils.reply(UserUtils.USER_INEXISTENT);
			} else {
				Color color = CommandUtils.getRandomItem(
						Color.PINK,
						Color.RED,
						Color.MAGENTA,
						Color.GREEN
				);

				User user = member.get().getUser();

				MessageEmbed embed = new EmbedBuilder()
						.setTitle(user.getAsTag())
						.setDescription("Information:")
						.addField("User ID:", member.get().getId(), false)
						.addField("Created At:", UserUtils.formatDate(member
								.get()
								.getTimeCreated()), false)
						.addField("Joined at:",
								UserUtils.formatDate(member.get().getTimeJoined()), false)
						.addField("Status:",
								String.valueOf(member
										.get()
										.getOnlineStatus()
										.getKey()), false)
						.setColor(color)
						.setThumbnail(user.getEffectiveAvatarUrl())
						.build();

				event.getChannel().sendMessage(embed).submit();
			}
		}
	}
}
