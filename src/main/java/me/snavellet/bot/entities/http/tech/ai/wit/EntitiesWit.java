package me.snavellet.bot.entities.http.tech.ai.wit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EntitiesWit {

	@SerializedName("sentiment:positive")
	private final List<EntityWit> positive;

	@SerializedName("sentiment:negative")
	private final List<EntityWit> negative;

	@SuppressWarnings("unused")
	public EntitiesWit(List<EntityWit> positive, List<EntityWit> negative) {
		this.positive = positive;
		this.negative = negative;
	}

	public List<EntityWit> getPositive() {
		return positive;
	}

	public List<EntityWit> getNegative() {
		return negative;
	}
}
