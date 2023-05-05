package eden.common.excep.cd;

import eden.common.model.cd.CDLayoutObject;

/**
 * Thrown when a track postgap is defined again when it has already been
 * defined.
 *
 * @author Brendon
 */
public class PostgapAgainException extends BadCueSheetException {

  /** Problem description. */
  protected static final String PROBLEM = makeAgainProblem(
    CDLayoutObject.POSTGAP
  );
  /** Suggested remedy. */
  protected static final String REMEDY = makeAgainRemedy(
    CDLayoutObject.POSTGAP
  );

  /** Makes an instance with the given track and line numbers. */
  public PostgapAgainException(int track, long line) {
    super(track, line, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given postgap or track label. */
  public PostgapAgainException(String label) {
    super(label, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected PostgapAgainException() {}
}
