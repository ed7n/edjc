package eden.common.util;

import eden.common.excep.NumberNegativeException;

/**
 * Utility methods for operating on numbers.
 *
 * @author Brendon
 */
public class Numbers {

  /** Ensures that the given byte is not negative. */
  public static byte requireNonNegative(byte number) {
    if (number < 0) {
      throw new NumberNegativeException(number);
    }
    return number;
  }

  /** Ensures that the given short is not negative. */
  public static short requireNonNegative(short number) {
    if (number < 0) {
      throw new NumberNegativeException(number);
    }
    return number;
  }

  /** Ensures that the given integer is not negative. */
  public static int requireNonNegative(int number) {
    if (number < 0) {
      throw new NumberNegativeException(number);
    }
    return number;
  }

  /** Ensures that the given long is not negative. */
  public static long requireNonNegative(long number) {
    if (number < 0) {
      throw new NumberNegativeException(number);
    }
    return number;
  }

  /** Ensures that the given float is not negative. */
  public static float requireNonNegative(float number) {
    if (number < 0) {
      throw new NumberNegativeException(number);
    }
    return number;
  }

  /** Ensures that the given double is not negative. */
  public static double requireNonNegative(double number) {
    if (number < 0) {
      throw new NumberNegativeException(number);
    }
    return number;
  }

  /** Stringifies the given number to at least two digits. */
  public static String toString2Digits(long number) {
    return toString(number, 2);
  }

  /** Stringifies the given number to at least the given number of digits. */
  public static String toString(long number, int digits) {
    requireNonNegative(digits);
    String out = Long.toString(Math.abs(number));
    int remain = digits - out.length();
    if (remain < 0) {
      return number < 0 ? "-" + out : out;
    }
    StringBuilder padding = new StringBuilder(remain);
    while (remain-- > 0) {
      padding.append("0");
    }
    out = padding.append(out).toString();
    return number < 0 ? "-" + out : out;
  }

  /** To prevent instantiations of this class. */
  protected Numbers() {}
}
