package me.snavellet.bot.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.entities.hibernate.Warning;
import me.snavellet.bot.utils.ColorUtils;
import me.snavellet.bot.utils.CommandUtils;
import me.snavellet.bot.utils.UserUtils;
import me.snavellet.bot.utils.db.WarningUtilsDB;
import me.snavellet.bot.utils.enums.WarningEnum;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Warnings extends Command {

	public Warnings() {
		this.name = "warnings";
		this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
		this.help = "Gets the list of warnings of a member.";
		this.aliases = new String[]{"infractions", "warns"};
		this.arguments = CommandUtils.ARGUMENTS_GENERAL;
	}

	@Override
	protected void execute(@NotNull CommandEvent event) {

		WarningUtilsDB warningUtils = new WarningUtilsDB(event);

		Color color = ColorUtils.getRandomColor();


		User author = event.getAuthor();

		Optional<List<String>> ids = warningUtils.getMentionsAndIdsAndNames();

		if(ids.isEmpty()) {
			warningUtils.reply(CommandUtils.ARGUMENTS_MISSING);
		} else if(ids.get().size() >= 1) {
			try {
				final String id = ids.get().get(0);
				Optional<List<Warning>> warnings =
						warningUtils.getAllWarnings(id);

				if(warnings.isPresent()) {
					final Member member = event.getGuild().getMemberById(id);

					assert member != null;
					EmbedBuilder embed = new EmbedBuilder()
							.setTitle("List of infractions for " + member
									.getUser()
									.getAsTag())
							.setColor(color)
							.setThumbnail(member.getUser().getEffectiveAvatarUrl())
							.setDescription("This user has " + warnings
									.get()
									.size() + " " +
									"infractions\n\n")
							.setAuthor(author.getName(),
									null, author.getEffectiveAvatarUrl());

					for(int i = 0, j = 1; i < warnings.get().size(); i++, j++) {

						Warning warning = warnings.get().get(i);

						String date =
								UserUtils.formatDate(new Date(warning.getDateMs())
										.toInstant()
										.atOffset(ZoneOffset.UTC));

						Optional<Member> optMember =
								Optional.ofNullable(event
										.getGuild()
										.getMemberById(warning.getModeratorId()));

						String newMember;

						if(optMember.isEmpty()) {
							newMember = "THIS_MEMBER_IS_INEXISTENT";
						} else {
							newMember = optMember.get().getUser().getAsTag();
						}

						String infraction = j +
								". Moderator: " +
								newMember +
								" | " +
								warning.getModeratorId() +
								"\n" +
								"--Reason: " +
								warning.getReason() +
								"\n--Time: " +
								date +
								"\n\n";

						embed.appendDescription(infraction);
					}

					event.getChannel().sendMessage(embed.build()).submit();
				} else {
					warningUtils.reply(WarningEnum.NO_WARNINGS.getValue());
				}
			} catch(NullPointerException exception) {
				warningUtils.reply(UserUtils.USER_INEXISTENT);
			}
		}
	}
}
