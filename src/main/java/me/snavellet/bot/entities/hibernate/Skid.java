package me.snavellet.bot.entities.hibernate;

import javax.persistence.*;

@SuppressWarnings("unused")
@Entity
@Table(name = "skids")
public class Skid {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private int id;

	@Column(name = "guild_id", nullable = false)
	private String guildId;

	@Column(name = "user_id", nullable = false)
	private String userId;

	public Skid() {
	}

	public Skid(String guildId, String userId) {
		this.guildId = guildId;
		this.userId = userId;
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
}
