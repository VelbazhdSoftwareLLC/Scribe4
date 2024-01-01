package eu.veldsoft.scribe4.model;

import java.util.Arrays;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class RegionGlyphTest {

	@Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][] {

				// single
				{ "00", true },
				{ "01", true },
				{ "20", true },
				{ "11", true },

				// double
				{ "00 01", true },
				// etc.

				// pipe
				{ "01 11 21 20", true }, { "00 01 02 12", true },
				{ "00 10 20 01", true }, { "00 10 11 12", true },

				/*
				 * mini-L's, such as
				 * 
				 * --- -O- -OO
				 */
				{ "00 10 11", false }, { "00 01 11", false },
				{ "01 02 12", false }, { "00 10 11", false },

				/*
				 * Shapes such as:
				 * 
				 * OOO -OO ---
				 */
				{ "00 10 20 11 21", false } });
	}

	private Region region;
	private boolean isGlyph;

	public RegionGlyphTest(String region, boolean isGlyph) {
		this.region = Region.fromString(region);
		this.isGlyph = isGlyph;
	}

	@Test
	public void glyphTest() {
		Assert.assertEquals(isGlyph, region.isGlyph());
	}
}
