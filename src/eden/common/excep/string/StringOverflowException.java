package eden.common.excep.string;

import static eden.common.shared.Constants.NUL_INT;

/**
 * Thrown when a string is too long.
 *
 * @author Brendon
 */
public class StringOverflowException extends BadStringLengthException {

  /** Problem description. */
  protected static final String PROBLEM = "The string is too long.";
  /** Suggested remedy: at most. */
  protected static final String REMEDY_MOST =
    "Shorten the string to at least %1$d character(s) long.";
  /** Suggested remedy: exactly. */
  protected static final String REMEDY_EXACT =
    "Shorten the string to %1$d character(s) long.";
  /** Suggested remedy: at most but at least. */
  protected static final String REMEDY_LEAST =
    "Shorten " +
    "the string to at most %1$d but at least %2$d character(s) long.";

  /** Makes a remedy message with the given minimum and maximum lengths. */
  protected static String makeRemedy(int minimum, int maximum) {
    if (maximum < 1) {
      throw new IllegalArgumentException("maximum < 1");
    }
    if (maximum == minimum) {
      return String.format(REMEDY_EXACT, maximum);
    }
    if (minimum > 0 && maximum > minimum) {
      return String.format(REMEDY_LEAST, maximum, minimum);
    }
    return String.format(REMEDY_MOST, maximum);
  }

  /** Makes an instance with the given string label, and minimum length. */
  public StringOverflowException(String string, int maximum) {
    this(string, NUL_INT, maximum);
  }

  /**
   * Makes an instance with the given string label, minimum and maximum lengths.
   */
  public StringOverflowException(String string, int minimum, int maximum) {
    super(string, PROBLEM, makeRemedy(minimum, maximum));
  }
}
