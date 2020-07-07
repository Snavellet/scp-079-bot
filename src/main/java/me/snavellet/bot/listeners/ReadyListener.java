package me.snavellet.bot.listeners;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class ReadyListener extends ListenerAdapter {

	@Override
	public void onReady(@Nonnull ReadyEvent event) {
		String userTag = event.getJDA().getSelfUser().getAsTag();

		System.out.println("Logged in successfully as " + userTag + "!");
	}
}
