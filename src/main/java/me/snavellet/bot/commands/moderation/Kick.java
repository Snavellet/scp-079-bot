package me.snavellet.bot.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.utils.CommandUtils;
import me.snavellet.bot.utils.ModerationUtils;
import net.dv8tion.jda.api.Permission;
import org.jetbrains.annotations.NotNull;

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

		ModerationUtils.kick(event);
	}
}
