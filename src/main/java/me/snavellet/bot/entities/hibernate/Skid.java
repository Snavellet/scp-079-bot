package me.snavellet.bot.entities.hibernate;

import javax.persistence.*;

@SuppressWarnings("unused")
@Entity
@Table(name = "skids")
public class Skid {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "guild_id")
	private String guildId;

	@Column(name = "user_id")
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
