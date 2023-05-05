package eden.common.excep.cd;

import eden.common.model.cd.CDLayoutObject;

/**
 * Thrown when track flags are defined again when they have already been
 * defined.
 *
 * @author Brendon
 */
public class FlagsAgainException extends BadCueSheetException {

  /** Problem description. */
  protected static final String PROBLEM = makeAgainProblem(
    CDLayoutObject.FLAGS
  );
  /** Suggested remedy. */
  protected static final String REMEDY = makeAgainRemedy(CDLayoutObject.FLAGS);

  /** Makes an instance with the given track and line number. */
  public FlagsAgainException(int track, long line) {
    super(track, line, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given flags or track label. */
  public FlagsAgainException(String label) {
    super(label, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected FlagsAgainException() {}
}
