package eden.common.excep.cd;

import eden.common.excep.EDENExceptions;

/**
 * Thrown when an International Standard Recording Code (ISRC) is misformatted.
 *
 * @author Brendon
 * @see <a href="https://isrc.ifpi.org/en/isrc-standard/structure">Structure</a>
 */
public class ISRCMisformatException extends BadCueSheetException {

  /** Problem description. */
  protected static final String PROBLEM
      = EDENExceptions.makeMisformatProblem(
          "International Standard Recording Code (ISRC)");

  /** Suggested remedy. */
  protected static final String REMEDY
      = "Correct the ISRC to have five alphanumeric characters followed by "
      + "seven numeric characters.";

  /** Makes an instance with the given track number. */
  public ISRCMisformatException(int track) {
    super(track, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given line number. */
  public ISRCMisformatException(long line) {
    super(line, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given track and line numbers. */
  public ISRCMisformatException(int track, long line) {
    super(track, line, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given ISRC label. */
  public ISRCMisformatException(String isrc) {
    super(isrc, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected ISRCMisformatException() {
  }
}
