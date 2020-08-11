package me.snavellet.bot.entities.hibernate;

import javax.persistence.*;

@Entity
@Table(name = "warnings")
public class Warning {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private int id;

	@Column(name = "guild_id", updatable = false, nullable = false)
	private String guildId;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "moderator_id", nullable = false)
	private String moderatorId;

	@Column(name = "date_ms", nullable = false)
	private long dateMs;

	@Column(name = "reason")
	private String reason;

	public Warning() {
	}

	public Warning(String guildId, String userId, String moderatorId,
	               long dateMs, String reason) {
		this.guildId = guildId;
		this.userId = userId;
		this.moderatorId = moderatorId;
		this.dateMs = dateMs;
		this.reason = reason;
	}

	public int getId() {
		return id;
	}

	public String getGuildId() {
		return guildId;
	}

	public void setGuildId(String guildId) {
		this.guildId = guildId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getModeratorId() {
		return moderatorId;
	}

	public void setModeratorId(String moderatorId) {
		this.moderatorId = moderatorId;
	}

	public long getDateMs() {
		return dateMs;
	}

	public void setDateMs(long dateMs) {
		this.dateMs = dateMs;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
