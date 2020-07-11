package me.snavellet.bot.commands.general;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
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

		Color color = CommandUtils.getRandomItem(
				Color.CYAN,
				Color.RED,
				Color.GREEN,
				Color.PINK,
				Color.MAGENTA
		);

		Guild guild = event.getGuild();

		List<Member> members = guild.getMembers();

		EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Server Information")
				.setThumbnail(guild.getIconUrl())
				.setColor(color)
				.addField("Server Name", guild.getName(), false)
				.addField("Owner Name", Objects
						.requireNonNull(guild.getOwner())
						.getUser().getName(), false)
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


//		EmbedBuilder newEmbed = new EmbedBuilder()
//				.setTitle("Server Information")
//				.setThumbnail(guild.getIconUrl())
//				.setColor(color);
//
//		List<MessageEmbed.Field> newFields = new ArrayList<>();
//
//		final String ZERO_WIDTH_SPACE = "\u200E";
//
//		int j = 0;
//
//		for(int i = 0; i < embed.getFields().size(); i++) {
//			if((i != 0 && i != j) && i % 2 == 0) {
//				newFields.add(new MessageEmbed.Field(ZERO_WIDTH_SPACE,
//						ZERO_WIDTH_SPACE, true));
//				newFields.add(new MessageEmbed.Field(ZERO_WIDTH_SPACE,
//						ZERO_WIDTH_SPACE, true));
//				newFields.add(new MessageEmbed.Field(ZERO_WIDTH_SPACE,
//						ZERO_WIDTH_SPACE, true));
//				newFields.add(new MessageEmbed.Field(ZERO_WIDTH_SPACE,
//						ZERO_WIDTH_SPACE, true));
//			} else {
//				newFields.add(embed.getFields().get(i));
//				j += 1;
//			}
//		}

//		newFields.forEach(newEmbed::addField);
//
//		System.out.println(newEmbed.getFields());


//		event.getChannel().sendMessage(newEmbed.build()).submit();
		event.getChannel().sendMessage(embed.build()).submit();
	}
}
