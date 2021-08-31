package eden.common.excep.string;

/**
 * Thrown when a string length is invalid.
 *
 * @author Brendon
 */
public abstract class BadStringLengthException extends BadStringException {

  /** Makes an instance with the given string label, problem, and remedy. */
  protected BadStringLengthException(
      String string, String problem, String remedy) {
    super(string, problem, remedy);
  }

  /** To prevent null instantiations of this class. */
  protected BadStringLengthException() {
  }
}
