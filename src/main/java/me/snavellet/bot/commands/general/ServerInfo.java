package me.snavellet.bot.commands.general;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.utils.ColorUtils;
import me.snavellet.bot.utils.CommandUtils;
import me.snavellet.bot.utils.UserUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class ServerInfo extends Command {

	public ServerInfo() {
		this.name = "serverinfo";
		this.aliases = new String[]{"infoserver", "serverabout", "aboutserver"};
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
		this.help = "Gets the info of this server";
	}

	@Override
	protected void execute(@NotNull CommandEvent event) {

		Color color = ColorUtils.getRandomColor();

		Guild guild = event.getGuild();

		List<Member> members = guild.getMembers();

		EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Server Information")
				.setThumbnail(guild.getIconUrl())
				.setColor(color)
				.addField("Server Name", guild.getName(), false)
				.addField("Owner Name", Objects
						.requireNonNull(guild.getOwner())
						.getUser().getAsTag(), false)
				.addField("Channel Count", String.valueOf(guild
						.getChannels()
						.size()), false)
				.addField("Total Members", String.valueOf(members.size()), false)
				.addField("Total Bots", String.valueOf(members
						.parallelStream()
						.filter(member -> member
								.getUser()
								.isBot())
						.count()), false)
				.addField("Total Humans", String.valueOf(members
						.parallelStream()
						.filter(member -> !member
								.getUser()
								.isBot())
						.count()), false)
				.addField("Region", guild.getRegionRaw(), false)
				.addField("Created At", UserUtils.formatDate(guild.getTimeCreated()),
						true);

		event.getChannel().sendMessage(embed.build()).submit();
	}
}
