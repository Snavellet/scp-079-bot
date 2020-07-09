package me.snavellet.bot.entities.http.tech.ai.wit;

import java.util.List;

@SuppressWarnings("unused")
public class MessageWit {

	private final String text;
	private final List<IntentWit> intents;
	private final EntitiesWit entities;

	public MessageWit(String text, List<IntentWit> intents, EntitiesWit entities) {
		this.text = text;
		this.intents = intents;
		this.entities = entities;
	}

	public String getText() {
		return text;
	}

	public List<IntentWit> getIntents() {
		return intents;
	}

	public EntitiesWit getEntities() {
		return entities;
	}
}
