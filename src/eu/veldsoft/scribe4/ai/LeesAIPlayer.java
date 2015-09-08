package eu.veldsoft.scribe4.ai;

import java.util.List;
import java.util.Map;

import eu.veldsoft.scribe4.AIPlayer;
import eu.veldsoft.scribe4.Log;
import eu.veldsoft.scribe4.Util;
import eu.veldsoft.scribe4.model.GridPosition;
import eu.veldsoft.scribe4.model.MiniGrid;
import eu.veldsoft.scribe4.model.ScribeMark;
import eu.veldsoft.scribe4.model.XY;

/**
 * An experimental AI.
 */
public class LeesAIPlayer extends AIPlayer {

	/**
	 * Overall approach: 1. choose a minigrid for this turn, usually there's
	 * just one 2. choose a minigrid for next turn 3. choose a cell for this
	 * turn, based on either: a. make more glyph points in this grid b. go for
	 * next turn's minigrid
	 */
	@Override
	public GridPosition itsYourTurn() {
		/*
		 * This particular (first crude attempt) does: 1. if there's more than
		 * one, it just picks on at random. 2. skips this step. 3. goes for 3a,
		 * nothing for 3b (because we didn't do #2 anyway).
		 */
		List<MiniGrid> minisThisTurn = this.board.getEnabledMinigrids();
		MiniGrid miniThisTurn;
		if (minisThisTurn.size() == 1) {
			miniThisTurn = minisThisTurn.get(0);
		} else {
			/*
			 * pick one at random
			 */
			miniThisTurn = Util.choice(minisThisTurn);
		}
		Map<ScribeMark, Integer> points = miniThisTurn.points();
		int myPoints = points.get(this.mark);
		int opponentPoints = points.get(this.mark.other());
		int delta = myPoints - opponentPoints;
		Log.d("itsYourTurn: mg: " + miniThisTurn + ", delta: " + delta);
		List<XY> cellsThisTurn = miniThisTurn.getEmptyCells();
		XY max = null;
		for (XY xy : cellsThisTurn) {
			MiniGrid trial = miniThisTurn.copy();
			trial.set(xy, this.mark);
			Map<ScribeMark, Integer> trialPoints = trial.points();
			int trialDelta = trialPoints.get(this.mark)
					- trialPoints.get(this.mark.other());
			Log.d("itsYourTurn: xy: " + xy + ", trialPoints: " + trialPoints
					+ ", trialDelta: " + trialDelta + ", delta: " + delta);
			if (trialDelta >= delta) {
				delta = trialDelta;
				max = xy;
			}
		}
		if (max != null) {
			return new GridPosition(miniThisTurn, max);
		}
		return new GridPosition(miniThisTurn, Util.choice(cellsThisTurn));
	}

}
