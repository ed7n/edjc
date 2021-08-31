package eden.common.excep.cd;

import eden.common.model.cd.CDLayoutObject;

/**
 * Thrown when a track pregap is defined again when it has already been defined.
 *
 * @author Brendon
 */
public class PregapAgainException extends BadCueSheetException {

  /** Problem description. */
  protected static final String PROBLEM
      = makeAgainProblem(CDLayoutObject.PREGAP);

  /** Suggested remedy. */
  protected static final String REMEDY
      = makeAgainRemedy(CDLayoutObject.PREGAP);

  /** Makes an instance with the given track and line numbers. */
  public PregapAgainException(int track, long line) {
    super(track, line, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given pregap or track label. */
  public PregapAgainException(String label) {
    super(label, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected PregapAgainException() {
  }
}
