package me.snavellet.bot.commands.knowledge;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.entities.http.animals.catFact.RandomCatFact;
import me.snavellet.bot.utils.CommandUtils;
import me.snavellet.bot.utils.HttpUtils;
import me.snavellet.bot.utils.enums.Api;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CatFact extends Command {

	public CatFact() {
		this.name = "catfact";
		this.aliases = new String[]{"catf"};
		this.help = "Gets you a random cat fact.";
		this.cooldown = CommandUtils.DEFAULT_COOLDOWN;
	}

	@Override
	protected void execute(@NotNull CommandEvent event) {

		CommandUtils commandUtils = new CommandUtils(event);

		HttpUtils<RandomCatFact> http = new HttpUtils<>(Api.CAT_FACT.getValue(),
				RandomCatFact.class);

		http.asynchronousGet(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
				commandUtils.reply(Api.ERROR.getValue());
			}

			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

				assert response.body() != null;
				RandomCatFact result = http.fromJson(response.body().string());

				event.getChannel().sendMessage(result.getText()).submit();
			}
		});
	}
}
