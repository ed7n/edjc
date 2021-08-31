package eden.common.excep.cd;

import eden.common.model.cd.CDLayoutObject;

/**
 * Thrown when a FILE statement is incomplete.
 *
 * @author Brendon
 */
public class FileIncompleteException extends BadCueSheetException {

  /** Problem description. */
  protected static final String PROBLEM
      = "The `" + CDLayoutObject.FILE + "` statement is incorrect.";

  /** Suggested remedy. */
  protected static final String REMEDY
      = "Correct the `" + CDLayoutObject.FILE
      + "` statement to have the path and type.";

  /** Makes an instance with the given track and index numbers. */
  public FileIncompleteException(int track, int index) {
    super(track, index, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given track, index, and line numbers. */
  public FileIncompleteException(int track, int index, long line) {
    super(track, index, line, PROBLEM, REMEDY);
  }

  /** Makes an instance with the given index label. */
  public FileIncompleteException(String index) {
    super(index, PROBLEM, REMEDY);
  }

  /** To prevent null instantiations of this class. */
  protected FileIncompleteException() {
  }
}
