package me.snavellet.bot.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.utils.CommandUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class Ask extends Command {

	public Ask() {
		this.name = "ask";
		this.aliases = new String[]{"8ball", "question"};
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
		this.help = "The bot will respond to your questions, don't expect it to be an " +
				"AI.";
	}

	@Override
	protected void execute(@NotNull CommandEvent event) {

		CommandUtils commandUtils = new CommandUtils(event);

		String response = CommandUtils.getRandomItem(
				"perhaps.",
				"most definitely.",
				"I don't think so.",
				"suppose that you are asking a genius an idiotic question.",
				"please don't waste my time.",
				"yes.",
				"no.",
				"nah."
		);

		Optional<List<String>> args = commandUtils.getArgs();

		if(args.isEmpty()) {
			commandUtils.reply(CommandUtils.ARGUMENTS_MISSING);
		} else {
			commandUtils.reply(response);
		}
	}
}
