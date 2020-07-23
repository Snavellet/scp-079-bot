package me.snavellet.bot.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.utils.CommandUtils;
import me.snavellet.bot.utils.ModerationUtils;
import net.dv8tion.jda.api.Permission;
import org.jetbrains.annotations.NotNull;

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
	protected void execute(@NotNull CommandEvent event) {

		ModerationUtils.ban(event);
	}
}
