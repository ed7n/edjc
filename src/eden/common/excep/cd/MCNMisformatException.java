package eden.common.excep.cd;

import eden.common.excep.EDENExceptions;

/**
 * Thrown when a Media Catalog Number (MCN) is misformatted.
 *
 * @author Brendon
 */
public class MCNMisformatException extends BadCueSheetException {

  /** Problem description. */
  protected static final String PROBLEM = EDENExceptions.makeMisformatProblem(
    "Media Catalog Number (MCN)"
  );
  /** Suggested remedy. */
  protected static final String REMEDY =
    "Correct the MCN to have thirteen alphanumeric characters.";

  /** Makes an instance with the given line number. */
  public MCNMisformatException(long line) {
    super(line, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given MCN label. */
  public MCNMisformatException(String mcn) {
    super(mcn, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected MCNMisformatException() {}
}
