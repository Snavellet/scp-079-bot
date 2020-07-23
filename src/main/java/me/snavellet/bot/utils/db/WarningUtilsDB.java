package me.snavellet.bot.utils.db;

import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.entities.hibernate.Warning;
import me.snavellet.bot.utils.DatabaseUtils;
import me.snavellet.bot.utils.enums.WarningEnum;
import net.dv8tion.jda.api.entities.Member;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class WarningUtilsDB extends DatabaseUtils {

	public WarningUtilsDB(CommandEvent event) {
		super(Warning.class, event);
	}

	public Optional<List<Warning>> getAllWarnings(@NotNull String id) {
		Optional<List<Warning>> allWarnings = Optional.empty();
		Optional<Member> member = memberExistsById(id);

		if(member.isEmpty()) {
			throw new NullPointerException("The member doesn't exist anymore!");
		} else {
			session.beginTransaction();

			Query<Warning> warningQuery = session.createQuery("from Warning as " +
					"W where W.guildId=:guildId and W.userId=:userId");

			warningQuery.setParameter("guildId", guild.getId());
			warningQuery.setParameter("userId", member.get().getId());

			Optional<List<Warning>> warnings = queryExists(warningQuery);

			if(warnings.isPresent()) {
				allWarnings = warnings;
			}

			session.getTransaction().commit();

			return allWarnings;
		}
	}

	public int deleteAllWarns(String id) {
		session.beginTransaction();

		Query<Warning> warningQuery = session.createQuery("from Warning as " +
				"W where W.guildId=:guildId and W.userId=:userId");

		warningQuery.setParameter("guildId", guild.getId());
		warningQuery.setParameter("userId", id);

		Optional<List<Warning>> warnings =
				queryExists(warningQuery);

		if(warnings.isEmpty()) {
			throw new NullPointerException(WarningEnum.NO_WARNINGS.getValue());
		} else {
			warnings.get().forEach(session::delete);
		}

		session.getTransaction().commit();

		return warnings.get().size();
	}

	public void warnUser(Warning warning) {
		session.beginTransaction();

		session.save(warning);

		session.getTransaction().commit();
	}
}
