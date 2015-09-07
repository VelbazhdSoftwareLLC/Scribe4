package eu.veldsoft.scribe4;

import java.util.List;
import java.util.Random;

import eu.veldsoft.scribe4.model.ScribeMark;
import android.content.Context;

public abstract class Util {
  private static Random random = new Random();

  public static ScribeMark getRandomMark() {
    return random.nextBoolean() ? ScribeMark.RED : ScribeMark.BLUE;
  }

  public static String scribeMarkName(Context context, ScribeMark mark) {
    switch (mark) {
    case RED:
      return context.getString(R.string.red);
    case BLUE:
      return context.getString(R.string.blue);
    default:
      return context.getString(R.string.empty);
    }
  }

  /**
   * Chooses one of the objects from the array at random.
   *
   * Roughly equivalent to Python's random.choice()
   */
  public static <T> T choice(T[] array) {
    if (array.length == 0)
      throw new IllegalArgumentException(
          "Can't pick an item from an empty array");
    int i = new Random().nextInt(array.length);
    return array[i];
  }

  /**
   * Chooses one of the objects from the list at random.
   *
   * Roughly equivalent to Python's random.choice()
   */
  public static <T> T choice(List<T> list) {
    if (list.isEmpty())
      throw new IllegalArgumentException(
          "Can't pick an item from an empty list");
    int i = new Random().nextInt(list.size());
    return list.get(i);
  }
}
