package eu.veldsoft.scribe4.model;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MiniGridEmptyCellsTest {

	private String miniGridString;
	private int numEmptyCells;
	private MiniGrid miniGrid;

	public MiniGridEmptyCellsTest(String miniGridString, int numEmptyCells) {
		this.miniGridString = miniGridString;
		this.numEmptyCells = numEmptyCells;

		this.miniGrid = MiniGrid.fromString(miniGridString);
	}

	@Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][] { { "+++ O++ OO+", 0 },
				{ "+++ --- OOO", 3 }, { "+-+ --- OOO", 4 },
				{ "+O+ --- OO-", 4 }, { "+++ --- --O", 5 },
				{ "--- --- ---", 9 }, { "-+- +-+ -+-", 5 },

		});
	}

	@Test
	public void numEmptyCells() {
		Assert.assertEquals('\n' + miniGridString + '\n', numEmptyCells,
				miniGrid.getEmptyCells().size());
	}
}
