package eu.veldsoft.scribe4.model;

import eu.veldsoft.scribe4.Util;

/**
 * 
 * @author
 */
public enum ScribeMark {
	PURPLE('P'), GREEN('G'), BLUE('B'), RED('R'), EMPTY('-');

	private final char ch;

	private String name = "";

	private ScribeMark(char ch) {
		this.ch = ch;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String scribeMarkName() {
		return name;
	}

	public static ScribeMark getRandomMark() {
		switch (Util.PRNG.nextInt(4)) {
		case 0:
			return ScribeMark.RED;
		case 1:
			return ScribeMark.BLUE;
		case 2:
			return ScribeMark.GREEN;
		case 3:
			return ScribeMark.PURPLE;
		}

		return null;
	}

	public char toChar() {
		return ch;
	}

	static ScribeMark fromChar(char ch) {
		if (ch == PURPLE.ch)
			return PURPLE;

		if (ch == GREEN.ch)
			return GREEN;

		if (ch == BLUE.ch)
			return BLUE;

		if (ch == RED.ch)
			return RED;

		return EMPTY;
	}

	/**
	 * The "other" player
	 */
	public ScribeMark other() {
		switch (this) {
		case RED:
			return BLUE;
		case BLUE:
			return GREEN;
		case GREEN:
			return PURPLE;
		case PURPLE:
			return RED;
		case EMPTY:
			return EMPTY;
		default:
			return EMPTY;
		}
	}
}
