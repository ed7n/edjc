package eden.common.excep;

import static eden.common.shared.Constants.LINE_WIDTH;

import eden.common.util.Strings;

/**
 * An extension to Java's Exception following a subject-problem-remedy
 * structure.
 *
 * For simplicity, its Exception superclass omits the remedy message.
 *
 * @author Brendon
 * @see eden.common.excep
 */
public class EDENException extends Exception {

  /** Default for null subjects. */
  static final String NUL_SUBJECT = "Unspecified Subject.";
  /** Default for null problems. */
  static final String NUL_PROBLEM = "Unspecified problem.";
  /** Default for null remedies. */
  static final String NUL_REMEDY = "No suggested remedy.";

  /** Returns a string describing itself for its superclass. */
  static String makeMessage(String subject, String problem) {
    StringBuilder builder = new StringBuilder(LINE_WIDTH);
    if (subject != null) {
      builder.append(subject).append(": ");
    }
    if (problem != null) {
      builder.append(problem);
    }
    return builder.toString();
  }

  /** Subject that has the problem. */
  private final String subject;
  /** Problem that the subject has. */
  private final String problem;
  /** Suggested remedy to the problem. */
  private final String remedy;

  /** Makes an instance with the given subject and problem. */
  public EDENException(String subject, String problem) {
    this(subject, problem, null, null);
  }

  /** Makes an instance with the given subject, problem, and remedy. */
  public EDENException(String subject, String problem, String remedy) {
    this(subject, problem, remedy, null);
  }

  /** Makes an instance with the given subject, problem, and cause. */
  public EDENException(String subject, String problem, Exception cause) {
    this(subject, problem, null, cause);
  }

  /** Makes an instance with the given subject, problem, remedy, and cause. */
  public EDENException(
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
  protected EDENException() {
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
