package eden.common.excep.cd;

/**
 * Thrown when an index is unexpected at its place.
 *
 * @author Brendon
 */
public class IndexUnexpectedException extends BadCueSheetException {

  /** Problem description. */
  protected static final String PROBLEM = makeUnexpectedProblem("index");
  /** Suggested remedy. */
  protected static final String REMEDY = "Correct its placement or number.";

  /** Makes an instance with the given track and index numbers. */
  public IndexUnexpectedException(int track, int index) {
    super(track, index, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given track, index, and expected numbers. */
  public IndexUnexpectedException(int track, int index, int expected) {
    super(track, index, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given track, index, and line numbers. */
  public IndexUnexpectedException(int track, int index, long line) {
    super(track, index, line, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given index label. */
  public IndexUnexpectedException(String index) {
    super(index, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected IndexUnexpectedException() {}
}
