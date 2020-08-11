package me.snavellet.bot.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.utils.CommandUtils;
import me.snavellet.bot.utils.db.WarningThresholdUtilsDB;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.exceptions.ParsingException;

import java.util.List;
import java.util.Optional;

public class WarnThres extends Command {

	public WarnThres() {
		this.name = "warnthres";
		this.aliases = new String[]{"warningthreshold"};
		this.help = "Set the warning threshold for the server.";
		this.arguments = "[amount]";
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
		this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};
	}

	@Override
	protected void execute(CommandEvent event) {

		WarningThresholdUtilsDB warningThresholdUtilsDB =
				new WarningThresholdUtilsDB(event);

		Optional<List<String>> args = warningThresholdUtilsDB.getArgs();

		if(args.isEmpty()) {
			warningThresholdUtilsDB.reply(CommandUtils.ARGUMENTS_MISSING);
		} else {
			try {
				int parsedInteger = Integer.parseInt(args.get().get(0));

				if(parsedInteger >= 25 || parsedInteger < 1) {
					warningThresholdUtilsDB.reply("the minimum and maximum value to set" +
							" the " +
							"threshold is `1` and `25`!");
				} else {
					warningThresholdUtilsDB.reply("I've set the threshold to `" + warningThresholdUtilsDB
							.setThreshold(parsedInteger) + "`!");
				}
			} catch(ParsingException parsingException) {
				warningThresholdUtilsDB.reply("it must be a valid amount!");
			}
		}
	}
}
