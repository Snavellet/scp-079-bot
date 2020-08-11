package me.snavellet.bot.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.utils.CommandUtils;
import me.snavellet.bot.utils.UserUtils;
import me.snavellet.bot.utils.db.WarningUtilsDB;
import me.snavellet.bot.utils.enums.WarningEnum;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class ClearWarns extends Command {

	public ClearWarns() {
		this.name = "clearwarns";
		this.aliases = new String[]{"clearnwarn"};
		this.help = "Clear all the warns of a specific user.";
		this.arguments = CommandUtils.ARGUMENTS_GENERAL;
		this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
	}

	@Override
	protected void execute(@NotNull CommandEvent event) {

		WarningUtilsDB warningsUtils = new WarningUtilsDB(event);

		Optional<List<String>> ids = warningsUtils.getMentionsAndIdsAndNames();

		if(ids.isEmpty()) {
			warningsUtils.reply(UserUtils.ARGUMENTS_MISSING);
		} else if(ids.get().size() >= 1) {
			ids.get().forEach(id -> {
				Optional<Member> member = warningsUtils.memberExistsById(id);

				if(member.isEmpty()) {
					warningsUtils.reply(UserUtils.USER_INEXISTENT);
				} else {
					try {
						int numberOfWarns =
								warningsUtils.deleteAllWarns(member.get().getId());

						warningsUtils.reply("I cleared **" + numberOfWarns + "** " +
								"infraction(s) for ***" + member
								.get()
								.getUser()
								.getAsTag() + "***.");
					} catch(NullPointerException exception) {
						warningsUtils.reply(WarningEnum.NO_WARNINGS.getValue());
					}
				}
			});
		}
	}
}
