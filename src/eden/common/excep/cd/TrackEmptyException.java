package eden.common.excep.cd;

/**
 * Thrown when a track has too few or no indexes.
 *
 * @author Brendon
 */
public class TrackEmptyException extends BadCueSheetException {

  /** Problem description. */
  protected static final String PROBLEM =
    "The track has too few or no indexes.";
  /** Suggested remedy. */
  protected static final String REMEDY =
    "Divide the track into at least indexes `00` and `01`.";

  /** Makes an instance with the given track number. */
  public TrackEmptyException(int track) {
    super(track, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given track and line numbers. */
  public TrackEmptyException(int track, long line) {
    super(track, line, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given track label. */
  public TrackEmptyException(String track) {
    super(track, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected TrackEmptyException() {}
}
