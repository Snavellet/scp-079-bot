package me.snavellet.bot.utils.enums;

public enum WarningEnum {

	NO_WARNINGS("this user has no warnings!");

	private final String value;

	WarningEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
