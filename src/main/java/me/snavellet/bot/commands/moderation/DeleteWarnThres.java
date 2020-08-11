package me.snavellet.bot.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.utils.CommandUtils;
import me.snavellet.bot.utils.db.WarningThresholdUtilsDB;
import net.dv8tion.jda.api.Permission;

public class DeleteWarnThres extends Command {

	public DeleteWarnThres() {
		this.name = "deletewarnthres";
		this.aliases = new String[]{"rmwarnthres", "removewarnthres"};
		this.help = "Removes the warning threshold if it exists.";
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
		this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};
	}

	@Override
	protected void execute(CommandEvent event) {
		WarningThresholdUtilsDB warningThresholdUtils =
				new WarningThresholdUtilsDB(event);

		if(warningThresholdUtils.deleteThreshold()) {
			warningThresholdUtils.reply("I've successfully removed the warning " +
					"threshold!");
		} else {
			warningThresholdUtils.reply("there is no warning threshold set for this " +
					"server!");
		}
	}
}
