package me.snavellet.bot.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.utils.CommandUtils;
import net.dv8tion.jda.api.Permission;

import java.util.List;
import java.util.Optional;

public class Ban extends Command {

	public Ban() {
		this.name = "ban";
		this.help = "Bans a member to prevent it from going back.";
		this.aliases = new String[]{
				"annihilate", "hammer", "mjolnir", "destroy",
				"obliterate"
		};
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
		this.arguments = CommandUtils.ARGUMENTS_MODERATION;
		this.userPermissions = new Permission[]{Permission.BAN_MEMBERS};
	}

	@Override
	protected void execute(CommandEvent event) {

		CommandUtils commandUtils = new CommandUtils(event);

		Optional<List<String>> ids = commandUtils.getMentionsAndIdsAndEffectiveName();

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
										.ban(0, reason.get())
										.submit()
										.thenAccept(aVoid -> {
											commandUtils.reply("I successfully " +
													"banned" +
													" ***" + member
													.getUser()
													.getAsTag() + "*** for `" + reason.get() + "`");
										})));
			}
		}
	}
}
