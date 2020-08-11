package me.snavellet.bot.utils.db;

import com.jagrosh.jdautilities.command.CommandEvent;
import me.snavellet.bot.entities.hibernate.WarningThresholdEntity;
import me.snavellet.bot.utils.DatabaseUtils;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@SuppressWarnings("unchecked")
public class WarningThresholdUtilsDB extends DatabaseUtils {

	public WarningThresholdUtilsDB(@NotNull CommandEvent event) {
		super(WarningThresholdEntity.class, event);
	}

	public int setThreshold(int threshold) {
		session.beginTransaction();

		Optional<WarningThresholdEntity> thresholdExist = checkIfThresholdExist();

		WarningThresholdEntity warningThreshold = null;

		if(thresholdExist.isPresent()) {
			thresholdExist.get().setWarningThreshold(threshold);
		} else {
			warningThreshold = new WarningThresholdEntity(
					guild.getId(),
					threshold
			);

			session.save(warningThreshold);
		}


		session.getTransaction().commit();

		return thresholdExist.isPresent() ? thresholdExist.get().getWarningThreshold() :
				warningThreshold.getWarningThreshold();
	}

	public Optional<Integer> getThreshold() {
		session.beginTransaction();

		Optional<WarningThresholdEntity> threshold = checkIfThresholdExist();

		session.getTransaction().commit();

		return threshold.map(WarningThresholdEntity::getWarningThreshold);
	}

	public boolean deleteThreshold() {
		session.beginTransaction();

		Optional<WarningThresholdEntity> threshold = checkIfThresholdExist();

		if(threshold.isPresent()) {
			session.delete(threshold.get());
		} else {
			return false;
		}

		session.getTransaction().commit();

		return true;
	}

	private Optional<WarningThresholdEntity> checkIfThresholdExist() {
		Query<WarningThresholdEntity> query = session.createQuery("from " +
				"WarningThresholdEntity as W where W.guildId=:guildId");

		query.setParameter("guildId", guild.getId());

		return this.queryExistsSingle(query);
	}
}
