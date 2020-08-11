package me.snavellet.bot.entities.hibernate;

import javax.persistence.*;

@Entity
@Table(name = "warnthres")
public class WarningThresholdEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private String id;

	@Column(name = "guild_id", updatable = false, nullable = false)
	private String guildId;

	@Column(name = "warning_threshold", nullable = false)
	private int warningThreshold;

	public WarningThresholdEntity() {
	}

	public WarningThresholdEntity(String guildId, int warningThreshold) {
		this.guildId = guildId;
		this.warningThreshold = warningThreshold;
	}

	public String getId() {
		return id;
	}

	public String getGuildId() {
		return guildId;
	}

	public void setGuildId(String guildId) {
		this.guildId = guildId;
	}

	public int getWarningThreshold() {
		return warningThreshold;
	}

	public void setWarningThreshold(int warningThreshold) {
		this.warningThreshold = warningThreshold;
	}
}
