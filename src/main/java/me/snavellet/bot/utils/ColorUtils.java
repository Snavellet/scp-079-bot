package me.snavellet.bot.utils;

import java.awt.*;

public class ColorUtils {

	public static Color getRandomColor() {
		return CommandUtils.getRandomItem(
				Color.BLACK,
				Color.BLUE,
				Color.PINK,
				Color.CYAN,
				Color.MAGENTA,
				Color.GREEN,
				Color.YELLOW,
				Color.GRAY
		);
	}
}
