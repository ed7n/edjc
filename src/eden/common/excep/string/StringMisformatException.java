package eden.common.excep.string;

/**
 * Thrown when a string is not formatted to the given format.
 *
 * @author Brendon
 */
public class StringMisformatException extends BadStringException {

  /** Problem description. */
  protected static final String PROBLEM =
    "The string is not formatted to `%1$s`.";
  /** Suggested remedy. */
  protected static final String REMEDY = "Format the string to `%1$s`.";

  /** Makes an instance with the given string label and format. */
  public StringMisformatException(String string, String format) {
    super(
      string,
      String.format(PROBLEM, format),
      String.format(REMEDY, format)
    );
  }

  /** To prevent null instantiations of this class. */
  protected StringMisformatException() {}
}
