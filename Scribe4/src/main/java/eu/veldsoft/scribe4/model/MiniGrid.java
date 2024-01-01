package eu.veldsoft.scribe4.model;

import static eu.veldsoft.scribe4.model.ScribeBoard.GRID_SIZE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MiniGrid {
	private final ScribeMark[][] data;
	List<Region> regions = new ArrayList<Region>();
	private final List<MiniGridListener> listeners = new ArrayList<MiniGridListener>();
	private boolean enabled = true;

	private Collection<XY> lastMoves = new HashSet<XY>();
	private ScribeBoard parent;

	private Set<ScribeMark> winner = null;

	MiniGrid() {
		data = new ScribeMark[3][3];
		for (XY xy : XY.allXYs()) {
			data[xy.y][xy.x] = ScribeMark.EMPTY;
		}
	}

	public MiniGrid(ScribeBoard scribeBoard) {
		this();
		this.parent = scribeBoard;
		this.addMiniGridListener(scribeBoard.miniGridListener);
	}

	public void set(int x, int y, ScribeMark mark) {
		set(XY.at(x, y), mark);
	}

	public void set(XY xy, ScribeMark mark) {
		if (!this.enabled)
			throw new ScribeException("This MiniGrid is disabled");
		if (mark != ScribeMark.EMPTY && this.get(xy) != ScribeMark.EMPTY) {
			throw new ScribeException(
					"You cannot over-write a square that has already been claimed.");
		}
		data[xy.x][xy.y] = mark;
		if (mark != ScribeMark.EMPTY) {
			updateRegions(xy, mark);
			notifyListenersOfMark(xy, mark);
		}
		if (this.isFull()) {
			notifyListenersOfMiniGridWon();
		}
	}

	private void updateRegions(XY xy, ScribeMark mark) {
		List<Region> regionsContainingNewSquare = new ArrayList<Region>();

		for (Region region : this.regions) {
			for (XY neighbor : xy.neighbors()) {
				if (this.get(neighbor) == mark && region.contains(neighbor)) {
					region.add(xy);
					regionsContainingNewSquare.add(region);
					break;
				}
			}
		}

		switch (regionsContainingNewSquare.size()) {
		case 0:
			Region newRegion = new Region(xy, mark);
			this.regions.add(newRegion);
			break;
		default:
			/*
			 * merge all the regions into one
			 */
			Region mergedRegion = regionsContainingNewSquare.remove(0);
			for (Region region : regionsContainingNewSquare) {
				this.regions.remove(region);
				mergedRegion.addAll(region);
			}
			break;
		}
	}

	public ScribeMark get(int x, int y) {
		return data[x][y];
	}

	public ScribeMark get(XY xy) {
		return data[xy.x][xy.y];
	}

	/**
	 * @return A list of all the positions on this MiniGrid that are still
	 *         empty.
	 */
	public List<XY> getEmptyCells() {
		List<XY> emptyCells = new ArrayList<XY>();
		for (XY xy : XY.allXYs()) {
			if (this.get(xy) == ScribeMark.EMPTY) {
				emptyCells.add(xy);
			}
		}
		return emptyCells;
	}

	public boolean isEmpty() {
		for (XY xy : XY.allXYs()) {
			if (this.get(xy) != ScribeMark.EMPTY)
				return false;
		}

		return true;
	}

	public boolean isFull() {
		for (XY xy : XY.allXYs()) {
			if (this.get(xy) == ScribeMark.EMPTY)
				return false;
		}

		return true;
	}

	public Set<ScribeMark> winner() {
		if (winner != null) {
			return winner;
		}

		if (isFull() == false) {
			Set<ScribeMark> result = new TreeSet<ScribeMark>();
			result.add(ScribeMark.EMPTY);
			return result;
		}

		Map<ScribeMark, Integer> points = points();

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

	public Map<ScribeMark, Integer> points() {
		Map<ScribeMark, Integer> points = new EnumMap<ScribeMark, Integer>(
				ScribeMark.class);

		points.put(ScribeMark.BLUE, 0);
		points.put(ScribeMark.RED, 0);
		points.put(ScribeMark.GREEN, 0);
		points.put(ScribeMark.PURPLE, 0);

		for (Region region : this.regions) {
			if (region.isGlyph() == false) {
				continue;
			}

			points.put(region.mark, points.get(region.mark) + region.size());
		}

		return points;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				sb.append(data[x][y].toChar());
			}
			if (y < 2)
				sb.append('\n'); // hacky!
		}
		return sb.toString();
	}

	/**
	 * The input to fromString is the same as the output of toString
	 */
	public static MiniGrid fromString(String string) {
		string = string.replaceAll("\\s", "");
		MiniGrid miniGrid = new MiniGrid();
		int i = 0;
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				miniGrid.set(x, y, ScribeMark.fromChar(string.charAt(i++)));
			}
		}
		return miniGrid;
	}

	public void addMiniGridListener(MiniGridListener listener) {
		if (listener != null)
			listeners.add(listener);
	}

	private void notifyListenersOfMark(XY xy, ScribeMark mark) {
		for (MiniGridListener listener : listeners) {
			listener.miniGridMarked(this, xy, mark);
		}
	}

	private void notifyListenersOfEnabledState() {
		for (MiniGridListener listener : listeners) {
			listener.miniGridEnabled(this, enabled);
		}
	}

	private void notifyListenersOfLastMovesChange() {
		for (MiniGridListener listener : listeners) {
			listener.miniGridLastMovesChanged(this, getLastMoves());
		}
	}

	private void notifyListenersOfMiniGridWon() {
		Iterator<ScribeMark> iterator = winner().iterator();
		while (iterator.hasNext() == true) {
			ScribeMark value = iterator.next();
			for (MiniGridListener listener : listeners) {
				listener.miniGridWon(this, value);
			}
		}
	}

	void setEnabled(boolean enabled) {
		this.enabled = enabled;
		notifyListenersOfEnabledState();
	}

	public boolean isEnabled() {
		return enabled;
	}

	void clearLastMoves() {
		if (!this.lastMoves.isEmpty()) {
			this.lastMoves.clear();
			notifyListenersOfLastMovesChange();
		}
	}

	void addLastMove(XY xy) {
		this.lastMoves.add(xy);
		notifyListenersOfLastMovesChange();
	}

	public Collection<XY> getLastMoves() {
		return Collections.unmodifiableCollection(lastMoves);
	}

	public void tryMove(XY xy) {
		try {
			this.set(xy, parent.whoseTurn());
		} catch (ScribeException e) {
			/*
			 * do nothing
			 */
		}
	}

	public MiniGrid copy() {
		MiniGrid copy = new MiniGrid();
		copy.parent = this.parent;
		for (int y = 0; y < GRID_SIZE; y++) {
			for (int x = 0; x < GRID_SIZE; x++) {
				copy.set(x, y, this.get(x, y));
			}
		}
		return copy;

	}
}
