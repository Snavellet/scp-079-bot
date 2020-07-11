package me.snavellet.bot.utils;

import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class UserUtils {

	public static final String USER_INEXISTENT = "this user does not exist in this " +
			"server anymore!";

	public static @NotNull String formatDate(@NotNull OffsetDateTime dateTime) {
		return dateTime.format(DateTimeFormatter
				.ofLocalizedDateTime(FormatStyle.LONG).withZone(ZoneId
						.of("UTC")));
	}
}
