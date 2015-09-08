package eu.veldsoft.scribe4;

import android.graphics.Color;
import eu.veldsoft.scribe4.model.ScribeMark;

/**
 * Settings that affect the UI
 */
public abstract class Settings {
	/**
	 * not configurable yet. everything hard-coded for now
	 * 
	 * @param mark
	 * @return
	 */
	public static int getEnabledColorForMark(ScribeMark mark) {
		switch (mark) {
		case BLUE:
			return Color.BLUE;
		case RED:
			return Color.RED;
		case GREEN:
			return Color.GREEN;
		case PURPLE:
			return Color.MAGENTA;
		case EMPTY:
			return Color.WHITE;
		default:
			return Color.WHITE;
		}
	}

	public static int getDisabledColorForMark(ScribeMark mark) {
		switch (mark) {
		case BLUE:
			return Color.rgb(Color.red(Color.BLUE) / 2,
					Color.green(Color.BLUE) / 2, Color.blue(Color.BLUE) / 2);
		case RED:
			return Color.rgb(Color.red(Color.RED) / 2,
					Color.green(Color.RED) / 2, Color.blue(Color.RED) / 2);
		case GREEN:
			return Color.rgb(Color.red(Color.GREEN) / 2,
					Color.green(Color.GREEN) / 2, Color.blue(Color.GREEN) / 2);
		case PURPLE:
			return Color.rgb(Color.red(Color.MAGENTA) / 2,
					Color.green(Color.MAGENTA) / 2,
					Color.blue(Color.MAGENTA) / 2);
		case EMPTY:
			return Color.rgb(Color.red(Color.WHITE) / 2,
					Color.green(Color.WHITE) / 2, Color.blue(Color.WHITE) / 2);
		default:
			return Color.rgb(Color.red(Color.WHITE) / 2,
					Color.green(Color.WHITE) / 2, Color.blue(Color.WHITE) / 2);
		}
	}

	public static int getColorForMark(ScribeMark mark, boolean enabled) {
		return enabled ? getEnabledColorForMark(mark)
				: getDisabledColorForMark(mark);
	}

	public static int getLastMoveColorForMark(ScribeMark mark, boolean enabled) {
		return Color.WHITE;
	}

}
