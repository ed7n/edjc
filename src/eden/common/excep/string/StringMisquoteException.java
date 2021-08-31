package eden.common.excep.string;

/**
 * Thrown when a string is not enclosed in quotation marks.
 *
 * @author Brendon
 */
public class StringMisquoteException extends BadStringException {

  /** Problem description. */
  protected static final String PROBLEM
      = "The string is not enclosed in quotation marks (\").";

  /** Suggested remedy. */
  protected static final String REMEDY
      = "Enclose the string in quotation marks (\").";

  /** Makes an instance with the given string label. */
  public StringMisquoteException(String string) {
    super(string, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected StringMisquoteException() {
  }
}
