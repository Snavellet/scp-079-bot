package me.snavellet.bot.entities.http.tech.ai.wit;

public class IntentWit {

	private final String id;
	private final String name;
	private final double confidence;

	public IntentWit(String id, String name, double confidence) {
		this.id = id;
		this.name = name;
		this.confidence = confidence;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getConfidence() {
		return confidence;
	}
}
