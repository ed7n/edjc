package eden.common.excep.cd;

import eden.common.model.cd.Index;

/**
 * Thrown when a track has too many indexes.
 *
 * @author Brendon
 */
public class TrackOverflowException extends BadCueSheetException {

  /** Problem description. */
  protected static final String PROBLEM
      = "The track has too many indexes.";

  /** Suggested remedy. */
  protected static final String REMEDY
      = "Erase excess indexes until at most " + Index.MAX_COUNT + " remain.";

  /** Makes an instance with the given track number. */
  public TrackOverflowException(int track) {
    super(track, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given track and line numbers. */
  public TrackOverflowException(int track, long line) {
    super(track, line, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given track label. */
  public TrackOverflowException(String track) {
    super(track, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected TrackOverflowException() {
  }
}
