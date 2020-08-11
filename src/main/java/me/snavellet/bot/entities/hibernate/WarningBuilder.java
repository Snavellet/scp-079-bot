package me.snavellet.bot.entities.hibernate;

import org.jetbrains.annotations.NotNull;

public class WarningBuilder {
	private String guildId;
	private String userId;
	private String moderatorId;
	private long dateMs;
	private String reason;

	public @NotNull WarningBuilder setGuildId(String guildId) {
		this.guildId = guildId;
		return this;
	}

	public @NotNull WarningBuilder setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public @NotNull WarningBuilder setModeratorId(String moderatorId) {
		this.moderatorId = moderatorId;
		return this;
	}

	public @NotNull WarningBuilder setDateMs(long dateMs) {
		this.dateMs = dateMs;
		return this;
	}

	public @NotNull WarningBuilder setReason(String reason) {
		this.reason = reason;
		return this;
	}

	public @NotNull Warning createWarning() {
		return new Warning(guildId, userId, moderatorId, dateMs, reason);
	}
}
