package me.snavellet.bot.commands.general;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.utils.CommandUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Help extends Command {

	public Help() {
		this.name = "help";
		this.aliases = new String[]{"cmds", "cmd", "commands", "command"};
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
		this.help = "Displays categories that are full of commands or you can provide a" +
				" category then" +
				" it will list all the commands for the category.";
	}

	@Override
	protected void execute(CommandEvent event) {

		CommandUtils commandUtils = new CommandUtils(event);

		User author = event.getAuthor();
		MessageChannel channel = event.getChannel();

		Color color = CommandUtils.getRandomItem(
				Color.CYAN,
				Color.ORANGE,
				Color.GREEN,
				Color.MAGENTA
		);
		Optional<List<String>> args = commandUtils.getArgs();

		EmbedBuilder embedBuilder = new EmbedBuilder()
				.setAuthor(author.getName(), null,
						author.getEffectiveAvatarUrl())
				.setColor(color);

		if(args.isEmpty()) {
			embedBuilder
					.setTitle("Category List:");

			CommandUtils.getCommandsCategories().forEach(category -> {
				String capitalizedCategoryName =
						category.substring(0, 1).toUpperCase() + category.substring(1);
				embedBuilder.appendDescription(capitalizedCategoryName + "\n");
			});

			channel.sendMessage(embedBuilder.build()).submit();
		} else {
			try {
				CommandClient commandClient = event.getClient();

				List<Command> commands =
						commandUtils.getCommands(args.get().get(0).toLowerCase(),
								commandClient);

				commands.forEach(command -> {

					List<String> originalAliases = Arrays.asList(command.getAliases());

					String aliases = originalAliases.size() >= 1 ? " | " + String.join(
							", ",
							Arrays.asList(command.getAliases())) : "";

					Optional<String> optArguments =
							Optional.ofNullable(command.getArguments());

					String arguments = optArguments.isEmpty() ? "" :
							"\n\nUsage: " + event
									.getClient()
									.getPrefix() + command.getName() + " " + optArguments.get();

					embedBuilder.addField(
							commandClient.getPrefix() +
									command.getName().toLowerCase()
									+ aliases,
							command.getHelp() + arguments,
							false
					);
				});

				channel.sendMessage(embedBuilder.build()).submit();
			} catch(IllegalArgumentException illegalArgumentException) {
				commandUtils.reply("please provide a valid category!");
			}
		}
	}
}
