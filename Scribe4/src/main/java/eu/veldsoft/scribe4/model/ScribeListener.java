package eu.veldsoft.scribe4.model;

public interface ScribeListener {

	/**
	 * Called automatically whenever the scribeBoard being listened to is
	 * filled, and thus one player or the other has won the game.
	 */
	void scribeBoardWon(ScribeBoard scribeBoard, ScribeMark winner);

	/**
	 * Called automatically whenever someone makes a move on the scribeBoard
	 * being listened to.
	 * 
	 * @param scribeBoard
	 * @param currentPlayer
	 *            The player whose turn it is now.
	 */
	void whoseTurnChanged(ScribeBoard scribeBoard, ScribeMark currentPlayer);

}
