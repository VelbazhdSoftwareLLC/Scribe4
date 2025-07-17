package eu.veldsoft.scribe4;

import java.util.List;
import java.util.Random;

/**
 * @author
 */
public abstract class Util {
    /**
     *
     */
    public final static Random PRNG = new Random();

    /**
     * Chooses one of the objects from the array at random.
     * <p>
     * Roughly equivalent to Python's random.choice()
     */
    public static <T> T choice(T[] array) {
        if (array.length == 0)
            throw new IllegalArgumentException(
                    "Can't pick an item from an empty array");

        return array[PRNG.nextInt(array.length)];
    }

    /**
     * Chooses one of the objects from the list at random.
     * <p>
     * Roughly equivalent to Python's random.choice()
     */
    public static <T> T choice(List<T> list) {
        if (list.isEmpty())
            throw new IllegalArgumentException(
                    "Can't pick an item from an empty list");

        return list.get(PRNG.nextInt(list.size()));
    }
}
