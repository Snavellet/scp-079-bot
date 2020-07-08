package me.snavellet.bot.utils;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class CommandUtils {

	public static final String ARGUMENTS_MISSING = "please provide an argument!";

	public static @NotNull CompletableFuture<Message> reply(@NotNull String userId,
	                                                        @NotNull MessageChannel channel,
	                                                        @NotNull String message) {
		String messageReply = "<@" + userId + ">, " + message;

		return channel.sendMessage(messageReply).submit();
	}

	public static void helpCommand(@NotNull CommandEvent event) {
		Color color = getRandomItem(
				Color.CYAN,
				Color.ORANGE,
				Color.GREEN,
				Color.MAGENTA
		);

		User author = event.getAuthor();
		MessageChannel channel = event.getChannel();

		Optional<List<String>> args = getArgs(event.getArgs());

		EmbedBuilder embedBuilder = new EmbedBuilder()
				.setAuthor(author.getAsTag(), null, author.getEffectiveAvatarUrl())
				.setColor(color);

		if(args.isEmpty()) {
			embedBuilder
					.setTitle("Category List:");

			getCommandsCategories().forEach(category -> {
				String capitalizedCategoryName =
						category.substring(0, 1).toUpperCase() + category.substring(1);
				embedBuilder.appendDescription(capitalizedCategoryName + "\n");
			});

			channel.sendMessage(embedBuilder.build()).submit();
		} else {
			try {
				CommandClient commandClient = event.getClient();

				List<Command> commands =
						getCommands(args.get().get(0).toLowerCase(), commandClient);

				commands.forEach(command -> {

					List<String> originalAliases = Arrays.asList(command.getAliases());

					String aliases = originalAliases.size() >= 1 ? " | " + String.join(
							", ",
							Arrays.asList(command.getAliases())) : "";

					embedBuilder.addField(
							commandClient.getPrefix() +
									command.getName().toLowerCase()
									+ aliases,
							command.getHelp(),
							false
					);
				});

				channel.sendMessage(embedBuilder.build()).submit();
			} catch(IllegalArgumentException illegalArgumentException) {
				reply(author.getId(), channel,
						illegalArgumentException.getMessage().toLowerCase());
			}
		}
	}

	private static @NotNull List<Command> getCommands(String category,
	                                                  CommandClient commandClient) throws IllegalArgumentException {

		String commandsDirectory = "./src/main/java/me/snavellet/bot/commands/"
				+ category;

		Optional<String[]> commandFilesDirectory =
				Optional.ofNullable(new File(commandsDirectory).list());

		if(commandFilesDirectory.isEmpty())
			throw new IllegalArgumentException("Unknown category!");

		List<Command> commands = new ArrayList<>();

//		long start = System.currentTimeMillis();

		Arrays.asList(commandFilesDirectory.get()).forEach(file -> {
			String newFileName = file.split("\\.")[0];
			commandClient.getCommands()
			             .stream()
			             .filter(command -> command
					             .getName()
					             .equalsIgnoreCase(newFileName))
			             .forEach(commands::add);
		});

//		long finish = System.currentTimeMillis();

//		System.out.println(((double) (finish - start)) + " milliseconds");

		return commands;
	}

	private static @NotNull List<String> getCommandsCategories() {

		String categoriesDirectory = "./src/main/java/me/snavellet/bot/commands";

		List<String> categories = Arrays.asList(new File(categoriesDirectory).list());

		categories.sort(Comparator.naturalOrder());

		return categories;
	}

	public static @NotNull Optional<List<String>> getArgs(@NotNull String content) {
		List<String> result;

		if(content.equals(""))
			result = null;
		else
			result = Arrays.asList(content.split("\\s+"));

		return Optional.ofNullable(result);
	}

	@SafeVarargs
	public static <T> T getRandomItem(T @NotNull ... items) {

		return items[new Random().nextInt(items.length)];
	}

	public static <T> T getRandomItem(@NotNull List<T> items) {

		return items.get(new Random().nextInt(items.size()));
	}
}
