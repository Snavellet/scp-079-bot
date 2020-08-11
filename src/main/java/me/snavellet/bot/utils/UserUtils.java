package me.snavellet.bot.utils;

import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

public class UserUtils extends CommandUtils {

	public static final String USER_INEXISTENT = "this user does not exist in this " +
			"server anymore!";

	public UserUtils(@NotNull CommandEvent event) {
		super(event);
	}

	public UserUtils(@NotNull MessageReceivedEvent event) {
		super(event);
	}

	public static @NotNull String formatDate(@NotNull OffsetDateTime dateTime) {
		return dateTime.format(DateTimeFormatter
				.ofLocalizedDateTime(FormatStyle.LONG).withZone(ZoneId
						.of("UTC")));
	}

	public Optional<Member> memberExistsById(@NotNull String id) {
		return Optional.ofNullable(super.event.getGuild().getMemberById(id));
	}

	public boolean memberExistsByIdBoolean(@NotNull String id) {
		return Optional.ofNullable(super.guild.getMemberById(id)).isPresent();
	}
}
