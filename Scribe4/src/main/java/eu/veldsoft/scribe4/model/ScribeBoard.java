package eu.veldsoft.scribe4.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The game board and some other data, such as whose turn it is.
 */
public class ScribeBoard {
	public final static int GRID_SIZE = 3;

	private MiniGrid[][] data = new MiniGrid[GRID_SIZE][GRID_SIZE];

	private Map<ScribeMark, GridPosition> lastMove = new EnumMap<ScribeMark, GridPosition>(
			ScribeMark.class);

	ScribeMark whoseTurn = ScribeMark.RED;

	private List<ScribeListener> listeners = new ArrayList<ScribeListener>();

	public ScribeBoard() {
		for (XY xy : XY.allXYs()) {
			MiniGrid miniGrid = new MiniGrid(this);
			data[xy.x][xy.y] = miniGrid;
		}
	}

	public MiniGrid get(XY xy) {
		return data[xy.x][xy.y];
	}

	public MiniGrid get(int x, int y) {
		return data[x][y];
	}

	public GridPosition getLastMove(ScribeMark player) {
		return lastMove.get(player);
	}

	public ScribeMark whoseTurn() {
		return whoseTurn;
	}

	public boolean isFull() {
		for (XY xy : XY.allXYs()) {
			if (get(xy).isFull() == false) {
				return false;
			}
		}

		return true;
	}

	public boolean isEmpty() {
		for (XY xy : XY.allXYs()) {
			if (get(xy).isEmpty() == false) {
				return false;
			}
		}

		return true;
	}

	private void update() {
		if (isFull() == true) {
			Iterator<ScribeMark> iterator = winner().iterator();
			while (iterator.hasNext() == true) {
				ScribeMark mark = iterator.next();
				notifyListenersOfWinner(mark);
			}
		} else {
			setWhoseTurn(whoseTurn.other());
		}

		for (XY xy : XY.allXYs()) {
			MiniGrid miniGrid = this.get(xy);
			miniGrid.clearLastMoves();
		}

		for (GridPosition gp : lastMove.values()) {
			gp.miniGrid.addLastMove(gp.xy);
		}
	}

	private void enableMiniGrids() {
		GridPosition gridPosition = lastMove.get(whoseTurn);
		if (gridPosition == null) {
			setAllMiniGridsEnabled(true);
			return;
		}

		XY xy = gridPosition.xy;
		MiniGrid miniGrid = data[xy.x][xy.y];
		if (miniGrid.isFull()) {
			enableAllNonFullMiniGrids();
		} else {
			setAllMiniGridsEnabled(false);
			miniGrid.setEnabled(true);
		}
	}

	private void enableAllNonFullMiniGrids() {
		for (XY xy : XY.allXYs()) {
			MiniGrid miniGrid = get(xy);
			boolean enable = !miniGrid.isFull();
			miniGrid.setEnabled(enable);
		}
	}

	private void setAllMiniGridsEnabled(boolean enable) {
		for (XY xy : XY.allXYs()) {
			get(xy).setEnabled(enable);
		}
	}

	/**
	 * @return a collection of all the MiniGrids that are currently enabled.
	 */
	public List<MiniGrid> getEnabledMinigrids() {
		List<MiniGrid> enabledMiniGrids = new ArrayList<MiniGrid>();
		for (XY xy : XY.allXYs()) {
			MiniGrid miniGrid = get(xy);
			if (miniGrid.isEnabled()) {
				enabledMiniGrids.add(miniGrid);
			}
		}
		return Collections.unmodifiableList(enabledMiniGrids);
	}

	void setWhoseTurn(ScribeMark mark) {
		whoseTurn = mark;
		enableMiniGrids();
		notifyListenersOfWhoseTurn();
	}

	private void notifyListenersOfWhoseTurn() {
		for (ScribeListener listener : listeners) {
			listener.whoseTurnChanged(this, whoseTurn);
		}
	}

	private void notifyListenersOfWinner(ScribeMark winner) {
		for (ScribeListener listener : listeners) {
			listener.scribeBoardWon(this, winner);
		}
	}

	public void addListener(ScribeListener listener) {
		listeners.add(listener);
	}

	private Set<ScribeMark> winner() {
		assert isFull();
		switch (Settings.getGameMode()) {
		case SuperGlyph:
			/*
			 * build a temporary "MiniGrid" for calculating the overall winner
			 */
			MiniGrid superGrid = new MiniGrid();
			for (XY xy : XY.allXYs()) {
				Iterator<ScribeMark> iterator = data[xy.x][xy.y].winner()
						.iterator();
				while (iterator.hasNext() == true) {
					ScribeMark value = iterator.next();
					superGrid.set(xy, value);
				}
			}
			return superGrid.winner();
		case Majority:
		default:
			Map<ScribeMark, Integer> points = new EnumMap<ScribeMark, Integer>(
					ScribeMark.class);
			points.put(ScribeMark.RED, 0);
			points.put(ScribeMark.BLUE, 0);
			points.put(ScribeMark.GREEN, 0);
			points.put(ScribeMark.PURPLE, 0);
			for (XY xy : XY.allXYs()) {
				Iterator<ScribeMark> iterator = get(xy).winner().iterator();
				while (iterator.hasNext() == true) {
					ScribeMark value = iterator.next();
					points.put(value, points.get(value) + 1);
				}
			}

			/*
			 * Find maximum points.
			 */
			int max = 0;
			for (ScribeMark key : points.keySet()) {
				if (max < points.get(key)) {
					max = points.get(key);
				}
			}

			/*
			 * Remove all elements with points less than maximum.
			 */
			for (ScribeMark key : points.keySet()) {
				if (points.get(key) < max) {
					points.remove(key);
				}
			}

			return points.keySet();
		}
	}

	MiniGridListener miniGridListener = new DefaultMiniGridListener() {
		@Override
		public void miniGridMarked(MiniGrid miniGrid, XY xy, ScribeMark mark) {
			lastMove.put(mark, new GridPosition(miniGrid, xy));
			update();
		}
	};

	XY getMiniGridXY(MiniGrid mg) {
		for (XY xy : XY.allXYs()) {
			if (this.get(xy) == mg) {
				return xy;
			}
		}
		throw new RuntimeException("cannot find minigrid: " + mg);
	}
}
