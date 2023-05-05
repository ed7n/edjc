package eden.common.excep.cd;

import eden.common.excep.EDENExceptions;
import eden.common.model.cd.Index;

/**
 * Thrown when a frame or time code is misnumbered.
 *
 * @author Brendon
 */
public class FrameMisnumberException extends BadCueSheetException {

  /** Problem description. */
  protected static final String PROBLEM = EDENExceptions.makeMisnumberProblem(
    "frame or time code"
  );
  /** Suggested remedy. */
  protected static final String REMEDY = makeMisnumberRemedy(
    "it",
    Index.MIN_FRAME,
    Index.MAX_FRAME
  );

  /** Makes an instance with the given track and index numbers. */
  public FrameMisnumberException(int track, int index) {
    super(track, index, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given track, index, and line numbers. */
  public FrameMisnumberException(int track, int index, long line) {
    super(track, index, line, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected FrameMisnumberException() {}
}
