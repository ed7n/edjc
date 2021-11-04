package eden.common.excep;

/**
 * Thrown when a nullified object was used.
 *
 * @author Brendon
 * @see eden.common.object.Nullifiable
 */
public class NullifiedObjectUseException extends EDENRuntimeException {

  /** Problem description. */
  protected static final String PROBLEM
      = "Internal Error: The nullified object was used.";

  /** Makes a null instance. */
  public NullifiedObjectUseException() {
    this(null, null);
  }

  /** Makes an instance with the given object label. */
  public NullifiedObjectUseException(String object) {
    this(object, null);
  }

  /** Makes an instance with the given object label and cause. */
  public NullifiedObjectUseException(String object, Exception cause) {
    super(object, PROBLEM, cause);
  }
}
