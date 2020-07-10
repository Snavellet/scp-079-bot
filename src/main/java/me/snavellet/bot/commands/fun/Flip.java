package me.snavellet.bot.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.utils.CommandUtils;

public class Flip extends Command {

	public Flip() {
		this.name = "flip";
		this.help = "Flips a coin and get either heads or tails";
		this.aliases = new String[]{"flipcoin", "coinflip"};
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
	}

	@Override
	protected void execute(CommandEvent event) {

		CommandUtils commandUtils = new CommandUtils(event);

		String choice = CommandUtils.getRandomItem(
				"heads",
				"tails"
		);

		commandUtils.reply(choice + ".");
	}
}
