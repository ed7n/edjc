package eden.common.excep.cd;

/**
 * Thrown when a track is unexpected at its place.
 *
 * @author Brendon
 */
public class TrackUnexpectedException extends BadCueSheetException {

  /** Problem description. */
  protected static final String PROBLEM = makeUnexpectedProblem("track");
  /** Suggested remedy. */
  protected static final String REMEDY = "Correct its placement or number.";

  /** Makes an instance with the given track number. */
  public TrackUnexpectedException(int track) {
    super(track, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given track and line numbers. */
  public TrackUnexpectedException(int track, long line) {
    super(track, line, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given track label. */
  public TrackUnexpectedException(String track) {
    super(track, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected TrackUnexpectedException() {}
}
