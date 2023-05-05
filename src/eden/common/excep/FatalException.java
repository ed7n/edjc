package eden.common.excep;

/**
 * Thrown when a fatal event has occurred.
 *
 * @author Brendon
 */
public class FatalException extends EDENRuntimeException {

  /** Problem description. */
  protected static final String PROBLEM =
    "Internal Error: A fatal event has occurred.";
  /** Suggested remedy. */
  protected static final String REMEDY =
    "Stop the fatal component or the application.";

  /** Makes an instance with the given subject. */
  public FatalException(String subject) {
    super(subject, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected FatalException() {}
}
