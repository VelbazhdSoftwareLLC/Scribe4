package eu.veldsoft.scribe4.ai;

import eu.veldsoft.scribe4.AIPlayer;
import eu.veldsoft.scribe4.Util;
import eu.veldsoft.scribe4.model.GridPosition;
import eu.veldsoft.scribe4.model.MiniGrid;
import eu.veldsoft.scribe4.model.XY;

/**
 * A very stupid AI, simply to demonstrate the way an AI player could be
 * written.
 */
public class RandomAIPlayer extends AIPlayer {

    /**
     *
     */
    @Override
    public GridPosition itsYourTurn() {
        MiniGrid miniGrid = Util.choice(this.board.getEnabledMinigrids());
        XY xy = Util.choice(miniGrid.getEmptyCells());
        return new GridPosition(miniGrid, xy);
    }

}
