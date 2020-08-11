package me.snavellet.bot.utils;

import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class DatabaseUtils extends UserUtils {

	protected final SessionFactory factory;
	protected final Session session;

	protected <T> DatabaseUtils(Class<T> clazz, @NotNull CommandEvent event) {
		super(event);

		this.factory = new Configuration()
				.configure(ConfigUtils.getHibernateConfigFile())
				.addAnnotatedClass(clazz)
				.buildSessionFactory();

		this.session = this.factory.openSession();
	}

	protected <T> DatabaseUtils(Class<T> clazz, @NotNull MessageReceivedEvent event) {
		super(event);

		this.factory = new Configuration()
				.configure(ConfigUtils.getHibernateConfigFile())
				.addAnnotatedClass(clazz)
				.buildSessionFactory();

		this.session = this.factory.openSession();
	}

	protected <T> Optional<List<T>> queryExists(@NotNull Query<T> query) {
		return Optional.ofNullable(query
				.getResultList()
				.size() >= 1 ? query.getResultList() : null);
	}

	protected <T> Optional<T> queryExistsSingle(@NotNull Query<T> query) {
		return Optional.ofNullable(query
				.getResultList()
				.size() >= 1 ? query.getSingleResult() : null);
	}
}
