package eu.veldsoft.scribe4.model;

import java.util.Arrays;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.veldsoft.scribe4.model.Region;

@RunWith(Parameterized.class)
public class RotatedRegionTest {

  @Parameters
  public static Collection<Object[]> parameters() {
    return Arrays.asList(new Object[][] { { "00", "00" }, { "01", "00" },
        { "20", "00" }, { "11", "00" }, { "00 01", "00 10" },

        // pipe
        { "01 11 21 20", "00 01 02 12" }, { "00 01 02 12", "00 10 20 01" },
        { "00 10 20 01", "00 10 11 12" }, { "00 10 11 12", "01 11 21 20" }, });
  }

  private Region originalRegion;
  private Region rotatedRegion;

  public RotatedRegionTest(String originalRegion, String rotatedRegion) {
    this.originalRegion = Region.fromString(originalRegion);
    this.rotatedRegion = Region.fromString(rotatedRegion);
  }

  @Test
  public void rotatedTest() {
    String message = "original:\n" + originalRegion + "rotated:\n"
        + originalRegion.rotated();
    Assert.assertEquals(message, rotatedRegion, originalRegion.rotated());
    Assert.assertEquals(originalRegion.mark, originalRegion.rotated().mark);
  }
}
