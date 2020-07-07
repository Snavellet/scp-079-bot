package me.snavellet.bot.entities.http.tech.ai.wit;

import java.util.List;

public class EntityWit {

	private final String id;
	private final String name;
	private final String role;
	private final int start;
	private final int end;
	private final String body;
	private final String value;
	private final double confidence;
	private final List<EntityWit> entities;

	public EntityWit(String id, String name, String role, int start, int end, String body, String value, double confidence, List<EntityWit> entities) {
		this.id = id;
		this.name = name;
		this.role = role;
		this.start = start;
		this.end = end;
		this.body = body;
		this.value = value;
		this.confidence = confidence;
		this.entities = entities;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRole() {
		return role;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public String getBody() {
		return body;
	}

	public String getValue() {
		return value;
	}

	public double getConfidence() {
		return confidence;
	}

	public List<EntityWit> getEntities() {
		return entities;
	}
}
