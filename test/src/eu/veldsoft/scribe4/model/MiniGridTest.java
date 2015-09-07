package eu.veldsoft.scribe4.model;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import eu.veldsoft.scribe4.model.MiniGrid;
import eu.veldsoft.scribe4.model.ScribeMark;

@RunWith(Parameterized.class)
public class MiniGridTest {

  @Parameters
  public static Collection<Object[]> parameters() {
    return Arrays.asList(new Object[][] {

        // I guess you would call this a "squat U"?
        { "++O\nO+O\n++O", 3, ScribeMark.fromChar('O') },

        { "OOO\n+OO\nO++", 4, ScribeMark.fromChar('+') },

        // Bomber
        { "+++\nO++\nOO+", 2, ScribeMark.fromChar('+') },
        { "+OO\n++O\n+++", 2, ScribeMark.fromChar('+') },

        // Chair
        { "++O\nOOO\nO+O", 3, ScribeMark.fromChar('O') },
        { "+OO\n+O+\nOOO", 3, ScribeMark.fromChar('O') },

        // Earring
        { "O++\n+O+\n+++", 3, ScribeMark.fromChar('+') },
        { "+OO\nO+O\nOOO", 3, ScribeMark.fromChar('O') },
        { "++O\n+O+\n+++", 3, ScribeMark.fromChar('+') },
        { "+++\n+O+\nO++", 3, ScribeMark.fromChar('+') },

        // House
        { "O+O\n+++\n+++", 3, ScribeMark.fromChar('+') },
        { "++O\n+++\n++O", 3, ScribeMark.fromChar('+') },
        { "+++\n+++\nO+O", 3, ScribeMark.fromChar('+') },
        { "O++\n+++\nO++", 3, ScribeMark.fromChar('+') },

        // O
        { "OOO\nO+O\nOOO", 2, ScribeMark.fromChar('O') },
        { "+++\n+O+\n+++", 2, ScribeMark.fromChar('+') },

        // J
        { "++O\nO+O\nOOO", 2, ScribeMark.fromChar('O') },
        { "OO+\nO++\nOOO", 2, ScribeMark.fromChar('O') },
        { "O++\nO+O\nOOO", 2, ScribeMark.fromChar('O') },

        // Cross
        { "O+O\n+++\nO+O", 5, ScribeMark.fromChar('+') },
        { "+O+\nOOO\n+O+", 5, ScribeMark.fromChar('O') },

        // Six Block
        { "+++\n+++\nOOO", 2, ScribeMark.fromChar('+') },
        { "++O\n++O\n++O", 2, ScribeMark.fromChar('+') },

        // T
        { "OOO\n+O+\n+O+", 3, ScribeMark.fromChar('O') },
        { "+O+\n+O+\nOOO", 3, ScribeMark.fromChar('O') },
        { "O++\nOOO\nO++", 3, ScribeMark.fromChar('O') },
        { "++O\nOOO\n++O", 3, ScribeMark.fromChar('O') },

        // U
        { "+O+\n+O+\n+++", 2, ScribeMark.fromChar('+') },
        { "+++\n+OO\n+++", 2, ScribeMark.fromChar('+') },
        { "+++\n+O+\n+O+", 2, ScribeMark.fromChar('+') },
        { "+++\nOO+\n+++", 2, ScribeMark.fromChar('+') },

        // ottoman
        { "O+O\nOOO\nOOO", 2, ScribeMark.fromChar('O') },
        { "OOO\n+OO\nOOO", 2, ScribeMark.fromChar('O') },
        { "OOO\nOO+\nOOO", 2, ScribeMark.fromChar('O') },
        { "OOO\nOOO\nO+O", 2, ScribeMark.fromChar('O') },

        // Four block
        { "++O\n++O\nOOO", 2, ScribeMark.fromChar('+') },
        { "O++\nO++\nOOO", 2, ScribeMark.fromChar('+') },
        { "OOO\n++O\n++O", 2, ScribeMark.fromChar('+') },
        { "OOO\nO++\nO++", 2, ScribeMark.fromChar('+') },

        // Nine block
        { "+++\n+++\n+++", 1, ScribeMark.fromChar('+') },
        { "OOO\nOOO\nOOO", 1, ScribeMark.fromChar('O') },

        // line line line
        { "+++\nOOO\n+++", 3, ScribeMark.fromChar('+') },

        // checkboard pattern: all singles
        { "+O+\nO+O\n+O+", 9, ScribeMark.fromChar('+') },

        // single in the corner wins
        { "+OO\nOOO\nOOO", 2, ScribeMark.fromChar('+') },
        { "OO+\nOOO\nOOO", 2, ScribeMark.fromChar('+') },
        { "OOO\nOOO\n+OO", 2, ScribeMark.fromChar('+') },
        { "OOO\nOOO\nOO+", 2, ScribeMark.fromChar('+') },

        { "O+O\n+O+\nO++", 7, ScribeMark.fromChar('O') },

        // pipe
        { "OO+\n+++\nOOO", 3, ScribeMark.fromChar('O') }, // O wins with 2+3
        { "+OO\n+++\nOOO", 3, ScribeMark.fromChar('O') }, // mirror image of
                                                          // previous
        { "OOO\nOO+\n+++", 2, ScribeMark.fromChar('+') }, // + wins because O
                                                          // has a nonglyph
                                                          // 5-region

        // squat T
        { "O+O\n+++\nOOO", 4, ScribeMark.fromChar('O') }, // O wins with 1+1+3
        { "O+O\nO++\nO+O", 4, ScribeMark.fromChar('O') },
        { "O+O\n++O\nO+O", 4, ScribeMark.fromChar('O') },
        { "OOO\nO+O\n+++", 2, ScribeMark.fromChar('+') }, // + wins since O has
                                                          // nonglyph 5-region

        // H
        { "O+O\nOOO\nO+O", 3, ScribeMark.fromChar('O') },
        { "OOO\n+O+\nOOO", 3, ScribeMark.fromChar('O') },

    });
  }

  private String miniGridString;
  private int numRegions;
  private ScribeMark winner;
  private MiniGrid miniGrid;

  public MiniGridTest(String miniGridString, int numRegions, ScribeMark winner) {
    this.miniGridString = miniGridString;
    this.numRegions = numRegions;
    this.winner = winner;

    this.miniGrid = MiniGrid.fromString(miniGridString);
  }

  @Test
  public void fromStringTest() {
    Assert.assertEquals(miniGridString, miniGrid.toString());
  }

  @Test
  public void numRegions() {
    Assert.assertEquals('\n' + miniGridString + '\n', numRegions,
        miniGrid.regions.size());
  }

  @Test
  public void winner() {
    Assert
        .assertEquals('\n' + miniGridString + '\n', winner, miniGrid.winner());
  }

}
