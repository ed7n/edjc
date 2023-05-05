package eden.common.excep.cd;

import eden.common.model.cd.CDLayoutObject;

/**
 * Thrown when a session media catalog number is defined again when it has
 * already been defined.
 *
 * @author Brendon
 */
public class CatalogAgainException extends BadCueSheetException {

  /** Problem description. */
  protected static final String PROBLEM = makeAgainProblem(
    CDLayoutObject.CATALOG
  );
  /** Suggested remedy. */
  protected static final String REMEDY = makeAgainRemedy(
    CDLayoutObject.CATALOG
  );

  /** Makes an instance with the given line number. */
  public CatalogAgainException(long line) {
    super(line, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given catalog or session label. */
  public CatalogAgainException(String label) {
    super(label, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected CatalogAgainException() {}
}
