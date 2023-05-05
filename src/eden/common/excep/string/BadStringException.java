package eden.common.excep.string;

import eden.common.excep.EDENRuntimeException;

/**
 * Thrown when a string is malformed.
 *
 * @author Brendon
 */
public class BadStringException extends EDENRuntimeException {

  /** Makes an instance with the given subject and problem. */
  protected BadStringException(String subject, String problem) {
    super(subject, problem);
  }

  /** Makes an instance with the given subject, problem, and remedy. */
  protected BadStringException(String subject, String problem, String remedy) {
    super(subject, problem, remedy);
  }

  /** To prevent null instantiations of this class. */
  protected BadStringException() {}
}
