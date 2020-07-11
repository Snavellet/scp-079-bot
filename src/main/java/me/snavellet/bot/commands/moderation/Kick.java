package me.snavellet.bot.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.utils.CommandUtils;
import net.dv8tion.jda.api.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class Kick extends Command {

	public Kick() {
		this.name = "kick";
		this.help = "Kicks a member out of the server.";
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
		this.arguments = CommandUtils.ARGUMENTS_MODERATION;
		this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};
	}

	@Override
	protected void execute(@NotNull CommandEvent event) {

		CommandUtils commandUtils = new CommandUtils(event);

		Optional<List<String>> ids = commandUtils.getMentionsAndIdsAndNames();

		if(ids.isEmpty()) {
			commandUtils.reply(CommandUtils.ARGUMENTS_MISSING);
		} else if(ids.get().size() >= 1) {
			Optional<String> reason = commandUtils.getReason();

			if(reason.isEmpty()) {
				commandUtils.reply(CommandUtils.REASON_MISSING);
			} else {
				ids
						.get()
						.forEach(id -> commandUtils
								.memberExistsById(id)
								.ifPresent(member -> member
										.kick(reason.get())
										.submit()
										.thenAccept(aVoid -> commandUtils.reply("I successfully kicked ***" + member
												.getUser()
												.getAsTag() + "*** for `" + reason.get() + "`"))));
			}
		}
	}
}
