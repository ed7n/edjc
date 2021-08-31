package eden.common.excep.cd;

import eden.common.excep.EDENExceptions;
import eden.common.model.cd.Index;

/**
 * Thrown when an index is misnumbered.
 *
 * @author Brendon
 */
public class IndexMisnumberException extends BadCueSheetException {

  /** Problem description. */
  protected static final String PROBLEM
      = EDENExceptions.makeMisnumberProblem("index");

  /** Suggested remedy. */
  protected static final String REMEDY
      = makeMisnumberRemedy(
          "the index number", Index.MIN_NUMBER, Index.MAX_NUMBER);

  /** Makes an instance with the given track and index numbers. */
  public IndexMisnumberException(int track, int index) {
    super(track, index, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given track, index, and line numbers. */
  public IndexMisnumberException(int track, int index, long line) {
    super(track, index, line, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given index label. */
  public IndexMisnumberException(String index) {
    super(index, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected IndexMisnumberException() {
  }
}
