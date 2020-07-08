package me.snavellet.bot;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import me.snavellet.bot.commands.fun.Cat;
import me.snavellet.bot.commands.fun.Dog;
import me.snavellet.bot.commands.fun.Meme;
import me.snavellet.bot.commands.knowledge.CatFact;
import me.snavellet.bot.commands.utils.Avatar;
import me.snavellet.bot.commands.utils.UrlShorten;
import me.snavellet.bot.listeners.CommandListener;
import me.snavellet.bot.listeners.MessageListener;
import me.snavellet.bot.listeners.ReadyListener;
import me.snavellet.bot.utils.CommandUtils;
import me.snavellet.bot.utils.ConfigUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException, LoginException {

		ConfigUtils configUtils = new ConfigUtils();

		CommandClientBuilder commandClientBuilder = new CommandClientBuilder()
				.setPrefix(configUtils.getPrefix())
				.setActivity(Activity.of(Activity.ActivityType.WATCHING,
						"this server"))
				.setOwnerId(configUtils.getOwnerId())
				.setHelpConsumer(CommandUtils::helpCommand)
				.setListener(new CommandListener())
				.addCommands(
						// Fun
						new Cat(),
						new Dog(),
						new Meme(),

						// Knowledge
						new CatFact(),

						// Utilities
						new Avatar(),
						new UrlShorten()
				);

		CommandClient client = commandClientBuilder.build();

		GatewayIntent[] gatewayIntentsArray = {
				GatewayIntent.GUILD_MEMBERS,
				GatewayIntent.GUILD_MESSAGES,
				GatewayIntent.DIRECT_MESSAGES,
				GatewayIntent.GUILD_BANS,
				GatewayIntent.GUILD_MESSAGE_REACTIONS,
				GatewayIntent.GUILD_EMOJIS
		};

		List<GatewayIntent> gatewayIntentsList = Arrays.asList(gatewayIntentsArray);

		JDA jda =
				JDABuilder
						.create(configUtils.getToken(), gatewayIntentsList)
						.addEventListeners(
								client,
								new ReadyListener(),
								new MessageListener()
						)
						.build();

		jda.awaitReady();
	}
}
