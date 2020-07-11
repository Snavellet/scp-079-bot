package me.snavellet.bot.utils;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class CommandUtils {

	public static final String ARGUMENTS_MISSING = "please provide an argument!";
	public static final String REASON_MISSING = "please provide a reason!";
	public static final String ARGUMENTS_GENERAL = "(name | mention | id)";
	public static final String ARGUMENTS_MODERATION = ARGUMENTS_GENERAL + "... " +
			"\"[reason]\", must be in quotes.";
	public static final int DEFAULT_COOLDOWN = 2;

	private final User author;
	private final MessageChannel channel;
	private final Guild guild;
	private final String content;
	private final @NotNull CommandEvent event;

	public CommandUtils(@NotNull CommandEvent event) {
		this.author = event.getAuthor();
		this.channel = event.getChannel();
		this.guild = event.getGuild();
		this.content = event.getArgs();
		this.event = event;
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

	public @NotNull CompletableFuture<Message> reply(String message) {
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

		if(this.content.equals("")) {
			result = null;
		} else if(Pattern.matches("\\w+\\S+", this.content)) {
			result = Collections.singletonList(this.content);
		} else {
			result = Arrays.asList(this.content.split("\\s+"));
		}

		return Optional.ofNullable(result);
	}

	public Optional<List<String>> getMentionsAndIdsAndNames() {
		Optional<List<String>> arguments = this.getArgs();
		if(arguments.isEmpty())
			return Optional.empty();

		Color color = getRandomItem(
				Color.BLUE,
				Color.GRAY,
				Color.LIGHT_GRAY,
				Color.MAGENTA,
				Color.GREEN
		);

		List<String> args = arguments.get();
		List<String> ids = new ArrayList<>();

		args.forEach(arg -> {
			List<Member> membersByEffectiveName = this.guild.getMembers().parallelStream()
			                                                .filter(member -> member
					                                                .getEffectiveName()
					                                                .toLowerCase()
					                                                .contains(arg.toLowerCase()) || member
					                                                .getUser()
					                                                .getName()
					                                                .toLowerCase()
					                                                .contains(arg.toLowerCase()))
			                                                .limit(10)
			                                                .collect(Collectors.toList());
			Optional<Member> membersByTag;
			Matcher matchedIds = Pattern.compile("\\d{18}").matcher(arg);

			try {
				membersByTag = Optional.ofNullable(this.guild.getMemberByTag(arg));
			} catch(IllegalArgumentException illegalArgumentException) {
				membersByTag = Optional.empty();
			}

			if(membersByEffectiveName.size() == 1) {
				ids.add(membersByEffectiveName.get(0).getId());
			} else if(membersByEffectiveName.size() > 1) {
				EmbedBuilder multipleNames = new EmbedBuilder()
						.setColor(color)
						.setTitle("Search for: " + arg + "\nFound multiple results (Max" +
								" is 10)")
						.setThumbnail(this.guild.getIconUrl());


				membersByEffectiveName.forEach(member -> {
					Optional<String> nickname = Optional.ofNullable(member.getNickname());
					String username = member.getUser().getName();

					multipleNames.appendDescription((nickname.isEmpty() ?
							username : nickname.get() + " (" + username + ")") +
							"\n");
				});
				this.channel.sendMessage(multipleNames.build()).submit();
			} else if(membersByTag.isPresent()) {
				ids.add(membersByTag.get().getId());
			} else if(matchedIds.find()) {
				ids.add(matchedIds.group(0));
			} else {
				this.reply("user `" + arg + "` does not exist in this server!");
			}
		});


		return Optional.of(ids);
	}

	public Optional<Member> memberExistsById(@NotNull String id) {
		return Optional.ofNullable(this.event.getGuild().getMemberById(id));
	}

	public Optional<String> getReason() {
		Matcher matcher = Pattern.compile("\"(.+)\"").matcher(this.content);

		if(!matcher.find())
			return Optional.empty();

		return Optional.of(matcher.group(1));
	}
}
