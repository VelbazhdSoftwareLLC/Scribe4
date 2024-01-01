package eu.veldsoft.scribe4.model;

/**
 * Settings that affect gameplay
 */
public abstract class Settings {
	public static enum GameMode {
		Majority, SuperGlyph
	};

	private static GameMode gameMode = GameMode.Majority;

	public static void setGameMode(String gameMode) {
		if (gameMode.equals("majority")) {
			Settings.gameMode = GameMode.Majority;
		} else if (gameMode.equals("superglyph")) {
			Settings.gameMode = GameMode.SuperGlyph;
		} else {
			throw new IllegalArgumentException("game mode: " + gameMode);
		}
	}

	public static void setGameMode(GameMode gameMode) {
		Settings.gameMode = gameMode;
	}

	public static GameMode getGameMode() {
		return gameMode;
	}
}
