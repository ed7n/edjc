package eden.common.excep.string;

import static eden.common.shared.Constants.NUL_INT;

/**
 * Thrown when a string is too short.
 *
 * @author Brendon
 */
public class StringUnderflowException extends BadStringLengthException {

  /** Problem description. */
  protected static final String PROBLEM
      = "The string is too short.";

  /** Suggested remedy: at least. */
  protected static final String REMEDY_LEAST
      = "Lengthen the string to at least %1$d character(s) long.";

  /** Suggested remedy: exactly. */
  protected static final String REMEDY_EXACT
      = "Lengthen the string to %1$d character(s) long.";

  /** Suggested remedy: at least but at most. */
  protected static final String REMEDY_MOST
      = "Lengthen "
      + "the string to at least %1$d but at most %2$d character(s) long.";

  /** Makes a remedy message with the given minimum and maximum lengths. */
  protected static String makeRemedy(int minimum, int maximum) {
    if (minimum < 1)
      throw new IllegalArgumentException("minimum < 1");
    if (minimum == maximum)
      return String.format(REMEDY_EXACT, minimum);
    if (minimum < maximum)
      return String.format(REMEDY_MOST, minimum, maximum);
    return String.format(REMEDY_LEAST, minimum);
  }

  /** Makes an instance with the given string label, and minimum length. */
  public StringUnderflowException(String string, int minimum) {
    this(string, minimum, NUL_INT);
  }

  /**
   * Makes an instance with the given string label, minimum and maximum lengths.
   */
  public StringUnderflowException(String string, int minimum, int maximum) {
    super(string, PROBLEM, makeRemedy(minimum, maximum));
  }
}
