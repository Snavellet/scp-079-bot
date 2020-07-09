package me.snavellet.bot.listeners;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import org.jetbrains.annotations.NotNull;

public class CommandListener implements com.jagrosh.jdautilities.command.CommandListener {

	@Override
	public void onCommand(@NotNull CommandEvent event, Command command) {
		event.getClient().cleanCooldowns();
	}
}
