package eden.common.excep.cd;

/**
 * Thrown when a frame number or time code is unexpected at its place.
 *
 * @author Brendon
 */
public class FrameUnexpectedException extends BadCueSheetException {

  /** Problem description. */
  protected static final String PROBLEM = makeUnexpectedProblem(
    "frame number or time code"
  );
  /** Suggested remedy. */
  protected static final String REMEDY =
    "Correct it to follow after that of the previous index.";

  /** Makes an instance with the given track and index numbers. */
  public FrameUnexpectedException(int track, int index) {
    super(track, index, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given track, index, and line numbers. */
  public FrameUnexpectedException(int track, int index, long line) {
    super(track, index, line, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected FrameUnexpectedException() {}
}
