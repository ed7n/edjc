package eden.common.excep;

/**
 * Placeholder death cause for dieables that have been nullified.
 *
 * @author Brendon
 * @see eden.common.object.Dieable
 * @see eden.common.object.Nullifiable
 */
public class NullifiedObjectException extends EDENRuntimeException {

  /** Null instance. */
  public static final NullifiedObjectException nul
      = new NullifiedObjectException();

  /** Problem description. */
  protected static final String PROBLEM
      = "The object is nullified.";

  /** Makes an instance with the given object label. */
  public NullifiedObjectException(String object) {
    this(object, null);
  }

  /** Makes an instance with the given object label and cause. */
  public NullifiedObjectException(String object, Exception cause) {
    super(object, PROBLEM, cause);
  }

  /** To prevent null instantiations of this class. */
  protected NullifiedObjectException() {
    this(null, null);
  }
}
