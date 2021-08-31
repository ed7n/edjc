package eden.common.excep.cd;

/**
 * Thrown when a session has no tracks.
 *
 * @author Brendon
 */
public class SessionEmptyException extends BadCueSheetException {

  /** Problem description. */
  protected static final String PROBLEM
      = "The session has no tracks.";

  /** Suggested remedy. */
  protected static final String REMEDY
      = "Divide the session into tracks.";

  /** Makes an instance with the given line number. */
  public SessionEmptyException(long line) {
    super(line, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given session label. */
  public SessionEmptyException(String session) {
    super(session, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected SessionEmptyException() {
  }
}
