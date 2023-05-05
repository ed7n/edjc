package eden.common.excep.cd;

import eden.common.model.cd.Track;

/**
 * Thrown when a session has too many tracks.
 *
 * @author Brendon
 */
public class SessionOverflowException extends BadCueSheetException {

  /** Problem description. */
  protected static final String PROBLEM = "The session has too many tracks.";
  /** Suggested remedy. */
  protected static final String REMEDY =
    "Erase excess tracks until at most " + Track.MAX_COUNT + " remain.";

  /** Makes an instance with the given line number. */
  public SessionOverflowException(long line) {
    super(line, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given session label. */
  public SessionOverflowException(String session) {
    super(session, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected SessionOverflowException() {}
}
