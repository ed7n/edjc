package eden.common.excep;

import static eden.common.excep.EDENException.NUL_PROBLEM;
import static eden.common.excep.EDENException.NUL_REMEDY;
import static eden.common.excep.EDENException.NUL_SUBJECT;
import static eden.common.excep.EDENException.makeMessage;

import eden.common.util.Strings;

/**
 * The RuntimeException equivalent of EDENException.
 *
 * @author Brendon
 * @see eden.common.excep.EDENException
 */
public class EDENRuntimeException extends RuntimeException {

  /** Subject that has the problem. */
  private final String subject;
  /** Problem that the subject has. */
  private final String problem;
  /** Suggested remedy to the problem. */
  private final String remedy;

  /** Makes an instance with the given subject and problem. */
  public EDENRuntimeException(String subject, String problem) {
    this(subject, problem, null, null);
  }

  /** Makes an instance with the given subject, problem, and remedy. */
  public EDENRuntimeException(String subject, String problem, String remedy) {
    this(subject, problem, remedy, null);
  }

  /** Makes an instance with the given subject, problem, and cause. */
  public EDENRuntimeException(String subject, String problem, Exception cause) {
    this(subject, problem, null, cause);
  }

  /** Makes an instance with the given subject, problem, remedy, and cause. */
  public EDENRuntimeException(
    String subject,
    String problem,
    String remedy,
    Exception cause
  ) {
    super(makeMessage(subject, problem), cause);
    this.subject = Strings.defaultOrAsIs(NUL_SUBJECT, subject);
    this.problem = Strings.defaultOrAsIs(NUL_PROBLEM, problem);
    this.remedy = Strings.defaultOrAsIs(NUL_REMEDY, remedy);
  }

  /** To prevent null instantiations of this class. */
  protected EDENRuntimeException() {
    this.subject = null;
    this.problem = null;
    this.remedy = null;
  }

  /** Returns the subject that has the problem. */
  public String getSubject() {
    return this.subject;
  }

  /** Returns the problem that the subject has. */
  public String getProblem() {
    return this.problem;
  }

  /** Returns the suggested remedy to the problem. */
  public String getRemedy() {
    return this.remedy;
  }
}
