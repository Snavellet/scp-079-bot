package me.snavellet.bot.listeners;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.utils.CommandUtils;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import org.jetbrains.annotations.NotNull;

public class CommandListener implements com.jagrosh.jdautilities.command.CommandListener {

	@Override
	public void onCommandException(@NotNull CommandEvent event, Command command, Throwable throwable) {
		if(throwable instanceof HierarchyException) {
			CommandUtils commandUtils = new CommandUtils(event);

			commandUtils.reply("can't modify a member with higher or equal highest role" +
					" than myself!");
		} else {
			throwable.printStackTrace();
		}
	}
}
