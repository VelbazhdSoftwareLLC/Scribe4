package eu.veldsoft.scribe4.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * A Region is a collection of adjacent cells, sometimes a glyph.
 */
@SuppressWarnings("serial")
public class Region extends HashSet<XY> {
  ScribeMark mark;
  private Region normalized = null;

  public Region(XY xy, ScribeMark square) {
    this.add(xy);
    this.mark = square;
  }

  private Region(ScribeMark mark) {
    this.mark = mark;
  }

  /**
   * @return the "normalized" version of this region: the same region,
   *         translated up and left as far as possible.
   */
  private Region normalized() {
    if (normalized == null) {
      int minX = Integer.MAX_VALUE;
      int minY = Integer.MAX_VALUE;
      for (XY xy : this) {
        minX = Math.min(minX, xy.x);
        minY = Math.min(minY, xy.y);
      }
      normalized = new Region(this.mark);
      for (XY xy : this) {
        normalized.add(XY.at(xy.x - minX, xy.y - minY));
      }
    }
    return normalized;
  }

  /**
   * @return the reflected version of this region
   */
  private Region reflected() {
    Region reflectedRegion = new Region(this.mark);
    for (XY xy : this) {
      reflectedRegion.add(XY.at(2 - xy.x, xy.y));
    }
    return reflectedRegion;
  }

  /**
   * @return the 90-degree-rotated version of this region
   */
  Region rotated() {
    Region rotatedRegion = new Region(this.mark);
    for (XY xy : this) {
      rotatedRegion.add(XY.at(2 - xy.y, xy.x));
    }
    return rotatedRegion.normalized();
  }

  private boolean isGlyphUnreflected() {
    Region normalizedRegion = this.normalized();
    Region r = normalizedRegion;
    do {
      for (Set<XY> glyph : Glyphs.ALL_GLYPHS.values()) {
        if (glyph.equals(r)) {
          return true;
        }
      }
      r = r.rotated();
    } while (!r.equals(normalizedRegion));
    return false;
  }

  private boolean isGlyphReflected() {
    return this.reflected().isGlyphUnreflected();
  }

  public boolean isGlyph() {
    return this.isGlyphUnreflected() || this.isGlyphReflected();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int y = 0; y < 3; y++) {
      sb.append('\n');
      for (int x = 0; x < 3; x++) {
        if (this.contains(XY.at(x, y))) {
          sb.append(this.mark.toChar());
        }
        else {
          sb.append(ScribeMark.EMPTY.toChar());
        }
      }
    }
    return sb.toString();
  }

  /*
   * FOR TESTING ONLY
   */
  static Region fromString(String string) {
    Random random = new Random();
    Region region;
    if (random.nextBoolean())
      region = new Region(ScribeMark.RED);
    else
      region = new Region(ScribeMark.BLUE);
    for (String xyString : string.split(" ")) {
      region.add(XY.fromString(xyString));
    }
    return region;
  }
}
