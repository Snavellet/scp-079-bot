package me.snavellet.bot.listeners;

import me.snavellet.bot.utils.ConfigUtils;
import me.snavellet.bot.utils.WitUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.Headers;

import javax.annotation.Nonnull;
import java.io.IOException;

public class MessageListener extends ListenerAdapter {

	@Override
	public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
		if(!event.isFromGuild()) return;

		User author = event.getAuthor();
		Member member = event.getMember();

		if(author == event.getJDA().getSelfUser() || author.isBot()) return;
		if(member.hasPermission(Permission.KICK_MEMBERS)) return;

		ConfigUtils configUtils = null;
		try {
			configUtils = new ConfigUtils();
		} catch(IOException e) {
			e.printStackTrace();
		}
		Headers headers = new Headers.Builder().add("Authorization",
				"Bearer " + configUtils.getWitAccessToken()).build();

		try {
			WitUtils.checkSkid(event, headers);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
