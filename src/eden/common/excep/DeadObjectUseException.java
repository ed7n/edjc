package eden.common.excep;

/**
 * Thrown when a dead object was used.
 *
 * @author Brendon
 * @see eden.common.object.Dieable
 */
public class DeadObjectUseException extends EDENRuntimeException {

  /** Problem description. */
  protected static final String PROBLEM =
    "Internal Error: The dead object was used.";

  /** Makes a null instance. */
  public DeadObjectUseException() {
    this(null, null);
  }

  /** Makes an instance with the given object label. */
  public DeadObjectUseException(String object) {
    this(object, null);
  }

  /** Makes an instance with the given object label and cause. */
  public DeadObjectUseException(String object, Exception cause) {
    super(object, PROBLEM, cause);
  }
}
