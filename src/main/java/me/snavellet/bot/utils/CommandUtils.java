package me.snavellet.bot.utils;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class CommandUtils {

	public static final String ARGUMENTS_MISSING = "please provide an argument!";

	private final User author;
	private final MessageChannel channel;
	private final String content;
//	private final @NotNull CommandEvent event;

	public CommandUtils(@NotNull CommandEvent event) {
		this.author = event.getAuthor();
		this.channel = event.getChannel();
		this.content = event.getArgs();
//		this.event = event;
	}

	public static List<String> getCommandsCategories() {

		String categoriesDirectory = "./src/main/java/me/snavellet/bot/commands";

		return Arrays.stream(new File(categoriesDirectory).list())
		             .sorted(Comparator.naturalOrder())
		             .collect(Collectors.toList());
	}

	@SafeVarargs
	public static <T> T getRandomItem(T @NotNull ... items) {

		return items[new Random().nextInt(items.length)];
	}

	public static <T> T getRandomItem(@NotNull List<T> items) {

		return items.get(new Random().nextInt(items.size()));
	}

	public static boolean checkIfCanBeParsedToInt(@NotNull String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch(NumberFormatException numberFormatException) {
			return false;
		}
	}

	public CompletableFuture<Message> reply(String message) {
		String messageReply = "<@" + this.author.getId() + ">, " + message;

		return this.channel.sendMessage(messageReply).submit();
	}

	@NotNull
	public List<Command> getCommands(String category,
	                                 @NotNull CommandClient commandClient) throws IllegalArgumentException {

		String commandsDirectory = "./src/main/java/me/snavellet/bot/commands/"
				+ category;

		Optional<String[]> commandFilesDirectory =
				Optional.ofNullable(new File(commandsDirectory).list());

		if(commandFilesDirectory.isEmpty())
			throw new IllegalArgumentException();

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

	public Optional<List<String>> getArgs() {
		@Nullable List<String> result;

		if(this.content.equals(""))
			result = null;
		else
			result = Arrays.asList(this.content.split("\\s+"));

		return Optional.ofNullable(result);
	}
}
