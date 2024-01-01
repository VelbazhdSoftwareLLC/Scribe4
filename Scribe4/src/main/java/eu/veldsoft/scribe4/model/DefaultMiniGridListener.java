package eu.veldsoft.scribe4.model;

import java.util.Collection;

/**
 * Does no action at all for any MiniGridListener methods. Extend this class and
 * override only the ones you want.
 */
public abstract class DefaultMiniGridListener implements MiniGridListener {

	@Override
	public void miniGridEnabled(MiniGrid miniGrid, boolean enabled) {
	}

	@Override
	public void miniGridLastMovesChanged(MiniGrid miniGrid,
			Collection<XY> lastMoves) {
	}

	@Override
	public void miniGridMarked(MiniGrid miniGrid, XY xy, ScribeMark mark) {
	}

	@Override
	public void miniGridWon(MiniGrid miniGrid, ScribeMark winner) {
	}
}
