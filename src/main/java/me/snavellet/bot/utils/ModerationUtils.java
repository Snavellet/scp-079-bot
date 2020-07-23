package me.snavellet.bot.utils;

import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class ModerationUtils {

	public static void kick(@NotNull CommandEvent event) {
		UserUtils userUtils = new UserUtils(event);

		Optional<List<String>> ids = userUtils.getMentionsAndIdsAndNames();

		if(ids.isEmpty()) {
			userUtils.reply(CommandUtils.ARGUMENTS_MISSING);
		} else if(ids.get().size() >= 1) {
			Optional<String> reason = userUtils.getReason();

			if(reason.isEmpty()) {
				userUtils.reply(CommandUtils.REASON_MISSING);
			} else {
				ids
						.get()
						.stream()
						.filter(userUtils::memberExistsByIdBoolean)
						.forEach(id -> {
							Member member = event.getGuild().getMemberById(id);
							assert member != null;
							member.kick(reason.get())
							      .submit()
							      .thenAccept(aVoid -> userUtils.reply("I successfully kicked ***" + member
									      .getUser()
									      .getAsTag() + "*** for `" + reason.get() +
									      "`"));
						});
			}
		}
	}

	public static void ban(@NotNull CommandEvent event) {
		UserUtils userUtils = new UserUtils(event);

		Optional<List<String>> ids = userUtils.getMentionsAndIdsAndNames();

		if(ids.isEmpty()) {
			userUtils.reply(CommandUtils.ARGUMENTS_MISSING);
		} else if(ids.get().size() >= 1) {
			Optional<String> reason = userUtils.getReason();

			if(reason.isEmpty()) {
				userUtils.reply(CommandUtils.REASON_MISSING);
			} else {
				ids
						.get()
						.forEach(id -> userUtils
								.memberExistsById(id)
								.ifPresent(member -> member
										.ban(0, reason.get())
										.submit()
										.thenAccept(aVoid -> {
											userUtils.reply("I successfully " +
													"banned" +
													" ***" + member
													.getUser()
													.getAsTag() + "*** for `" + reason.get() + "`");
										})));
			}
		}
	}
}
