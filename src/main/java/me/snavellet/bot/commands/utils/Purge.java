package me.snavellet.bot.commands.utils;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.utils.CommandUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class Purge extends Command {

	public Purge() {
		this.name = "purge";
		this.aliases = new String[]{"clean"};
		this.help = "Deletes a number of messages in the channel it's executed in.";
		this.userPermissions = new Permission[]{Permission.MESSAGE_MANAGE};
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
		this.arguments = "[amount to purge, max 100 and min 1]";
	}

	@Override
	protected void execute(@NotNull CommandEvent event) {

		CommandUtils commandUtils = new CommandUtils(event);

		Optional<List<String>> arguments = commandUtils.getArgs();

		TextChannel channel = event.getTextChannel();

		if(arguments.isEmpty()) {
			commandUtils.reply(CommandUtils.ARGUMENTS_MISSING);
			return;
		}

		String amountToPurgeString = arguments.get().get(0);

		if(!CommandUtils.checkIfCanBeParsedToInt(amountToPurgeString)) {
			commandUtils.reply("please provide a valid amount!");
			return;
		}

		int amountToPurge = Integer.parseInt(amountToPurgeString);

		if(!(amountToPurge >= 1) || amountToPurge > 100) {
			commandUtils.reply("the minimum of messages to purge is 1 and the maximum " +
					"is 100!");
			return;
		}

		List<Message> messages =
				channel
						.getHistory()
						.retrievePast(amountToPurge + 1)
						.complete();

		try {
			channel
					.deleteMessages(messages)
					.submit()
					.thenAccept(aVoid -> commandUtils.reply("I successfully purged the " +
							"messages!")
					                                 .thenAccept(message -> {
						                                 try {
							                                 Thread.sleep(2000);
							                                 message.delete().submit();
						                                 } catch(InterruptedException e) {
							                                 e.printStackTrace();
						                                 }
					                                 }));
		} catch(IllegalArgumentException illegalArgumentException) {
			commandUtils.reply("there are no messages here!");
		}

	}
}
