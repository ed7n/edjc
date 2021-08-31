package eden.common.excep.string;

/**
 * Thrown when a string is empty.
 *
 * @author Brendon
 */
public class StringEmptyException extends BadStringException {

  /** Problem description. */
  protected static final String PROBLEM
      = "The string is empty.";

  /** Makes an instance with the given string label. */
  public StringEmptyException(String string) {
    super(string, PROBLEM);
  }

  /** To prevent null instantiations of this class. */
  protected StringEmptyException() {
  }
}
