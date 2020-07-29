package me.snavellet.bot.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.entities.hibernate.Warning;
import me.snavellet.bot.entities.hibernate.WarningBuilder;
import me.snavellet.bot.utils.CommandUtils;
import me.snavellet.bot.utils.db.WarningUtilsDB;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class Warn extends Command {

	public Warn() {
		this.name = "warn";
		this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
		this.arguments = CommandUtils.ARGUMENTS_MODERATION;
		this.help = "Warn someone, the bot will automatically ban once the user reaches" +
				" its defined threshold.";
	}

	@Override
	protected void execute(@NotNull CommandEvent event) {

		WarningUtilsDB warningsUtils = new WarningUtilsDB(event);

		Optional<List<String>> ids = warningsUtils.getMentionsAndIdsAndNames();

		Guild guild = event.getGuild();

		User author = event.getAuthor();

		if(ids.isEmpty()) {
			warningsUtils.reply(CommandUtils.ARGUMENTS_MISSING);
		} else if(ids.get().size() >= 1) {
			Optional<String> reason = warningsUtils.getReason();

			if(reason.isEmpty()) {
				warningsUtils.reply(CommandUtils.REASON_MISSING);
			} else {
				ids.get().stream()
				   .filter(warningsUtils::memberExistsByIdBoolean)
				   .forEach(id -> {
					   Member member = guild.getMemberById(id);

					   assert member != null;
					   String message = "I warned `" + member.getUser().getAsTag() +
							   "` " +
							   "for `" + reason.get() + "`.";


					   Warning warning = new WarningBuilder()
							   .setGuildId(guild.getId())
							   .setUserId(id)
							   .setModeratorId(author.getId())
							   .setReason(reason.get())
							   .setDateMs(System.currentTimeMillis())
							   .createWarning();

					   warningsUtils.warnUser(warning);

					   warningsUtils.reply(message);
				   });
			}
		}
	}
}
